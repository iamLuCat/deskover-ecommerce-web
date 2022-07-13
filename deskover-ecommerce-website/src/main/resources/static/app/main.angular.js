var app = angular.module('app', [])

// app.controller('mainCtrl', function ($scope) {

// })

app.controller('shopCtrl', function ($scope, $http) {
  $scope.shop = {
    item: [],
    filter: {
      keyword: '',
      categories: [],
      subcategories: [],
      brands: [],
      minPrice: 0,
      maxPrice: 0,
      currentPage: 1,
      itemsPerPage: 9,
      brandsToggleSelection(brand){
        var idx = $scope.shop.filter.brands.indexOf(brand);
        if(idx > -1){
          $scope.shop.filter.brands.splice(idx,1);
        }else{
          $scope.shop.filter.brands.push(brand);
        }
        
      } 
    },
    totalPage: 0,
    totalItems: 0,
    categories: [],
    brands: [],
    init: async function () {
      await this.loadDatabase();
    },
    sliderUpdate(value) {
      this.minPrice = value[0];
      this.maxPrice = value[1];

      console.log(this.minPrice, this.maxPrice);
    },
    loadDatabase() {
      $http({
        method: 'GET',
        url: '/api/category/all'
      }).then(function successCallback(response) {
        $scope.shop.categories = response.data;
      }, function errorCallback(response) {
        console.error(response.statusText);
      });
      $http({
        method: 'GET',
        url: '/api/brand/all'
      }).then(function successCallback(response) {
        $scope.shop.brands = response.data;
      }, function errorCallback(response) {
        console.error(response.statusText);
      });
    }
  }

}).directive('repeatDirective', function() {
  return function(scope, element, attrs) {
    if (scope.$last){
      setTimeout(() => {
        for (var t = document.querySelectorAll(".widget-filter"), e = 0; e < t.length; e++)(function (e) {
        var r = t[e].querySelector(".widget-filter-search"),
          n = t[e].querySelector(".widget-filter-list").querySelectorAll(".widget-filter-item");
        if (!r) return;
        r.addEventListener("keyup", function () {
          for (var e = r.value.toLowerCase(), t = 0; t < n.length; t++) - 1 < n[t].querySelector(".widget-filter-item-text").innerHTML.toLowerCase().indexOf(e) ? n[t].classList.remove("d-none") : n[t].classList.add("d-none")
        })
      })(e);
      }, 1000)
      
    }
  };
})

