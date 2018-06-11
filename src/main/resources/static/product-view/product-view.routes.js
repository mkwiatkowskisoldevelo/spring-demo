angular.module('product-view')
.config(function($routeProvider) {
  $routeProvider.when('/products/view/:productId', {
    templateUrl: '/product-view/product-view.html',
    controller: 'ProductViewController',
    controllerAs: 'controller',
    resolve: {
      product: function(productService, $route) {
        return productService.get($route.current.params.productId);
      }
    }
  });
});