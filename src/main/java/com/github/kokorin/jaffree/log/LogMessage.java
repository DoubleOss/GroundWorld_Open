/*
 *    Copyright 2021 Denis Kokorin
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 *
 */

package com.github.kokorin.jaffree.log;

import com.github.kokorin.jaffree.LogLevel;

/**
 * ffprobe/ffmpeg log message with log level.
 */
public class LogMessage {
    public final LogLevel logLevel;
    public final String message;

    /**
     * Creates {@link LogMessage}.
     *
     * @param logLevel log level
     * @param message  message
     */
    public LogMessage(final LogLevel logLevel, final String message) {
        this.logLevel = logLevel;
        this.message = message;
    }
}
