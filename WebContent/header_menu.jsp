<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<header>
	<div id="title">ClosetManagement</div>
	<hr id="headline" width="100%" size="2px" noshade>
	<div class="menu">
		<input type="checkbox" id="menu-button">
		<label for="menu-button" class="menu-btn"><span></span></label>
		<div class="menu-item">
			<ul>
				<li>
				<div class="user_tbl">
				<table>
				<tr><th colspan="3">logged in user</th></tr>
				<tr><th>ID</th><td>:</td><td><%= session.getAttribute("id") %></td></tr>
				<tr><th>NAME</th><td>:</td><td><%= session.getAttribute("name") %></td></tr>
				</table>
				</div>
				</li>
				<li><a href="UserInfoChangeServlet">ユーザー情報変更</a></li>
				<li><a href="ClosetInfoServlet">クロゼット情報</a></li>
				<li><a href="LogoutServlet" id="logout">ログアウト</a></li>
			</ul>
		</div>
	</div>
	<script>
		document.getElementById("logout").addEventListener('click', function(e){
			let dialog = confirm("ログアウトしますか？");
			if(dialog == false){
			    e.preventDefault();  // 画面遷移を無効化
			}
		}, false);

	</script>
</header>