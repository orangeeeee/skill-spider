<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>ユーザ登録画面</title>
</head>
<body>
<h1>入力画面</h1>
<form:form id="oForm" modelAttribute="userForm" method="post">
<table>
	<tr>
		<th align="left">名前</th>
		<td>
			<form:input path="sUserId" placeholder="山田 太郎"/>
			<form:errors path="sUserId" cssStyle="color: red;"/>
		</td>
	</tr>
	<tr>
		<th align="left">パスワード</th>
		<td>
			<form:input path="password"/>
		</td>
	</tr>
</table>
</form:form>
</body>
</html>