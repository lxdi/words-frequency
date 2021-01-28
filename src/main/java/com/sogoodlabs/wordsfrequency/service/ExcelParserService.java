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

    public List<String> parseSingleColumn(File file){
        return parse(file, false);
    }

    public List<String> parseMultipleColumn(File file){
        return parse(file, true);
    }

    private List<String> parse(File file, boolean isMultipleColumns){

        log.info("Parsing input words [{} columns]", isMultipleColumns? "multiple": "single");

        List<String> result = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            for(int i = 0; i<workbook.getNumberOfSheets(); i++){
                XSSFSheet worksheet = workbook.getSheetAt(i);

                if(isMultipleColumns){
                    handleMultipleColumnSheet(worksheet, result);
                } else {
                    handleSingleColumnSheet(worksheet, result);
                }
            }
        } catch (Exception ex){
            throw new RuntimeException("Couldn't parse excel file", ex);
        }

        log.info("Successfully parsed {} words", result.size());
        return result;
    }

    private void handleSingleColumnSheet(XSSFSheet worksheet, List<String> result){
        for (int i = 1; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);
            //log.info("{}: {}", row.getCell(0).getNumericCellValue(), row.getCell(1).getStringCellValue());
            result.add(row.getCell(1).getStringCellValue());
        }
    }

    private void handleMultipleColumnSheet(XSSFSheet worksheet, List<String> result){
        int cellNum = 1;
        while(worksheet.getRow(0)!=null && worksheet.getRow(0).getCell(cellNum)!=null) {
            for (int i = 0; i < worksheet.getPhysicalNumberOfRows(); i++) {
                XSSFRow row = worksheet.getRow(i);

                if(row!=null && row.getCell(cellNum)!=null){
                    result.add(row.getCell(cellNum).getStringCellValue());
                    //log.info("{}: {}", row.getCell(cellNum-1).getNumericCellValue(), row.getCell(cellNum).getStringCellValue());
                }
            }
            cellNum = cellNum + 3;
        }
    }

}
