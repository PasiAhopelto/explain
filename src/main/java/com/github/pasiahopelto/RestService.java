package com.github.pasiahopelto;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class RestService {
	private final FortuneGetter getter;
	private final FortuneExplainer explainer;
	
	@GetMapping("/get-and-explain")
	public Explanation getAndEplainFortune() {
		Explanation result = new Explanation();
		String fortune = getter.getFortune();
		String explanation = explainer.explain(fortune);
		result.setExplanation(explanation);
		result.setFortune(fortune);
		return result;
	}
}
