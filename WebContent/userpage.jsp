<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
	if(session.getAttribute("id") != null){
%>
<%
	// Servletの受け取り
	request.setCharacterEncoding("UTF8");
	int maxPage = (int)session.getAttribute("maxPage");
	int nowPage = (int)session.getAttribute("nowPage");
	int i =0;
%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html" charset="UTF-8">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/userpage.css">
<link rel="stylesheet" href="${pageContext.request.contextPath}/css/menu.css">
<script type="text/javascript">
function search_click(form){

	var id = document.s_box.id_search;
	var cate = document.s_box.type_search;
	var color = document.s_box.color_search;
	if(color.value == "none"){
		color.selectedIndex = 0;
	}
	var makers = document.s_box.makers_search;
	if(id.value.trim() == "" && cate.value.trim() == "" && color.selectedIndex == 0 && makers.value.trim() == ""){
		alert("いずれかの項目を入力または選択してください");
	}
	else{
		if(color.selectedIndex == 0){
			color.value = "";
		}
		form.submit();
	}
}

function change_click(){

	checked = selectRadio();

	if(checked == ""){
		alert("項目を選んでください");
	}
	else{
		const form = document.createElement('form');
		form.action = "GInfoChangeServlet";
		form.method = "post";

		const input = document.createElement('input');
		let name = "id" + checked;
		const eleId = document.getElementById(name).value;
		input.value = eleId;
		input.name = "g_id";
		input.style.display = "none";

		document.body.appendChild(form);
		form.appendChild(input);
		form.submit();

	}
}

function delete_click(){
	checked = selectRadio();

	if(checked == ""){
		alert("項目を選んでください");
	}
	else{
		var t_name = "r_table" + checked;
		var table = document.getElementById(t_name);
		let item = Number(checked);
		item += 1;
		var color = table.rows[3].cells[1].id;

		// ダイアログ表示文字列作成
		var str = "削除しますか？\n\n";
		for(var i = 1;i <= 4;i++){
			if(i == 3){
				str += "色 : " + color + "\n";
				continue;
			}
			str += table.rows[i].cells[0].innerText + " : " + table.rows[i].cells[1].innerText + "\n";
		}

		// ダイアログ表示
		let check_delete = confirm(str);

		// はい選択時
		if(check_delete){
			const form = document.createElement('form');
			form.action = "GearInfoDelete";
			form.method = "post";

			const input = document.createElement('input');
			let name = "id" + checked;
			const eleId = document.getElementById(name).value;
			input.value = eleId;
			input.name = "g_id";
			input.style.display = "none";

			document.body.appendChild(form);
			form.appendChild(input);
			form.submit();
		}
		// いいえ選択時
		else{
			alert("キャンセルしました");
		}
	}

}

function selectRadio(){
	let radio = document.getElementById("gear_info").select;
	let checked = "";

	// ラジオボタンが一つの時(なぜか.lengthがundefinedになるので分岐)
	if(radio.length == undefined && radio.checked){
		checked = radio.value;
	}
	else{
		for(i=0; i<radio.length; i++){
			if(radio[i].checked){
				checked = radio[i].value;
			}
		}
	}

	return checked;
}



function info_click(){

	let end = document.getElementsByClassName("result");
	var c_box = "";
	for(var num = 0; num < end.length; num++){
		c_box = "hidbox" + num.toString();
		var c_ele = document.getElementsByClassName(c_box);
		for(var j = 0; j < c_ele.length; j++){
			c_ele[j].style.display = "none";
		}
	}

	checked = selectRadio();
	var boxname = "hidbox" + checked;
	var ele = document.getElementsByClassName(boxname);
	for(var i = 0; i < ele.length; i++){
		ele[i].style = "display:table-row;";
	}
}

function page_click(form){
	form.submit();
}

</script>

	<title>userpage</title>
</head>
<body>
	<h1>closet</h1>
	<jsp:include page="header_menu.jsp" />

			<form name="s_box" action="GearSearch" method="post">
			<div id="labelbtn" align="center"><label for="label1">検索を開く</label></div>
			<input type="checkbox" id="label1">
			<div class="search_box" align="center">
			<table class="search_tbl">
			<tr>
				<th>ID検索</th>
				<td><input type="text" name="id_search" value="${searchID}"></td>
			</tr>
			<tr>
				<th>カテゴリ検索</th>
				<td>
					<input type="search" name="type_search" autocomplete="on" value="${searchType}" list="list">
					<!-- 候補を表示 -->
					<datalist id="list">
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
				</td>
			</tr>
			<tr>
				<th>色検索</th>
				<td><select style="width:123px;" name="color_search" onFocus="searchColor_reset()">
							<!-- 候補を表示 -->
							<c:if test="${searchColor == '' or searchColor == null}">
							<option disabled selected value="">--select color--</option>
							</c:if>
							<c:if test="${searchColor != null and searchColor != ''}">
							<option disabled selected>--selected ${searchColor}--</option>
							</c:if>
							<option value="none">--select none--</option>
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
				</select></td>
			</tr>
			<tr>
				<th>ブランド検索</th>
				<td><input type="text" name="makers_search" value="${searchMakers}"></td>
			</tr>
			<tr class="nline"><td colspan="2"><div align="center" class="btn"><input type="button" name="category" class="s_btn" value="検索" onclick="search_click(this.form)"><input type="button" class="s_btn" value="全件" onclick="location.href='BackUserpage'"></div></td></tr>
			</table>
			</div>
			</form>
			<hr width=80% size=1>
				<c:if test="${closet == null}">
					<div align="center"><img src="http://192.168.1.134:8080/ClosetManagement/imagefile/closetimage.png" alt="" onerror="this.onerror = null; this.src='';"height="200"></div>
					<div align="center">データがありません</div>
				</c:if>

				<c:if test="${closet != null}">
					<form id="gear_info">
				<div class="list" align="center">
				<ul>
					<c:forEach items="${closet}" var="gear" varStatus="sts">
					<li>
						<table id="r_table${sts.index}" class="result">
						<tr><th colspan="2" class="img_box"><div align="center" class="result_img"><img src="http://192.168.1.134:8080/ClosetManagement/imagefile/${gear.image}"style="height:200px;width:200px;object-fit:contain;vertical-align: center;"onError="this.onerror=null;this.src='http://192.168.1.134:8080/ClosetManagement/imagefile/noimage.png';"  alt="画像ナシ" ></div></th></tr>
						<tr><th><input type="radio" name="select" value="${sts.index}" onclick="info_click()">ID</th><td class="intd">${gear.id}<input type="hidden" id="id${sts.index}" value="${gear.id}"></td></tr>
						<tr class="hidbox${sts.index}" style="display:none;"><th nowrap>カテゴリ</th><td nowrap class="intd">${gear.type}</td></tr>
						<tr class="hidbox${sts.index}" style="display:none;"><th nowrap>色</th>
							<td class="intd" id="${gear.color}"><img src="http://192.168.1.134:8080/ClosetManagement/imagefile/${gear.color}.png" alt="${gear.color}" height="20"></td>
						</tr>
						<tr class="hidbox${sts.index}" style="display:none;"><th nowrap>ブランド</th><td nowrap class="intd">${gear.makers}</td></tr>
						<tr class="hidbox${sts.index}" style="display:none;"><th nowrap>登録日</th><td nowrap class="intd">${gear.date_create}</td></tr>
						<tr class="hidbox${sts.index}" style="display:none;"><th nowrap>更新日</th><td nowrap class="intd">${gear.date_update}</td></tr>
						</table>
					</li>
						</c:forEach>
				</ul>
				</div>
					</form>
				</c:if>
			<c:if test="${closet != null}">
			<div align="center">
				<ul id="pageingUl">
					<%if(maxPage < 5){
						if(nowPage == 1){
					%>
					<li><input type="button" class="pageingBtnDis" value="前" disabled="disabled"></li>
					<%
						}
						else{
					%><li><form action="PageTransition" method="post"><input type="button" class="pageingBtn" value="前" onclick="page_click(this.form)"><input type="hidden" name="bAa" value="before"></form></li>
					<%	}
						for(i = 1;i <= maxPage; i++){
							if(i == nowPage){
					%>
					<li class="pageingList"><input type="button" class="pageingBtnDis" value="<%= i %>" disabled="disabled"></li>
					<%		}
							else{
					%>
					<li class="pageingList"><form action="PageTransition" method="post"><input type="button" class="pageingBtn" value="<%= i %>" onclick="page_click(this.form)"><input type="hidden" name="page" value="<%= i %>"></form></li>
					<%
							}
						}
						if(nowPage == maxPage){
					%>
					<li><input type="button" class="pageingBtnDis" value="次" disabled="disabled"></li>
					<%
						}
						else{
					%>
					<li><form action="PageTransition" method="post"><input type="button" class="pageingBtn" value="次" onclick="page_click(this.form)"><input type="hidden" name="bAa" value="next"></form></li>
					<%	}
					}
					else if(nowPage > 2){
						if(nowPage == 1){
					%><li><input type="button" class="pageingBtnDis" value="1" disabled="disabled"></li>
					<li><input type="button" class="pageingBtnDis" value="前" disabled="disabled"></li>
					<%		}
							else{
					%><li><form action="PageTransition" method="post"><input type="button" class="outpageingBtn" value="1" onclick="page_click(this.form)"><input type="hidden" name="page" value="1"></form><%if(nowPage - 2 > 1){ %><%} %></li>
					<li><form action="PageTransition" method="post"><input type="button" class="pageingBtnBaA" value="前" onclick="page_click(this.form)"><input type="hidden" name="bAa" value="before"></form></li>
					<%		}
						int start = nowPage - 2;
						int finish = nowPage + 2 ;
						if(finish > maxPage){
							finish = maxPage;
							start = maxPage - 4;
						}
						for(i = start;i <= finish ; i++){
							if(i == nowPage){
					%>
					<li class="pageingList"><input type="button" class="pageingBtnDis" value="<%= i %>" disabled="disabled"></li>
					<%		}
							else{
					%>
					<li class="pageingList"><form action="PageTransition" method="post"><input type="button" class="pageingBtn" value="<%= i %>" onclick="page_click(this.form)"><input type="hidden" name="page" value="<%= i %>"></form></li>
					<%
							}
						}
						if(nowPage == maxPage){
					%><li><input type="button" class="pageingBtnDis" value="次" disabled="disabled"></li>
					<li><input type="button" class="pageingBtnDis" value="<%= maxPage %>" disabled="disabled"></li>
					<%
						}
						else{
					%><li><form action="PageTransition" method="post"><input type="button" class="pageingBtnBaA" value="次" onclick="page_click(this.form)"><input type="hidden" name="bAa" value="next"></form></li>
					<li><%if(nowPage + 2 < maxPage){ %><%} %><form action="PageTransition" method="post"><input type="button" class="outpageingBtn" value="<%= maxPage %>" onclick="page_click(this.form)"><input type="hidden" name="page" value="<%= maxPage %>"></form></li>
					<%	}}
						else{
							if(nowPage == 1){
					%><li><input type="button" class="pageingBtnDis" value="1" disabled="disabled"></li>
					<li><input type="button" class="pageingBtnDis" value="前" disabled="disabled"></li>
					<%		}
							else{
					%><li><form action="PageTransition" method="post"><input type="button" class="outpageingBtn" value="1" onclick="page_click(this.form)"><input type="hidden" name="page" value="1"></form><%if(nowPage - 2 > 1){ %><%} %></li>
					<li><form action="PageTransition" method="post"><input type="button" class="pageingBtnBaA" value="前" onclick="page_click(this.form)"><input type="hidden" name="bAa" value="before"></form></li>
					<%		}
							for(i = 1;i <= 5 ; i++){
								if(i == nowPage){
					%>
					<li class="pageingList"><input type="button" class="pageingBtnDis" value="<%= i %>" disabled="disabled"></li>
					<%		}
							else{
					%>
					<li class="pageingList"><form action="PageTransition" method="post"><input type="button" class="pageingBtn" value="<%= i %>" onclick="page_click(this.form)"><input type="hidden" name="page" value="<%= i %>"></form></li>
					<%
							}
						}
						if(nowPage == maxPage){
					%><li><input type="button" class="pageingBtnDis" value="次" disabled="disabled"></li>
					<li><input type="button" class="pageingBtnDis" value="<%= maxPage %>" disabled="disabled"></li>
					<%
						}
						else{
					%><li><form action="PageTransition" method="post"><input type="button" class="pageingBtnBaA" value="次" onclick="page_click(this.form)"><input type="hidden" name="bAa" value="next"></form></li>
					<li><%if(nowPage + 2 < maxPage){ %><%} %><form action="PageTransition" method="post"><input type="button" class="outpageingBtn" value="<%= maxPage %>" onclick="page_click(this.form)"><input type="hidden" name="page" value="<%= maxPage %>"></form></li>
					<%	}}
					%>
				</ul>
			</div>
			</c:if>
			<div id="marginbtn" class="btn" align="center">
			<input type="button" name="" value="変更" onclick="change_click()">
			<input type="button" name="" value="削除" onclick="delete_click()">
			<input type="button" value="追加" onclick="location.href='GearInfoAddServlet'">
			</div>
</body>
</html>
<%
}else{
	RequestDispatcher dispatcher = request.getRequestDispatcher("/ClosetManagement"); // ログイン画面遷移
	dispatcher.forward(request, response);
}
%>

