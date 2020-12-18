
$(document).ready(function() {
	//console.log("ready");
	
	
	
	var d = new Date(), n = d.getTime();

	ajaxObj = {
			type : "GET",
			url : "http://localhost:8081/Readit/rest/books/getCount",
			data : "ts=" + n,
			contentType : "application/json",
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			},
			success : function(data) {
				// console.log(data);
				
				var data1=data.status;
				 var html_string="";
	/*				$.each(data, function(index1, val1) {
						//console.log(val1);
					
						html_string = html_string + templateGetInventory(val1);
					});*/
				 var source = $("#document-facet").html();
				 var template = Handlebars.compile(source);
				 var html_string = template(data);
				 $('#side1').html(html_string);
					
						
			},
			complete : function(XMLHttpRequest) {
				// console.log( XMLHttpRequest.getAllResponseHeaders() );
			},
			dataType : "json" // request JSON
		};

		$.ajax(ajaxObj);
		
	
	ajaxObj = {
		type : "GET",
		url : "http://localhost:8081/Readit/rest/books/getDocuments",
		data : "ts=" + n,
		contentType : "application/json",
		error : function(jqXHR, textStatus, errorThrown) {
			console.log(jqXHR.responseText);
		},
		success : function(data) {
			// console.log(data);
			
			var data1=data.status;
			 var html_string="";

			 var source = $("#document-template").html();
			 var template = Handlebars.compile(source);
			 var html_string = template(data);
			 $('#side2').html(html_string);
				
					
		},
		complete : function(XMLHttpRequest) {
			// console.log( XMLHttpRequest.getAllResponseHeaders() );
		},
		dataType : "json" // request JSON
	};

	$.ajax(ajaxObj);
	
	
	
	ajaxObj = {
			type : "GET",
			url : "http://localhost:8081/Readit/rest/books/getPane",
			data : "ts=" + n,
			contentType : "application/json",
			error : function(jqXHR, textStatus, errorThrown) {
				console.log(jqXHR.responseText);
			},
			success : function(data) {
				// console.log(data);
				
				var data1=data.status;
				 var html_string="";
				 var i=0;
				$.each(data, function(index1, val1) {
						//console.log(val1);
					
						html_string = html_string + templateGetPanes(val1,i++);
					});
				$('#side3').html("<form id='labelform'><table id='labeltable'>"+html_string+"</table></form>");
				/* var source = $("#document-pane").html();
				 var template = Handlebars.compile(source);
				 var html_string = template(data);
				 $('#side3').html(html_string);*/
					
						
			},
			complete : function(XMLHttpRequest) {
				// console.log( XMLHttpRequest.getAllResponseHeaders() );
			},
			dataType : "json" // request JSON
		};

	$.ajax(ajaxObj);
	
$('#submit_review').click(function(e) {
		
		
	var $post_example = $('#setlabel');
	e.preventDefault();
	var jsObj = $post_example.serializeObject()
		, ajaxObj = {};
	var str="";
	str+=jsObj.id;
	$post_example=$('#labelform');
	jsObj= $post_example.serializeObject();
	str+="|";
	str+=JSON.stringify(jsObj);

		ajaxObj = {  
			type: "POST",
			url: "http://localhost:8081/Readit/rest/books/setlabel", 
			data: JSON.stringify(str), 
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
	

$(document.body).on('click', '.add', function(e) {
	var $this = $(this)
	,cart= $this.val()
	, $tr = $this.closest('tr')
	, id = $tr.find('.label').val()
	alert(id);
			var newWindow = window.open("addsubpane.html");
			newWindow.label=id;
});




});


function templateGetPanes(param,ia){
	
	var space="";
	for(var i=0;i<param.space.length;i++)
		{
		space+="&nbsp";
		}
	var str="";
	if(param.space.length==1)
	{
		str+='<button class="add">+</button>';
		name="labels";
	}	
	else
		{
			name="label"+ia;
		}
	return ''+
			'<tr>'+
		    '<td>'+
		   space+'<input type='+param.type+' name='+name+' id="william" class="label" value='+param.name+'></input>'+param.space+param.name+'<br><br>'+
		   '</td>'+
		   '<td>'+
		    str+
		   '</td>'+   
		   '</tr>';
	
}
