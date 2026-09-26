# Explain a fortune

Webapp that explains a fortune, and "of course it's a toy" for exploring local LLM use from Java and webapp implementation with htmx and Thymeleaf.

Gets a fortune with fortune command and gives it to a local LLM for analysis. Shows fortune and explanation to user.

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

Self-written code with some advice from AI.

# TODO

- code cleanup
- show fortune and progress indicator while waiting for explanation
- tell user about error
- make UI better looking
- unit tests
