package com.mycompany.myapp.utils.excel;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.mycompany.myapp.utils.DateUtil;
import com.mycompany.myapp.utils.DateUtil.DatePatternEnum;
import com.mycompany.myapp.vo.UserVO;
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
import java.lang.reflect.Field;
import java.util.*;

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
        int j = 0;
        for (String key : titles.keySet()) {
            Title title = titles.get(key);
            row.createCell(j, title.cellType()).setCellValue(title.value());
            j++;
        }
    }

    /**
     * 生成数据行
     *
     * @param sheet  表单对象
     * @param titles
     * @param data   数据列表
     * @throws Exception
     */
    private static void generateExcelDataContent(XSSFSheet sheet, Map<String, Title> titles, List<? extends Object> data) throws Exception {

        Class clz = data.get(0).getClass();

        for (int i = 0; i < data.size(); i++) {
            XSSFRow row = sheet.createRow(i + 1);
            int j = 0;
            for (String key : titles.keySet()) {
                Object value = new PropertyDescriptor(key, clz).getReadMethod().invoke(data.get(i));
                if (value != null) {
                    int cellType = titles.get(key).cellType();
                    XSSFCell cell = row.createCell(j, cellType);
                    switch (cellType) {
                        case Cell.CELL_TYPE_NUMERIC:
                            cell.setCellValue(NumberUtils.toDouble(Objects.toString(value)));
                            break;
                        default:
                            cell.setCellValue(Objects.toString(value));
                    }
                }
                j++;
            }
        }
    }


    /**
     * 生成文件名
     * @param clz
     * @return
     */
    private static String generateFileName(Class clz) {
        FileName fileName = (FileName) clz.getAnnotation(FileName.class);
        return fileName.value() + "_" + DateUtil.parseDate2Str(new Date(), DatePatternEnum.DATE_JOIN_TIME_JOIN) + ".xlsx";
    }

    public static void generateExcelFile(List<? extends Object> dataList) throws Exception {

        Preconditions.checkArgument(CollectionUtils.isNotEmpty(dataList));

        Class clz = dataList.get(0).getClass();
        File file = new File(generateFileName(clz));
        Map<String, Title> titles = titles(clz);

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("sheet");
        generateExcelTitle(sheet, titles);
        generateExcelDataContent(sheet, titles, dataList);
        workbook.write(new FileOutputStream(file));
    }

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

    public static void main(String[] args) throws Exception {
        List<UserVO> list = Lists.newArrayList();
        for (int i = 0; i < 10; i++) {
            UserVO user = new UserVO();
            user.setAccount(DateUtil.parseDate2Str(new Date()));
            user.setUsername("1111");
            user.setAge(19);
            user.setGmtCreate(new Date());
            list.add(user);
        }

        ExcelUtils.generateExcelFile(list);
    }
}
