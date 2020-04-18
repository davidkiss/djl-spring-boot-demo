package com.kaviddiss.djldemo.config;

import ai.djl.Model;
import ai.djl.modality.Classifications;
import ai.djl.repository.zoo.Criteria;
import ai.djl.repository.zoo.ModelZoo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.awt.image.BufferedImage;

@Configuration
public class DjlConfig {
    @Bean
    public Model xrayModel(@Value("${models.xray.path}") String xrayModelPath) throws Exception {
        Criteria<BufferedImage, Classifications> criteria =
                Criteria.builder()
                        .setTypes(BufferedImage.class, Classifications.class)
                        .build();

        return ModelZoo.loadModel(criteria);
    }
}