package com.github.pasiahopelto;

import org.springframework.stereotype.Component;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.generate.OllamaGenerateRequest;
import io.github.ollama4j.models.request.ThinkMode;
import io.github.ollama4j.models.response.OllamaResult;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class FortuneExplainer {
	public String explain(String fortune) {
		String result = null;
        try {
            Ollama ollama = new Ollama("http://localhost:11434");
            ollama.setRequestTimeoutSeconds(100l);
	        OllamaGenerateRequest request = new OllamaGenerateRequest();
	        request.setModel("qwen3:1.7b");
	        request.setThink(ThinkMode.HIGH);
	        request.setPrompt("Please explain this fortune: " + fortune);
	        OllamaResult ollamaResult = ollama.generate(request, null);
	        log.info(ollamaResult.getThinking());
	        result = ollamaResult.getResponse();
		} catch (OllamaException e) {
			e.printStackTrace();
		}
		return result;
	}
}
