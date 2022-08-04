(function (root, factory) {
  "use strict";

  if (typeof define === 'function' && define.amd) {
      define(['angular', 'Sweetalert2'], factory);
  } else if (typeof module === 'object' && module.exports) {
      module.exports = factory(require('angular'), require('Sweetalert2'));
  } else {
      factory(root.angular, root.swal);
  }

}(this, function (angular, swal) {
  "use strict";

  angular.module('ngSweetAlert2', [])
      .factory('swal', ['$rootScope', '$q',
          function ($rootScope, $q) {
              return {
                  fire: function (args1, args2, args3) {
                      var deferred = $q.defer();
                      $rootScope.$evalAsync(function () {
                          if (args1 != null && args2 == null && args3 == null) {
                              let opened = Swal.fire(args1);
                              deferred.resolve(opened);
                          }
                          else if (typeof args1 === 'string' && typeof args2 === 'string' && typeof args3 === 'string') {
                              let opened = Swal.fire(args1, args1, args3);
                              deferred.resolve(opened);
                          }
                      });
                      return deferred.promise;
                  }
              };
          }]);
}));
