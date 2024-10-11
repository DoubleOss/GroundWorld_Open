/*
 *    Copyright  2019-2021 Denis Kokorin
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

import com.github.kokorin.jaffree.process.ProcessHelper;

/**
 * Interface for any ffprobe input.
 */
public interface Input {
    /**
     * Path or URL to be analyzed by ffprobe.
     *
     * @return path or URL
     */
    String getUrl();

    /**
     * Helper {@link ProcessHelper} which should be ran in dedicated thread.
     *
     * @return null if no helper thread is needed, otherwise Runnable
     */
    ProcessHelper helperThread();
}
