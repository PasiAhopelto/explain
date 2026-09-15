package com.github.pasiahopelto;

import org.springframework.stereotype.Component;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.response.OllamaResult;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class FortuneExplainer {
	public String explain(String fortune) {
		String result = null;
        try {
            Ollama ollama = new Ollama("http://localhost:11434");
            ollama.setRequestTimeoutSeconds(100l);
	        OllamaGenerateRequest request = new OllamaGenerateRequest();
	        request.setModel("qwen3:1.7b");
	        request.setPrompt("Please explain this fortune: " + fortune);
	        OllamaResult ollamaResult = ollama.generate(request, null);
	        result = ollamaResult.getResponse();
		} catch (OllamaException e) {
			e.printStackTrace();
		}
		return result;
	}
}
