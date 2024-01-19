## Text2image ##
- a spring boot tool for generating images from text
- uses a ready-to-use onnx text-tokenizer model to tokenize the text
- then uses stable-diffusion model to create an image, utilizing the onnx runtime.
- for cpu usage, I've downgraded some of the params(like the number of steps, and image size) to make it faster.

### credit: I'd like to give credit to this [infoQ article](https://www.infoq.com/news/2023/12/stable-diffusion-in-java/?source=post_page-----32dc3a2d14fc--------------------------------) for the inspiration and system prep. ###

#### you can find a hint for the system prep in the article, but I'll also write it here: ####
- install [git-lfs](https://git-lfs.com/) if you don't have it already.
- clone the [stable-diffusion repo](https://huggingface.co/runwayml/stable-diffusion-v1-5), from huggingface, and go to the onnx branch.
- clone the [ONNXRuntime-Extensions] repo(https://github.com/microsoft/onnxruntime-extensions).
- install latest [cmake](https://cmake.org/download/) if you don't have it already.
- inside terminal(or gitbash for windows), go to the ONNXRuntime-Extensions repo, and run the following command:
```./build_lib.sh --config Release --update --build --parallel```
- get the relevant lib file(for windows, it's ortextensions.dll) and copy to the root of this project.

#### todo: ####
- put all the tedious system prep steps in a docker container - create a dockerfile
- add a vector database as a cache layer for the text tokens, and storage database for the images.
### how to use ###
- run the project, and go to ```localhost:8080/generate-sample```(or ```localhost:8080/generate-sample```) endpoint, and add the prompt query param.