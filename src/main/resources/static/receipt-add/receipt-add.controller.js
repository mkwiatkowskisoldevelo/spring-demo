angular.module('receipt-add')
.controller('ReceiptAddController', function(receiptService, products, $location) {
  var controller = this;

  controller.receipt = {
    products: []
  };
  controller.products = products;

  controller.save = save;

  controller.addProduct = function() {
    controller.receipt.products.push(controller.selectedProduct);
  }

  function save() {
    receiptService.create(controller.receipt).then(function() {
      $location.path('/receipts');
    });
  }
});
