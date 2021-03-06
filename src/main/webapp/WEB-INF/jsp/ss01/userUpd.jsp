<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/css/ss01/ss01-common.css">
<title>ユーザ情報更新画面</title>
</head>
<body>
<h1>ユーザ情報更新画面</h1>
<form:form id="oForm" modelAttribute="userForm" method="post">
<input type="hidden" id="contextPath" value="${pageContext.servletContext.contextPath}" />
<input type="button" name="_event_processd" onclick="submitPrc('ss01\/userSchList')" value="一覧へ戻る">
<br/>
<p class="error-msg">
	<c:out value="${ message}" />
</p>
<table class="info-table">
	<tr>
		<th align="left">ユーザID</th>
		<td>
			<c:out value="${userForm.sUserId}" />
			<form:hidden path="sUserId"/>
		</td>
	</tr>
	<tr>
		<th align="left">名前</th>
		<td>
			<form:input path="name" placeholder="山田 太郎"/>
			<form:errors path="name" cssStyle="color: red;"/>
		</td>
	</tr>
	<tr>
		<th align="left">e-mail</th>
		<td>
			<form:input path="eMail" placeholder="test@gmail.com"/>
			<form:errors path="eMail" cssStyle="color: red;"/>
		</td>
	</tr>
	<tr>
		<th align="left">パスワード</th>
		<td>
			<form:input path="password" placeholder="英数記号の組合せ（7桁以上）"/>
			<form:errors path="password" cssStyle="color: red;"/>
		</td>
	</tr>
	<tr>
		<th align="left">パスワード（確認用）</th>
		<td>
			<form:input path="passwordConf" placeholder="パスワード確認用"/>
			<form:errors path="passwordConf" cssStyle="color: red;"/>
		</td>
	</tr>
	<tr>
		<th align="left">自己紹介</th>
		<td>
			<form:textarea path="introduceMyself" cols="30" rows="5" placeholder="Java8,Springが得意です。"/>
			<form:errors path="introduceMyself" cssStyle="color: red;"/>
		</td>
	</tr>
</table>
<br>
<input type="button" name="_event_processd" onclick="submitPrc('ss01\/updConf')" value="確認">
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