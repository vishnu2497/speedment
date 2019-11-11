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
open module com.speedment.runtime.field {
    exports com.speedment.runtime.field;
    exports com.speedment.runtime.field.collector;
    exports com.speedment.runtime.field.comparator;
    exports com.speedment.runtime.field.exception;
    exports com.speedment.runtime.field.expression;
    exports com.speedment.runtime.field.method;
    exports com.speedment.runtime.field.predicate;
    exports com.speedment.runtime.field.predicate.trait;
    exports com.speedment.runtime.field.trait;
    exports com.speedment.runtime.field.util;

    requires com.speedment.common.invariant;
    requires transitive com.speedment.common.tuple;
    requires transitive com.speedment.common.function;
    requires transitive com.speedment.common.annotation;

    requires transitive com.speedment.runtime.config;
    requires transitive com.speedment.runtime.compute;
    requires transitive com.speedment.runtime.typemapper;
}
