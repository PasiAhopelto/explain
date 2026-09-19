package com.github.pasiahopelto;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class RestService {
	private final FortuneGetter getter;
	private final FortuneExplainer explainer;

	@GetMapping("/fortune")
    public String page() {
        return "view";
    }
	
	@HxRequest
	@GetMapping("/fortune/random")
	public String getAndEplainFortune(Model model) {
		String fortune = getter.getFortune();
		model.addAttribute("fortune", fortune);
		model.addAttribute("explanation", explainer.explain(fortune));
		return "view :: fortune";
	}
}