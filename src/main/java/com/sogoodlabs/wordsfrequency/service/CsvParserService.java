package com.sogoodlabs.wordsfrequency.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class CsvParserService {

    Logger log = LoggerFactory.getLogger(CsvParserService.class);

    private final static String COMMA_DELIMITER = ",";

    public Map<String, Long> parse(File frequencyDict){

        log.info("Loading frequency dictionary");

        Map<String, Long> result = new HashMap<>();

        try (FileReader fr = new FileReader(frequencyDict); BufferedReader br = new BufferedReader(fr)) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                result.put(values[0], Long.parseLong(values[1]));
                //log.info("{} - {}", values[0], values[1]);
            }
        } catch (Exception ex){
            throw new RuntimeException("Failed to load frequency dictionary", ex);
        }

        log.info("Successfully loaded dictionary, {} words", result.keySet().size());
        return result;
    }

}
