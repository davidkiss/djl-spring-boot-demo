package com.kaviddiss.djldemo.service;

import ai.djl.Model;
import ai.djl.inference.Predictor;
import ai.djl.modality.Classifications;
import ai.djl.modality.cv.util.BufferedImageUtils;
import ai.djl.modality.cv.util.NDImageUtils;
import ai.djl.modality.nlp.qa.QAInput;
import ai.djl.ndarray.NDArray;
import ai.djl.ndarray.NDList;
import ai.djl.translate.TranslateException;
import ai.djl.translate.Translator;
import ai.djl.translate.TranslatorContext;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.awt.image.BufferedImage;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

@Service
public class Covid19Service {

    private static final class XrayTranslator implements Translator<BufferedImage, Classifications> {

        private List<String> classes;

        public XrayTranslator() {
            classes = Arrays.asList("covid-19", "normal");
        }

        @Override
        public NDList processInput(TranslatorContext ctx, BufferedImage input) {
            NDArray array =
                    BufferedImageUtils.toNDArray(
                            ctx.getNDManager(), input, NDImageUtils.Flag.COLOR);
            array = NDImageUtils.resize(array, 224).div(255.0f);
            return new NDList(array);
        }

        @Override
        public Classifications processOutput(TranslatorContext ctx, NDList list) {
            NDArray probabilities = list.singletonOrThrow();
            return new Classifications(classes, probabilities);
        }
    }

    @Autowired
    private Model xrayModel;
    @Resource
    private Supplier<Predictor<QAInput, String>> predictorProvider;
    private String text;

    @PostConstruct
    public void init() throws Exception {
        text = IOUtils.toString(getClass().getResourceAsStream("/covid19.wiki"), StandardCharsets.UTF_8);
    }

    public String ask(String question) {
        try (Predictor<QAInput, String> predictor = predictorProvider.get()){
            return predictor.predict(new QAInput(question, text));
        } catch (TranslateException e) {
            throw new RuntimeException("Failed to find answer", e);
        }
    }

    public String diagnose(String imageUrl) {
        try (Predictor<BufferedImage, Classifications> predictor = xrayModel.newPredictor(new XrayTranslator())) {
            Classifications result = predictor.predict(BufferedImageUtils.fromUrl(imageUrl));
            return "Diagnose: "
                    + result.best().getClassName()
                    + " , probability: "
                    + result.best().getProbability();
        } catch (Exception e) {
            throw new RuntimeException("Failed to diagnose", e);
        }
    }
}
