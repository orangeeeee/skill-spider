<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<h1>結果確認画面</h1>
<form:form id="oForm" method="post">
<input type="hidden" id="contextPath" value="${pageContext.servletContext.contextPath}" />
<table>
	<tr>
		<th align="left">名前</th>
		<td><c:out value="${sssionUserForm.name}" /></td>
	</tr>
	<tr>
		<th align="left">パスワード</th>
		<td><c:out value="${sssionUserForm.password}" /></td>
	</tr>
</table>
<input type="button" name="_event_processd" onclick="submitPrc('backRegister')" value="戻る">
<input type="button" name="_event_processd" onclick="submitPrc('registerComp')" value="登録">
</form:form>
<script type="text/javascript">
function submitPrc(btnName) {
	var contextPath = document.getElementById('contextPath').value;
	var oForm = document.getElementById('oForm');
	oForm.method = "post";
	oForm.action = contextPath + "/" + btnName;
	oForm.submit();
}
</script>
</body>
</html>