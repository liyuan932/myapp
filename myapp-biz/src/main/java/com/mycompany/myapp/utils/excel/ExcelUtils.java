package com.mycompany.myapp.utils.excel;

import com.mycompany.myapp.utils.DateUtil;
import com.mycompany.myapp.utils.DateUtil.DatePatternEnum;

import com.google.common.base.Preconditions;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.beans.PropertyDescriptor;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;

/**
 * Excel文件生成工具
 */
public class ExcelUtils {

  /**
   * 生成标题行
   *
   * @param sheet  表单对象
   * @param titles LinkedHashMap，顺序与数据对象字段顺序一致,key为字段,value为Title注解
   */
  private static void generateExcelTitle(XSSFSheet sheet, Map<String, Title> titles) {
    XSSFRow row = sheet.createRow(0);
    int col = 0;
    for (Map.Entry<String, Title> entry : titles.entrySet()) {
      Title title = entry.getValue();
      row.createCell(col, title.cellType()).setCellValue(title.value());
      col++;
    }
  }

  /**
   * 生成数据行
   *
   * @param sheet 表单对象
   * @param data  数据列表
   */
  private static void generateExcelDataContent(XSSFSheet sheet, Map<String, Title> titles, List<?> data)
      throws Exception {

    Class clz = data.get(0).getClass();

    for (int i = 0; i < data.size(); i++) {
      XSSFRow row = sheet.createRow(i + 1);
      int col = 0;
      for (Entry<String, Title> entry : titles.entrySet()) {
        Object value = new PropertyDescriptor(entry.getKey(), clz).getReadMethod().invoke(data.get(i));
        if (value != null) {
          int cellType = entry.getValue().cellType();
          XSSFCell cell = row.createCell(col, cellType);
          switch (cellType) {
            case Cell.CELL_TYPE_NUMERIC:
              cell.setCellValue(NumberUtils.toDouble(Objects.toString(value)));
              break;
            default:
              cell.setCellValue(Objects.toString(value));
          }
        }
        col++;
      }
    }
  }

  /**
   * 生成文件名
   */
  private static String generateFileName(Class clz) {
    FileName fileName = (FileName) clz.getAnnotation(FileName.class);
    return fileName.value() + "_" + DateUtil.parseDate2Str(new Date(), DatePatternEnum.DATE_JOIN_TIME_JOIN) + ".xlsx";
  }

  /**
   * 创建Excel工作簿
   */
  private static <T> XSSFWorkbook createWorkbook(List<T> dataList) throws Exception {

    Map<String, Title> titles = titles(dataList.get(0).getClass());

    XSSFWorkbook workbook = new XSSFWorkbook();
    XSSFSheet sheet = workbook.createSheet("sheet");
    generateExcelTitle(sheet, titles);
    generateExcelDataContent(sheet, titles, dataList);

    return workbook;
  }

  /**
   * 生成Excel文件
   *
   * @param dataList 数据列表
   * @param <T>      数据对象
   * @return File对象
   */
  public static <T> File generateFile(List<T> dataList) {

    Preconditions.checkArgument(CollectionUtils.isNotEmpty(dataList));

    File file = new File(System.getProperty("webapp.root"), generateFileName(dataList.get(0).getClass()));
    FileOutputStream fos = null;

    try {
      fos = new FileOutputStream(file);
      createWorkbook(dataList).write(fos);
    } catch (Exception ex) {
      ex.printStackTrace();
    } finally {
      if (fos != null) {
        try {
          fos.close();
        } catch (IOException ex) {
        }
      }
    }

    return file;
  }

  /**
   * 获取所有需要导出的字段信息，key为字段，value为字段信息
   */
  private static Map<String, Title> titles(Class<?> clz) {

    LinkedHashMap<String, Title> map = new LinkedHashMap<>();
    for (Field f : clz.getDeclaredFields()) {
      Title title = f.getAnnotation(Title.class);
      if (title != null) {
        map.put(f.getName(), title);
      }
    }

    return map;
  }
}
