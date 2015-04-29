<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/css/ss01/ss01-common.css">
<title>ユーザ登録確認画面</title>
</head>
<body>
<h1>ユーザ登録確認画面</h1>
<form:form id="oForm" modelAttribute="sssionUserForm" method="post">
<input type="hidden" id="contextPath" value="${pageContext.servletContext.contextPath}" />
<input type="button" name="_event_processd" onclick="submitPrc('ss01\/userSchList')" value="一覧へ戻る">
<br/>
<c:out value="${ message}" />
<table class="info-table">
	<tr>
		<th align="left">ユーザID</th>
		<td><c:out value="${sssionUserForm.sUserId}" /></td>
	</tr>
	<tr>
		<th align="left">名前</th>
		<td><c:out value="${sssionUserForm.name}" /></td>
	</tr>
	<tr>
		<th align="left">email</th>
		<td><c:out value="${sssionUserForm.eMail}" /></td>
	</tr>
	<tr>
		<th align="left">パスワード</th>
		<td><c:out value="${sssionUserForm.password}" /></td>
	</tr>
	<tr>
		<th align="left">パスワード（確認用）</th>
		<td><c:out value="${sssionUserForm.passwordConf}" /></td>
	</tr>
	<tr>
		<th align="left">自己紹介</th>
		<td>
			<textarea rows="5" cols="30" readonly="readonly"><c:out value="${sssionUserForm.introduceMyself}" /></textarea>
		</td>
	</tr>
</table>
<input type="button" name="_event_processd" onclick="submitPrc('ss01\/backRegister')" value="修正">
<input type="button" name="_event_processd" onclick="submitPrc('ss01\/registerComp')" value="登録">
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