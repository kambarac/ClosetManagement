<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	request.setCharacterEncoding("UTF8");
	String record = (String)request.getAttribute("recordcount");

	String result = (String) request.getAttribute("result");
	if(session.getAttribute("id") != null){
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/closet.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css">
<script>
window.onload = function(){

	// CSV書き込み後確認アラート
	var re = document.getElementById("result");
	if(re.value == "OK"){
		alert("正常に書き込まれました");
	}
	else if(re.value == "error"){
		alert("書き込み中にエラーが発生しました");
	}
}

function alldelete_click(){

	// ダイアログ表示
	let deleteDialog = confirm("※登録したデータがすべて消えます※\n\n本当によろしいですか？");
	if(deleteDialog){
		location.href='GearAllDelete';
	}
	else{
		alert("キャンセルしました");
	}
}

function csvWrite(){

	// ダイアログ表示
	let dialog = confirm("C:下に「csv」フォルダを作成します");
	if(dialog){
		location.href='CsvWriterServlet';
	}
	else{
		alert("キャンセルしました");
	}
}
</script>

<title>クロゼット情報</title>
</head>
<body>
<jsp:include page="header_menu.jsp" />
	<h1>クロゼット情報</h1>
	<c:if test="${recordcount == 0 or recordcount == null}">
	<div class="div1"><img src="http://192.168.1.134:8080/ClosetManagement/imagefile/closetimage.png" alt="" onerror="this.onerror = null; this.src='';"height="200"></div>
	<div class="div1">登録されたデータがありません</div>
	</c:if>

	<c:if test="${recordcount != 0 and recordcount != null}">
	<div class="div1">登録データ数 : ${recordcount}</div>
	<table id="info">
		<tr>
			<th id="typebgc">カテゴリ</th>
			<th id="colorbgc">色</th>
			<th id="makersbgc">ブランド</th>
		</tr>
		<tr>
			<td>${typeCount[0].key} : ${typeCount[0].count}</td>
			<td><img src="http://192.168.1.134:8080/ClosetManagement/imagefile/${colorCount[0].key}.png" alt="${colorCount[0].key}" height="20"> : ${colorCount[0].count}</td>
			<td>${makersCount[0].key} : ${makersCount[0].count}</td>
		</tr>
		<tr id="last">
			<td><div class="labelbtn" align="center"><label class="info_label" for="label1">詳しく見る</label></div></td>
			<td><div class="labelbtn" align="center"><label class="info_label" for="label2">詳しく見る</label></div></td>
			<td><div class="labelbtn" align="center"><label class="info_label" for="label3">詳しく見る</label></div></td>
		</tr>
	</table>
	<input type="checkbox" id="label1">
	<div class="hidden_box_type" align="center">
		<table class="hidden_type">
			<c:forEach items="${typeCount}" var="type">
			<tr>
				<th nowrap>${type.key}</th>
				<td>${type.count}</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<input type="checkbox" id="label2">
	<div class="hidden_box_color" align="center">
		<table class="hidden_color">
			<c:forEach items="${colorCount}" var="color">
			<tr>
				<th><img src="http://192.168.1.134:8080/ClosetManagement/imagefile/${color.key}.png" alt="${color.key}" height="20"></th>
				<td>${color.count}</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<input type="checkbox" id="label3">
	<div class="hidden_box_makers" align="center">
		<table class="hidden_makers">
			<c:forEach items="${makersCount}" var="makers">
			<tr>
				<th nowrap>${makers.key}</th>
				<td>${makers.count}</td>
			</tr>
			</c:forEach>
		</table>
	</div>
	<div align="center" class="csv_btn"><input type="button" onclick="csvWrite()" value="CSV出力"></div>
	<form action="GearAllDelete" method="post">
	<div align="center" class="div_del"><input type="button" onclick="alldelete_click()" value="データをすべて削除"></div>
	</form>
	<hr width=85% size=2>
	<div align="center" class="div_btn"><input type="button" class="btn" value="top" onclick="location.href='BackUserpage'"></div>
	</c:if>
	<input type="hidden" id="result" value="<%= result %>">
</body>
</html>
<%
}else{
	RequestDispatcher dispatcher = request.getRequestDispatcher("/ClosetManagement"); // ログイン画面遷移
	dispatcher.forward(request, response);
}
%>