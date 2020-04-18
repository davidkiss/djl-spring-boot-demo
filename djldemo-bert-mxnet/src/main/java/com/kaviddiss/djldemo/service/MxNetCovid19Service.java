package com.kaviddiss.djldemo.service;

import ai.djl.inference.Predictor;
import ai.djl.modality.nlp.qa.QAInput;
import ai.djl.translate.TranslateException;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.nio.charset.StandardCharsets;
import java.util.function.Supplier;

@Service
public class MxNetCovid19Service {

    @Resource
    private Supplier<Predictor<QAInput, String>> predictorProvider;
    private String text;

    @PostConstruct
    public void init() throws Exception {
        text = IOUtils.toString(getClass().getResourceAsStream("/covid19.wiki"), StandardCharsets.UTF_8);
    }

    public String ask(String question) {
        try (Predictor<QAInput, String> predictor = predictorProvider.get()){
            return predictor.predict(new QAInput(question, text, 384));
        } catch (TranslateException e) {
            throw new RuntimeException("Failed to find answer", e);
        }
    }
}
