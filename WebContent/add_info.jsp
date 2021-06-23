<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
	if(session.getAttribute("id") != null){
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/add.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css">
<script type="text/javascript">
function btn_click(form){

	// value値取得用
	var miss = false;
	var select_color = document.form.g_color;
	var type = document.form.g_type;
	var makers = document.form.g_makers;
	var image = document.form.g_image;

	// 項目名変更用
	var e_color = document.getElementById("h_color");
	var e_type = document.getElementById("h_type");
	var e_makers = document.getElementById("h_makers");

	// 判定毎項目名を黒字に戻す
	e_type.style.color = "black";
	e_color.style.color = "black";
	e_makers.style.color ="black";

	// 未入力時項目名赤字
	if(type.value.trim() == ""){
		e_type.style.color = "#ff0000";
		miss = true;
	}
	if(select_color.selectedIndex == 0){
		e_color.style.color = "#ff0000";
		miss = true;
	}
	if(makers.value.trim() == ""){
		e_makers.style.color ="#ff0000";
		miss = true;
	}


	if(miss == true){  // アラート表示
		alert("画像以外は必須項目です");
	}
	if(miss == false){  // チェックオッケー
		// 変数colorに色名代入
		var color = select_color.value;

		// 画像データ判定
		var str_img = null;
		if(image.value == ""){
			str_img = "ナシ";
		}
		else{
			str_img = "アリ";
		}

		// ダイアログ表示文字列作成
		var str = "内容確認\n\n";
		str += "カテゴリ : " + type.value + "\n";
		str += "色 : " + color + "\n";
		str += "ブランド : " + makers.value + "\n";
		str += "画像 : " + str_img;

		// ダイアログ表示
		let check_delete = confirm(str);
		// はい選択時
		if(check_delete == true){
			// フォーム送信
			form.submit();
		}
		// いいえ選択時
		else{
			alert("キャンセルしました");
		}
	}
}


</script>

<title>クローゼットに追加</title>
</head>
<body>
	<jsp:include page="header_menu.jsp" />

	<h1>登録</h1>
	<p id="attention" align="center">新規登録画像はサーバー更新後に反映されます</p>
	<form name="form" action="GearInfoAdd" method="post" enctype="multipart/form-data">
		<table align="center" class="tbl">
			<tr><th id="h_image">画像</th><td><input type="file" name="g_image" value=""></td></tr>
			<tr><th id="h_type">カテゴリー</th>
				<td><input type="search" name="g_type" autocomplete="on" size="15" list="list_category">
							<!-- 候補を表示 -->
							<datalist id="list_category">
								<option value="アウター">
								<option value="インナー">
								<option value="トップス">
								<option value="ボトムス">
								<option value="スカート">
								<option value="ワンピース/ドレス">
								<option value="オールインワン/セットアップ">
								<option value="シューズ">
								<option value="帽子">
								<option value="バッグ">
								<option value="アクセサリー">
								<option value="その他">
							</datalist>
				<font color= "color:#ff0000" size="2">※必須</font></td></tr>
			<tr><th id="h_color">色</th>
				<td><select style="width:123px;" name="g_color">
							<!-- 候補を表示 -->
							<option disabled selected value>--select color--</option>
							<option style="background-color:#fa5757;" value="red">red</option>
							<option style="background-color:#fa8257;" value="orange">orange</option>
							<option style="background-color:#fac457;" value="yellow">yellow</option>
							<option style="background-color:#b3f07a;" value="green">green</option>
							<option style="background-color:#5798fa;" value="blue">blue</option>
							<option style="background-color:#7857fa;" value="purple">purple</option>
							<option style="background-color:#7a3a3a;" value="brown">brown</option>
							<option style="background-color:#f2dcc2;" value="ivory">ivory</option>
							<option style="background-color:#4d4d4d;" value="black">black</option>
							<option style="background-color:#c7c3c3;" value="gray">gray</option>
							<option style="background-color:#fff;" value="white">white</option>
				</select>
				<font color= "color:#ff0000" size="2">※必須</font></td></tr>
			<tr><th id="h_makers">ブランド</th><td><input type="text" name="g_makers" size="15">
				<font color="color:#ff0000" size="2">※必須</font></td></tr>
		</table>
		<div class="div_btn"><input type="button" value="追加" onclick="btn_click(form)"></div>
	</form>
	<hr width=85% size=2>
	<div class="div_btn"><input type="button" class="btn" value="top" onclick="location.href='BackUserpage'"></div>
</body>
</html>
<%
}else{
	RequestDispatcher dispatcher = request.getRequestDispatcher("/ClosetManagement"); // ログイン画面遷移
	dispatcher.forward(request, response);
}
%>