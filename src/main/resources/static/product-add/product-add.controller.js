angular.module('product-add')
.controller('ProductAddController', function(productService, $location) {
  var controller = this;

  controller.product = {};

  controller.save = save;

  function save() {
    productService.create(controller.product).then(function() {
      $location.path('/products');
    });
  }
});
