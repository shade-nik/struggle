$(document).ready(function() {
	$("#action_div").click(function()
	{
		var baseUrl = document.location.origin;
		var somePath = window.location.pathname;
		var somePathArray = somePath.split('/');
		var somePathParse = somePath.split('/')[1];
		var authn = btoa("user:user");

		$.ajax({
	        url: "http://localhost:8088/webapi/rest/api/simple/task/Test",
			type: "GET",

	        headers: {          
                "Accept": "application/xml; charset=utf-8",         
                "authorization" : "Basic " + authn
            },
            success: function(data, textStatus, xqXHR) {
				$("#validation_error").html("<i>Success</i>"+textStatus+"<br/> Data:" + data + "<br/>jqXHR:" + jqXHR);
				$("#validation_error").append("<br/>jqXHR:" + jqXHR);
				$("#validation_error").hide('slow');
		        $('.user-roles').append(data);
			    xmlDoc = $.parseXML(data);
			    $xml = $( xmlDoc ) ;
			    $id = xml.find("Id");
			    $roles = xml.find("Roles");
		    	$('.user-id').append(id);
		        $('.user-roles').append(roles);

           },
           error:function(jqXHR, textStatus, errThrown)
           {
			 alert("Error: " + errThrown);
		     $("#validation_error").html("<i>jqXHR: "+ jqXHR + "</i><br/> Status:" + textStatus + "<br/>" + errThrown);
		   },
		   complete: function(jqXHR, textStatus )
	   	   {
			   $("#validation_error").html("<i>Complete: " + jqXHR + "</i> <br/>" + textStatus);
		   }					
	    });
	})
	
	
});