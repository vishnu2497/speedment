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
package com.speedment.common.tuple;

import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * @author Emil Forslund
 * @since  1.0.5
 */
final class TuplesTest {

    private static final int SIZE = 100;

    /**
     * Attempts to recreate issue #500 (initializing a very large tuple caused
     * a NullPointerException since the constructor implementation of internal
     * abstract base class {@code BasicAbstractTuple} accessed the
     * {@link Tuple#degree()}-method before the inner array was initialized.)
     */
    @Test
    void of() {
        final Random random = new Random();
        final Integer[] array = new Integer[SIZE];
        for (int i = 0; i < SIZE; i++) {
            array[i] = random.nextInt();
        }

        final Tuple tuple = Tuples.ofArray((Object[]) array);
        assertEquals(SIZE, tuple.degree());

        for (int i = 0; i < SIZE; i++) {
            final Integer expected = array[i];
            final Integer actual = (Integer) tuple.get(i);
            assertEquals(expected, actual);
        }
    }
}