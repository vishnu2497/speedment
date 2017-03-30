/**
 *
 * Copyright (c) 2006-2017, Speedment, Inc. All Rights Reserved.
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
package com.speedment.common.logger.internal.formatter;

import com.speedment.common.logger.Level;
import com.speedment.common.logger.LoggerFormatter;
import java.time.Instant;
import static java.util.Objects.requireNonNull;

/**
 *
 * @author pemi
 */
public final class PlainFormatter implements LoggerFormatter {

    public static final int INSTANCE_LENGTH = "2017-03-29T21:55:39.172Z".length();

    @Override
    public String apply(Level level, String name, String message) {
        requireNonNull(level);
        requireNonNull(name);

        return new StringBuilder()
            .append(formatInstance(Instant.now().toString()))
            .append(" ")
            .append(level.toText())
            .append(" [")
            .append(Thread.currentThread().getName())
            .append("] (")
            .append(name)
            .append(") - ")
            .append(message)
            .toString();
    }

    protected String formatInstance(final String instantString) {
        if (instantString.length() == INSTANCE_LENGTH) {
            return instantString;
        }
        final int missingCharacters = INSTANCE_LENGTH - instantString.length();
        if (missingCharacters > 4) {
            // Huston, we've had a problem... Just return to Earth...
            return instantString;
        }
        final String padding = ".000".substring(4 - missingCharacters);
        return instantString.substring(0, instantString.length() - 1) + padding + "Z";
    }

}
