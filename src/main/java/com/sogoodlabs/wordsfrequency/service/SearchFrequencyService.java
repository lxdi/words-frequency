package com.sogoodlabs.wordsfrequency.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class SearchFrequencyService {

    public Map<String, Long> find(List<String> words, Map<String, Long> dict){
        Map<String, Long> result = new LinkedHashMap<>();
        words.forEach(word -> result.put(word, dict.getOrDefault(word, 0L)));
        return result;
    }

}
