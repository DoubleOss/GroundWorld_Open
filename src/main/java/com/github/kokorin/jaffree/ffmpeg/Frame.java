/*
 *    Copyright  2017 Denis Kokorin
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

package com.github.kokorin.jaffree.ffmpeg;

/**
 * Represents video/audio data to be encoded or has been decoded.
 *
 * <b>Note</b>: image and samples must not be changed after creating Frame instance.
 * Otherwise it may affect (or event corrupt) produced media, because Jaffree internally
 * maintains frame reordering buffer while producing video.
 *
 * @see FrameInput
 * @see FrameOutput
 */
public class Frame {
    private final int streamId;
    private final long pts;
    private final byte[] data;
    private final byte[] samples;

    /**
     * Creates {@link Frame}.
     *
     * @param streamId streamId
     * @param pts      pts in {@link Stream} timebase
     * @param image    video frame image
     * @param samples  audio samples in PCM S16BE format
     * @see Stream#getTimebase()
     */
    protected Frame(final int streamId, final long pts, final byte[] data,
                    final byte[] samples) {
        if (data != null && samples != null) {
            throw new IllegalArgumentException(
                    "Only one of data and samples parameters may be non null");
        }
        if (data == null && samples == null) {
            throw new IllegalArgumentException(
                    "One of data and samples parameters must be non null");
        }

        this.streamId = streamId;
        this.pts = pts;
        this.data = data;
        this.samples = samples;
    }

    /**
     * @return stream id (starting with 0)
     */
    public int getStreamId() {
        return streamId;
    }

    /**
     * PTS in corresponding {@link Stream} timebase.
     * E.g. Track's timebase is 44100 (audio track), timecode is 4410 - it means 0.1 second
     * (100 milliseconds)
     *
     * @return timecode
     * @see Stream#getTimebase()
     */
    public long getPts() {
        return pts;
    }

    /**
     * Returns video frame data (or null if current frame isn't video frame).
     *
     * @return video frame data
     */
    public byte[] getData() {
        return data;
    }

    /**
     * Returns audio samples (or null if current frame isn't audio frame).
     *
     * @return audio samples in PCM S16BE format
     */
    public byte[] getSamples() {
        return samples;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "Frame{"
                + "streamId=" + streamId
                + ", pts=" + pts
                + ", data?=" + (data != null)
                + ", samples?=" + (samples != null)
                + '}';
    }

    /**
     * Creates video {@link Frame}, samples are set to null.
     *
     * @param streamId stream id (starting with 0)
     * @param pts      pts in {@link Stream} timebase
     * @param image    video frame image
     * @return video {@link Frame}
     * @see Stream#getTimebase()
     */
    public static Frame createVideoFrame(final int streamId, final long pts,
                                         final byte[] data) {
        return new Frame(streamId, pts, data, null);
    }

    /**
     * Creates audio {@link Frame}, image is set to null.
     *
     * @param streamId streamId
     * @param pts      pts in {@link Stream} timebase
     * @param samples  audio samples in PCM S16BE format
     * @return audio {@link Frame}
     * @see Stream#getTimebase()
     */
    public static Frame createAudioFrame(final int streamId, final long pts, final byte[] samples) {
        return new Frame(streamId, pts, null, samples);
    }

}
