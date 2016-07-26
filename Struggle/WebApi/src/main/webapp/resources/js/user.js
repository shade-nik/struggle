var userBaseUrl = "http://localhost:8088/webapi/rest/api/users";

var currentUser;
var mode;
var detached;

function ObjTest_Position(pos) {
	this.left = pos.left;
	this.top = pos.top;
}

ObjTest_Position.prototype.getLeft() {
	return this.left;
}

ObjTest_Position.prototype.getTop() {
	return this.top;
}

// initial
$("#delete_button").hide();

$("#search_button").click(function() {
	search($("#name_for_search").val());
	return false;
});

$("#chat_button").click(function() {
	if(mode == 'chat_mode') {
	   return false;
	}
	else {
		mode = 'chat_mode';
		switchMode(mode);
	}
});

$("#add_button").click(function() {
    newUser();
    return false;
});

$("#save_button").click(function() {
	if($("#user_id").val() == '') 
		createUser();
	else
		updateUser();
	return false;
});

$('#user_list a').on('click', function() {
	getUserById($(this).data('identity'));
});

$("#delete_button").click(function() {
	deleteUser();
	return false;
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

//$("img").attr("src", "resources/img/default_user.jpg");

/* animation tests */
$("span#add_setting_button").click(function() {
	var position = $(this).position();
	var testObj = new ObjTest_Position(position);
	var l = testObj.getLeft();
	var t = testObj.getTop();
	$("span#add_setting_button")
		.css({position:'absolute',
		top:position.top,
		left:position.left});
	console.log("1-rst animation.");
	$("span#add_setting_button").animate({left:'+=250'},2500);
	console.log("2-nd animation.");
	$("span#add_setting_button").animate({top:'+=250'},2500);
	console.log("3-rd animation.");
	$("span#add_setting_button").animate({left:'-=250'},2500);
	console.log("4-th animation.");
	$("span#add_setting_button").animate({top:'-=250'},2500);
});

$("span#add_role_button").click(function() {
	console.log("Add role click");
});

$("span#add_group_button").click(function() {
	console.log("Add group click");
});


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

function newUser() {
	console.log("create new user");
	$("#delete_button").hide();
	currentUser = {};
	displayUserDetails(currentUser);
}

function createUser() {
	console.log("create user");
	$.ajax({
		url: userBaseUrl + "/user",
		type: "POST",
		dataType: 'xml',
		contentType: "application/xml",
	    headers: {          
	       "Accept": "application/xml; charset=utf-8"         
	    },
	    data: formToXML(),
	    success: function(data, textStatus, jqXHR) {
			console.log("User added succesfully ");
			$("#delete_button").show();
			$("#user_id").val($(data).find("userUUID").text());
			
			console.log("Add created user to userlist" + $(data).find("username").text());
			$('#user_list').append(
					'<li><a href="#" data-identity="'
							+ $(data).find("userUUID").text() + '">'
							+ $(data).find("username").text() + '</a></li>');
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log("Error adding user " + textStatus);
		}
	});
}


function updateUser() {
	console.log("Update user");
	$.ajax({
		url: userBaseUrl + "/user/" + $("#user_name").val(),
		type: "PUT",
		dataType: 'xml',
		contentType: "application/xml",
	    headers: {          
	       "Accept": "application/xml; charset=utf-8"         
	    },
	    data: formToXML(),
	    success: function(data, textStatus, jqXHR) {
	    	console.log("Updated succesfully");
	    },
	    error:function(jqXHR, textStatus, errorThrown) {
	    	console.log("Error when updating");
	    	if(textStatus == 'badRequest') {
	    		displayValidationErrors();
	    	}
	    }
	});
}

function deleteUser() {
	console.log("Delete user");	
	$.ajax({
		url: userBaseUrl + "/user/" + $("#user_name").val(),
		type: "DELETE",
		success: function(data, textStatus, jqXHR) {
			console.log("Sucessfully deleted");
			console.log("Removing <a> with [data-identity='"+ $("#user_id").val() +"]'");
			$('#user_list a[data-identity="'+ $("#user_id").val() +'"]').remove();
			currentUser = {};
			displayUserDetails(currentUser);
		},
		error: function(jqXHR, textStatus, errorThrown) {
			console.log("Can't delete user, error" + errorThrown);
		}
	});
}

function displayResults(data) {
	$("#user_list li").remove();

	var list = $(data);
	var list_items = []; 
	$.each(list.find("StruggleUser"), function(index, user) {
		console.log("Found payload in the res list " + user);
		list_items.push(		
				'<li class="ui-state-default ui-corner-all">'+
						'<span class="ui-icon ui-icon-person"></span>'
						+'<a href="#" data-identity="'
						+ $(user).find("userUUID").text() + '">'
						+ $(user).find("username").text() + '</a></li>');
	});
	
	$('#user_list').append(list_items.join(''));
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

function formToXML () {
	try {
		var xmlString;
		var serializer = new XMLSerializer();
		
		var xml = $($.parseXML('<?xml version="1.0" encoding="utf-8" ?><StruggleUserRequest/>'));

		$('StruggleUserRequest', xml).append($('<StruggleUserPayload/>', xml));
		$('StruggleUserPayload', xml).append($('<username/>', xml).text($("#user_name").val()));
		$('StruggleUserPayload', xml).append($('<password/>', xml).text($("#user_pass").val()));
		$('StruggleUserPayload', xml).append($('<userUUID/>', xml).text($("#user_id").val()));

		$('StruggleUserPayload', xml).append($('<profile/>', xml));
		$('profile', xml).append($('<firstName/>', xml).text($("#profile_user_first_name").val()));
		$('profile', xml).append($('<lastName/>', xml).text($("#profile_user_last_name").val()));
		$('profile', xml).append($('<enabled/>', xml).text($("#profile_user_enabled").is(":checked")));
//skip filling groups, roles, settings... 				
		$('StruggleUserPayload', xml).append($('<Settings/>', xml));
		$(".user_settings li").each(function () {
			var setting =  $(this).val();
			var elem = $('Settings', xml).append($('<Setting/>', xml));
			$(elem).append($('<Description/>', xml).text(setting));
		});
						
		xmlString = serializer.serializeToString(xml[0]);
	     
	    return xmlString;
	} catch (e) {
		console.log("Exception " + e);
	}
}


function displayValidationErrors(jqXHR) {
	console.log("Validation errors " + jqXHR);

}



/* Chat stuff */

function switchMode(mode) {
    detached = $("div.right_container").children().detach();
    var right_container = $("div.right_container");
    
    $('div.template#chatSection').children().clone().appendTo(right_container);
}