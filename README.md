## Text2image
- a spring boot tool for generating images from text
- uses a ready-to-use onnx text-tokenizer model to tokenize the text
- then uses stable-diffusion model to create an image, utilizing the onnx runtime.
- for cpu usage, I've downgraded some of the params(like the number of steps, and image size) to make it faster.
