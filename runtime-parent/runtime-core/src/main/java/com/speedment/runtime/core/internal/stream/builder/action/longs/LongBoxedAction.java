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
package com.speedment.runtime.core.internal.stream.builder.action.longs;

import com.speedment.runtime.core.stream.action.Action;

import java.util.stream.LongStream;
import java.util.stream.Stream;

import static com.speedment.runtime.core.internal.stream.builder.action.StandardBasicAction.BOXED;

/**
 *
 * @author pemi
 */
public final class LongBoxedAction extends Action<LongStream, Stream<Long>> {

    public LongBoxedAction() {
        super(s -> s.boxed(), Stream.class, BOXED);
    }

}
