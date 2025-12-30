package ua.gov.court.supreme.contractwork.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ua.gov.court.supreme.contractwork.dao.EstimateDAO;
import ua.gov.court.supreme.contractwork.model.Estimate;

import java.math.BigDecimal;
import java.util.List;

public class EstimateExcelConstructor {

    public Workbook createWorkbook(EstimateDAO estimateDAO) {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Кошторис");

        // ====== BASE FONT STYLE ======
        Font baseFont = workbook.createFont();
        baseFont.setFontName("Roboto Condensed Light");
        baseFont.setFontHeightInPoints((short) 12);

        CellStyle baseStyle = workbook.createCellStyle();
        baseStyle.setFont(baseFont);

        int rowIndex = 0;

        // SINGLE HEADER FOR THE ENTIRE DOCUMENT
        rowIndex = writeHeader(workbook, sheet, rowIndex, baseStyle);

        // 2210 with table header
        rowIndex = writeKekvBlock(workbook, sheet,
                "2210", "Предмети, матеріали, обладнання та інвентар, у т. ч. м'який інвентар та обмундирування",
                estimateDAO.findAllByKekv(2210), rowIndex, baseStyle, true);

        // 2240 and 3110 without table headers
        rowIndex = writeKekvBlock(workbook, sheet,
                "2240", "Оплата послуг (крім комунальних)",
                estimateDAO.findAllByKekv(2240), rowIndex, baseStyle, false);

        rowIndex = writeKekvBlock(workbook, sheet,
                "3110", "Придбання обладнання і предметів довгострокового користування",
                estimateDAO.findAllByKekv(3110), rowIndex, baseStyle, false);

        // COLUMN WIDTHS (Quantity column slightly wider)
        sheet.setColumnWidth(0, 6 * 256);
        sheet.setColumnWidth(1, 50 * 256);
        sheet.setColumnWidth(2, 10 * 256);
        sheet.setColumnWidth(3, 14 * 256);
        sheet.setColumnWidth(4, 14 * 256);
        sheet.setColumnWidth(5, 14 * 256);
        sheet.setColumnWidth(6, 14 * 256);
        sheet.setColumnWidth(7, 14 * 256);
        sheet.setColumnWidth(8, 70 * 256);

        rowIndex+=2;

        rowIndex = writeFooter(workbook, sheet, rowIndex, baseStyle);

        return workbook;
    }

    private int writeHeader(Workbook workbook, Sheet sheet, int rowIndex, CellStyle baseStyle) {
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.cloneStyleFrom(baseStyle);
        headerStyle.setAlignment(HorizontalAlignment.LEFT);
        headerStyle.setVerticalAlignment(VerticalAlignment.TOP);

        Font headerFont = workbook.createFont();
        headerFont.setFontName("Roboto Condensed Light");
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);

        // === One row with three text fragments ===
        Row headerRow = sheet.createRow(rowIndex++);
        Cell headerCell = headerRow.createCell(8);
        headerCell.setCellValue("""
        
        ПОГОДЖУЮ
        
        Керівник Апарату Верховного Суду
        
        ______________ Віктор КАПУСТИНСЬКИЙ
        """);
        headerCell.setCellStyle(headerStyle);
        headerCell.getCellStyle().setWrapText(true); // Allow text wrapping within a cell

        rowIndex += 2;

        CellStyle titleStyle = workbook.createCellStyle();
        titleStyle.cloneStyleFrom(baseStyle);

        Font titleFont = workbook.createFont();
        titleFont.setFontName("Roboto Condensed Light");
        titleFont.setFontHeightInPoints((short) 14);
        titleFont.setBold(true);
        titleStyle.setFont(titleFont);
        titleStyle.setAlignment(HorizontalAlignment.CENTER);

        Row titleRow1 = sheet.createRow(rowIndex++);
        sheet.addMergedRegion(new CellRangeAddress(titleRow1.getRowNum(), titleRow1.getRowNum(), 0, 8));
        Cell t1 = titleRow1.createCell(0);
        t1.setCellValue("Пропозиції до кошторису Верховного Суду на 2025 рік");
        t1.setCellStyle(titleStyle);

