package com.mycompany.myapp.utils;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Map;

/**
 * Mybasis映射生成器
 */
public class MybatisMappingUtils {

    private static Map<String,String> typeMap = Maps.newHashMap();

    private static String[] tableNames = {"user","lesson"};

    public MybatisMappingUtils(){
        typeMap.put("int","Integer");
        typeMap.put("bigint","Long");
        typeMap.put("decimal","Double");
        typeMap.put("double","Double");
        typeMap.put("float","Float");
        typeMap.put("varchar","String");
        typeMap.put("char","String");
        typeMap.put("text","String");
        typeMap.put("datetime","Date");
        typeMap.put("date","Date");
        typeMap.put("timestamp","Date");
    }

    public static void main(String[] args) throws Exception {
        new MybatisMappingUtils().mappingGenerator();
    }

    public void mappingGenerator() throws Exception{
        Class.forName("com.mysql.jdbc.Driver");
        Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "1234");

        Statement statement = conn.createStatement();

        for(String tableName : tableNames){
            String className = tableNameToClassName(tableName);
            List<PropertyInfo> propertyInfos = Lists.newArrayList();

            ResultSet rs = statement.executeQuery("show full columns from  " + tableName);
            while(rs.next()){
                String columnName = rs.getString("Field");
                String propertyName = columnToProperty(columnName);
                String propertyType = typeMap.get(rs.getString("Type").replaceAll("\\(.*\\)",""));
                String comment = rs.getString("Comment");
                propertyInfos.add(new PropertyInfo(columnName,propertyName,propertyType,comment));
            }

            System.out.println(JSON.toJSONString(propertyInfos));

            beanGenerator(className,propertyInfos);
            sqlmapGenerator(tableName, className,propertyInfos);
        }
    }

    private void sqlmapGenerator(String tableName, String className, List<PropertyInfo> propertyInfos) throws IOException {

        File  file = new File(System.getProperty("user.dir"),"src/main/resources/sqlmap/mapping-" + tableName +".xml");
        FileWriter writer = new FileWriter(file);
        writer.write("<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n");
        writer.write("<!DOCTYPE mapper PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\" \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\" >\n");
        writer.write("<mapper namespace=\"com.mycompany.myapp.dao."+className+"Dao\">\n");
        writer.write(resultMap(className,propertyInfos));
        writer.write(baseColumn(propertyInfos));
        writer.write(queryParams());
        writer.write(orderBy());
        writer.write(insert(tableName,propertyInfos));
        writer.write(batchInsert(tableName,propertyInfos));
        writer.write(update(tableName,propertyInfos));
        writer.write(delete(tableName));
        writer.write(batchDelete(tableName));
        writer.write(getById(tableName));
        writer.write(queryByIds(tableName));
        writer.write(queryList(tableName));
        writer.write(count(tableName));
        writer.write(queryPage(tableName));
        writer.write(updateStatus(tableName));
        writer.write(batchUpdateStatus(tableName));
        writer.write("</mapper>");

        writer.close();
    }

    private void beanGenerator(String className,List<PropertyInfo> propertyInfos) throws IOException {

        File file = new File(System.getProperty("user.dir"),"src/main/java/com/mycompany/myapp/domain/" + className +".java");
        FileWriter writer = new FileWriter(file);
        writer.write("package com.mycompany.myapp.domain;\n");
        writer.write("import com.mycompany.myapp.base.BaseDO;\n");
        writer.write("import java.util.Date;\n");

        writer.write("public class "+className+" extends BaseDO {\n");
        writer.write("\n");
        for(PropertyInfo info : propertyInfos){
            writer.write("private " + info.getType() + " " + info.getProperty()+"; //"+ info.getComment()+" \n");
        }
        for(PropertyInfo info : propertyInfos){
            String type = info.getType();
            String property = info.getProperty();
            writer.write("public " + type + " get" + StringUtils.capitalize(property)+"(){\n");
            writer.write("return " + property +";\n");
            writer.write("}\n");
            writer.write("public void set" + StringUtils.capitalize(property) +"("+ type + " " + property +"){\n");
            writer.write("this." + property + " = " + property +";\n");
            writer.write("}\n");
        }
        writer.write("}\n");
        writer.close();
    }

    private String resultMap(String className, List<PropertyInfo> propertyInfos){

        StringBuffer buff = new StringBuffer();
        buff.append("\t<resultMap id=\"BaseResultMap\" type=\""+className+"\">\n");
        for(PropertyInfo info : propertyInfos){
            String property = info.getProperty();
            String column = info.getColumn();
            if(info.getProperty().equals("id")){
                buff.append("\t\t<id column=\"id\" property=\"id\"/>\n");
            }else{
                buff.append("\t\t<result column=\""+column+"\" property=\""+property+"\"/>\n");
            }
        }
        buff.append("\t</resultMap>\n\n");

        return buff.toString();
    }

    private String baseColumn(List<PropertyInfo> propertyInfos){
        return "\t<sql id=\"baseColumn\">\n" +
                "\t\t" + getBaseColumn(propertyInfos) + "\n" +
                "\t</sql>\n\n";
    }

    private String queryParams() {
        return "\t<sql id=\"queryParams\">\n" +
                "\t</sql>\n\n";

    }

    private String orderBy(){
        return "\t<sql id=\"orderBy\">\n" +
                "\t\torder by gmt_modified desc\n" +
                "\t</sql>\n\n";
    }


    private String insert(String tableName,List<PropertyInfo> propertyInfos){
        return "\t<insert id=\"insert\" useGeneratedKeys=\"true\" keyProperty=\"id\">\n"+
                "\t\tinsert into "+tableName + " ("+ getInsertColumn(propertyInfos)+ ") values\n"+
                "\t\t("+getInsertProperty(propertyInfos)+")\n"+
                "\t</insert>\n\n";
    }

    private String batchInsert(String tableName,List<PropertyInfo> propertyInfos){

        return "\t<insert id=\"batchInsert\">\n" +
                "\t\tinsert into "+tableName + " ("+ getInsertColumn(propertyInfos)+ ") values\n" +
                "\t\t<foreach collection=\"list\" item=\"item\" separator=\",\">\n" +
                "\t\t\t("+getBatchInsertProperty(propertyInfos)+")\n" +
                "\t\t</foreach>\n" +
                "\t</insert>\n\n";
    }

    private String update(String tableName,List<PropertyInfo> propertyInfos) {

        StringBuffer buff = new StringBuffer();
        buff.append("\t<update id=\"update\">\n");
        buff.append("\t\tupdate "+tableName+"\n\t\tset ");
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
        buff = new StringBuffer(buff.substring(0,buff.length()-1));

        buff.append("\n\t\twhere id = #{id}\n");
        buff.append("\t</update>\n\n");

        return buff.toString();
    }

    private String delete(String tableName) {

        return "\t<delete id=\"delete\">\n" +
                "\t\tdelete from " + tableName + " where id = #{id}\n" +
                "\t</delete>\n\n";
    }
    private String batchDelete(String tableName) {

        return "\t<delete id=\"batchDelete\">\n" +
                "\t\tdelete from "+tableName+"\n" +
                "\t\t<where>\n" +
                "\t\t\t<choose>\n" +
                "\t\t\t\t<when test=\"list != null and list.size()>0\">\n" +
                "\t\t\t\t\tid in\n" +
                "\t\t\t\t\t<foreach collection=\"list\" item=\"id\" separator=\",\" open=\"(\" close=\")\">\n" +
                "\t\t\t\t\t\t#{id}\n" +
                "\t\t\t\t\t</foreach>\n" +
                "\t\t\t\t</when>\n" +
                "\t\t\t\t<otherwise>\n" +
                "\t\t\t\t\t1=0\n" +
                "\t\t\t\t</otherwise>\n" +
                "\t\t\t</choose>\n" +
                "\t\t</where>\n" +
                "\t</delete>\n\n";
    }
    private String getById(String tableName) {
        return "\t<select id=\"getById\" resultMap=\"BaseResultMap\">\n" +
                "\t\tselect <include refid=\"baseColumn\" /> from "+tableName+"\n"+
                "where id = #{id}\n" +
                "\t</select>\n\n";
    }
    private String queryByIds(String tableName) {
        return "\t<select id=\"queryByIds\" resultMap=\"BaseResultMap\">\n" +
                "\t\tselect <include refid=\"baseColumn\" /> from "+tableName+"\n"+
                "\t\t<where>\n" +
                "\t\t\t<choose>\n" +
                "\t\t\t\t<when test=\"list != null and list.size()>0\">\n" +
                "\t\t\t\t\tid in\n" +
                "\t\t\t\t\t<foreach collection=\"list\" item=\"id\" separator=\",\" open=\"(\" close=\")\">\n" +
                "\t\t\t\t\t\t#{id}\n" +
                "\t\t\t\t\t</foreach>\n" +
                "\t\t\t\t</when>\n" +
                "\t\t\t\t<otherwise>\n" +
                "\t\t\t\t\t1=0\n" +
                "\t\t\t\t</otherwise>\n" +
                "\t\t\t</choose>\n" +
                "\t\t</where>\n" +
                "\t</select>\n\n";
    }
    private String queryList(String tableName) {
        return "<select id=\"queryList\" resultMap=\"BaseResultMap\">\n" +
                "\t\tselect <include refid=\"baseColumn\" /> from "+tableName+"\n"+
                "\t\t<include refid=\"queryParams\" />\n" +
                "\t\t<include refid=\"orderBy\"/>\n" +
                "\t</select>\n\n";
    }

    private String count(String tableName) {
        return "\t<select id=\"count\" resultType=\"int\">\n" +
                "\t\tselect count(1) from "+tableName+"\n" +
                "\t\t<include refid=\"queryParams\" />\n" +
                "\t</select>\n\n";
    }
    private String queryPage(String tableName) {
        return "\t<select id=\"queryPage\" resultMap=\"BaseResultMap\">\n" +
                "\t\tselect <include refid=\"baseColumn\" /> from "+tableName+"\n"+
                "\t\t<include refid=\"queryParams\" />\n" +
                "\t</select>\n\n";
    }
    private String updateStatus(String tableName) {
        return "\t<update id=\"updateStatus\">\n" +
                "\t\tupdate "+tableName+" set status=#{status}\n"+
                "\t\twhere id=#{id}\n" +
                "\t</update>\n\n";
    }
    private String batchUpdateStatus(String tableName) {
        return "\t<update id=\"batchUpdateStatus\">\n" +
                "\t\tupdate "+tableName+" set status=#{status}\n" +
                "\t\t<where>\n" +
                "\t\t\t<choose>\n" +
                "\t\t\t\t<when test=\"list != null and list.size()>0\">\n" +
                "\t\t\t\t\tid in\n" +
                "\t\t\t\t\t<foreach collection=\"list\" item=\"id\" separator=\",\" open=\"(\" close=\")\">\n" +
                "\t\t\t\t\t\t#{id}\n" +
                "\t\t\t\t\t</foreach>\n" +
                "\t\t\t\t</when>\n" +
                "\t\t\t\t<otherwise>\n" +
                "\t\t\t\t\t1=0\n" +
                "\t\t\t\t</otherwise>\n" +
                "\t\t\t</choose>\n" +
                "\t\t</where>\n" +
                "\t</update>\n\n";
    }


    private String getInsertColumn(List<PropertyInfo> propertyInfos) {

        StringBuffer insertColumn = new StringBuffer();
        for(PropertyInfo info : propertyInfos){
            if(!info.getColumn().equals("id")){
                insertColumn.append(info.getColumn()).append(",");
            }
        }

        return insertColumn.substring(0,insertColumn.length()-1);
    }

    private String getInsertProperty(List<PropertyInfo> propertyInfos){
        StringBuffer batchInsertProperty = new StringBuffer();
        for(PropertyInfo info : propertyInfos){
            if(info.getColumn().equals("gmt_modified") || info.getColumn().equals("gmt_create")){
                batchInsertProperty.append("now(),");
                continue;
            }

            if(!info.getColumn().equals("id")){
                batchInsertProperty.append("#{").append(info.getColumn()).append("},");
            }
        }
        return batchInsertProperty.substring(0,batchInsertProperty.length()-1);
    }

    private String getBatchInsertProperty(List<PropertyInfo> propertyInfos){
        StringBuffer batchInsertProperty = new StringBuffer();
        for(PropertyInfo info : propertyInfos){
            if(info.getColumn().equals("gmt_modified") || info.getColumn().equals("gmt_create")){
                batchInsertProperty.append("now(),");
                continue;
            }

            if(!info.getColumn().equals("id")){
                batchInsertProperty.append("#{item.").append(info.getColumn()).append("},");
            }
        }
        return batchInsertProperty.substring(0,batchInsertProperty.length()-1);
    }


    private String getBaseColumn(List<PropertyInfo> propertyInfos) {

        StringBuffer baseColumn = new StringBuffer();
        for(PropertyInfo info : propertyInfos){
                baseColumn.append(info.getColumn()).append(",");
        }

        return baseColumn.substring(0,baseColumn.length()-1);
    }

    public  String columnToProperty(String column){

        StringBuffer buff = new StringBuffer();
       for(String s : StringUtils.split(column,"_")){
            if(buff.length() == 0){
                buff.append(s);
            }else{
                buff.append(StringUtils.capitalize(s));
            }
       }

        return buff.toString();
    }

    public String tableNameToClassName(String tableName){

        StringBuffer buff = new StringBuffer();
        for(String s : StringUtils.split(tableName,"_")){
            buff.append(StringUtils.capitalize(s));
        }

        return buff.toString();
    }


    private class PropertyInfo{
        private String column;
        private String property;
        private String type;
        private String comment;

        public PropertyInfo(String column, String property, String type,String comment) {
            this.column = column;
            this.property = property;
            this.type = type;
            this.comment = comment;
        }

        public String getColumn() {
            return column;
        }

        public void setColumn(String column) {
            this.column = column;
        }

        public String getProperty() {
            return property;
        }

        public void setProperty(String property) {
            this.property = property;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getComment() {
            return comment;
        }

        public void setComment(String comment) {
            this.comment = comment;
        }
    }
}

