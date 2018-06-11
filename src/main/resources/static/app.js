var app = angular.module('app', ['ngRoute', 'product-list', 'product-view', 'product-add', 'receipt-list', 'receipt-add']);

app.config(function($routeProvider) {
    $routeProvider
    .otherwise({
        redirectTo: '/'
    });
});