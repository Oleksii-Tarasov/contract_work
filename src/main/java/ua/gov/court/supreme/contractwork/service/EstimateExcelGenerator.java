package ua.gov.court.supreme.contractwork.service;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import ua.gov.court.supreme.contractwork.dao.EstimateDAO;
import ua.gov.court.supreme.contractwork.model.Estimate;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

public class EstimateExcelGenerator {

    private final EstimateDAO estimateDAO = new EstimateDAO();

    public void generate(String filePath) {
        try (Workbook workbook = new XSSFWorkbook()) {

            Sheet sheet = workbook.createSheet("Кошторис");

            // ====== БАЗОВИЙ СТИЛЬ ШРИФТУ ======
            Font baseFont = workbook.createFont();
            baseFont.setFontName("Roboto Condensed Light");
            baseFont.setFontHeightInPoints((short) 12);

            CellStyle baseStyle = workbook.createCellStyle();
            baseStyle.setFont(baseFont);

            int rowIndex = 0;

            // ТІЛЬКИ ОДИН ЗАГОЛОВОК ДЛЯ ВСЬОГО ДОКУМЕНТА
            rowIndex = writeHeader(workbook, sheet, rowIndex, baseStyle);

            // 2210 з заголовком таблиці
            rowIndex = writeKekvBlock(workbook, sheet,
                    "2210", "Предмети, матеріали, обладнання та інвентар, у т. ч. м'який інвентар та обмундирування",
                    estimateDAO.getProjectsByKekv(2210), rowIndex, baseStyle, true);

            // 2240 та 3110 без заголовків таблиці
            rowIndex = writeKekvBlock(workbook, sheet,
                    "2240", "Оплата послуг (крім комунальних)",
                    estimateDAO.getProjectsByKekv(2240), rowIndex, baseStyle, false);

            rowIndex = writeKekvBlock(workbook, sheet,
                    "3110", "Придбання обладнання і предметів довгострокового користування",
                    estimateDAO.getProjectsByKekv(3110), rowIndex, baseStyle, false);

            // ШИРИНА СТОВПЦІВ (Кількість робимо трішки ширше)
            sheet.setColumnWidth(0, 6 * 256);
            sheet.setColumnWidth(1, 50 * 256);
            sheet.setColumnWidth(2, 10 * 256);
            sheet.setColumnWidth(3, 14 * 256);
            sheet.setColumnWidth(4, 14 * 256);
            sheet.setColumnWidth(5, 14 * 256);
            sheet.setColumnWidth(6, 14 * 256);
            sheet.setColumnWidth(7, 14 * 256);
            sheet.setColumnWidth(8, 70 * 256);

            try (FileOutputStream fileOut = new FileOutputStream(filePath)) {
                workbook.write(fileOut);
            }

            System.out.println("Excel файл створено: " + filePath);

        } catch (IOException e) {
            throw new RuntimeException("Помилка при збереженні Excel файлу", e);
        }
    }

    private int writeHeader(Workbook workbook, Sheet sheet, int rowIndex, CellStyle baseStyle) {
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

        // ===== СТИЛЬ ЗАГОЛОВКА ТАБЛИЦІ =====
        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.cloneStyleFrom(baseStyle);

        Font headerFont = workbook.createFont();
        headerFont.setFontName("Roboto Condensed Light");
        headerFont.setFontHeightInPoints((short) 12);
        headerFont.setBold(true);
        headerStyle.setFont(headerFont);
        headerStyle.setAlignment(HorizontalAlignment.CENTER);
        headerStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        headerStyle.setBorderTop(BorderStyle.THIN);
        headerStyle.setBorderBottom(BorderStyle.THIN);
        headerStyle.setBorderLeft(BorderStyle.THIN);
        headerStyle.setBorderRight(BorderStyle.THIN);
        headerStyle.setWrapText(true);

        if (writeHeader) {
            Row headerRow = sheet.createRow(rowIndex++);
            headerRow.setHeightInPoints(30);

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
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
                cell.setCellStyle(headerStyle);
            }
        }

        // ==== РЯДОК КЕКВ ====
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

        // ====== СТИЛІ ЯЧЕЙОК ======
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

        // СТИЛЬ: ГРОШОВИЙ ФОРМАТ
        CellStyle currencyStyle = workbook.createCellStyle();
        currencyStyle.cloneStyleFrom(cellStyle); // Копіюємо всі базові налаштування (рамки, вирівнювання тощо)
        DataFormat format = workbook.createDataFormat();
        // Встановлюємо формат: #,##0.00 означає роздільник тисяч, 2 знаки після коми
        currencyStyle.setDataFormat(format.getFormat("#,##0.00"));
        currencyStyle.setAlignment(HorizontalAlignment.CENTER);

        // ===== ПІДСУМКИ =====
        double totalQty = 0;
        double totalSum = 0;
        double totalZF = 0;
        double totalSF = 0;

        int index = 1;
        for (Estimate project : list) {
            Row row = sheet.createRow(rowIndex++);

            double sum = project.getQuantity() * project.getPrice();

            totalQty += project.getQuantity();
            totalSum += sum;
            totalZF += project.getGeneralFund();
            totalSF += project.getSpecialFund();

            row.createCell(0).setCellValue(index++);
            row.createCell(1).setCellValue(project.getDkCode() + " " + project.getNameProject());
            row.createCell(2).setCellValue(project.getUnitOfMeasure());
            row.createCell(3).setCellValue(project.getQuantity());
            row.createCell(4).setCellValue(project.getPrice());
            row.createCell(5).setCellValue(sum);
            row.createCell(6).setCellValue(project.getGeneralFund());
            row.createCell(7).setCellValue(project.getSpecialFund());
            row.createCell(8).setCellValue(project.getJustification());

            // центр
            row.getCell(0).setCellStyle(centerStyle);
            row.getCell(2).setCellStyle(centerStyle);
            row.getCell(3).setCellStyle(centerStyle);
            row.getCell(4).setCellStyle(currencyStyle);
            row.getCell(5).setCellStyle(currencyStyle);
            row.getCell(6).setCellStyle(currencyStyle);
            row.getCell(7).setCellStyle(currencyStyle);

            // текст
            row.getCell(1).setCellStyle(cellStyle);
            row.getCell(8).setCellStyle(cellStyle);
        }

        // ======= РЯДОК ПІДСУМКІВ =======
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
        totalRow.createCell(5).setCellValue(totalSum);
        totalRow.createCell(6).setCellValue(totalZF);
        totalRow.createCell(7).setCellValue(totalSF);

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
}
