<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	// sessionの初期化(削除)
	session.invalidate();

	//Servletの受け取り
	request.setCharacterEncoding("UTF8");
	String data = (String) request.getAttribute("noData");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/login.css">
<script>
window.onload = function(){
	var ngdata = document.getElementById("ng");
	if(ngdata.value == "NG"){
		alert("ユーザーIDかパスワードが間違っています");
	}
}

function btn_click(form){

	<%-- 未入力 --%>
	var doc1 = document.login_form.input_id;
	var doc2 = document.login_form.input_pas;
	var ele1 = document.getElementById("user");
	var ele2 = document.getElementById("pass");
	ele1.style.color = "black";
	ele2.style.color = "black";
	var on_a = false;

	if(doc1.value == "" || doc2.value == ""){
		if(doc1.value == ""){
			<%-- ミス入力時項目名を赤字にする --%>
			error(ele1);
			doc1.placeholder = "入力してください";
		}
		if(doc2.value == ""){
			error(ele2);
			doc2.placeholder = "入力してください";
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
<title>closet</title>
</head>
<body>
<div>
	<h1>closet</h1>
	<p>ログイン</p>
	<form name="login_form" action="LoginSearch" method="post">
		<table align="center">
			<tr class="line">
				<td><div id="user">ユーザーID</div></td>
				<td><input type="text" name="input_id" size="15" maxlength="20" placeholder="" value=""></td>
			</tr>
			<tr class="line">
				<td><div id="pass">パスワード</div></td>
				<td><input type="text" name="input_pas" size="15" maxlength="20" placeholder="" value="" autocomplete="off"></td>
			</tr>
		</table>
		<div align="center"><input type="button" value="ログイン" onclick="btn_click(this.form)"></div>
	</form>
</div>
<input type="hidden" id="ng" value="<%=data%>">
<hr width=85% size=2>
<div id="entry_div"><input type="button" id="entry_btn" value="新規ユーザー登録" onclick="location.href='UserEntryServlet'"></div>
</body>
</html>