package com.sogoodlabs.wordsfrequency.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


@Service
public class WordsFrequencyService {

    Logger log = LoggerFactory.getLogger(WordsFrequencyService.class);

    @Value("${frequency.dict}")
    private String frequencyDictPath;

    @Value("${input.file.path}")
    private String inputFilePath;

    @Value("${output.file.path}")
    private String outputFilePath;

    public void generate(){
        log.info("Generating... frequency.dict [{}], input.file [{}], output.file [{}]", frequencyDictPath, inputFilePath, outputFilePath);


    }

}
