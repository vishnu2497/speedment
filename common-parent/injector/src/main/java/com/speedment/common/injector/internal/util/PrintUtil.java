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
package com.speedment.common.injector.internal.util;

/**
 *
 * @author Emil Forslund
 * @since  1.2.0
 */
public final class PrintUtil {

    public static String horizontalLine() {
        return "+---------------------------------------------------------------------------------+";
    }
    
    public static String limit(String in, int length) {
        if (in.length() < length) {
            return in;
        } else {
            final int breakpoint = (length - 3) / 2;
            return in.substring(0, breakpoint) + 
                "..." + in.substring(length - breakpoint - 3);
        }
    }
    
    private PrintUtil() {}
}