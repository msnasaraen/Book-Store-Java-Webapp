
$(document).ready(function() {
	//console.log("ready");
	
	getUser();
	

});

function getUser() {

	var d = new Date(), n = d.getTime();
	ajaxObj = {
		type : "GET",
		url : "http://localhost:8081/Readit/rest/user/getUser",
		data : "ts=" + n,
		contentType : "application/json",
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR.responseText);
		},
		success : function(data) {
			// console.log(data);
			var path = window.location.pathname;
			var page = path.split("/").pop();
					$('#name').html(data.username);
					$('#designation').html(data.designation);
					if(data.designation!="waste")
					{
						window.open("mainhome.html","_self");
					}
		},
		complete : function(XMLHttpRequest) {
		},
		dataType : "json" // request JSON
	};

	return $.ajax(ajaxObj);

}





