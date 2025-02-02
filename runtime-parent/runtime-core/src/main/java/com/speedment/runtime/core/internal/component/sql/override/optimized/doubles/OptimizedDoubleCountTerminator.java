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
package com.speedment.runtime.core.internal.component.sql.override.optimized.doubles;

import com.speedment.runtime.core.component.sql.SqlStreamOptimizerInfo;
import com.speedment.runtime.core.component.sql.override.doubles.DoubleCountTerminator;
import static com.speedment.runtime.core.internal.component.sql.override.optimized.util.CountUtil.countHelper;
import com.speedment.runtime.core.internal.manager.sql.SqlStreamTerminator;
import com.speedment.runtime.core.internal.stream.builder.pipeline.DoublePipeline;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author Per Minborg
 * @param <ENTITY> the original stream entity source type 
 */
public final class OptimizedDoubleCountTerminator<ENTITY> implements DoubleCountTerminator<ENTITY> {

    private OptimizedDoubleCountTerminator() {
    }

    @Override
    public <T> long apply(
        final SqlStreamOptimizerInfo<ENTITY> info,
        final SqlStreamTerminator<ENTITY> sqlStreamTerminator,
        final DoublePipeline pipeline
    ) {
        requireNonNull(info);
        requireNonNull(sqlStreamTerminator);
        requireNonNull(pipeline);
        return countHelper(
            info,
            sqlStreamTerminator,
            pipeline,
            () -> DoubleCountTerminator.<ENTITY>defaultTerminator().apply(info, sqlStreamTerminator, pipeline)
        );
    }

    private static final DoubleCountTerminator<?> INSTANCE = new OptimizedDoubleCountTerminator<>();

    @SuppressWarnings("unchecked")
    public static <ENTITY> DoubleCountTerminator<ENTITY> create() {
        return (DoubleCountTerminator<ENTITY>) INSTANCE;
    }

}
