function toggleSubmitButton(type){
	$("#form_"+type+"_send").toggle();
	$("#form_"+type+"_send_waiting").toggle();
}

//使用$.param序列化數據發送
function isSubmitting(type){
	return $("#form_"+type+"_send_waiting").css("display") !== "none";
}

$("#loginRegion").submit(function(){
	
	if(isSubmitting("login")) return false;
	
	toggleSubmitButton("login");
	
	var formData = $(this).serialize();
	var requestUrl = window.location.origin + "/HealthyLifestyle/Account/Login";
	$.post({
		url: requestUrl,
		data: formData
	}).done(function(){
		alert("成功登入帳號!!");
		window.location.replace("account.html");
	}).fail(function(data){
		var errmsg = "";
		switch(data.status){
			case 401:
				errmsg = "無效的帳號或密碼!!";
				break;
			case 404:
				errmsg = "伺服器沒有回應，無法登入帳號。";
				break;
			default:
				errmsg = "伺服器發生未預期錯誤，無法登入帳號。";
		}
		alert(errmsg);
	}).always(function(){
		toggleSubmitButton("login");
	});
	
	return false;
});

$("#registerRegion").submit(function(){
	
	if(isSubmitting("register")) return false;
	
	toggleSubmitButton("register");
	
	var formData = $(this).serialize();
	var requestUrl = window.location.origin + "/HealthyLifestyle/Account/Register";
	$.post({
		url: requestUrl,
		data: formData
	}).done(function(){
		alert("成功註冊帳號!!");
	}).fail(function(data){
		var errmsg = "";
		switch(data.status){
			case 400:
				errmsg = "帳號或密碼格式錯誤，無法註冊。";
				break;
			case 409:
				errmsg = "已存在相同的帳號，請選擇另一個帳號註冊。";
				break;
			case 404:
				errmsg = "伺服器沒有回應，無法註冊帳號。";
				break;
			default:
				errmsg = "伺服器發生未預期錯誤，無法註冊帳號。";
		}
		alert(errmsg);
	}).always(function(){
		toggleSubmitButton("register");
	});
	
	return false;
});


