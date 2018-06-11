angular.module('product')
.service('productService', function($resource) {
  var service = this;
  var resource = $resource('http://localhost:8080/products/:productId', {}, {
    update: {
        method: 'PUT'
    }
  });

  service.query = function() {
    return resource.query().$promise;
  }

  service.get = function(id) {
    return resource.get({productId: id}).$promise;
  }

  service.create = function(product) {
    return resource.save(null, product).$promise;
  }

  service.update = function(product) {
    return resource.update({productId: product.id}, product).$promise;
  }

  service.remove = function(id) {
    return resource.remove({productId: id}).$promise;
  }
});