package com.sogoodlabs.wordsfrequency.dto;

import java.io.File;

public class GenerationContext {

    private File frequencyDict;
    private File inputFile;
    private File outputFile;

    public GenerationContext(File frequencyDict, File inputFile, File outputFile) {
        this.frequencyDict = frequencyDict;
        this.inputFile = inputFile;
        this.outputFile = outputFile;
    }

    public File getFrequencyDict() {
        return frequencyDict;
    }

    public void setFrequencyDict(File frequencyDict) {
        this.frequencyDict = frequencyDict;
    }

    public File getInputFile() {
        return inputFile;
    }

    public void setInputFile(File inputFile) {
        this.inputFile = inputFile;
    }

    public File getOutputFile() {
        return outputFile;
    }

    public void setOutputFile(File outputFile) {
        this.outputFile = outputFile;
    }

}
