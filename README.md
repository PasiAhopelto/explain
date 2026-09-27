# Explain a fortune

Webapp that explains a fortune, and "of course it's a toy" for exploring local LLM use from Java and webapp implementation with htmx and Thymeleaf.

Gets a fortune with fortune command and gives it to a local LLM for analysis. Shows fortune and explanation to user.

# Environment

Install fortune and ollama with a local LLM. Even smallest qwen will do.

# Compile

```
mvn clean package

```

# Run

Start ollama and then do
```
java -jar target/explain-fortune-<VERSION>.jar
```

# Use

Open http://localhost:8080/fortune

# AI statement

This is self-written code with some advice from AI.

# TODO

- disable button while getting fortune and explanation
- convert ollama use to builder
- make UI better looking
- tell user about error
- check whether md to html can be used for fortunes
- allow limited prompt modification, eg select style from drowdown
- prevent repeated requests, eg allow single active request from a source with cooldown period
- check can ollama client use limits how many requests it will process at a time
- unit tests
