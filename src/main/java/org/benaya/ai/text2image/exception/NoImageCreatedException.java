package org.benaya.ai.text2image.exception;


public class NoImageCreatedException extends RuntimeException {

    private static final String message = "No image created";
    public NoImageCreatedException() {
        super(message);
    }


}