        Row titleRow2 = sheet.createRow(rowIndex++);
        sheet.addMergedRegion(new CellRangeAddress(titleRow2.getRowNum(), titleRow2.getRowNum(), 0, 8));
        Cell t2 = titleRow2.createCell(0);
        t2.setCellValue("управління інформатизації");
        t2.setCellStyle(titleStyle);

        return rowIndex + 1;
    }

    private int writeKekvBlock(Workbook workbook, Sheet sheet, String kekvCode, String kekvTitle,
                               List<Estimate> list, int rowIndex, CellStyle baseStyle, boolean writeHeader) {

        // ===== TABLE HEADER STYLE =====
        CellStyle headerTableStyle = workbook.createCellStyle();
        headerTableStyle.cloneStyleFrom(baseStyle);

        Font headerTableFont = workbook.createFont();
        headerTableFont.setFontName("Roboto Condensed Light");
        headerTableFont.setFontHeightInPoints((short) 12);
        headerTableFont.setBold(true);
        headerTableStyle.setFont(headerTableFont);
        headerTableStyle.setAlignment(HorizontalAlignment.CENTER);
        headerTableStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerTableStyle.setBorderTop(BorderStyle.THIN);
        headerTableStyle.setBorderBottom(BorderStyle.THIN);
        headerTableStyle.setBorderLeft(BorderStyle.THIN);
        headerTableStyle.setBorderRight(BorderStyle.THIN);
        headerTableStyle.setWrapText(true);

        if (writeHeader) {
            Row headerTableRow = sheet.createRow(rowIndex++);
            headerTableRow.setHeightInPoints(30);

            String[] headers = {
                    "№ з/п",
                    "Найменування",
                    "Одиниця\nвиміру",
                    "Кількість",
                    "Ціна, грн",
                    "Сума, грн",
                    "ЗФ",
                    "СФ",
                    "Розрахунок та обґрунтування"
            };

            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerTableRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerTableStyle);
            }
        }

        // ==== KEKV ROW ====
        Row kekvRow = sheet.createRow(rowIndex++);
        Cell kekvCell = kekvRow.createCell(0);
        kekvCell.setCellValue(kekvCode + " " + kekvTitle);

        CellStyle kekvStyle = workbook.createCellStyle();
        kekvStyle.cloneStyleFrom(baseStyle);
        Font kekvFont = workbook.createFont();
        kekvFont.setFontName("Roboto Condensed Light");
        kekvFont.setFontHeightInPoints((short) 12);
        kekvFont.setBold(true);
        kekvStyle.setFont(kekvFont);
        kekvCell.setCellStyle(kekvStyle);

        // ====== CELL STYLES ======
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.cloneStyleFrom(baseStyle);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setWrapText(true);
        cellStyle.setVerticalAlignment(VerticalAlignment.TOP);

        CellStyle centerStyle = workbook.createCellStyle();
        centerStyle.cloneStyleFrom(cellStyle);
        centerStyle.setAlignment(HorizontalAlignment.CENTER);

        // STYLE: CURRENCY FORMAT
        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.cloneStyleFrom(cellStyle); // Copy all base settings (borders, alignment, etc.)
        DataFormat format = workbook.createDataFormat();
        // Set format: #,##0.00 means thousands separator, 2 decimal places
        currencyStyle.setDataFormat(format.getFormat("#,##0.00"));
        currencyStyle.setAlignment(HorizontalAlignment.CENTER);

        // ===== TOTALS =====
        double totalQty = 0;
        BigDecimal totalSum = BigDecimal.ZERO;
        BigDecimal totalZF = BigDecimal.ZERO;
        BigDecimal totalSF = BigDecimal.ZERO;

        int index = 1;
        for (Estimate project : list) {
            Row row = sheet.createRow(rowIndex++);

            BigDecimal sum = project.getQuantity() == 0 ? BigDecimal.ZERO 
                : project.getPrice().multiply(BigDecimal.valueOf(project.getQuantity()));

            totalQty += project.getQuantity();
            totalSum = totalSum.add(sum);
            totalZF = totalZF.add(project.getGeneralFund());
            totalSF = totalSF.add(project.getSpecialFund());

            row.createCell(0).setCellValue(index++);
            row.createCell(1).setCellValue(project.getDkCode() + " " + project.getProjectName());
            row.createCell(2).setCellValue(project.getUnitOfMeasure());
            row.createCell(3).setCellValue(project.getQuantity());
            
            // Set double values for POI from BigDecimal
            row.createCell(4).setCellValue(project.getPrice().doubleValue());
            row.createCell(5).setCellValue(sum.doubleValue());
            row.createCell(6).setCellValue(project.getGeneralFund().doubleValue());
            row.createCell(7).setCellValue(project.getSpecialFund().doubleValue());
            
            row.createCell(8).setCellValue(project.getJustification());

            // center
            row.getCell(0).setCellStyle(centerStyle);
            row.getCell(2).setCellStyle(centerStyle);
            row.getCell(3).setCellStyle(centerStyle);
            row.getCell(4).setCellStyle(currencyStyle);
            row.getCell(5).setCellStyle(currencyStyle);
            row.getCell(6).setCellStyle(currencyStyle);
            row.getCell(7).setCellStyle(currencyStyle);

            // text
            row.getCell(1).setCellStyle(cellStyle);
            row.getCell(8).setCellStyle(cellStyle);
        }

        // ======= TOTALS ROW =======
        Row totalRow = sheet.createRow(rowIndex++);

        CellStyle rightKekvStyle = workbook.createCellStyle();

        rightKekvStyle.cloneStyleFrom(cellStyle);
        rightKekvStyle.setAlignment(HorizontalAlignment.RIGHT);
        Font boldFont = workbook.createFont();
        boldFont.setFontName("Roboto Condensed Light");
        boldFont.setBold(true);
        rightKekvStyle.setFont(boldFont);

        CellStyle sumCenter = workbook.createCellStyle();
        sumCenter.cloneStyleFrom(cellStyle);
        sumCenter.setAlignment(HorizontalAlignment.CENTER);
        sumCenter.setDataFormat(format.getFormat("#,##0.00"));

        totalRow.createCell(1).setCellValue("ВСЬОГО ЗА " + kekvCode);
        totalRow.getCell(1).setCellStyle(rightKekvStyle);

        totalRow.createCell(3).setCellValue(totalQty);
        totalRow.createCell(5).setCellValue(totalSum.doubleValue());
        totalRow.createCell(6).setCellValue(totalZF.doubleValue());
        totalRow.createCell(7).setCellValue(totalSF.doubleValue());

        totalRow.createCell(0).setCellStyle(sumCenter);
        totalRow.createCell(2).setCellStyle(sumCenter);
        totalRow.getCell(3).setCellStyle(centerStyle);
        totalRow.createCell(4).setCellStyle(sumCenter);
        totalRow.getCell(5).setCellStyle(sumCenter);
        totalRow.getCell(6).setCellStyle(sumCenter);
        totalRow.getCell(7).setCellStyle(sumCenter);
        totalRow.createCell(8).setCellStyle(sumCenter);

        return rowIndex;
    }

    private int writeFooter(Workbook workbook, Sheet sheet, int rowIndex, CellStyle baseStyle) {
        CellStyle footerStyle = workbook.createCellStyle();
        footerStyle.cloneStyleFrom(baseStyle);
        footerStyle.setAlignment(HorizontalAlignment.LEFT);

        Font footerFont = workbook.createFont();
        footerFont.setFontName("Roboto Condensed Light");
        footerFont.setFontHeightInPoints((short) 14);
        footerFont.setBold(true);
        footerStyle.setFont(footerFont);

        Row footerRow = sheet.createRow(rowIndex++);
        Cell footerCell1 = footerRow.createCell(1);
        footerCell1.setCellValue("Начальник управління інформатизації");
        Cell footerCell5 = footerRow.createCell(7);
        footerCell5.setCellValue("Денис ЩЕГОЛІХІН");

        footerCell1.setCellStyle(footerStyle);
        footerCell5.setCellStyle(footerStyle);

        return rowIndex;
    }
}
