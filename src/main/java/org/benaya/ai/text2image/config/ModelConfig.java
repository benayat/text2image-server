package org.benaya.ai.text2image.config;

import org.benaya.ai.text2image.sd4j.SD4J;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class ModelConfig {
    private static final String modelPathParamName = "--model-path";
    private static final String modelPath = "C:\\big-files\\test-sbai\\stable-diffusion-v1-5";
    private static final String executionProviderParamName = "--execution-provider";
    private static final String executionProvider = "CPU";
    private static final String[] args = List.of(modelPathParamName, modelPath, executionProviderParamName, executionProvider).toArray(new String[0]);

//      --execution-provider CPU"
    @Bean
    public SD4J.SD4JConfig sd4jConfig() {
        Optional<SD4J.SD4JConfig> config =  SD4J.SD4JConfig.parseArgs(args);
        if (config.isEmpty()) {
            System.out.println(SD4J.SD4JConfig.help());
            System.exit(1);
        }
        return config.get();
    }
    @Bean
    public SD4J sd4j() {
        return SD4J.factory(sd4jConfig());
    }
}
