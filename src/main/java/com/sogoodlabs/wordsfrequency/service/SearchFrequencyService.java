package com.sogoodlabs.wordsfrequency.service;

import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class SearchFrequencyService {

    public Map<String, List<Object>> find(List<String> words, Map<String, Long> freqDict, Set<String> efWords){
        Map<String, List<Object>> result = new LinkedHashMap<>();

        words.forEach(word -> {
            String ef = efWords.contains(word)?"ef":"";
            result.put(word, new ArrayList<>(Arrays.asList(freqDict.getOrDefault(word, 0L), ef)));
        });

        return result;
    }

}
