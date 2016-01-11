var libApp = angular.module("libApp", [])

libApp.controller("routeController", 
	function ($scope)	{
		$scope.inc = {
		    "url":"login.html"
		};
	}
);

libApp.controller("bookController", 
	function ($scope, $http, TokenFactory)	{
		$scope.addBook = function()	{
			var request = $http( {
				method  : "POST",
				url		: "/library/add",
				data	: {
					"name" 	: $scope.book.name,
					"author": $scope.book.author,
					"isbn"	: $scope.book.isbn,
					"category": $scope.book.category
				},
				headers	: {
					"LIB_AUTH_TOKEN" : TokenFactory.getValue(),
					"Accept" : "application/json"
				}
			});
			request.success(
				function (response)	{
					angular.element(document.querySelector("#divstatus")).css("visibility", "visible");
					if (response.status == "success")	{
						angular.element(document.querySelector("#divstatus")).addClass("alert-success");
						$scope.statusmsg="Book has been added successfully!";
						$scope.book = null; 
					}
					else	{
						angular.element(document.querySelector("#divstatus")).addClass("alert-danger");
						$scope.statusmsg="Error:" + response.detail;
					}
				});
			
			request.error(
				function (response)	{
					angular.element(document.querySelector("#divstatus")).css("visibility", "visible");
					angular.element(document.querySelector("#divstatus")).addClass("alert-danger");
					$scope.statusmsg="Error:" + response.detail;
				});
				
		}
	
	});
libApp.controller("loginController",  
		function ($scope, $http, TokenFactory)	{
			$scope.login = function()	{
				var request = $http( {
					method  : "GET",
					url		: "/library/role",
					headers	: {
						"LIB_AUTH_TOKEN" : $scope.txtlogin + ":" + $scope.txtpassword,
						"Accept" : "application/json"
					}
				});
				request.success(
					function (response)	{
						// if user has access to search books, direct to that page, else to add books page 
						if (response["role"][0] == "ROLE_SEARCH_BOOKS")	{
							$scope.inc.url= "searchbook.html"; //"addbook.html";
						}
						else	{
							$scope.inc.url= "addbook.html"; //"searchbook.html";
						}
							
						//TokenFactory.setValue($scope.loginkey);
						TokenFactory.setValue($scope.txtlogin + ":" + $scope.txtpassword);
						
					});
				
				request.error(
					function (response)	{
						angular.element(document.querySelector("#divstatus")).css("visibility", "visible");
						angular.element(document.querySelector("#divstatus")).addClass("alert-danger");
						$scope.statusmsg="Error: " + response.message;
					});
			}
		});

libApp.controller("searchController", 
		function ($scope, $http, TokenFactory)	{
	
			$scope.search = function()	{
				var request = $http( {
					method  : "POST",
					url		: "/library/search",
					headers	: {
						"LIB_AUTH_TOKEN" : TokenFactory.getValue(),
						"Accept" : "application/json"
					},
					data : {
						"bookname" : $scope.searchname
					}
				});
				request.success(
					function (response)	{
						angular.element(document.querySelector("#divstatus")).css("visibility", "visible");
						angular.element(document.querySelector("#divstatus")).addClass("alert-success");
						
						if (response.results == null)	{
							$scope.statusmsg = "No books found!";
						}
						else	{	
							$scope.statusmsg =  response["results"].length + " Books found!";
						}
					});
				
				request.error(
					function (response)	{
						angular.element(document.querySelector("#divstatus")).css("visibility", "visible");
						angular.element(document.querySelector("#divstatus")).addClass("alert-danger");
						$scope.statusmsg="Error: " + response.message;
					});
			}
		});

libApp.constant('authToken', 'authToken');

libApp.factory('TokenFactory', function() {
    var authToken = {
            value: ""
        };

        authToken.setValue = function(val) {
          this.value = val;
        };

        authToken.getValue = function() {
            return this.value;
        };

        return authToken;
    })
