angular.module('receipt-add')
.config(function($routeProvider) {
  $routeProvider.when('/receipts/add', {
    templateUrl: '/receipt-add/receipt-add.html',
    controller: 'ReceiptAddController',
    controllerAs: 'controller',
    resolve: {
      products: function(productService) {
        return productService.query();
      }
    }
  });
});