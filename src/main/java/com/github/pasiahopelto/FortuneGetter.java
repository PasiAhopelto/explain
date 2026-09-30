package com.github.pasiahopelto;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@Component
@Slf4j
public class FortuneGetter {
	private static final String[] FORTUNE_CMD = new String[] { "/opt/homebrew/bin/fortune" };
	
	public String getFortune() {
		String result = null;
		try (InputStream inputStream = Runtime.getRuntime().exec(FORTUNE_CMD).getInputStream();
				Scanner s = new Scanner(inputStream).useDelimiter("\\A")) {
			result = s.hasNext() ? s.next() : null;
		} catch (IOException e) {
			log.error("failed to get fortune", e);
		}
		return result;
	}
}
