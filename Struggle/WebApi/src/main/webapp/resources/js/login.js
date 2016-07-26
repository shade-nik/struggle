var loginBaseUrl = "http://localhost:8088/webapi/login.html";


//initial
$("#login-button").click(function(){
	login();
	return false;
});



function login() {
	console.log("try login...");
	$.ajax({
		url: loginBaseUrl,
		type: "POST",
	    data: $('.form-signin').serialize(),
	    success: function(data, textStatus) {
			console.log("Loggend in succesfully ");
			console.log("Data: " + data + " status: " + textStatus);
			window.document.write(data);
			
//			window.location = window.location.origin + '/webapi/index.html';

	    },
		error: failedLogin
	});
}

function failedLogin(data, status) {
	console.log("Login failed");
    $('.alert').append('<div class="error">Login failed, please try again.</div>');

}