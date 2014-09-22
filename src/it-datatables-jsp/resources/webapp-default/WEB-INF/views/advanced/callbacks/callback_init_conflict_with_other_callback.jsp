<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<!DOCTYPE html>
<html>
<head>
<script>
	function callback1(oSettings, json) {
		console.log(oSettings);
		console.log(json);
	}
	function callback2(oSettings, json) {
		console.log(oSettings);
		console.log(json);
	}
</script>
</head>
<body>
   <datatables:table id="myTableId" url="/persons">
      <datatables:column title="Id" property="id" />
      <datatables:column title="Firstname" property="firstName" />
      <datatables:column title="LastName" property="lastName" />
      <datatables:column title="City" property="address.town.name" sortable="false" />
      <datatables:column title="Mail" property="mail" />
      <datatables:callback type="init" function="callback1" />
      <datatables:callback type="init" function="callback2" />
   </datatables:table>
</body>
</html>