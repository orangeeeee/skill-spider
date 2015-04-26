<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ユーザ検索一覧画面</title>
</head>
<body>
<h1>ユーザ検索一覧画面</h1>
<form:form id="oForm" modelAttribute="userSrchLstForm" method="post">
<input type="hidden" id="contextPath" value="${pageContext.servletContext.contextPath}" />
<table>
	<tr>
		<th align="left">ユーザID</th>
		<td>
		</td>
	</tr>
</table>
<c:forEach var="userList" items="${userSrchLstForm.userList}" varStatus="status">
index：<c:out value="${status.index}"/><br>
id：<c:out value="${userList.sUserId}"/>|
名前：<c:out value="${userList.name}"/><br>
</c:forEach>
<br><br>
<input type="button" name="_event_processd" onclick="submitPrc('search')" value="検索">
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