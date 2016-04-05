<%@ page language= "java" contentType= "text/html; charset=UTF-8" pageEncoding= "GBK"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
totalCount:<spring:bind path="model.totalCount">${status.value}</spring:bind><br/>
discount:<spring:bind path="model.discount">${status.value}</spring:bind><br/>
sumMoney:<spring:bind path="model.sumMoney">${status.value}</spring:bind><br/>
registerDate:<spring:bind path="model.registerDate">${status.value}</spring:bind><br/>
orderDate:<spring:bind path="model.orderDate">${status.value}</spring:bind><br/>
<br/><br/>
<form:form commandName="model">
    <form:input path="sumMoney"/>
</form:form>
