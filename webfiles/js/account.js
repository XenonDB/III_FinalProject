$(function(){
	var requestUrl = window.location.origin + "/HealthyLifestyle/Account/Login";
	$.get({
		url: requestUrl
	}).done(function(data){
		$("#loginAccount").html(data.account);
	}).fail(function(data){
		var errmsg = "";
		switch(data.status){
			case 401:
				errmsg = "登入狀態過期，請重新登入。";
				break;
			default:
				errmsg = "伺服器發生未預期錯誤，請重新登入。";
		}
		alert(errmsg);
		window.location.replace("login.html");
	});
	
});

function buildTable(data){

	var initStr = 
		`<thead>
			<tr>
				<td>使用者帳戶</td>
				<td>暱稱</td>
			</tr>
		</thead>`;
	
	
	$("#memberTable").html(initStr);
	$("#adminTable").show();
	
	data.forEach(function(e){
		
		var col1 = $("<td>").html(e.user);
		var col2 = $("<td>").html(e.nickName);
		
		var row = $("<tr>").append(col1).append(col2);
		
		$("#memberTable").append(row);
	
	});
	
	$("#memberTable").DataTable();

}

function getRegisteredMembers(){

	var requestUrl = window.location.origin + "/HealthyLifestyle/Account/Admin/MemberManager";
	$.get({
		url: requestUrl
	}).done(function(data){
		alert("成功取得會員列表。");
		buildTable(data);
	}).fail(function(data){
		var errmsg = "";
		switch(data.status){
			case 403:
				errmsg = "無權限的操作。";
				break;
			default:
				errmsg = "伺服器發生未預期錯誤。";
		}
		alert(errmsg);
	});

}

function logout(){

	var requestUrl = window.location.origin + "/HealthyLifestyle/Account/Logout";
	$.get({
		url: requestUrl
	}).done(function(data){
		alert("成功登出帳號");
		window.location.replace("login.html");
	});

}

function deleteAccount(){
	
	var account = $("#accountToDelete")[0].value;
	
	if(!account) return;
	
	var formData = "accountToDelete="+account;
	var requestUrl = window.location.origin + "/HealthyLifestyle/Account/Admin/MemberManager";
	$.post({
		url: requestUrl,
		data: formData
	}).done(function(){
		alert("成功刪除帳號!!");
	}).fail(function(data){
		var errmsg = "";
		switch(data.status){
			case 401:
				errmsg = "無權限的操作。";
				break;
			case 404:
				errmsg = "伺服器沒有回應，無法刪除帳號。";
				break;
			case 400:
				errmsg = "無法刪除不存在的帳號。";
				break
			default:
				errmsg = "伺服器發生未預期錯誤。";
		}
		alert(errmsg);
	});

}
