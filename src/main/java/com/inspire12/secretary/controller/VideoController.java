package com.inspire12.secretary.controller;

import com.inspire12.secretary.service.VideoService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

//https://github.com/saravanastar/video-streaming
@RestController
@RequestMapping("/video")
public class VideoController {
    private final VideoService videoStreamService;

    public VideoController(VideoService videoStreamService) {
        this.videoStreamService = videoStreamService;
    }

    @GetMapping("/stream/{fileType}/{fileName}")
    public Mono<ResponseEntity<byte[]>> getStreamVideo(@RequestHeader(value = "Range", required = false) String httpRangeList,
                                                       @PathVariable("fileType") String fileType,
                                                       @PathVariable("fileName") String fileName) {
        return Mono.just(videoStreamService.prepareContent(fileName, fileType, httpRangeList));
    }
}

