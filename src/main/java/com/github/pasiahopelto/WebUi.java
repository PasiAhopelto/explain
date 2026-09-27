package com.github.pasiahopelto;


import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebUi {
	private final FortuneGetter getter;
	private final FortuneExplainer explainer;

	@GetMapping("/")
    public String view() {
        return "view";
    }

    @GetMapping(
    		value = "/fortune",
            produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public SseEmitter getAndExplainFortune() {
        SseEmitter emitter = new SseEmitter(100000L);
		String fortune = getter.getFortune();
		Thread.startVirtualThread(() -> {
			explainer.explainAndSend(emitter, fortune);
			emitter.complete();
		});
		return emitter;
    }
}