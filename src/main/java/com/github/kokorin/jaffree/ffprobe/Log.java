/*
 *    Copyright 2018-2021 Denis Kokorin
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

package com.github.kokorin.jaffree.ffprobe;

import com.github.kokorin.jaffree.LogCategory;
import com.github.kokorin.jaffree.LogLevel;
import com.github.kokorin.jaffree.ffprobe.data.ProbeData;

/**
 * Logging information from the decoder about each frame.
 *
 * @see FFprobe#setShowLog(LogLevel)
 */
public class Log {
    private final ProbeData probeData;

    /**
     * Creates {@link Log} description based on provided ffprobe data.
     *
     * @param probeData ffprobe data
     */
    public Log(final ProbeData probeData) {
        this.probeData = probeData;
    }

    /**
     * Returns data section which holds all the data provided by ffprobe for
     * the current {@link Log}.
     * <p>
     * Use this method if you have to access properties which are not accessible through
     * other getters in this class.
     *
     * @return data section
     */
    public ProbeData getProbeData() {
        return probeData;
    }

    /**
     * @return context
     */
    public String getContext() {
        return probeData.getString("context");
    }

    /**
     * Return parsed {@link LogLevel}.
     *
     * @return LogLevel
     */
    public LogLevel getLevel() {
        return probeData.getLogLevel("level");
    }

    /**
     * Return parsed {@link LogCategory}.
     *
     * @return LogCategory
     */
    public LogCategory getCategory() {
        return probeData.getLogCategory("category");
    }

    /**
     * @return parent context
     */
    public String getParentContext() {
        return probeData.getString("parent_context");
    }

    /**
     * Returns parsed parent {@link LogCategory}.
     *
     * @return parent {@link LogCategory}
     */
    public LogCategory getParentCategory() {
        return probeData.getLogCategory("parent_category");
    }

    /**
     * @return message
     */
    public String getMessage() {
        return probeData.getString("message");
    }
}
