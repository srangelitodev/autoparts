package org.srangelito.autoparts.controller;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.srangelito.autoparts.entity.ProductEntity;
import org.srangelito.autoparts.exception.ExcelFormatException;
import org.srangelito.autoparts.exception.MissingAttributeColumnsException;
import org.srangelito.autoparts.exception.SheetTitleNotFoundException;
import org.srangelito.autoparts.exception.UnsupportedFileTypeException;
import org.srangelito.autoparts.utils.excel.ExcelUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

@Controller
public class MenuController {

    @GetMapping(value = "/menu")
    public String showMenuPage () {
        return "menu";
    }

    @GetMapping ("/menu/products")
    public String showProductsPage () {
        return "products";
    }

    @GetMapping ("/menu/sells")
    public String showSellsPage () {
        return "sells";
    }

    @GetMapping ("/menu/data_load")
    public String showDataLoadPage() {
        return "load";
    }

    @PostMapping ("/menu/data_load")
    public String showDataLoadPage (@RequestParam("excelFile") MultipartFile file) {

        try {
            ExcelUtils.validateExcelFile(file);
        }
        catch (UnsupportedFileTypeException e) {
            e.printStackTrace();
            return "load";
        }

        XSSFWorkbook xssfWorkbook;
        int sheetTitleRowIndex;
        Map<String, Integer> helperMap;
        ArrayList<ProductEntity> productEntitys;
        try {
            xssfWorkbook = ExcelUtils.getXssfWorkbookByMultipartFile(file);
        }
        catch (IOException e) {
            e.printStackTrace();
            return "load";
        }

        try {
            sheetTitleRowIndex = ExcelUtils.getSheetTitleRowIndex(xssfWorkbook.getSheetAt(0));
        }
        catch (SheetTitleNotFoundException e) {
            e.printStackTrace();
            return "load";
        }

        try {
            helperMap = ExcelUtils.mappingAttributesColumnsIndexes(xssfWorkbook.getSheetAt(0), sheetTitleRowIndex);
        }
        catch (ExcelFormatException e) {
            e.printStackTrace();
            return "load";
        }

        productEntitys = ExcelUtils.getProducts(xssfWorkbook.getSheetAt(0), sheetTitleRowIndex, helperMap);

        for (ProductEntity productEntity : productEntitys) {
            System.out.println(productEntity.toString());
        }

        return "load";
    }

}
