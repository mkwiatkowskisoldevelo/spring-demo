angular.module('product-add')
.config(function($routeProvider) {
  $routeProvider.when('/products/add', {
    templateUrl: '/product-add/product-add.html',
    controller: 'ProductAddController',
    controllerAs: 'controller'
  });
});