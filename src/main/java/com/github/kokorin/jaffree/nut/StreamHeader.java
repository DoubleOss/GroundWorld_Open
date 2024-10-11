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

package com.github.kokorin.jaffree.nut;

import com.github.kokorin.jaffree.Rational;

import java.util.Arrays;
import java.util.Collections;
import java.util.Set;

/**
 * Nut format stream header.
 */
public class StreamHeader {

    public final int streamId;
    public final Type streamType;
    public final byte[] fourcc;
    public final int timeBaseId;
    public final int msbPtsShift;
    public final long maxPtsDistance;
    public final long decodeDelay;
    public final Set<Flag> flags;
    public final byte[] codecSpecificData;
    public final Video video;
    public final Audio audio;

    public StreamHeader(final int streamId, final Type streamType, final byte[] fourcc,
                        final int timeBaseId, final int msbPtsShift, final long maxPtsDistance,
                        final long decodeDelay, final Set<Flag> flags,
                        final byte[] codecSpecificData, final Video video, final Audio audio) {
        this.streamId = streamId;
        this.streamType = streamType;
        this.fourcc = fourcc;
        this.timeBaseId = timeBaseId;
        this.msbPtsShift = msbPtsShift;
        this.maxPtsDistance = maxPtsDistance;
        this.decodeDelay = decodeDelay;
        this.flags = flags;
        this.codecSpecificData = codecSpecificData;
        this.video = video;
        this.audio = audio;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return "StreamHeader{"
                + "streamId=" + streamId
                + ", streamType=" + streamType
                + ", fourcc=" + Arrays.toString(fourcc)
                + ", timeBaseId=" + timeBaseId
                + ", msbPtsShift=" + msbPtsShift
                + ", maxPtsDistance=" + maxPtsDistance
                + ", decodeDelay=" + decodeDelay
                + ", flags=" + flags
                + ", codecSpecificData=" + Arrays.toString(codecSpecificData)
                + ", video=" + video
                + ", audio=" + audio
                + '}';
    }

    /**
     * Video-related data for {@link StreamHeader}.
     */
    public static class Video {
        public final int width;
        public final int height;
        public final int sampleWidth;
        public final int sampleHeight;
        public final ColourspaceType type;

        public Video(final int width, final int height,
                     final int sampleWidth, final int sampleHeight,
                     final ColourspaceType type) {
            this.width = width;
            this.height = height;
            this.sampleWidth = sampleWidth;
            this.sampleHeight = sampleHeight;
            this.type = type;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "Video{"
                    + "width=" + width
                    + ", height=" + height
                    + ", sampleWidth=" + sampleWidth
                    + ", sampleHeight=" + sampleHeight
                    + ", type=" + type
                    + '}';
        }
    }

    /**
     * Audio-related data for {@link StreamHeader}.
     */
    public static class Audio {
        public final Rational sampleRate;
        public final int channelCount;

        public Audio(final Rational sampleRate, final int channelCount) {
            this.sampleRate = sampleRate;
            this.channelCount = channelCount;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            return "Audio{"
                    + "samplerate=" + sampleRate
                    + ", channelCount=" + channelCount
                    + '}';
        }
    }

    /**
     * AKA Stream Class.
     */
    public enum Type {
        VIDEO(0),
        AUDIO(1),
        SUBTITLES(2),
        USER_DATA(4);

        public final long code;

        Type(final long code) {
            this.code = code;
        }

        public static Type fromCode(final long code) {
            for (Type type : values()) {
                if (type.code == code) {
                    return type;
                }
            }

            return null;
        }
    }

    /**
     * NUT Stream bit flags.
     */
    public enum Flag {
        /**
         * indicates that the fps is fixed.
         */
        FIXED_FPS(1);

        private final long code;

        Flag(final long code) {
            this.code = code;
        }

        public static Set<Flag> fromBitCode(final long value) {
            if (value == FIXED_FPS.code) {
                return Collections.singleton(FIXED_FPS);
            }

            return Collections.emptySet();
        }

        public static long toBitCode(final Set<Flag> flags) {
            long result = 0;
            for (Flag flag : flags) {
                result += flag.code;
            }
            return result;
        }
    }

    /**
     * NUT Colorspace type.
     */
    public enum ColourspaceType {
        /*
     0    unknown
     1    ITU Rec 624 / ITU Rec 601 Y range: 16..235 Cb/Cr range: 16..240
     2    ITU Rec 709               Y range: 16..235 Cb/Cr range: 16..240
    17    ITU Rec 624 / ITU Rec 601 Y range:  0..255 Cb/Cr range:  0..255
    18    ITU Rec 709               Y range:  0..255 Cb/Cr range:  0..255
         */
        UNKNOWN(0),
        ITU_624(1),
        ITU_709(2),
        ITU_624_255(17),
        ITU_709_255(18);

        public final long code;

        ColourspaceType(final long code) {
            this.code = code;
        }

        public static ColourspaceType fromCode(final long code) {
            for (ColourspaceType type : values()) {
                if (code == type.code) {
                    return type;
                }
            }

            return null;
        }
    }
}
