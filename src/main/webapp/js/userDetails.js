
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
					var html_string="";
					if(data.designation=="student"){
						if(page=="mainhome.html")
							{
							html_string+="<li><a class='selected'  href='mainhome.html'><i class='fa fa-home' aria-hidden='true'></i>&nbspHome</a></li>";
							}
						else
							html_string+="<li><a href='mainhome.html'><i class='fa fa-home' aria-hidden='true'></i>&nbspHome</a></li>";
						if(page=="cartitems.html")
							{
							html_string+="<li><a class='selected'  href='cartitems.html'><i class='fa fa-shopping-cart' aria-hidden='true'></i>&nbspMy Cart</a></li>";
							}
						else
							html_string+="<li><a href='cartitems.html'><i class='fa fa-shopping-cart' aria-hidden='true'></i>&nbspMy Cart</a></li>";
						if(page=="mypurchase.html")
						{
						html_string+="<li><a class='selected'  href='mypurchase.html'><i class='fa fa-exchange' aria-hidden='true'></i>&nbspBought items</a></li>";
						}
						else
						html_string+="<li><a href='mypurchase.html'><i class='fa fa-exchange' aria-hidden='true'></i>&nbspBought items</a></li>";
				
					
						if(page=="approve-account.html" || page=="add-books.html" || page=="UpdateBook.html" || page=="showmybooks")
							{
							window.open("mainhome.html","_self");
							}

						}
						if(data.designation=="employee"){
						
						if(page=="mainhome.html")
							html_string="<li><a class='selected'  href='mainhome.html'><i class='fa fa-home' aria-hidden='true'></i>&nbspHome</a></li>";
						else
							html_string="<li><a href='mainhome.html'><i class='fa fa-home' aria-hidden='true'></i>&nbspHome</a></li>";
						if(page=="cartitems.html")
						{
						html_string+="<li><a class='selected'  href='cartitems.html'><i class='fa fa-shopping-cart' aria-hidden='true'></i>&nbspMy Cart</a></li>";
						}
						else
						html_string+="<li><a href='cartitems.html'><i class='fa fa-shopping-cart' aria-hidden='true'></i>&nbspMy Cart</a></li>";
						if(page=="mypurchase.html")
						{
						html_string+="<li><a class='selected'  href='mypurchase.html'><i class='fa fa-exchange' aria-hidden='true'></i>&nbspBought items</a></li>";
						}
						else
						html_string+="<li><a href='mypurchase.html'><i class='fa fa-exchange' aria-hidden='true'></i>&nbspBought items</a></li>";
				
		
				
						if(page=="approve-account.html" || page=="add-books.html" || page=="UpdateBook.html" || page=="showmybooks")
						{
						window.open("mainhome.html","_self");
						}

						}
					else if(data.designation=="admin"){
						if(page=="mainhome.html")
							html_string="<li><a class='selected' href='mainhome.html'><i class='fa fa-home' aria-hidden='true'></i>&nbspHome</a></li>";
						else
							html_string="<li><a href='mainhome.html'><i class='fa fa-home' aria-hidden='true'></i>&nbspHome</a></li>";

						if(page=="add-books.html")
							html_string=html_string+"<li><a class='selected' href='add-books.html'><i class='fa fa-plus' aria-hidden='true'></i></i>&nbspAdd books</a></li>";
						else
							html_string=html_string+"<li><a href='add-books.html'><i class='fa fa-plus' aria-hidden='true'></i></i>&nbspAdd books</a></li>";
						if(page=="approve-account.html")
							html_string=html_string+"<li><a class='selected' href='approve-account.html'><i class='fa fa-user-plus' aria-hidden='true'>s</i>&nbspApprove Client</a></li>";
						else
							html_string=html_string+"<li><a href='approve-account.html'><i class='fa fa-user-plus' aria-hidden='true'></i>&nbspApprove Client</a></li>";
						if(page=="showmybooks.html")
							html_string=html_string+"<li><a class='selected' href='showmybooks.html'><i class='fa fa-address-book' aria-hidden='true'></i></i>&nbspShow My Books</a></li>";
						else
							html_string=html_string+"<li><a href='showmybooks.html'><i class='fa fa-address-book' aria-hidden='true'></i></i>&nbspShow My Books</a></li>";
						if(page=="UpdateBook.html")
							html_string=html_string+"<li><a class='selected' href='UpdateBook.html'><i class='fa fa-plus-square-o' aria-hidden='true'></i></i>&nbspUpdate Books</a></li>";
						else
							html_string=html_string+"<li><a href='UpdateBook.html'><i class='fa fa-plus-square-o' aria-hidden='true'></i></i>&nbspUpdate Books</a></li>";
						if(page=="cartitems.html")
						{
						html_string+="<li><a class='selected'  href='cartitems.html'><i class='fa fa-shopping-cart' aria-hidden='true'></i>&nbspMy Cart</a></li>";
						}
						else
						html_string+="<li><a href='cartitems.html'><i class='fa fa-shopping-cart' aria-hidden='true'></i>&nbspMy Cart</a></li>";
						if(page=="mypurchase.html")
						{
						html_string+="<li><a class='selected'  href='mypurchase.html'><i class='fa fa-exchange' aria-hidden='true'></i>&nbspBought items</a></li>";
						}
						else
						html_string+="<li><a href='mypurchase.html'><i class='fa fa-exchange' aria-hidden='true'></i>&nbspBought items</a></li>";
				
		
						


						}
					else if(data.designation=="retailclient"){
						
						if(page=="mainhome.html")
						html_string="<li><a class='selected' href='mainhome.html'><i class='fa fa-home' aria-hidden='true'></i>&nbspHome</a></li>";
						else
							html_string="<li><a  href='mainhome.html'><i class='fa fa-home' aria-hidden='true'></i>&nbspHome</a></li>";
						if(page=="add-books.html")
							html_string=html_string+"<li><a class='selected' href='add-books.html'><i class='fa fa-plus' aria-hidden='true'></i></i>&nbspAdd books</a></li>";
						else
							html_string=html_string+"<li><a href='add-books.html'><i class='fa fa-plus' aria-hidden='true'></i></i>&nbspAdd books</a></li>";
						if(page=="showmybooks.html")
							html_string=html_string+"<li><a class='selected' href='showmybooks.html'><i class='fa fa-address-book' aria-hidden='true'></i></i>&nbspShow My Books</a></li>";
						else
							html_string=html_string+"<li><a href='showmybooks.html'><i class='fa fa-address-book' aria-hidden='true'></i></i>&nbspShow My Books</a></li>";
						if(page=="UpdateBook.html")
							html_string=html_string+"<li><a class='selected' href='UpdateBook.html'><i class='fa fa-plus-square-o' aria-hidden='true'></i></i>&nbspUpdate Books</a></li>";
						else
							html_string=html_string+"<li><a href='UpdateBook.html'><i class='fa fa-plus-square-o' aria-hidden='true'></i></i>&nbspUpdate Books</a></li>";
						if(page=="cartitems.html")
						{
						html_string+="<li><a class='selected'  href='cartitems.html'><i class='fa fa-shopping-cart' aria-hidden='true'></i>&nbspMy Cart</a></li>";
						}
						else
						html_string+="<li><a href='cartitems.html'><i class='fa fa-shopping-cart' aria-hidden='true'></i>&nbspMy Cart</a></li>";
						if(page=="mypurchase.html")
						{
						html_string+="<li><a class='selected'  href='mypurchase.html'><i class='fa fa-exchange' aria-hidden='true'></i>&nbspBought items</a></li>";
						}
						else
						html_string+="<li><a href='mypurchase.html'><i class='fa fa-exchange' aria-hidden='true'></i>&nbspBought items</a></li>";
				
		
				
						if(page=="approve-account.html")
						{
						window.open("mainhome.html","_self");
						}
				
					}
					else if(data.designation=="waste")
					{
						window.open("index.html","_self");
					}
					
					$('#major').html(html_string);

					
		},
		complete : function(XMLHttpRequest) {
		},
		dataType : "json" // request JSON
	};

	return $.ajax(ajaxObj);

}





