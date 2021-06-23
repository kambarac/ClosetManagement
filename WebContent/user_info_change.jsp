<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	// Servletの受け取り
	request.setCharacterEncoding("UTF8");
	String result = (String) request.getAttribute("result");
	if(session.getAttribute("id") != null){
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/user.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css">
<script>
window.onload = function(){
	var re = document.getElementById("result");
	if(re.value == "OK"){
		var doc1 = document.entry_form.user_id;
		var doc2 = document.entry_form.user_pass;
		var doc3 = document.entry_form.user_name;

		var str = "変更しますか？\n\n";
		if(doc1.value != "" && doc1.value != null){
			str += "id：" + doc1.value + "\n";
		}
		if(doc2.value != "" && doc2.value != null){
			str += "pass：" + doc2.value + "\n";
		}
		if(doc3.value != "" && doc3.value != null){
			str += "name：" + doc3.value;
		}

		let user_dialog = confirm(str);

		if(user_dialog){
			const form = document.entry_form;
			form.action = "UserInfoChange";
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
	var doc1 = document.entry_form.user_id;
	var doc2 = document.entry_form.user_pass;
	var doc3 = document.entry_form.user_name;
	if((doc1.value == "" || doc1.value == null) && (doc2.value == "" || doc2.value == null) && (doc3.value == "" || doc3.value == null)){
		alert("いずれかの項目を入力してください");
	}
	else{
		form.submit();
	}
}

function user_delete_click(){
	let dialog = confirm("※クロゼット情報が全て消えログインができなくなります※\n\n本当によろしいですか？");
	if(dialog){
		location.href='UserInfoDelete';
	}
	else{
		alert("キャンセルしました");
	}
}

</script>

<title>ユーザー情報変更</title>
</head>
<body>
<jsp:include page="header_menu.jsp" />
	<h2 id="change">ユーザー情報変更</h2>
		<c:if test="${userdata == null}">
			<form name="entry_form" action="CheckDuplicate" method="post">
			<table class="tbl">
			<tr>
				<th><div class="b_div" id="user">ユーザーID</div></th>
				<td><input type="text" name="user_id" size="15" maxlength="20" placeholder="<%= session.getAttribute("id") %>" value=""></td>
			</tr>
			<tr>
				<th><div class="b_div" id="pass">パスワード</div></th>
				<td><input type="text" name="user_pass" size="15" maxlength="20" placeholder="" value="" autocomplete="off"></td>
			</tr>
			<tr>
				<th><div class="b_div" id="name">ニックネーム</div></th>
				<td><input type="text" name="user_name" size="15" maxlength="20" placeholder="<%= session.getAttribute("name") %>" value=""></td>
			</tr>
			</table>
			<div class="b_div" align="center"><input type="button" value="変更" onclick="btn_click(this.form)"></div>
			</form>
			</c:if>

			<c:if test="${userdata != null}">
			<form name="entry_form" action="CheckDuplicate" method="post">
			<c:forEach items="${userdata}" var="user">
			<table class="tbl">
			<tr>
				<th><div class="b_div" id="user">ユーザーID</div></th>
				<td><input type="text" name="user_id" size="15" maxlength="20" placeholder="<%= session.getAttribute("id") %>" value="${user.id}"></td>
			</tr>
			<tr>
				<th><div class="b_div" id="pass">パスワード</div></th>
				<td><input type="text" name="user_pass" size="15" maxlength="20" placeholder="" value="${user.pass}"></td>
			</tr>
			<tr>
				<th><div class="b_div" id="name">ニックネーム</div></th>
				<td><input type="text" name="user_name" size="15" maxlength="20" placeholder="<%= session.getAttribute("name") %>" value="${user.name}"></td>
			</tr>
			</table>
			<div class="b_div" align="center"><input type="button" value="変更" onclick="btn_click(this.form)"></div>
			</c:forEach>
			</form>
			</c:if>

			<div align="center" class="div_del"><input type="button" onclick="user_delete_click()" value="ユーザー削除"></div>
		<hr width=85% size=2>
		<div class="b_div"><input type="button" id="top_btn" class="btn" value="top" onclick="location.href='BackUserpage'"></div>
		<input type="hidden" id="result" value="<%=result%>">
</body>
</html>
<%
}else{
	RequestDispatcher dispatcher = request.getRequestDispatcher("/ClosetManagement"); // ログイン画面遷移
	dispatcher.forward(request, response);
}
%>