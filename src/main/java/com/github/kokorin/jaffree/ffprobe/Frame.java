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

import com.github.kokorin.jaffree.Rational;
import com.github.kokorin.jaffree.StreamType;
import com.github.kokorin.jaffree.ffprobe.data.ProbeData;

import java.util.List;

/**
 * Frame description.
 *
 * @see FFprobe#setShowFrames(boolean)
 */
public class Frame implements TagAware, FrameSubtitle, PacketFrameSubtitle {
    private final ProbeData probeData;

    /**
     * Creates {@link Frame} description based on provided data sections.
     *
     * @param probeData data section
     */
    public Frame(final ProbeData probeData) {
        this.probeData = probeData;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ProbeData getProbeData() {
        return probeData;
    }

    /**
     * Returns logging information from the decoder about each frame according to the value
     * set in loglevel.
     *
     * @return logs
     * @see FFprobe#setShowLog(com.github.kokorin.jaffree.LogLevel)
     */
    public List<Log> getLogs() {
        return probeData.getSubDataList("logs", Log::new);
    }

    /**
     * Returns additional frame data that can be provided by the container.
     * Frame can contain several types of side information.
     *
     * @return side data for the frame
     */
    public List<SideData> getSideDataList() {
        return probeData.getSubDataList("side_data_list", SideData::new);
    }

    /**
     * Presentation timestamp in timebase units (time when frame should be shown to user).
     * <p>
     * <b>Note</b>: despite declared in
     * <a href="https://github.com/FFmpeg/FFmpeg/blob/master/doc/ffprobe.xsd">ffprobe.xsd</a>
     * for ffprobe:frameType, this property has never been reported by ffprobe.
     *
     * @return pts
     * @see #getPktPts()
     */
    public Long getPts() {
        return probeData.getLong("pts");
    }

    /**
     * Presentation timestamp in seconds (time when frame should be shown to user).
     * <p>
     * <b>Note</b>: despite declared in
     * <a href="https://github.com/FFmpeg/FFmpeg/blob/master/doc/ffprobe.xsd">ffprobe.xsd</a>
     * for ffprobe:frameType, this property has never been reported by ffprobe.
     *
     * @return pts in seconds
     * @see #getPktPtsTime()
     */
    public Float getPtsTime() {
        return probeData.getFloat("pts_time");
    }

    /**
     * @return media type
     */
    public StreamType getMediaType() {
        return probeData.getStreamType("media_type");
    }

    /**
     * @return corresponding stream id
     */
    public Integer getStreamIndex() {
        return probeData.getInteger("stream_index");
    }

    /**
     * @return true if key frame
     */
    public Boolean getKeyFrame() {
        return probeData.getBoolean("key_frame");
    }

    /**
     * PTS in time_base units copied from the AVPacket that was decoded to produce this frame.
     *
     * @return packet pts
     */
    public Long getPktPts() {
        return probeData.getLong("pkt_pts");
    }

    /**
     * PTS in seconds copied from the AVPacket that was decoded to produce this frame.
     *
     * @return packet pts
     */
    public Float getPktPtsTime() {
        return probeData.getFloat("pkt_pts_time");
    }

    /**
     * DTS in time_base units copied from the AVPacket. (if frame threading isn't used)
     * This is also the Presentation time of this AVFrame calculated from
     * only AVPacket.dts values without pts values.
     *
     * @return packet DTS
     */
    public Long getPktDts() {
        return probeData.getLong("pkt_dts");
    }

    /**
     * DTS in seconds copied from the AVPacket. (if frame threading isn't used)
     * This is also the Presentation time of this AVFrame calculated from
     * only AVPacket.dts values without pts values.
     *
     * @return packet DTS time
     */
    public Float getPktDtsTime() {
        return probeData.getFloat("pkt_dts_time");
    }

    /**
     * Frame timestamp in stream time_base units, estimated using various heuristics.
     * <ul>
     * <li>encoding: unused</li>
     * <li>decoding: set by libavcodec, read by user</li>
     * </ul>
     *
     * @return best effort PTS
     */
    public Long getBestEffortTimestamp() {
        return probeData.getLong("best_effort_timestamp");
    }

    /**
     * Frame timestamp in seconds, estimated using various heuristics.
     * <ul>
     * <li>encoding: unused</li>
     * <li>decoding: set by libavcodec, read by user</li>
     * </ul>
     *
     * @return best effort time
     */
    public Float getBestEffortTimestampTime() {
        return probeData.getFloat("best_effort_timestamp_time");
    }

    /**
     * Duration of the corresponding packet in stream time_base units, 0 if unknown.
     *
     * @return packet duration
     */
    public Long getPktDuration() {
        return probeData.getLong("pkt_duration");
    }

    /**
     * Duration of the corresponding packet in seconds, 0 if unknown.
     *
     * @return packet duration
     */
    public Float getPktDurationTime() {
        return probeData.getFloat("pkt_duration_time");
    }

    /**
     * Reordered pos from the last AVPacket that has been input into the decoder.
     *
     * @return packet position
     */
    public Long getPktPos() {
        return probeData.getLong("pkt_pos");
    }

    /**
     * Size of the corresponding packet containing the compressed frame.
     * <p>
     * It is set to a negative value if unknown.
     *
     * @return packet size
     */
    public Integer getPktSize() {
        return probeData.getInteger("pkt_size");
    }

    /**
     * @return audio samples format
     */
    public String getSampleFmt() {
        return probeData.getString("sample_fmt");
    }

    /**
     * @return number of audio sample in a single channel
     */
    public Long getNbSamples() {
        return probeData.getLong("nb_samples");
    }

    /**
     * @return number of channels
     */
    public Integer getChannels() {
        return probeData.getInteger("channels");
    }

    /**
     * @return channels layout
     */
    public String getChannelLayout() {
        return probeData.getString("channel_layout");
    }

    /**
     * @return video frame width
     */
    public Long getWidth() {
        return probeData.getLong("width");
    }

    /**
     * @return video frame height
     */
    public Long getHeight() {
        return probeData.getLong("height");
    }

    /**
     * @return video frame pixel format
     */
    public String getPixFmt() {
        return probeData.getString("pix_fmt");
    }

    /**
     * Return sample aspect ratio for the video frame, 0/1 if unknown/unspecified.
     *
     * @return aspect ration
     */
    public Rational getSampleAspectRatio() {
        return probeData.getRatio("sample_aspect_ratio");
    }

    /**
     * Possible return values:
     * <ul>
     *     <li>I -&gt; Intra</li>
     *     <li>P -&gt; Predicted</li>
     *     <li>B -&gt; Bi-dir predicted</li>
     *     <li>S -&gt; S(GMC)-VOP MPEG-4</li>
     *     <li>i -&gt; Switching Intra</li>
     *     <li>p -&gt; Switching Predicted</li>
     *     <li>b -&gt; BI type</li>
     *     <li>? -&gt; unknown/undefined</li>
     * </ul>
     *
     * @return picture type of the frame
     * @see <a href="https://github.com/FFmpeg/FFmpeg/blob/master/libavutil/avutil.h#L272">
     *     enum AVPictureType</a>
     * @see <a href="https://github.com/FFmpeg/FFmpeg/blob/master/libavutil/utils.c#L88">
     *     char av_get_picture_type_char(enum AVPictureType pict_type)</a>
     */
    public String getPictType() {
        return probeData.getString("pict_type");
    }

    /**
     * @return picture number in bitstream order
     */
    public Long getCodedPictureNumber() {
        return probeData.getLong("coded_picture_number");
    }

    /**
     * @return picture number in display order
     */
    public Long getDisplayPictureNumber() {
        return probeData.getLong("display_picture_number");
    }

    /**
     * The content of the picture is interlaced.
     *
     * @return true if interlaced
     */
    public Boolean getInterlacedFrame() {
        return probeData.getBoolean("interlaced_frame");
    }

    /**
     * If the content is interlaced, is top field displayed first.
     *
     * @return true if top field is displayed first
     */
    public Boolean getTopFieldFirst() {
        return probeData.getBoolean("top_field_first");
    }

    /**
     * When decoding, this signals how much the picture must be delayed.
     * <p>
     * extra_delay = repeat_pict / (2*fps)
     *
     * @return picture extra delay
     */
    public Integer getRepeatPict() {
        return probeData.getInteger("repeat_pict");
    }
}
