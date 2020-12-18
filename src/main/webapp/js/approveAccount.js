
$(document).ready(function() {
	//console.log("ready");
	getDetails();
	
	
$('#submit_approve').click(function(e) {
		
		var $post_example = $('#approve');
		e.preventDefault();
		var jsObj = $post_example.serializeObject()
			, ajaxObj = {};
		alert(jsObj.username);
		var str=jsObj.username;
        var JSONObj = { "username" : jsObj.username};
        alert(JSONObj.username);
		alert(str);
		
		ajaxObj = {  
			type: "POST",
			url: "http://localhost:8081/Readit/rest/user/approve", 
			data:JSON.stringify(str), 
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


function getDetails()
{

var d = new Date(), n = d.getTime();

ajaxObj = {
	type : "GET",
	url : "http://localhost:8081/Readit/rest/user/approveAccount",
	data : "ts=" + n,
	contentType : "application/json",
	error : function(jqXHR, textStatus, errorThrown) {
		console.log(jqXHR.responseText);
	},
	success : function(data) {
		// console.log(data);
		
		var data1=data.status;
		 var html_string="";
			$.each(data, function(index1, val1) {
				//console.log(val1);
			
				html_string = html_string + templateGetInventory(val1);
			});
			
			$('#acctable').html("<table><tr><td id='head'>NAME</td><td id='head'>USERNAME</td><td id='head'>MOBILE</td><td id='head'>EMAIL</td><td id='head'>APPROVE</td></tr>"+html_string+"</table>");
				
	},
	complete : function(XMLHttpRequest) {
		// console.log( XMLHttpRequest.getAllResponseHeaders() );
	},
	dataType : "json" // request JSON
};

return $.ajax(ajaxObj);

}


function templateGetInventory(param) {

	return '<tr>' +
	
			'<td id="content">'+param.name+'</td>'+
			'<td id="content">'+param.username+'</td>'+
			'<td id="content">'+param.mobile+'</td>'+
			'<td id="content">'+param.email+'</td>'+
			'<td id="content">'+'<input type="checkbox" name="username" value='+param.username+'> Approve</input>'+'</td>'+
			'</tr>';
}

