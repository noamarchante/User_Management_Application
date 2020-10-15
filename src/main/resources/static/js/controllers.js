angular
    .module("myApp.controllers")
    .controller("LoginController", LoginController);


function LoginController($auth, $location) {
        var vm = this;
        this.login = function(){
           $auth.login({
               userName: vm.userName,
               password: vm.password
           })
           .then(function(){
               // Si se ha logueado correctamente, lo tratamos aquí.
               // Podemos también redirigirle a una ruta
               $location.path("/v1/user")
           })
           .catch(function(response){
               // Si ha habido errores llegamos a esta parte
           });
        }
}