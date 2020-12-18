
$(document).ready(function() {
	//console.log("ready");
	
	var jsObj = {"sample":"hi"}
		, ajaxObj = {};
		ajaxObj = {  
			type: "POST",
			url: "http://localhost:8081/Readit/rest/books/cartitems", 
			data: JSON.stringify(jsObj),
			contentType:"application/json",
			error: function(jqXHR, textStatus, errorThrown) {
				console.log("Error " + jqXHR.getAllResponseHeaders() + " " + errorThrown);
			},
			success: function(data) { 
			/*	var data1=data.status;
				 var html_string="";
					$.each(data, function(index1, val1) {
						//console.log(val1);
					
						html_string = html_string + templateGetInventory(val1);
					});
				*/	
				 var source = $("#document-template").html();
				 var template = Handlebars.compile(source);
				 var html_string = template(data);
				 $('#sidebar1').html(html_string);

			},
			complete: function(XMLHttpRequest) {
			}, 
			dataType: "json" 
		};
		
		$.ajax(ajaxObj);
		
		$(document.body).on('click', '.buy', function(e) {
			var $this = $(this)
			,cart= $this.val()
			, $tr = $this.closest('tr')
			, id = $tr.find('.myid').val()
			alert(id);
					var newWindow = window.open("buyitem.html");
					newWindow.my_id=id;
		});
		


});

function templateGetInventory(param) {

	
	  if(param.category=="Arts & Science")
	  {
	    var icon='<i style="margin-top:10px;" class="fa fa-flask" aria-hidden="true">';
	  }
	  else if(param.category=="Bibliograpgy")
	  {
	    var icon='<i style="margin-top:10px;" class="fa fa-book" aria-hidden="true">';
	  }
	  else if(param.category=="Health and Cooking")
	  {
		    var icon='<i style="margin-top:10px;" class="fa fa-medkit" aria-hidden="true">';
	  }
	  else if(param.category=="Computers")
	  {
		    var icon='<i style="margin-top:10px;" class="fa fa-desktop" aria-hidden="true">';
	  }
	  else if(param.category=="Dictionaries")
	  {
		    var icon='<i style="margin-top:10px;" class="fa fa-globe" aria-hidden="true">';
	  }
	  else if(param.category=="College and Schools")
	  {
		    var icon='<i style="margin-top:10px;" class="fa fa-graduation-cap" aria-hidden="true">';
	  }
	  else if(param.category=="Business and Corporate")
	  {
		    var icon='<i style="margin-top:10px;" class="fa fa-briefcase" aria-hidden="true">';
	  }
	  else if(param.category=="Entertainment")
	  {
		    var icon='<i style="margin-top:10px;" class="fa fa-film" aria-hidden="true">';
	  }
	return ' ' +
    '<table id="table1">' +
    '<tr id="row1">' +
    '<td >' +
    '<div id="image1">'+icon+'</div>' +
    '</td>' +
    '<td id="detail2">' +
    '<ul style="list-style-type: none">' +
    '<li><span style="font-size: 17px ;color: #E6ECEA;">' + param.name + '</span></li>' +
    '<li><span style="font-size: 17px ;color: #E6ECEA;">By ' + param.author + '</span></li>' +
    '<li><span style="font-size: 15px ;color: #E6ECEA;">Rs.' + param.price + '&nbsp&nbspStock:' + param.available + '</span></li>' +
    '</ul>' +
    '</td>' +
    '</tr>' +
    '<tr id="row2">' +
    '<td ><div id="detail1"><span style="font-size: 17px ;color: #E6ECEA;">' + param.category + '</span></div></td>' +
    '<td id="detail2">' +
    '<ul style="list-style-type: none">' +
    '<li class="id" value='+param.id+'><span style="font-size: 17px ;color: #E6ECEA;">Book id:' + param.id + '</span></li>' +
    '<li style="display:inline;"><button class="buy">Buy Now</button></li>&nbsp&nbsp' +
    '</ul>' +
    '</td>' +
    '</tr>' +
    '</table>';
}

