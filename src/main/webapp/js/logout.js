
$(document).ready(function() {
	//console.log("ready");
	
	logout();
	

});

function logout() {

	var d = new Date(), n = d.getTime();

	ajaxObj = {
		type : "GET",
		url : "http://localhost:8081/Readit/rest/user/logout",
		data : "ts=" + n,
		contentType : "application/json",
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR.responseText);
		},
		success : function(data) {
			// console.log(data);
					if(data.status=="success")
						{
						window.open("index.html","_self");
						}
					
					
		},
		complete : function(XMLHttpRequest) {
			// console.log( XMLHttpRequest.getAllResponseHeaders() );
		},
		dataType : "json" // request JSON
	};

	return $.ajax(ajaxObj);

}





