$(document)
		.ready(
				function() {
					// console.log("ready");

					$('#submit_register')
							.click(
									function(e) {

										var $post_example = $('#registerform');
										e.preventDefault();
										var jsObj = $post_example
												.serializeObject(), ajaxObj = {};

										ajaxObj = {
											type : "POST",
											url : "http://localhost:8081/Readit/rest/user/register",
											data : JSON.stringify(jsObj),
											contentType : "application/json",
											error : function(jqXHR, textStatus,
													errorThrown) {
												console
														.log("Error "
																+ jqXHR
																		.getAllResponseHeaders()
																+ " "
																+ errorThrown);
											},
											success : function(data) {
												var data1 = data.status;
												if (data1 == "success") {
													window
															.open(
																	"otp-verification.html",
																	"_self");
												} else
													alert("username already taken");
												$('#div_ajaxResponse').text(
														data.status);

											},
											complete : function(XMLHttpRequest) {
											},
											dataType : "json"
										};

										$.ajax(ajaxObj);
									});

					$('#submit_login')
							.click(
									function(e) {

										var $post_example = $('#loginform');
										e.preventDefault();
										var jsObj = $post_example
												.serializeObject(), ajaxObj = {};

										ajaxObj = {
											type : "POST",
											url : "http://localhost:8081/Readit/rest/user/login",
											data : JSON.stringify(jsObj),
											contentType : "application/json",
											error : function(jqXHR, textStatus,
													errorThrown) {
												console
														.log("Error "
																+ jqXHR
																		.getAllResponseHeaders()
																+ " "
																+ errorThrown);
											},
											success : function(data) {
												var data1 = data.status;

												if (data1 == "success") {
													if (data.desig == "student")
														window
																.open(
																		"mainhome.html",
																		"_self");
													else if (data.desig == "employee")
														window
																.open(
																		"mainhome.html",
																		"_self");
													else if (data.desig == "retailclient") {
														if (data.login == "approved")
															window
																	.open(
																			"mainhome.html",
																			"_self");
														else {
															window
																	.open(
																			"not-approved.html",
																			"_self");
														}
													}
													else if (data.desig == "admin") {
															window
																	.open(
																			"mainhome.html",
																			"_self");
														
													}

												} else
													alert("username not exist");

											},
											complete : function(XMLHttpRequest) {
											},
											dataType : "json"
										};

										$.ajax(ajaxObj);
									});

				});
