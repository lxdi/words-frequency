package com.sogoodlabs.wordsfrequency.service;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Map;

@Service
public class ExcelGeneratorService {

    Logger log = LoggerFactory.getLogger(ExcelGeneratorService.class);

    public void generate(Map<String, Long> source, File outputFile){

        try(FileOutputStream fileOut = new FileOutputStream(outputFile); HSSFWorkbook workbook = new HSSFWorkbook()) {
            HSSFSheet sheet = workbook.createSheet("Sheet");
            int rowNum = 0;

            for(Map.Entry<String, Long> entry: source.entrySet()){
                HSSFRow rowhead = sheet.createRow(rowNum++);
                rowhead.createCell(0).setCellValue(entry.getKey());
                rowhead.createCell(1).setCellValue(""+entry.getValue());
            }

            workbook.write(fileOut);
        } catch (Exception ex) {
            throw new RuntimeException("Failed to generate excel", ex);
        }

        log.info("Successfully generated excel {}", outputFile.getAbsolutePath());
    }

}
