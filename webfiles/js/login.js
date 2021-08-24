function toggleSubmitButton(){
	$("#form_send").toggle();
	$("#form_send_waiting").toggle();
}

function isSubmitting(){
	return $("#form_send_waiting").css("display") !== "none";
}

$("#loginRegion").submit(function(){
	
	if(isSubmitting()) return false;
	
	toggleSubmitButton();
	
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
		toggleSubmitButton();
	});
	
	return false;
});


