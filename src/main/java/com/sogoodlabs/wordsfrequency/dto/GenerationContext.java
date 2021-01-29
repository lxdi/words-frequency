package com.sogoodlabs.wordsfrequency.dto;

import java.io.File;

public class GenerationContext {

    private File frequencyDict;
    private File efDict;
    private File inputFile;
    private File outputFile;

    public GenerationContext(File frequencyDict, File efDict, File inputFile, File outputFile) {
        this.frequencyDict = frequencyDict;
        this.efDict = efDict;
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

    public File getEfDict() {
        return efDict;
    }

    public void setEfDict(File efDict) {
        this.efDict = efDict;
    }
}
