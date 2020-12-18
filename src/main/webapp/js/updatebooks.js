
$(document).ready(function() {
	//console.log("ready");
	
	
	
	
	$('#submit_addbooks').click(function(e) {
		
		var $post_example = $('#updatebooksform');
		e.preventDefault();
		var jsObj = $post_example.serializeObject()
			, ajaxObj = {};
		
		
		ajaxObj = {  
			type: "POST",
			url: "http://localhost:8081/Readit/rest/books/updatebooks", 
			data: JSON.stringify(jsObj), 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
			},
			success: function(data) { 
				var data1=data.status;
				if(data1=="success")
				{
					alert("Book updated successfully");
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

