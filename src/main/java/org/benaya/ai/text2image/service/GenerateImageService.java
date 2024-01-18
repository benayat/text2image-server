package org.benaya.ai.text2image.service;

import lombok.RequiredArgsConstructor;
import org.benaya.ai.text2image.exception.NoImageCreatedException;
import org.benaya.ai.text2image.sd4j.SD4J;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Base64;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GenerateImageService {
    private final SD4J sd4j;
    @Value("${text2image.high-quality.inference-steps}")
    private int highQualityInferenceSteps;
    @Value("${text2image.low-quality.inference-steps}")
    private int lowQualityInferenceSteps = 4;
    @Value("${text2image.negative-text}")
    private String negativeText;
    @Value("${text2image.high-quality.image-size}")
    private int highQualityImageSize;
    @Value("${text2image.low-quality.image-size}")
    private int lowQualityImageSize;
    @Value("${text2image.guide-strength}")
    private float guideStrength;
    @Value("${text2image.batch-size}")
    private int batchSize;
    @Value("${text2image.rng-seed}")
    private int RngSeed;

    public byte[] generateLowQualityImageBytes(String prompt) {
        BufferedImage image = generateImage(prompt, lowQualityInferenceSteps, lowQualityImageSize);
        return getByteArrayRepOfImage(image);
    }

    public byte[] generateHighQualityImageBytes(String prompt) {
        BufferedImage image = generateImage(prompt, highQualityInferenceSteps, highQualityImageSize);
        return getByteArrayRepOfImage(image);
    }

    private BufferedImage generateImage(String prompt, int inferenceSteps, int imageSize) {
        List<SD4J.SDImage> imageList = sd4j.generateImage(inferenceSteps, prompt, negativeText, guideStrength, batchSize, getImageSize(imageSize), RngSeed);
        if (imageList.isEmpty()) {
            throw new NoImageCreatedException();
        }
        return imageList.getFirst().image();
    }

    private String getStringRepOfImage(BufferedImage image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private SD4J.ImageSize getImageSize(int size) {
        return new SD4J.ImageSize(size, size);
    }

    private byte[] getByteArrayRepOfImage(BufferedImage image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
            return outputStream.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
