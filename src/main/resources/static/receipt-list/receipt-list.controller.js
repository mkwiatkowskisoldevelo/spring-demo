angular.module('receipt-list')
.controller('ReceiptListController', function(receiptService) {
  var controller = this;

  controller.search = search;

  search();

  function search() {
    receiptService.query().then(function(response) {
      controller.receiptList = response;
    });
  }

});
