package com.sogoodlabs.wordsfrequency.service;

import org.apache.poi.xssf.usermodel.XSSFCell;
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

    private final static int COLUMN_NUM_DEFAULT = 1;
    private final static int ROW_START_DEFAULT = 1;


    public List<String> parseSingleColumn(File file){
        return parse(file, false, COLUMN_NUM_DEFAULT);
    }

    public List<String> parseMultipleColumn(File file){
        return parse(file, true, COLUMN_NUM_DEFAULT);
    }

    private List<String> parse(File file, boolean isMultipleColumns, int column){

        log.info("Parsing excel file {}, mode [{} columns]", file.getAbsolutePath(), isMultipleColumns? "multiple": "single");

        List<String> result = new ArrayList<>();

        try(FileInputStream fis = new FileInputStream(file); XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            for(int i = 0; i<workbook.getNumberOfSheets(); i++){
                XSSFSheet worksheet = workbook.getSheetAt(i);

                if(isMultipleColumns){
                    handleMultipleColumnSheet(worksheet, result, column);
                } else {
                    handleSingleColumnSheet(worksheet, result, column);
                }
            }
        } catch (Exception ex){
            throw new RuntimeException("Couldn't parse excel file", ex);
        }

        log.info("Successfully parsed {} excel", result.size());
        return result;
    }

    private void handleSingleColumnSheet(XSSFSheet worksheet, List<String> result, int column){
        for (int i = ROW_START_DEFAULT; i < worksheet.getPhysicalNumberOfRows(); i++) {
            XSSFRow row = worksheet.getRow(i);

            if(row == null){
                log.warn("No row; row {} col {}, sheet {}", i, column, worksheet.getSheetName());
                continue;
            }

            XSSFCell cell = row.getCell(column);

            if(cell == null){
                log.warn("No cell row {} col {}, sheet {}", i, column, worksheet.getSheetName());
                continue;
            }

            //log.info("{}: {}", row.getCell(0).getNumericCellValue(), row.getCell(1).getStringCellValue());
            result.add(row.getCell(column).getStringCellValue());
        }
    }

    private void handleMultipleColumnSheet(XSSFSheet worksheet, List<String> result, int column){
        int cellNum = column;
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
