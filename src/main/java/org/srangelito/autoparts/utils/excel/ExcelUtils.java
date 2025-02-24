package org.srangelito.autoparts.utils.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.srangelito.autoparts.exception.InvalidMapSizeException;
import org.srangelito.autoparts.exception.SheetTitleNotFoundException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ExcelUtils {

    private ExcelUtils() {
        throw new UnsupportedOperationException();
    }

    public static boolean validateExcelFile (MultipartFile targetMultipartFile) {
        if (targetMultipartFile.getOriginalFilename().endsWith(".xlsx"))
            return true;
        return false;
    }

    public static XSSFWorkbook getXssfWorkbookByMultipartFile (MultipartFile inputMultipartFile) throws IOException {
        return new XSSFWorkbook(inputMultipartFile.getInputStream());
    }

    public static int getSheetTitleRowIndex (XSSFWorkbook targetXssfWorkbook, int targetSheetIndex) throws SheetTitleNotFoundException {
        XSSFSheet xssfSheet = targetXssfWorkbook.getSheetAt(targetSheetIndex);

        for (Row row : xssfSheet) {
            for (Cell cell : row)
                if (cell.getCellType() == CellType.STRING)
                    return row.getRowNum();
        }

        throw new SheetTitleNotFoundException("The title of the Excel sheet could not be found");
    }

    public static Map<String, Integer> mappingAttributesColumnsIndexes (XSSFSheet xssfSheet, int sheetTitleRowIndex) throws InvalidMapSizeException {
        Row attributesRow = xssfSheet.getRow(sheetTitleRowIndex + 1);
        Map<String, Integer> mappingColumnsAttributes = new HashMap<>();
        Map<String, String> mappingPrefixes = Map.of(
            "cant", "Quantity",
            "n", "Part",
            "ap", "Application",
            "pre", "Private Price",
            "cos", "Private Price",
            "pu", "Public Price"
        );


        for (Cell cell : attributesRow)
            for (Map.Entry<String, String> mappingPrefix : mappingPrefixes.entrySet()) {
                if (cell.getCellType() == CellType.STRING)
                    if (cell.getStringCellValue().trim().toLowerCase().startsWith(mappingPrefix.getKey())) {
                        mappingColumnsAttributes.put(mappingPrefix.getValue(), cell.getColumnIndex());
                        break;
                    }
            }

        if (mappingColumnsAttributes.size() == 5)
            return mappingColumnsAttributes;
        else
            throw new InvalidMapSizeException("The map size is invalid");
    }
}
