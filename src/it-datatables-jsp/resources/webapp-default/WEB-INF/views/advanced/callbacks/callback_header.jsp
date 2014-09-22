<%@ page pageEncoding="UTF-8"%>
<%@ taglib prefix="datatables" uri="http://github.com/dandelion/datatables" %>

<!DOCTYPE html>
<html>
<head>
<script>
	function callback(nHead, aData, iStart, iEnd, aiDisplay) {
		console.log(nHead);
		console.log(aData);
		console.log(iStart);
		console.log(iEnd);
		console.log(aiDisplay);
	}
</script>
</head>
<body>
   <datatables:table id="myTableId" data="${persons}">
      <datatables:column title="Id" property="id" />
      <datatables:column title="Firstname" property="firstName" />
      <datatables:column title="LastName" property="lastName" />
      <datatables:column title="City" property="address.town.name" sortable="false" />
      <datatables:column title="Mail" property="mail" />
      <datatables:callback type="header" function="callback" />
   </datatables:table>
</body>
</html>