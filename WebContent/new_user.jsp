<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	// Servletの受け取り
	request.setCharacterEncoding("UTF8");
	String result = (String) request.getAttribute("result");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
<script>
window.onload = function(){
	var re = document.getElementById("result");
	if(re.value == "OK"){
		var doc1 = document.entry_form.user_id;
		var doc2 = document.entry_form.user_pass;
		var doc3 = document.entry_form.user_name;
		var str = "id：" + doc1.value + "\n" + "pass：" + doc2.value + "\n" + doc3.value;
		let entry_dialog = confirm("登録しますか？\n\n" + str);

		if(entry_dialog){
			const form = document.entry_form;
			form.action = "UserEntry";

			form.submit();
		}
		else{
			form.action = "CheckDuplicate";
			alert("キャンセルしました");
		}
	}
	else if(re.value == "NG"){
		alert("入力したIDは使えません");
	}
}

function btn_click(form){

<%-- 未入力 --%>
	var doc1 = document.entry_form.user_id;
	var doc2 = document.entry_form.user_pass;
	var doc3 = document.entry_form.user_name;
	var ele1 = document.getElementById("user");
	var ele2 = document.getElementById("pass");
	var ele3 = document.getElementById("name");
	ele1.style.color = "black";
	ele2.style.color = "black";
	ele3.style.color = "black";
	var on_a = false;

	if(doc1.value.trim() == "" || doc2.value.trim() == "" || doc3.value.trim() == ""){
		if(doc1.value == ""){
			<%-- ミス入力時項目名を赤字にする --%>
			error(ele1);
			doc1.placeholder = "入力してください";
		}
		if(doc2.value == ""){
			error(ele2);
			doc2.placeholder = "入力してください";
		}
		if(doc3.value == ""){
			error(ele3);
			doc3.placeholder = "入力してください";
		}
		on_a = true;

	}
	if(doc1.value.match(/[^A-Za-z0-9]+/) || doc2.value.match(/[^A-Za-z0-9]+/)){
		if(doc1.value.match(/[^A-Za-z0-9]+/)){
			error(ele1);
			doc1.value = "";
			doc1.placeholder = "半角英数字のみです";
		}
		if(doc2.value.match(/[^A-Za-z0-9]+/)){
			error(ele2);
			doc2.value = "";
			doc2.placeholder = "半角英数字のみです";
		}
		on_a = true;
	}

	if(on_a == true){
		error_alert();
	}
	else{

		form.submit();
	}
}

function error(e){
	e.style.color = "#ff0000";
}

function error_alert(){
	alert("入力に誤りがあります");
}

</script>
<title>新規ユーザー登録</title>
</head>
<body>
	<h1>closet</h1>
	<h2>ユーザー登録</h2>
			<c:if test="${userdata == null}">
			<form name="entry_form" action="CheckDuplicate" method="post">
			<table class="tbl">
			<tr>
				<th><div id="user">ユーザーID</div></th>
				<td><input type="text" name="user_id" size="15" maxlength="20" placeholder="半角小文字英数字" value="" autocomplete="off"></td>
			</tr>
			<tr>
				<th><div id="pass">パスワード</div></th>
				<td><input type="text" name="user_pass" size="15" maxlength="20" placeholder="半角英数字" value=""></td>
			</tr>
			<tr>
				<th><div id="name">ニックネーム</div></th>
				<td><input type="text" name="user_name" size="15" maxlength="20" placeholder="" value=""></td>
			</tr>
			</table>
			<div align="center"><input type="button" value="登録" onclick="btn_click(this.form)"></div>
			</form>
			</c:if>

			<c:if test="${userdata != null}">
			<form name="entry_form" action="CheckDuplicate" method="post">
			<c:forEach items="${userdata}" var="user">
			<table class="tbl">
			<tr>
				<th><div id="user">ユーザーID</div></th>
				<td><input type="text" name="user_id" size="15" maxlength="20" placeholder="" value="${user.id}" onFocus="input_onclick(this)"></td>
			</tr>
			<tr>
				<th><div id="pass">パスワード</div></th>
				<td><input type="text" name="user_pass" size="15" maxlength="20" placeholder="" value="${user.pass}" onFocus="input_onclick(this)"></td>
			</tr>
			<tr>
				<th><div id="name">ニックネーム</div></th>
				<td><input type="text" name="user_name" size="15" maxlength="20" placeholder="" value="${user.name}" onFocus="input_onclick(this)"></td>
			</tr>
			</table>
			<div align="center"><input type="button" value="登録" onclick="btn_click(this.form)"></div>
			</c:forEach>
			</form>
			</c:if>

<hr width=85% size=2>
<div id="login_div"><input type="button" id="login_btn" value="ログイン" onclick="location.href='login.jsp'"></div>
	<input type="hidden" id="result" value="<%=result%>">
</body>
</html>