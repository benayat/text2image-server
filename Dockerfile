FROM openjdk:21-slim-bookworm
RUN apt update && apt install -y git git-lfs cmake
RUN git clone https://huggingface.co/runwayml/stable-diffusion-v1-5 -b onnx
RUN git clone https://github.com/microsoft/onnxruntime-extensions
WORKDIR onnxruntime-extensions
RUN apt install -y python3 python3-pip
RUN chmod +x build_lib.sh && ./build_lib.sh --config Release --update --build --parallel
RUN mkdir -p /home/app  && cp -rl build/Linux/Release/lib/libortextensions.so /home/app
WORKDIR /home/app
RUN chmod +x libortextensions.so
COPY target/text2image-1.0.0-SNAPSHOT.jar text2image.jar
COPY text_tokenizer text_tokenizer

ENTRYPOINT ["java","-jar","text2image.jar"]