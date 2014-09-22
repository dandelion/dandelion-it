<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../common/taglib.jsp"%>
<%@ page import="java.lang.Boolean"%>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
   <datatables:table id="myTableId" data="${persons}">
      <datatables:column title="Id" property="id" />
      <datatables:column title="FirstName" property="firstName" />
      <datatables:column title="LastName" property="lastName" />
      <datatables:column title="City" property="address.town.name" />
      <datatables:column title="Mail" property="mail" visible="<%=Boolean.FALSE%>" />
      <datatables:column title="Action" visible="false">
         <input type="hidden" value="Toto" />
      </datatables:column>
   </datatables:table>
</body>
</html>