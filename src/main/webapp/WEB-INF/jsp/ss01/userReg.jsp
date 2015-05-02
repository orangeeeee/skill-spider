<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/css/ss01/ss01-common.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/view/js/lib/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/view/js/ss01/userReg.js"></script>
<title>ユーザ情報登録画面</title>
</head>
<body>
<h1>ユーザ情報登録画面</h1>
<form:form id="oForm" modelAttribute="userForm" method="post">
<div id="dragArea" draggable="true">
<input type="hidden" id="contextPath" value="${pageContext.servletContext.contextPath}" />
<input type="button" name="_event_processd" onclick="submitPrc('ss01\/userSchList')" value="一覧へ戻る">
<br/>
<c:out value="${ message}" />
<%--もうすべてDivでいい --%>
<table class="info-table">
	<tr>
		<th align="left">ユーザID</th>
		<td>
			<form:input path="sUserId" placeholder="英数記号の組合せ（7桁以上）"/>
			<form:errors path="sUserId" cssStyle="color: red;"/>
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
<div>
	<label class="th-label">プロフィール画像アップロード</label>（ファイルドロップ可）
	<br>
	<input id="profUpdBtn" type="file" multiple="multiple" />
	<div id="result"></div>
</div>
<br/>
</div><%-- dragArea end --%>
<input type="button" name="_event_processd" onclick="submitPrc('ss01\/registerConf')" value="確認">
</form:form>
<script type="text/javascript">

</script>
</body>
</html>