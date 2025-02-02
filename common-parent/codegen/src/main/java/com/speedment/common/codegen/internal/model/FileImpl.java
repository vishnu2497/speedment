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
package com.speedment.common.codegen.internal.model;

import com.speedment.common.codegen.internal.util.Copier;
import com.speedment.common.codegen.model.*;
import com.speedment.common.codegen.model.trait.HasCopy;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

/**
 * This is the default implementation of the {@link File} interface. This class
 * should not be instantiated directly. Instead you should call the
 * {@link File#of(java.lang.String)} method to get an instance. In that way, you
 * can later change the implementing class without modifying the using code.
 *
 * @author Emil Forslund
 * @see File
 */
public final class FileImpl implements File {

    private String name;
    private Javadoc doc;
    private LicenseTerm lic;
    private final List<Import> imports;
    private final List<ClassOrInterface<?>> classes;

    /**
     * Initializes this file using a name.
     * <p>
     * <b>Warning!</b> This class should not be instantiated directly but using
     * the {@link File#of(java.lang.String)} method!
     *
     * @param name the filename
     */
    public FileImpl(String name) {
        this.name = requireNonNull(name);
        this.doc = null;
        this.imports = new ArrayList<>();
        this.classes = new ArrayList<>();
    }

    /**
     * Copy constructor.
     *
     * @param prototype the prototype
     */
    protected FileImpl(File prototype) {
        this.name = requireNonNull(prototype).getName();
        this.doc = prototype.getJavadoc().map(Copier::copy).orElse(null);
        this.imports = Copier.copy(prototype.getImports());
        this.classes = Copier.copy(prototype.getClasses(), ClassOrInterface::copy);
    }

    @Override
    public File setName(String name) {
        this.name = requireNonNull(name);
        return this;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public File set(Javadoc doc) {
        this.doc = doc.setParent(this);
        return this;
    }

    @Override
    public File set(LicenseTerm doc) {
        this.lic = doc.setParent(this);
        return this;
    }

    @Override
    public Optional<Javadoc> getJavadoc() {
        return Optional.ofNullable(doc);
    }

    @Override
    public Optional<LicenseTerm> getLicenseTerm() {
        return Optional.ofNullable(lic);
    }

    @Override
    public List<Import> getImports() {
        return imports;
    }

    @Override
    public List<ClassOrInterface<?>> getClasses() {
        return classes;
    }

    @Override
    public FileImpl copy() {
        return new FileImpl(this);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        hash = 37 * hash + Objects.hashCode(this.doc);
        hash = 37 * hash + Objects.hashCode(this.imports);
        hash = 37 * hash + Objects.hashCode(this.classes);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final FileImpl other = (FileImpl) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.doc, other.doc)) {
            return false;
        }
        if (!Objects.equals(this.imports, other.imports)) {
            return false;
        }
        return Objects.equals(this.classes, other.classes);
    }

}
