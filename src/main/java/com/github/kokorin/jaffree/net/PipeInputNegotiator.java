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

package com.github.kokorin.jaffree.net;

import com.github.kokorin.jaffree.util.IOUtil;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

/**
 * {@link TcpNegotiator} implementation which copies bytes from {@link InputStream}
 * to {@link Socket}.
 */
public class PipeInputNegotiator implements TcpNegotiator {
    private final InputStream source;
    private final int bufferSize;

    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Creates {@link PipeInputNegotiator}.
     *
     * @param source     input stream to copy from
     * @param bufferSize buffer size to copy data
     */
    public PipeInputNegotiator(final InputStream source, final int bufferSize) {
        this.source = source;
        this.bufferSize = bufferSize;
    }

    /**
     * Copies bytes from {@link #source} to socket {@link OutputStream}.
     *
     * @param socket TCP socket
     * @throws IOException if any IO error
     */
    @Override
    public void negotiate(final Socket socket) throws IOException {
        try (OutputStream destination = socket.getOutputStream()) {
            IOUtil.copy(source, destination, bufferSize);
        } catch (SocketException e) {
            // Client (ffmpeg) has no way to notify server that no more data is needed.
            // It just closes TCP connection on its side.
            LOGGER.debug("Ignoring exception: " + e.getMessage());
        }
    }
}
