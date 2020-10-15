angular
    .module("myApp", ["satellizer"])
    .config(function($authProvider) {
        // Parametros de configuración
        $authProvider.loginUrl = "https://localhost:8443/login";
        $authProvider.tokenName = "token";
        $authProvider.tokenPrefix = "myApp",

    // Configuración de las rutas/estados
    $stateProvider
        .state("login", {
        url: "/authenticate",
        data : angular.toJson($scope.userForm),
        templateUrl: "login.html",
        controller: "LoginController",
        controllerAs: "login"
    });
});