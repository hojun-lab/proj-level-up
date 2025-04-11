package org.rojojun.levelupserver.common;

import jakarta.annotation.PostConstruct;
import net.bramp.ffmpeg.FFmpeg;
import net.bramp.ffmpeg.FFmpegExecutor;
import net.bramp.ffmpeg.FFprobe;
import net.bramp.ffmpeg.builder.FFmpegBuilder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class VideoEncoder {
    private final String FFMPEG = "/usr/bin/ffmpeg";
    private final String FFPROBE = "/usr/bin/ffmprobe";
    private final String VIDEO_OUTPUT_DIR = "/output";
    private final String THUMBNAIL_OUTPUT_DIR = "/output/thumbnail";
    private Path VIDEO_OUTPUT_PATH;
    private Path THUMBNAIL_OUTPUT_PATH;


    public String mediaEncoding(String url) {
        int coreCount = Runtime.getRuntime().availableProcessors();
        int threads = Math.max(1, coreCount - 2);

        try {
            FFmpeg ffmpeg = new FFmpeg(FFMPEG);
            FFprobe ffprobe = new FFprobe(FFPROBE);
            FFmpegBuilder builder = new FFmpegBuilder()
                    .setInput(url)
                    .overrideOutputFiles(true)
                    .addOutput(VIDEO_OUTPUT_DIR + "/" + UUID.randomUUID())
                    .setFormat("mp4")
                    .disableSubtitle()
                    .setVideoCodec("libx264")
                    .setVideoFrameRate(24, 1)
                    .setVideoResolution(600, 600)
                    .setStrict(FFmpegBuilder.Strict.STRICT)
//                    .setStartOffset()
//                    .addExtraArgs("-t", "5")
                    .addExtraArgs("-threads", String.valueOf(threads))
                    .addExtraArgs("-preset", "fast")
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
//            executor.createTwoPassJob(builder).run();
            executor.createJob(builder).run();

            return VIDEO_OUTPUT_DIR + "/" + UUID.randomUUID();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String getThumbnail(String inputVideoPath) {
        String filename = UUID.randomUUID() + ".jpg";

        try {
            FFmpeg ffmpeg = new FFmpeg(FFMPEG);
            FFprobe ffprobe = new FFprobe(FFPROBE);
            FFmpegBuilder builder = new FFmpegBuilder()
                    .addExtraArgs("-ss", String.format("%.3f", 1.0))
                    .setInput(inputVideoPath)
                    .overrideOutputFiles(true)
                    .addOutput(THUMBNAIL_OUTPUT_DIR + filename)
                    .setFrames(1)
                    .disableAudio()
                    .disableSubtitle()
                    .setVideoFilter(buildScaleFilter(600))
                    .addExtraArgs("q:v", "3")
                    .done();

            FFmpegExecutor executor = new FFmpegExecutor(ffmpeg, ffprobe);
            executor.createJob(builder).run();

            return THUMBNAIL_OUTPUT_DIR + filename;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String buildScaleFilter(int resolution) {
        String resolutionToString = String.valueOf(resolution);
        return String.format("scale=%s:%s", resolutionToString, resolutionToString);
    }

    @PostConstruct
    private void init() {
        try {
            this.THUMBNAIL_OUTPUT_PATH = Paths.get(THUMBNAIL_OUTPUT_DIR).toAbsolutePath().normalize();
            this.VIDEO_OUTPUT_PATH = Paths.get(VIDEO_OUTPUT_DIR).toAbsolutePath().normalize();
            Files.createDirectories(THUMBNAIL_OUTPUT_PATH);
            Files.createDirectories(VIDEO_OUTPUT_PATH);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
