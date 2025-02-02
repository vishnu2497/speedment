/*
 *
 * Copyright (c) 2006-2019, Speedment, Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); You may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at:
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package com.speedment.runtime.connector.sqlite.internal;

import com.speedment.common.injector.Injector;
import com.speedment.runtime.config.Dbms;
import com.speedment.runtime.config.DbmsUtil;
import com.speedment.runtime.config.Project;
import com.speedment.runtime.config.ProjectUtil;
import com.speedment.runtime.config.trait.HasIdUtil;
import com.speedment.runtime.config.trait.HasNameUtil;
import com.speedment.runtime.connector.sqlite.*;
import com.speedment.runtime.core.component.ProjectComponent;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.NoSuchElementException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import static com.speedment.common.mapbuilder.MapBuilder.mapBuilderTyped;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

/**
 * @author Emil Forslund
 * @since  3.1.10
 */
class SqliteMetadataHandlerTest {

    private final static String URL = "jdbc:sqlite::memory:";
    private final static String FILE = "/employees.sql";

    private Connection conn;
    private Injector injector;

    @BeforeEach
    void setUp() {
        try {
            conn = DriverManager.getConnection(URL);

            final ScriptRunner runner = new ScriptRunner(conn, false, true);
            try (final InputStream in = SqliteMetadataHandlerTest.class.getResourceAsStream(FILE);
                 final InputStreamReader reader = new InputStreamReader(in);
                 final BufferedReader buffered = new BufferedReader(reader)) {

                runner.runScript(buffered);

            } catch (final SQLException ex) {
                throw new RuntimeException(
                    "Error initializing SQLite-database.", ex);
            } catch (final IOException ex) {
                throw new RuntimeException("Error reading SQL-file.", ex);
            }

            try {
                injector = Injector.builder()
                    .withBundle(SqliteBundle.class)
                    .withComponent(MockProjectComponent.class)
                    .withComponent(MockDbmsHandlerComponent.class)
                    .withComponent(MockConnectionPoolComponent.class)
                    .withComponent(MockTransactionComponent.class)
                    .beforeInitialized(MockConnectionPoolComponent.class,
                        pool -> pool.setConnection(conn, URL)
                    )
                    .beforeInitialized(MockProjectComponent.class,
                        projects -> projects.setProject(Project.create(
                            mapBuilderTyped(String.class, Object.class)
                                .key(HasIdUtil.ID).value("test_project")
                                .key(HasNameUtil.NAME).value("test_project")
                                .key(ProjectUtil.DBMSES).value(singletonList(mapBuilderTyped(String.class, Object.class)
                                    .key(HasIdUtil.ID).value("test_dbms")
                                    .key(HasNameUtil.NAME).value("test_dbms")
                                    .key(DbmsUtil.CONNECTION_URL).value(URL)
                                    .key(DbmsUtil.TYPE_NAME).value(SqliteDbmsTypeImpl.SQLITE)
                                    .build()
                                ))
                                .build()
                        ))
                    ).build();
            } catch (final InstantiationException ex) {
                throw new RuntimeException(
                    "Error initializing the Injector.", ex);
            }
        } catch (final SQLException ex) {
            throw new RuntimeException(
                "Error connecting to in-memory SQLite-database.", ex);
        }
    }

    @AfterEach
    void tearDown() {
        if (conn != null) {
            try {
                conn.close();
            } catch (final Exception ex) {
                throw new RuntimeException(ex);
            } finally {
                conn = null;
            }
        }
    }

    @Test
    void readSchemaMetadata() {
        final SqliteMetadataHandler metadataHandler = injector.getOrThrow(SqliteMetadataHandler.class);
        final ProjectComponent projects = injector.getOrThrow(ProjectComponent.class);
        final Dbms dbms = projects.getProject().dbmses().findFirst().orElseThrow(NoSuchElementException::new);
        final CompletableFuture<Project> task = metadataHandler.readSchemaMetadata(dbms, new MockProgress(), s -> true);

        assertDoesNotThrow(() -> {
                final Project loaded = task.get(10, TimeUnit.SECONDS);
            }
        );

    }
}
