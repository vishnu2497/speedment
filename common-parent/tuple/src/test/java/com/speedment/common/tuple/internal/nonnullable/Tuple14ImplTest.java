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
package com.speedment.common.tuple.internal.nonnullable;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

final class Tuple14ImplTest<T0, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13> extends AbstractTupleImplTest<Tuple14Impl<Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer, Integer>> {
    
    Tuple14ImplTest() {
        super(() -> new Tuple14Impl<>(0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13), 14);
    }
    
    @Test
    void get0Test() {
        assertEquals(0, (int) instance.get0());
    }
    
    @Test
    void get1Test() {
        assertEquals(1, (int) instance.get1());
    }
    
    @Test
    void get2Test() {
        assertEquals(2, (int) instance.get2());
    }
    
    @Test
    void get3Test() {
        assertEquals(3, (int) instance.get3());
    }
    
    @Test
    void get4Test() {
        assertEquals(4, (int) instance.get4());
    }
    
    @Test
    void get5Test() {
        assertEquals(5, (int) instance.get5());
    }
    
    @Test
    void get6Test() {
        assertEquals(6, (int) instance.get6());
    }
    
    @Test
    void get7Test() {
        assertEquals(7, (int) instance.get7());
    }
    
    @Test
    void get8Test() {
        assertEquals(8, (int) instance.get8());
    }
    
    @Test
    void get9Test() {
        assertEquals(9, (int) instance.get9());
    }
    
    @Test
    void get10Test() {
        assertEquals(10, (int) instance.get10());
    }
    
    @Test
    void get11Test() {
        assertEquals(11, (int) instance.get11());
    }
    
    @Test
    void get12Test() {
        assertEquals(12, (int) instance.get12());
    }
    
    @Test
    void get13Test() {
        assertEquals(13, (int) instance.get13());
    }
}