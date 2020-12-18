
$(document).ready(function() {
	//console.log("ready");
	
	
	
	
	$('#submit_document').click(function(e) {
		
		var $post_example = $('#adddoc');
		e.preventDefault();
		var jsObj = $post_example.serializeObject()
			, ajaxObj = {};
		
		
		ajaxObj = {  
			type: "POST",
			url: "http://localhost:8081/Readit/rest/books/addpane", 
			data: JSON.stringify(jsObj), 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
			},
			success: function(data) { 
				var data1=data.status;
				if(data1=="success")
				{
					alert("Book added successfully");
				}
					else
						alert("username not exist");
				
			},
			complete: function(XMLHttpRequest) {
			}, 
			dataType: "json" 
		};
		
		$.ajax(ajaxObj);
	});
	

});
