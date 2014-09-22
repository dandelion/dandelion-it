<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
   <div id="firstNameFilter"></div>
   <div id="lastNameFilter"></div>
   <div id="cityFilter"></div>
   
   <datatables:table id="myTableId" data="${persons}">
      <datatables:column title="Id" property="id" />
      <datatables:column title="FirstName" property="firstName" filterable="true" filterType="select" selector="#firstNameFilter" />
      <datatables:column title="LastName" property="lastName" filterable="true" filterType="select" selector="#lastNameFilter" />
      <datatables:column title="City" property="address.town.name" filterable="true" filterType="select" selector="#cityFilter" />
      <datatables:column title="Mail" property="mail" />
   </datatables:table>
</body>
</html>