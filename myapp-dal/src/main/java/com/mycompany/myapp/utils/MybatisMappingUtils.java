package com.mycompany.myapp.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * Mybatis映射生成器
 */
public class MybatisMappingUtils {

  private static Map<String, String> typeMap = Maps.newHashMap();

  private static String projectPackageName;
  private static String driverClassName;
  private static String url;
  private static String username;
  private static String password;

  private static String baseJavaDir;
  private static String baseResourcesDir;

  /**
   * main方法
   */
  public static void main(String[] args) throws Exception {
    System.out.println(System.getProperty("user.dir"));

   // MybatisMappingUtils.clear(new String[]{"operation_log"});
   // MybatisMappingUtils.generate(new String[]{"operation_log"});

  }

  /**
   * 初始化数据
   */
  static {
    try {
      String curPackageName = MybatisMappingUtils.class.getPackage().getName();
      projectPackageName = curPackageName.substring(0, curPackageName.lastIndexOf("."));

      baseJavaDir = System.getProperty("user.dir") + "/myapp-dal/src/main/java/";
      baseResourcesDir = System.getProperty("user.dir") + "/myapp-dal/src/main/resources/";

      typeMap.put("int", "Integer");
      typeMap.put("bigint", "Long");
      typeMap.put("decimal", "Double");
      typeMap.put("double", "Double");
      typeMap.put("float", "Float");
      typeMap.put("varchar", "String");
      typeMap.put("char", "String");
      typeMap.put("text", "String");
      typeMap.put("datetime", "Date");
      typeMap.put("date", "Date");
      typeMap.put("timestamp", "Date");

      Properties pro = new Properties();
      InputStream in = MybatisMappingUtils.class.getClassLoader().getResourceAsStream("jdbc.properties");
      pro.load(in);
      driverClassName = pro.getProperty("jdbc.driverClassName");
      url = pro.getProperty("jdbc.url");
      username = pro.getProperty("jdbc.username");
      password = pro.getProperty("jdbc.password");
      in.close();
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }

  private static String getProjectPathName(String projectPackageName) {
    return projectPackageName.replaceAll("\\.", "/");
  }

  /**
   * 映射生成器
   */
  public static void generate(String[] tableNames) throws Exception {

    Class.forName(driverClassName);
    Connection conn = DriverManager.getConnection(url, username, password);

    Statement statement = conn.createStatement();

    ResultSet rs = null;
    for (String tableName : tableNames) {

      List<PropertyInfo> propertyInfos = Lists.newArrayList();

      rs = statement.executeQuery("show full columns from  " + tableName);
      while (rs.next()) {
        String columnName = rs.getString("Field");
        String propertyName = columnToProperty(columnName);
        String propertyType = typeMap.get(rs.getString("Type").replaceAll("\\(.*\\)", ""));
        String comment = rs.getString("Comment");
        propertyInfos.add(new PropertyInfo(columnName, propertyName, propertyType, comment));
      }

      String className = tableNameToClassName(tableName);
      daoobjectGenerator(className, propertyInfos);
      daoGenerator(className, propertyInfos);
      queryGenerator(className, propertyInfos);
      sqlmapGenerator(tableName, className, propertyInfos);
    }

    statement.close();
    rs.close();
    conn.close();
  }

  private static void queryGenerator(String className, List<PropertyInfo> propertyInfos) throws Exception {
    File file = new File(baseJavaDir + getProjectPathName(projectPackageName) + "/query/" + className + "Query.java");
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
    writer.write("package " + projectPackageName + ".query;\n\n");
    writer.write("import " + projectPackageName + ".daoobject." + className + ";\n");
    writer.write("\npublic class " + className + "Query extends " + className + " {\n");
    writer.write("}\n");
    writer.close();
  }

  private static void daoGenerator(String className, List<PropertyInfo> propertyInfos) throws Exception {
    File file = new File(baseJavaDir + getProjectPathName(projectPackageName) + "/dao/" + className + "DAO.java");
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
    writer.write("package " + projectPackageName + ".dao;\n\n");
    writer.write("import " + projectPackageName + ".base.BaseDAO;\n");
    writer.write("import " + projectPackageName + ".daoobject." + className + ";\n");
    writer.write("\npublic interface " + className + "DAO extends BaseDAO<" + className + "> {\n");
    writer.write("}\n");
    writer.close();
  }

  private static Map<String, PropertyInfo> getPropertyInfoMap(List<PropertyInfo> propertyInfos) {
    Map<String, PropertyInfo> propertyInfoMap = Maps.newHashMap();

    for (PropertyInfo info : propertyInfos) {
      propertyInfoMap.put(info.getProperty(), info);
    }
    return propertyInfoMap;
  }

  private static void sqlmapGenerator(String tableName, String className, List<PropertyInfo> propertyInfos)
      throws IOException {

    File file = new File(baseResourcesDir + "/sqlmap/mapping-" + tableName + ".xml");
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
    writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
    writer.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis"
                     + ".org/dtd/mybatis-3-mapper.dtd\" >\n");
    writer.write("<mapper namespace=\"" + projectPackageName + ".dao." + className + "DAO\">\n");
    writer.write(resultMap(className, propertyInfos));
    writer.write(baseColumn(propertyInfos));
    writer.write(queryParams(propertyInfos));
    writer.write(orderBy());
    writer.write(insert(tableName, propertyInfos));
    writer.write(batchInsert(tableName, propertyInfos));
    writer.write(update(tableName, propertyInfos));
    writer.write(delete(tableName));
    writer.write(batchDelete(tableName));
    writer.write(getById(tableName));
    writer.write(queryByIds(tableName));
    writer.write(queryList(tableName));
    writer.write(count(tableName));
    writer.write(queryPage(tableName));
    //writer.write(updateStatus(tableName));
    //writer.write(batchUpdateStatus(tableName));
    writer.write("</mapper>");

    writer.close();
  }

