package com.sogoodlabs.wordsfrequency.service;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class ExcelParserService {

    Logger log = LoggerFactory.getLogger(ExcelParserService.class);

    public List<String> parse(File file){

        log.info("Parsing input words");

        List<String> result = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            for(int i = 0; i<workbook.getNumberOfSheets(); i++){
                XSSFSheet worksheet = workbook.getSheetAt(i);
                handleSheet(worksheet, result);
            }
        } catch (Exception ex){
            throw new RuntimeException("Couldn't parse excel file", ex);
        }

        log.info("Successfully parsed {} words", result.size());
        return result;
    }

    private void handleSheet(XSSFSheet worksheet, List<String> result){
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);
            //log.info("{}: {}", row.getCell(0).getNumericCellValue(), row.getCell(1).getStringCellValue());
            result.add(row.getCell(1).getStringCellValue());
        }
    }

}
