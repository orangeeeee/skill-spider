<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/css/ss01/searchList.css">
<title>ユーザ検索一覧画面</title>
</head>
<body>
<h1>ユーザ検索一覧画面</h1>
<form:form id="oForm" modelAttribute="userSrchLstForm" method="post">
<input type="hidden" id="contextPath" value="${pageContext.servletContext.contextPath}" />
<input type="button" name="_event_processd" onclick="submitPrc('ss01\/register')" value="新規登録">
<input type="button" name="_event_processd" onclick="submitPrc('ss01\/register')" value="jqGrid検索一覧">
<br/>
<div>
<h3>検索条件</h3>
<%-- <form:input path="sUserId" placeholder="検索条件を入力してください。"/> --%>
ユーザ名<form:input path="name" placeholder="検索条件を入力してください。"/>
</div>
<br/>
<input type="button" name="_event_processd" onclick="submitPrc('ss01\/search')" value="検索">
<input type="button" name="_event_processd" onclick="submitPrc('ss01\/csvDownload')" value="CSVダウンロード">
<hr>
<table class="result-table" >
	<tr>
		<th>No.</th><th class="id_col">ID</th><th class="name_col">名前</th>
	</tr>
	<c:forEach var="userList" items="${userSrchLstForm.userList}" varStatus="status">
	<tr>
		<td><c:out value="${status.index + 1}"/></td>
		<td class="id_col">
			<a href="#" onclick="clickIdLink(${status.index})"><c:out value="${userList.sUserId}"/></a>
			<%--
			■JSTL tagsの使用について
				JSTLで記述する場合は以下のように記述する。
				<input type="hidden" id="sUserId_${status.index}" value="<c:out value="${userList.sUserId}"/>"/>
			 --%>
			 <%--
			 ■Spring tagsの使用について
			 	以下のように記述するとHTMLは下記のようにid属性が勝手に付与される。
			 	便利でよいが、困るケースが存在する場合は、JSTLでの記載でも良い。
			 	特にピリオドつながりでid名が付与さえれるのはjQueryでクラスタを記載する際に'\.'としなければならない為、
			 	あまり使いたくは無いが、まずは使ってやってみる。
			 	<input id="userList0.sUserId" name="userList[0].sUserId" type="hidden" value="testUser-1">
			  --%>
			<form:hidden path="userList[${status.index}].sUserId" />
		</td>
		<td class="name_col"><c:out value="${userList.name}"/></td>
	</tr>
	</c:forEach>
</table>
<br><br>
<%-- <form:hidden path="sUserId"/>は以下様にHTMLに展開される。
	<input id="sUserId" name="sUserId" type="hidden" value="">
--%>
<form:hidden path="sUserId"/>
</form:form>
<script type="text/javascript">
function submitPrc(btnName) {
	var contextPath = document.getElementById('contextPath').value;
	var oForm = document.getElementById('oForm');
	oForm.method = "post";
	oForm.action = contextPath + "/" + btnName;
	oForm.submit();
};

function clickIdLink(index) {

	//検索キーを取得
	var sUserId = document.getElementById("userList" + index + ".sUserId").value;

	//検索キーを設定
	document.getElementById("sUserId").value = sUserId;

	submitPrc('ss01\/ref');
};
</script>
</body>
</html>