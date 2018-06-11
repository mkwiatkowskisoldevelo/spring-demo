angular.module('product-list')
.controller('ProductListController', function(productService, $location) {
  var controller = this;

  controller.searchProducts = search;
  controller.viewProduct = view;
  controller.deleteProduct = remove;

  search();

  function search() {
    productService.query().then(function(response) {
      controller.productList = response;
    });
  }

  function view(id) {
    $location.path('/products/view/' + id);
  }

  function remove(id) {
    productService.remove(id).then(function() {
      search();
    });
  }
});
