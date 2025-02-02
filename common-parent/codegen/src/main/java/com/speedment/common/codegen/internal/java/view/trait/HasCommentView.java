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
package com.speedment.common.codegen.internal.java.view.trait;

import com.speedment.common.codegen.Generator;
import com.speedment.common.codegen.Transform;
import com.speedment.common.codegen.model.trait.HasComment;

import java.util.stream.Stream;

import static com.speedment.common.codegen.util.Formatting.nl;
import static java.util.stream.Collectors.joining;

/**
 * A trait with the functionality to render models with the trait 
 * {@link HasComment}.
 * 
 * @author     Emil Forslund
 * @param <M>  The model type
 * @see        Transform
 */
public interface HasCommentView<M extends HasComment<M>> extends Transform<M, String> {
    
    /**
     * Render the comment-part of the model, prepending each row with '//'.
     * 
     * @param gen    the generator
     * @param model  the model
     * @return       the generated code
     */
    default String renderComment(Generator gen, M model) {
        return model.getComment().map(comment -> 
            Stream.of(comment.split(nl()))
                .map(row -> "// " + row)
                .collect(joining(nl()))
        ).orElse("");
    }
}