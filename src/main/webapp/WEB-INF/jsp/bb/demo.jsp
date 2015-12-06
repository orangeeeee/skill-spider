<%@ page language="java" contentType="text/html; charset=utf-8"  pageEncoding="utf-8"%>
<!DOCTYPE html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/css/ss01/ss01-common.css">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/view/css/common.css">
<script type="text/javascript" src="${pageContext.request.contextPath}/view/js/lib/jquery-2.1.3.min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/view/js/lib/underscore-min.js"></script>
<script type="text/javascript" src="${pageContext.request.contextPath}/view/js/lib/backbone-min.js"></script>
<script type="text/javascript">
$(function() {

	var BaseView = Backbone.View.extend({
		el : '#id_buttons',
		events : {
			'click #id_button_update' : 'onClickUpdateButton',
			'click #id_button_delete' : 'onClickDeleteeButton'
		},
		onClickUpdateButton : function(event) {
			alert('onClickUpdateButton');
		},
		onClickDeleteeButton : function(event) {
			alert('onClickDeleteeButton');
		}
	});

	var baseView = new BaseView();
});
</script>
<title>backboneJS Demo</title>
</head>
<body>
<div class="all-parent">
<div class="title" >
	<h1>backboneJS Demo</h1>
</div>
<form:form id="oForm" modelAttribute="ddDemoForm" method="post">
</form:form>
</div><!-- all-parent -->
<div id="id_buttons">
  <input id="id_button_update" type="button" value="更新">
  <input id="id_button_delete" type="button" value="削除">
</div>
</body>
</html>