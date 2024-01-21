## Text2image ##
- a spring boot tool for generating images from text, based on the new oracle stable-diffusion [java implementation](https://github.com/oracle/sd4j).
- uses a ready-to-use onnx text-tokenizer model to tokenize the text
- then uses stable-diffusion model to create an image, utilizing the onnx runtime.
- for cpu usage, I've downgraded some of the params(like the number of steps, and image size) to make it faster.

### credit: I'd like to give credit to this [infoQ article](https://www.infoq.com/news/2023/12/stable-diffusion-in-java/?source=post_page-----32dc3a2d14fc--------------------------------) for the inspiration and system prep. ###
##### Note: if you run with docker, you don't need to do the system prep. #####

#### System prep: ####
- install [git-lfs](https://git-lfs.com/) if you don't have it already.
- clone the [stable-diffusion repo](https://huggingface.co/runwayml/stable-diffusion-v1-5), from huggingface, and go to the onnx branch.
- clone the [ONNXRuntime-Extensions] repo(https://github.com/microsoft/onnxruntime-extensions).
- install latest [cmake](https://cmake.org/download/) if you don't have it already.
- inside terminal(or gitbash for windows), go to the ONNXRuntime-Extensions repo, and run the following command:
```./build_lib.sh --config Release --update --build --parallel```
- get the relevant lib file(for windows, it's ortextensions.dll) and copy to the root of this project.

#### Todo: ####
- check an option for quzntized model, if any is comming soon - could make great difference in speed for cpu systems.
### Run - docker ###
- make sure you have docker installed
- clone the repo
- run ```docker build -t <choose image tag:version> .```
- run ```docker run -p 8080:8080 <image tag:version>`
- send GET request to ```localhost:8080/generate-sample```(or ```localhost:8080/generate-hq```) endpoint, and add the prompt query param.

### Run - local ###
- run the system prep part, according to the instructions above, and your OS and cpu type. 
- clone the repo
- run ```mvn clean package```
- run ```java -jar target/text2image.jar```

#### NOTES: ####
- The docker image size will go up to 15GB, because of the stable diffusion model, and the ONNXRuntime-Extensions repo, and that's only a POC.
- Despite the image size, running on docker is preferred, because of the system prep, and the need to customize the lib/so/dll file for your system, which is not needed on docker.
- You may change the env variables mentioned in application.yaml file, to customize the image generation, like the provider(cpu/gpu), number of steps, image size, and more.