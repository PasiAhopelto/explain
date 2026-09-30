package com.github.pasiahopelto;

import java.io.IOException;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import org.thymeleaf.context.Context;
import org.thymeleaf.spring6.SpringTemplateEngine;

import com.google.common.base.Strings;

import io.github.ollama4j.Ollama;
import io.github.ollama4j.exceptions.OllamaException;
import io.github.ollama4j.models.request.ThinkMode;
import io.github.ollama4j.models.response.OllamaAsyncResultStreamer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class FortuneExplainer {
	
	private final SpringTemplateEngine templateEngine;

	public void explainAndSend(SseEmitter emitter, String fortune) {
        try {
        	String prompt = "Please explain this fortune: " + fortune;
            Ollama ollama = new Ollama("http://localhost:11434");
            ollama.setRequestTimeoutSeconds(100L);
        	readResult(emitter, prompt, fortune, ollama);
		} catch (OllamaException e) {
			log.error("error explaining a fortune", e);
		} catch (InterruptedException e) {
			log.error("error sleeping", e);
		} catch (IOException e) {
			log.error("emitter failure", e);
		}
	}

	private void readResult(SseEmitter emitter, String prompt, String fortune, Ollama ollama) throws OllamaException, InterruptedException, IOException {
		OllamaAsyncResultStreamer resultStreamer = ollama.generateAsync("qwen3:1.7b", prompt, false, ThinkMode.HIGH);
		String thought = "";
		String explanation = "";
		boolean changed = true;
		while (resultStreamer.isAlive()) {
			String thoughtFragment = resultStreamer.getThinkingResponseStream().poll();
			if (!Strings.isNullOrEmpty(thoughtFragment)) {
				thought += thoughtFragment;
				changed = true;
			}
			String explanationFragment = resultStreamer.getResponseStream().poll();
			if (!Strings.isNullOrEmpty(explanationFragment)) {
				explanation += explanationFragment;
				changed = true;
			}
			if (changed) {
				send(emitter, fortune, thought, explanation);
				changed = false;
			}
			Thread.sleep(1000L);
		}
		send(emitter, fortune, resultStreamer.getCompleteThinkingResponse(), resultStreamer.getCompleteResponse());
	}

	private void send(SseEmitter emitter, String fortune, String thought, String explanation) {
		try {
			Context context = new Context();
			context.setVariable("fortune", fortune);
			context.setVariable("thought", markdownToHtml(thought));
			context.setVariable("explanation", markdownToHtml(explanation));
			String html = templateEngine.process("fortune", context);
			emitter.send(SseEmitter.event().data(html));
		} catch (IOException e) {
			log.error("send failed", e);
		}
	}
	
	private String markdownToHtml(String markdown) {
		Parser parse = Parser.builder().build();
		Node node = parse.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(node);
	}
}
