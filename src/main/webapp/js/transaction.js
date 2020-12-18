
$(document).ready(function() {
	//console.log("ready");
	
	
	
	$('#submit_debit').click(function(e) {
		
		var $post_example = $('#debitform');
		e.preventDefault();
		var jsobj = $post_example.serializeObject()
			, ajaxObj = {};
		var jsObj = {"id":window.my_id,"count":jsobj.count};
		
		
		ajaxObj = {  
			type: "POST",
			url: "http://localhost:8081/Readit/rest/books/buybook", 
			data: JSON.stringify(jsObj), 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
			},
			success: function(data) { 
				var data1=data.status;
				if(data1=="success")
				{
					alert("Book will set to your address");
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
	
	
$('#submit_netbank').click(function(e) {
		
		var $post_example = $('#netbankingform');
		e.preventDefault();
		var jsobj = $post_example.serializeObject()
			, ajaxObj = {};
		var jsObj = {"id":window.my_id,"count":jsobj.count};
		
		
		ajaxObj = {  
			type: "POST",
			url: "http://localhost:8081/Readit/rest/books/buybook", 
			data: JSON.stringify(jsObj), 
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
			},
			success: function(data) { 
				var data1=data.status;
				if(data1=="success")
				{
					alert("Book will set to your address");
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

