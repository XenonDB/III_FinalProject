function toggleSubmitButton(){
	$("#form_send_text").toggle();
	$("#form_send_waiting").toggle();
}

function isSubmitting(){
	return $("#form_send_waiting").css("display") !== "none";
}

$("#registerRegion").submit(function(){
	
	if(isSubmitting()) return false;
	
	toggleSubmitButton();
	
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
		toggleSubmitButton();
	});
	
	return false;
});


