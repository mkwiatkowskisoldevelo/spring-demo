angular.module('product-view')
.controller('ProductViewController',
            function(product, productService, $location) {
  var controller = this;

  controller.product = product;

  controller.update = update;

  function update() {
    productService.update(controller.product).then(function() {
      $location.path('/products');
    });
  }
});
