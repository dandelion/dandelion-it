<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<!DOCTYPE html>
<html>
<head>
</head>
<body>
   <datatables:table id="myTableId" data="${persons}" row="person">
      <datatables:column title="Id" property="id" />
      <datatables:column title="FirstName" property="firstName" />
      <datatables:column title="LastName" property="lastName" />
      <datatables:column title="City" property="address.town.name" />
      <datatables:column title="Mail" property="mail" />
      <datatables:column sortable="false" cssCellStyle="text-align:center;">
         <datatables:columnHead>
            <input type="checkbox"
               onclick="$('#myTableId').find(':checkbox').attr('checked', this.checked);" />
         </datatables:columnHead>
         <input type="checkbox" value="${person.id}" />
      </datatables:column>
   </datatables:table>
</body>
</html>