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

- make UI better looking
- tell user about error
- show fortune and progress indicator while waiting for explanation
	- generate with thinking
	- streaming / async
	- show thinking's intermediate responses while answer is being generated
	- replace thinking with explanation once final response is available
- unit tests
