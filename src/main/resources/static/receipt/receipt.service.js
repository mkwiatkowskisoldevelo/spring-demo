angular.module('receipt')
.service('receiptService', function($resource) {
  var service = this;
  var resource = $resource('http://localhost:8080/receipts/:receiptId', {}, {
    update: {
        method: 'PUT'
    }
  });

  service.query = function() {
    return resource.query().$promise;
  }

  service.get = function(id) {
    return resource.get({receiptId: id}).$promise;
  }

  service.create = function(receipt) {
    return resource.save(null, receipt).$promise;
  }

  service.update = function(receipt) {
    return resource.update({receiptId: receipt.id}, receipt).$promise;
  }

  service.remove = function(id) {
    return resource.remove({receiptId: id}).$promise;
  }
});