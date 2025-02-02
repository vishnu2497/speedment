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
package com.speedment.plugins.json;

import com.speedment.common.injector.InjectBundle;
import com.speedment.plugins.json.internal.JsonComponentImpl;
import com.speedment.plugins.json.provider.DelegateJsonComponent;

import java.util.stream.Stream;

/**
 *
 * @author  Emil Forslund
 * @since   3.0.1
 */
public final class JsonBundle implements InjectBundle {

    @Override
    public Stream<Class<?>> injectables() {
        return Stream.of(DelegateJsonComponent.class);
    }
}