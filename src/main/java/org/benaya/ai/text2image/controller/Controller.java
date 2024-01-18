package org.benaya.ai.text2image.controller;

import lombok.RequiredArgsConstructor;
import org.benaya.ai.text2image.service.GenerateImageService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class Controller {
    private final GenerateImageService generateImageService;
    @GetMapping(value = "/generate", produces = MediaType.IMAGE_PNG_VALUE)
    public byte[] generate(@RequestParam String prompt) {
        return generateImageService.generateImageBytes(prompt);
    }
}
