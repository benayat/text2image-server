package org.benaya.ai.text2image.config;

import org.benaya.ai.text2image.sd4j.SD4J;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;
import java.util.Optional;

@Configuration
public class Sd4jModelConfig {
    @Value("${text2image.model-path.param-name}")
    private String modelPathParamName;
    @Value("${text2image.model-path.value}")
    private String modelPath;
    @Value("${text2image.execution-provider.param-name}")
    private String executionProviderParamName;
    @Value("${text2image.execution-provider.value}")
    private String executionProvider;

    @Bean
    public SD4J.SD4JConfig sd4jConfig() {
        String[] args = List.of(modelPathParamName, modelPath, executionProviderParamName, executionProvider).toArray(new String[0]);
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
