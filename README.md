#Overview
Sample Spring Boot apps using https://github.com/awslabs/djl that can answer COVID-19 related questions using BERT and detect COVID-19 using X-ray.

The project has three modules:
- djldemo-bert-mxnet using Apache MxNet based BERT to answer questions related to COVID-19
- djldemo-bert-pytorch using PyTorch based BERT to answer questions related to COVID-19
- djldemo-xray using Tensorflow (only on mac or windows) to diagnose COVID-19 using urls of X-ray images

# Build
## BERT modules
Build the BERT modules using the profile matching your OS: `win`, `mac` or `linux`. For example:
`mvnw compile -Pwin`

## X-ray module
The `djldemo-xray` module doesn't need any profile and can be built using `mvn compile`.

# Run
## BERT modules
In order to run the BERT modules, you can run them from the command line:
`mvnw spring-boot:run`

or from your IDE by running the main method in either the `BertMxNetApplication` or `BertPyTorchApplication` classes.

Then visit http://localhost:8080/index.html to ask questions on COVID-19. The app will answer them based on Wikipedia excerpt in the `src/main/resources/covid19.wiki` files.

## X-ray module
The `djldemo-xray` module is built based on https://github.com/aws-samples/djl-demo/tree/master/covid19-detection sample project and it needs the Tensorflow module to be downloaded first before it can be run. 

Run the following command:
```
mkdir models
cd models
curl https://djl-tensorflow-javacpp.s3.amazonaws.com/tensorflow-models/covid-19/saved_model.zip | jar xv
cd ..

./mvnw spring-boot:run -Dai.djl.repository.zoo.location=models/saved_model
```

Then visit http://localhost:8080/index.html to get diagnose on X-ray image URLs.
Sample images to use:
- [COVID-19 infected lungs](https://github.com/ieee8023/covid-chestxray-dataset/tree/master/images)
- [Normal lungs](https://www.kaggle.com/paultimothymooney/chest-xray-pneumonia)
