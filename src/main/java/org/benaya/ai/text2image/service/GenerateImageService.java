package org.benaya.ai.text2image.service;

import lombok.RequiredArgsConstructor;
import org.benaya.ai.text2image.exception.NoImageCreatedException;
import org.benaya.ai.text2image.sd4j.SD4J;
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
    private final int defaultNumInferenceSteps = 50;
    private final int defaultSimpleNumInferenceSteps = 4;
    private final String emptyNegativeText = "";
    private final float defaultGuideStrength = 10f;
    private final int batchSize = 1;
    private final int defaultImageSize = 512;
    private final int defaultRngSeed = 42;

    public byte[] generateImageBytes(String prompt) {
        SD4J.ImageSize imageSize = new SD4J.ImageSize(defaultImageSize, defaultImageSize);
        List<SD4J.SDImage> imageList = sd4j.generateImage(defaultSimpleNumInferenceSteps, prompt, emptyNegativeText, defaultGuideStrength, batchSize, imageSize, defaultRngSeed);
        if (imageList.isEmpty()) {
            throw new NoImageCreatedException();
        }
        return getByteArrayRepOfImage(imageList.getFirst().image());
    }
//    public byte[] generateMultipleImagesBytes(String prompt, int numImages) {
// implement this
//    }
    private String getStringRepOfImage(BufferedImage image) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            ImageIO.write(image, "png", outputStream);
            return Base64.getEncoder().encodeToString(outputStream.toByteArray());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
