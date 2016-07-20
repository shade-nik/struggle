var userBaseUrl = "http://localhost:8088/webapi/rest/api/users";

var currentUser;

// for story with only get enabled
$('.details_container input, .details_container button').prop("disabled", true);
$("#delete_button").prop("disabled", true);


// initial
$("#delete_button").hide();

$("#search_button").click(function() {
	search($("#name_for_search").val());
	return false;
});

$('#user_list a').on('click', function() {
	getUserById($(this).data('identity'));
});

$('#user_list').delegate(
		'a',
		'click',
		function() {
			console.log("index " + $(this).index() + ' was clicked.');
			console.log("Attribute data-identity "
					+ $(this).attr('data-identity') + ' was clicked.');
			console.log("$(this).data('identity') " + $(this).data('identity')
					+ ' was clicked.');
			getUserById($(this).attr('data-identity'));

		});

$("img").attr("src", "resources/img/default_user.jpg");

function search(userName) {
	if (userName == '')
		getAll();
	else
		getUserByName(userName);
}

function getAll() {
	console.log("get all users");
	$.ajax({
		url : userBaseUrl,
		type : "GET",
		dataType : 'xml',
		headers : {
			"Accept" : "application/xml; charset=utf-8"
		},
		success : displayResults
	});
}

function getUserById(id) {
	console.log("get by id " + id);
	$.ajax({
		url : userBaseUrl + "/user/?uuid=" + id,
		type : "GET",
		dataType : 'xml',
		headers : {
			"Accept" : "application/xml; charset=utf-8"
		},
		success : function(data) {
			$("#delete_button").show();
			console.log("find by uuid success "
					+ $(data).find('username').text());
			currentUser = data;
			displayUserDetails(currentUser);
		}
	});
}

function getUserByName(userName) {
	console.log("get by name " + userName);
	$.ajax({
		url : userBaseUrl + "/user/" + userName,
		type : "GET",
		dataType : 'xml',
		headers : {
			"Accept" : "application/xml; charset=utf-8"
		},
		success : function(data) {
			$("#delete_button").show();
			console.log("find by name success "
					+ $(data).find('username').text());
			currentUser = data;
			displayUserDetails(currentUser);
		},
		error: function(xhr, textStatus, errorThrown) {
			console.log("find by name error:  textStatus:" + textStatus + "error: " + errorThrown);
			currentUser = {};
			displayUserDetails(currentUser);
		}
	});
}

function displayResults(data) {
	$("#user_list li").remove();

	var list = $(data);

	$.each(list.find("StruggleUser"), function(index, user) {
		var current = $(this);
		console.log("Found payload in the res list " + user);
		$('#user_list').append(
				'<li><a href="#" data-identity="'
						+ $(user).find("userUUID").text() + '">'
						+ $(user).find("username").text() + '</a></li>');
	});
}

function displayUserDetails(data) {
	$("#user_id").val($(data).find("userUUID").text());
	$("#user_name").val($(data).find("username").text());
	$("#user_pass").val($(data).find("password").text());
	$("#profile_user_first_name").val(
			$(data).find("profile").find("firstName").text());
	$("#profile_user_last_name").val(
			$(data).find("profile").find("lastName").text());
	$("#profile_user_enabled").prop('checked',
			$(data).find("profile").find("enabled").text());

	$(".user_info li").remove();

	$(data).find("Setting").each(
			function(index, setting) {
				var desct = $(setting).find("Description");
				$("#user_settings").append(
						'<li>' + $(setting).find("Description").text()
								+ '</li>');
			});

	$.each($(data).find("Role"),
			function(index, role) {
				$("#user_roles").append(
						'<li>' + $(role).find("name").text() + '</li>');
			});

	$.each($(data).find("Group"), function(index, group) {
		$("#user_groups").append('<li>' + $(group).text() + '</li>');
	});
}
