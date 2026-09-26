package com.github.pasiahopelto;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import io.github.wimdeblauwe.htmx.spring.boot.mvc.HxRequest;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class WebUi {
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
		model.addAttribute("explanation", markdownToHtml(explainer.explain(fortune)));
		return "view :: fortune";
	}
	
	private String markdownToHtml(String markdown) {
		Parser parse = Parser.builder().build();
		Node node = parse.parse(markdown);
		HtmlRenderer renderer = HtmlRenderer.builder().build();
		return renderer.render(node);
	}
}