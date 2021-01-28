package com.sogoodlabs.wordsfrequency;

import com.sogoodlabs.wordsfrequency.service.WordsFrequencyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WordsfrequencyApplication implements CommandLineRunner {

	@Autowired
	private WordsFrequencyService wordsFrequencyService;

	public static void main(String[] args) {
		SpringApplication.run(WordsfrequencyApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		wordsFrequencyService.generate();
	}
}
