package org.srangelito.autoparts.utils.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import org.srangelito.autoparts.entity.ProductEntity;
import org.srangelito.autoparts.exception.DuplicateAttributeColumnException;
import org.srangelito.autoparts.exception.MissingAttributeColumnsException;
import org.srangelito.autoparts.exception.SheetTitleNotFoundException;
import org.srangelito.autoparts.exception.UnsupportedFileTypeException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public final class ExcelUtils {

    private ExcelUtils() {
        throw new UnsupportedOperationException();
    }

    public static String getStringCellValue (Cell cell) {
        if (cell.getCellType() == CellType.STRING)
            return cell.getStringCellValue();
        else if (cell.getCellType() == CellType.NUMERIC)
            return String.valueOf((int) cell.getNumericCellValue());
        return null;
    }

    public static Float getFloatCellValue (Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC)
            return (float) cell.getNumericCellValue();
        return null;
    }

    public static Short getShortCellValue (Cell cell) {
        if (cell.getCellType() == CellType.NUMERIC)
            return (short) cell.getNumericCellValue();
        return null;
    }

    public static void validateExcelFile (MultipartFile multipartFile) throws UnsupportedFileTypeException {
        if (!multipartFile.getOriginalFilename().endsWith(".xlsx"))
            throw new UnsupportedFileTypeException("The file type is invalid");
    }

    public static XSSFWorkbook getXssfWorkbookByMultipartFile (MultipartFile multipartFile) throws IOException {
        return new XSSFWorkbook(multipartFile.getInputStream());
    }

    public static int getSheetTitleRowIndex (XSSFSheet xssfSheet) throws SheetTitleNotFoundException {
        for (Row row : xssfSheet) {
            for (Cell cell : row)
                if (cell.getCellType() == CellType.STRING)
                    return row.getRowNum();
        }

        throw new SheetTitleNotFoundException("The title of the Excel sheet could not be found");
    }

    public static Map<String, Integer> mappingAttributesColumnsIndexes (XSSFSheet xssfSheet, int sheetTitleRowIndex) throws MissingAttributeColumnsException, DuplicateAttributeColumnException {
        Row attributesRow = xssfSheet.getRow(sheetTitleRowIndex + 1);
        Map<String, Integer> mappingColumnsAttributes = new HashMap<>();
        Map<String, String> mappingPrefixes = Map.of(
            "can", "Quantity",
            "n", "Part Number",
            "ap", "Application",
            "pre", "Private Price",
            "cos", "Private Price",
            "pu", "Public Price"
        );


        for (Cell cell : attributesRow) {
            for (Map.Entry<String, String> mappingPrefix : mappingPrefixes.entrySet()) {
                if (cell.getCellType() == CellType.STRING) {

                    if (cell.getStringCellValue().trim().toLowerCase().startsWith(mappingPrefix.getKey())) {
                        if (mappingColumnsAttributes.get(mappingPrefix.getValue()) == null) {
                            mappingColumnsAttributes.put(mappingPrefix.getValue(), cell.getColumnIndex());
                            break;
                        }
                        else
                            throw new DuplicateAttributeColumnException("One or more columns are duplicated.");
                    }
                }
            }

            if (mappingColumnsAttributes.size() == 5)
                return mappingColumnsAttributes;
        }

        throw new MissingAttributeColumnsException("The map size is invalid");
    }

    public static ArrayList<ProductEntity> getProducts (XSSFSheet xssfSheet, int sheetTitleRowIndex, Map<String, Integer> helperMap) {
        ArrayList<ProductEntity> products = new ArrayList<>();

        for (int i = sheetTitleRowIndex + 2; i < xssfSheet.getLastRowNum() + 1; i++) {
            Row actualRow = xssfSheet.getRow(i);

            Short quantity = getShortCellValue(actualRow.getCell(helperMap.get("Quantity")));
            String partNumber = getStringCellValue(actualRow.getCell(helperMap.get("Part Number")));
            String application = getStringCellValue(actualRow.getCell(helperMap.get("Application")));
            Float privatePrice = getFloatCellValue(actualRow.getCell(helperMap.get("Private Price")));
            Float publicPrice = getFloatCellValue(actualRow.getCell(helperMap.get("Public Price")));

            products.add(new ProductEntity(quantity, partNumber, application, privatePrice, publicPrice));
        }

        return products;
    }


}
