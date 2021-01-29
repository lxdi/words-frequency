package com.sogoodlabs.wordsfrequency.service;

import com.sogoodlabs.wordsfrequency.dto.GenerationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Service
public class WordsFrequencyService {

    Logger log = LoggerFactory.getLogger(WordsFrequencyService.class);

    @Value("${frequency.dict}")
    private String frequencyDictPath;

    @Value("${ef.dict}")
    private String efDictPath;

    @Value("${input.file.path}")
    private String inputFilePath;

    @Value("${output.file.path}")
    private String outputFilePath;

    @Value("${input.file.parse.cols}")
    private String inputFileParseMode;

    @Autowired
    private ExcelParserService excelParserService;

    @Autowired
    private CsvParserService csvParserService;

    @Autowired
    private ExcelGeneratorService excelGeneratorService;

    @Autowired
    private SearchFrequencyService searchFrequencyService;

    public void generate(){
        log.info("Generating... frequency.dict [{}], input.file [{}], output.file [{}]", frequencyDictPath, inputFilePath, outputFilePath);

        GenerationContext context = createContext();
        Map<String, Long> freqDict = csvParserService.parse(context.getFrequencyDict());

        Set<String> efWords = new HashSet<>(excelParserService.parseSingleColumn(context.getEfDict()));

        List<String> wordsInput = null;


        if(inputFileParseMode.equalsIgnoreCase("single")){
            wordsInput = excelParserService.parseSingleColumn(context.getInputFile());
        }

        if(inputFileParseMode.equalsIgnoreCase("multiple")){
            wordsInput = excelParserService.parseMultipleColumn(context.getInputFile());
        }

        if(wordsInput == null){
            throw new RuntimeException("No input data");
        }

        excelGeneratorService.generate(
                searchFrequencyService.find(wordsInput, freqDict, efWords), context.getOutputFile());
    }

    private GenerationContext createContext(){
        File freqDictFile = new File(frequencyDictPath);

        if(!freqDictFile.exists()){
            throw new RuntimeException("Frequency dictionary file doesn't exist " + frequencyDictPath);
        }

        File efDictFile = new File(efDictPath);

        if(!efDictFile.exists()){
            throw new RuntimeException("EF dictionary file doesn't exist " + efDictPath);
        }

        File inputFile = new File(inputFilePath);

        if(!inputFile.exists()){
            throw new RuntimeException("Input file doesn't exist " + inputFilePath);
        }

        File outputFile = new File(outputFilePath);

        if(outputFile.exists()){
            throw new RuntimeException("Output file already exists " + outputFile);
        }

        return new GenerationContext(freqDictFile, efDictFile, inputFile, outputFile);
    }

}
