<%@ page pageEncoding="UTF-8"%>
<%@ include file="../../common/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<script>
function callback(oSettings) {
	console.log(oSettings);
}
</script>
</head>
<body>
   <datatables:table id="myTableId" data="${persons}">
      <datatables:column title="Id" property="id" />
      <datatables:column title="Firstname" property="firstName" />
      <datatables:column title="LastName" property="lastName" />
      <datatables:column title="City" property="address.town.name" />
      <datatables:column title="Mail" property="mail" />
      <datatables:callback type="draw" function="callback" />
   </datatables:table>
</body>
</html>