  private static void daoobjectGenerator(String className, List<PropertyInfo> propertyInfos) throws IOException {

    File file = new File(baseJavaDir + getProjectPathName(projectPackageName) + "/daoobject/" + className + ".java");
    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), "utf-8");
    writer.write("package " + projectPackageName + ".daoobject;\n\n");
    writer.write("import java.util.Date;\n");

    writer.write("import " + projectPackageName + ".base.BaseDO;\n");
    writer.write("\npublic class " + className + " extends BaseDO {\n");
    writer.write("\n");
    for (PropertyInfo info : propertyInfos) {
      writer.write("\tprivate " + info.getType() + " " + info.getProperty() + "; //" + info.getComment() + " \n");
    }
    writer.write("\n");
    for (PropertyInfo info : propertyInfos) {
      String type = info.getType();
      String property = info.getProperty();
      writer.write("\tpublic " + type + " get" + StringUtils.capitalize(property) + "() {\n");
      writer.write("\t\treturn " + property + ";\n");
      writer.write("\t}\n\n");
      writer.write("\tpublic void set" + StringUtils.capitalize(property) + "(" + type + " " + property + ") {\n");
      writer.write("\t\tthis." + property + " = " + property + ";\n");
      writer.write("\t}\n\n");
    }
    writer.write("}\n");
    writer.close();
  }

  private static String resultMap(String className, List<PropertyInfo> propertyInfos) {

    StringBuffer buff = new StringBuffer();
    buff.append("\t<resultMap id=\"BaseResultMap\" type=\"" + className + "\">\n");
    for (PropertyInfo info : propertyInfos) {
      String property = info.getProperty();
      String column = info.getColumn();
      if (info.getProperty().equals("id")) {
        buff.append("\t\t<id column=\"id\" property=\"id\"/>\n");
      } else {
        buff.append("\t\t<result column=\"" + column + "\" property=\"" + property + "\"/>\n");
      }
    }
    buff.append("\t</resultMap>\n\n");

    return buff.toString();
  }

  private static String baseColumn(List<PropertyInfo> propertyInfos) {
    return "\t<sql id=\"baseColumn\">\n" + "\t\t" + getBaseColumn(propertyInfos) + "\n" + "\t</sql>\n\n";
  }

  private static String queryParams(List<PropertyInfo> propertyInfos) {
    StringBuffer buff = new StringBuffer();
    for (PropertyInfo info : propertyInfos) {
      buff.append(
          "\t\t\t<if test=\"" + info.getProperty() + " !=null\">\n" + "\t\t\t\tand " + info.getColumn() + "=#{" + info
              .getProperty() + "}\n" + "\t\t\t</if>\n");
    }

    return "\t<sql id=\"queryParams\">\n" + "\t\t<where>\n" + buff.toString() + "\t\t</where>\n" + "\t</sql>\n\n";
  }

  private static String orderBy() {
    return "\t<sql id=\"orderBy\">\n" + "\t</sql>\n\n";
  }

  private static String insert(String tableName, List<PropertyInfo> propertyInfos) {
    return "\t<insert id=\"insert\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n" + "\t\tinsert into " + tableName
        + " (" + getInsertColumn(propertyInfos) + ") values\n" + "\t\t(" + getInsertProperty(propertyInfos) + ")\n"
        + "\t</insert>\n\n";
  }

  private static String batchInsert(String tableName, List<PropertyInfo> propertyInfos) {

    return "\t<insert id=\"batchInsert\">\n" + "\t\tinsert into " + tableName + " (" + getInsertColumn(propertyInfos)
        + ") values\n" + "\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">\n" + "\t\t\t("
        + getBatchInsertProperty(propertyInfos) + ")\n" + "\t\t</foreach>\n" + "\t</insert>\n\n";
  }

  private static String update(String tableName, List<PropertyInfo> propertyInfos) {

    StringBuffer buff = new StringBuffer();
    buff.append("\t<update id=\"update\">\n");
    buff.append("\t\tupdate " + tableName + "\n\t\tset ");
    for (PropertyInfo info : propertyInfos) {
      if (info.getColumn().equals("id")) {
        continue;
      }

      if (info.getColumn().equals("gmt_modified")) {
        buff.append(info.getColumn() + "=now(),");
      } else {
        buff.append(info.getColumn() + "=#{" + info.getProperty() + "},");
      }
    }
    buff = new StringBuffer(buff.substring(0, buff.length() - 1));

    buff.append("\n\t\twhere id = #{id}\n");
    buff.append("\t</update>\n\n");

    return buff.toString();
  }

  private static String delete(String tableName) {

    return "\t<delete id=\"delete\">\n" + "\t\tdelete from " + tableName + " where id = #{id}\n" + "\t</delete>\n\n";
  }

  private static String batchDelete(String tableName) {

    return "\t<delete id=\"batchDelete\">\n" + "\t\tdelete from " + tableName + "\n" + "\t\t<where>\n"
        + "\t\t\t<choose>\n" + "\t\t\t\t<when test=\"list != null and list.size()>0\">\n" + "\t\t\t\t\tid in\n"
        + "\t\t\t\t\t<foreach collection=\"list\" item=\"id\" separator=\",\" open=\"(\" close=\")\">\n"
        + "\t\t\t\t\t\t#{id}\n" + "\t\t\t\t\t</foreach>\n" + "\t\t\t\t</when>\n" + "\t\t\t\t<otherwise>\n"
        + "\t\t\t\t\t1=0\n" + "\t\t\t\t</otherwise>\n" + "\t\t\t</choose>\n" + "\t\t</where>\n" + "\t</delete>\n\n";
  }

  private static String getById(String tableName) {
    return "\t<select id=\"getById\" resultMap=\"BaseResultMap\">\n"
        + "\t\tselect <include refid=\"baseColumn\" /> from " + tableName + "\n" + "where id = #{id}\n"
        + "\t</select>\n\n";
  }

  private static String queryByIds(String tableName) {
    return "\t<select id=\"queryByIds\" resultMap=\"BaseResultMap\">\n"
        + "\t\tselect <include refid=\"baseColumn\" /> from " + tableName + "\n" + "\t\t<where>\n" + "\t\t\t<choose>\n"
        + "\t\t\t\t<when test=\"list != null and list.size()>0\">\n" + "\t\t\t\t\tid in\n"
        + "\t\t\t\t\t<foreach collection=\"list\" item=\"id\" separator=\",\" open=\"(\" close=\")\">\n"
        + "\t\t\t\t\t\t#{id}\n" + "\t\t\t\t\t</foreach>\n" + "\t\t\t\t</when>\n" + "\t\t\t\t<otherwise>\n"
        + "\t\t\t\t\t1=0\n" + "\t\t\t\t</otherwise>\n" + "\t\t\t</choose>\n" + "\t\t</where>\n" + "\t</select>\n\n";
  }

  private static String queryList(String tableName) {
    return "<select id=\"queryList\" resultMap=\"BaseResultMap\">\n"
        + "\t\tselect <include refid=\"baseColumn\" /> from " + tableName + "\n"
        + "\t\t<include refid=\"queryParams\" />\n" + "\t\t<include refid=\"orderBy\"/>\n" + "\t</select>\n\n";
  }

  private static String count(String tableName) {
    return "\t<select id=\"count\" resultType=\"int\">\n" + "\t\tselect count(1) from " + tableName + "\n"
        + "\t\t<include refid=\"queryParams\" />\n" + "\t</select>\n\n";
  }

  private static String queryPage(String tableName) {
    return "\t<select id=\"queryPage\" resultMap=\"BaseResultMap\">\n"
        + "\t\tselect <include refid=\"baseColumn\" /> from " + tableName + "\n"
        + "\t\t<include refid=\"queryParams\" />\n" + "\t</select>\n\n";
  }

  private static String updateStatus(String tableName) {
    return "\t<update id=\"updateStatus\">\n" + "\t\tupdate " + tableName + " set status=#{status}\n"
        + "\t\twhere id=#{id}\n" + "\t</update>\n\n";
  }

  private static String batchUpdateStatus(String tableName) {
    return "\t<update id=\"batchUpdateStatus\">\n" + "\t\tupdate " + tableName + " set status=#{status}\n"
        + "\t\t<where>\n" + "\t\t\t<choose>\n" + "\t\t\t\t<when test=\"list != null and list.size()>0\">\n"
        + "\t\t\t\t\tid in\n"
        + "\t\t\t\t\t<foreach collection=\"list\" item=\"id\" separator=\",\" open=\"(\" close=\")\">\n"
        + "\t\t\t\t\t\t#{id}\n" + "\t\t\t\t\t</foreach>\n" + "\t\t\t\t</when>\n" + "\t\t\t\t<otherwise>\n"
        + "\t\t\t\t\t1=0\n" + "\t\t\t\t</otherwise>\n" + "\t\t\t</choose>\n" + "\t\t</where>\n" + "\t</update>\n\n";
  }

  private static String getInsertColumn(List<PropertyInfo> propertyInfos) {

    StringBuffer insertColumn = new StringBuffer();
    for (PropertyInfo info : propertyInfos) {
      if (!info.getColumn().equals("id")) {
        insertColumn.append(info.getColumn()).append(",");
      }
    }

    return insertColumn.substring(0, insertColumn.length() - 1);
  }

  private static String getInsertProperty(List<PropertyInfo> propertyInfos) {
    StringBuffer batchInsertProperty = new StringBuffer();
    for (PropertyInfo info : propertyInfos) {
      if (info.getColumn().equals("gmt_modified") || info.getColumn().equals("gmt_create")) {
        batchInsertProperty.append("now(),");
        continue;
      }

      if (!info.getProperty().equals("id")) {
        batchInsertProperty.append("#{").append(info.getProperty()).append("},");
      }
    }
    return batchInsertProperty.substring(0, batchInsertProperty.length() - 1);
  }

  private static String getBatchInsertProperty(List<PropertyInfo> propertyInfos) {
    StringBuffer batchInsertProperty = new StringBuffer();
    for (PropertyInfo info : propertyInfos) {
      if (info.getColumn().equals("gmt_modified") || info.getColumn().equals("gmt_create")) {
        batchInsertProperty.append("now(),");
        continue;
      }

      if (!info.getProperty().equals("id")) {
        batchInsertProperty.append("#{item.").append(info.getProperty()).append("},");
      }
    }
    return batchInsertProperty.substring(0, batchInsertProperty.length() - 1);
  }

  private static String getBaseColumn(List<PropertyInfo> propertyInfos) {

    StringBuffer baseColumn = new StringBuffer();
    for (PropertyInfo info : propertyInfos) {
      baseColumn.append(info.getColumn()).append(",");
    }

    return baseColumn.substring(0, baseColumn.length() - 1);
  }

  /**
   * 表字段转换成java属性
   *
   * @param column 表字段
   * @return java属性
   */
  private static String columnToProperty(String column) {

    StringBuffer buff = new StringBuffer();
    for (String s : StringUtils.split(column, "_")) {
      if (buff.length() == 0) {
        buff.append(s);
      } else {
        buff.append(StringUtils.capitalize(s));
      }
    }

    return buff.toString();
  }

  /**
   * 表名转换成类名
   *
   * @param tableName 表名
   * @return 类名
   */
  private static String tableNameToClassName(String tableName) {

    StringBuffer buff = new StringBuffer();
    for (String s : StringUtils.split(tableName, "_")) {
      buff.append(StringUtils.capitalize(s));
    }

    return buff.toString();
  }

  private static class PropertyInfo {
    private String column;
    private String property;
    private String type;
    private String comment;

    public PropertyInfo(String column, String property, String type, String comment) {
      this.column = column;
      this.property = property;
      this.type = type;
      this.comment = comment;
    }

    public String getColumn() {
      return column;
    }

    public String getProperty() {
      return property;
    }

    public String getType() {
      return type;
    }

    public String getComment() {
      return comment;
    }
  }

  /**
   * 清除映射文件
   */
  public static void clear(String[] tableNames) {
    for (String tableName : tableNames) {
      String className = tableNameToClassName(tableName);
      Preconditions.checkState(
          new File(baseJavaDir + getProjectPathName(projectPackageName) + "/daoobject/" + className + ".java")
              .delete());
      Preconditions.checkState(
          new File(baseJavaDir + getProjectPathName(projectPackageName) + "/dao/" + className + "DAO.java").delete());
      Preconditions.checkState(
          new File(baseJavaDir + getProjectPathName(projectPackageName) + "/query/" + className + "Query.java")
              .delete());
      Preconditions.checkState(new File(baseResourcesDir + "/sqlmap/mapping-" + tableName + ".xml").delete());
    }
  }
}

