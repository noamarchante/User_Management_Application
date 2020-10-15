var app = angular.module("UserManagement", []);

//Controller Part
app.controller("UserController", function($scope, $http) {
    $scope.users = [];
    $scope.userForm = {
        userId: -1,
        userName : "",
        password : "",
        emailAddress: "",
        enabled: 0,
        lastLogin: "",
        accountId: ""
    };

//Now load the data from server
_refreshUserData();

//HTTP POST/PUT methods for add/edit customer
// with the help of id, we are going to find out whether it is put or post operation

    $scope.submitUser = function() {

        var method = "";
        var url = "";
        if ($scope.userForm.userId == -1) {
            //Id is absent in form data, it is create new customer operation
            method = "POST";
            url = 'https://localhost:8443/v1/userAdd';
        } else {
            //Id is present in form data, it is edit customer operation
            method = "PUT";
            url = 'https://localhost:8443/v1/userUpdate';
        }

        $http({
            method : method,
            url : url,
            data : angular.toJson($scope.userForm),
            headers : {
                'Content-Type' : 'application/json'
            }
        }).then( _success, _error );
    };

//HTTP DELETE- delete customer by Id
    $scope.deleteUser = function(user) {
        $http({
            method : 'DELETE',
            url : 'https://localhost:8443/v1/userDelete/' + user.userId
        }).then(_success, _error);
    };

// In case of edit, populate form fields and assign form.id with customer id
    $scope.editUser = function(user) {

        $scope.userForm.userName = user.userName;
        $scope.userForm.password = user.password;
        $scope.userForm.emailAddress = user.emailAddress;
        $scope.userForm.enabled =user.enabled;
        $scope.userForm.lastLogin = user.lastLogin;
        $scope.userForm.accountId = user.accountId;
        $scope.userForm.userId = user.userId;
    };

    // Private Methods
//HTTP GET- get all customers collection
    function _refreshUserData() {
        $http({
            method : 'GET',
            url : 'https://localhost:8443/v1/userSelect'
        }).then(function successCallback(response) {
            $scope.users = response.data;
        }, function errorCallback(response) {
            console.log(response.statusText);
        });
    }

    function _success(response) {
        _refreshUserData();
        _clearFormData()
    }

    function _error(response) {
        console.log(response.statusText);
    }

//Clear the form
    function _clearFormData() {
        $scope.userForm.userId = -1;
        $scope.userForm.userName = "";
        $scope.userForm.password = "";
        $scope.userForm.emailAddress = "";
        $scope.userForm.enabled = 0;
        $scope.userForm.lastLogin = "";
        $scope.userForm.accountId = "";
    };
});

