var app = angular.module('app', [])

// app.controller('mainCtrl', function ($scope) {

// })

app.controller('shopCtrl', function ($scope, $http) {
  $scope.shop = {
    items: [],
    filter: {
      keyword: '',
      category: "",
      subcategory: "",
      brands: [],
      minPrice: 0,
      maxPrice: 9999999999,
      currentPage: 0,
      itemsPerPage: 12,
      sort: '1',
      brandsToggleSelection(b) {
        var idx = $scope.shop.filter.brands.indexOf(b);
        if (idx > -1) {
          $scope.shop.filter.brands.splice(idx, 1);
        } else {
          $scope.shop.filter.brands.push(b);
        }
        $scope.shop.loadItems();
      },
      categoryToggleSelection(c,sc){
        this.category = c;
        this.subcategory = sc;
        $scope.shop.loadItems();
      },
      changePage(p){
        if(p > $scope.shop.totalPage - 1 || p < 0) return;

        this.currentPage = p;
        $scope.shop.loadItems();
      }
    },
    totalPage: 0,
    totalItems: 0,
    categories: [],
    brands: [],
    init: async function () {
      await this.loadDatabase();
      this.loadItems();
    },
    sliderUpdate(value) {
      this.filter.minPrice = value[0];
      this.filter.maxPrice = value[1];

      console.log(this.filter.minPrice, this.filter.maxPrice);
      this.loadItems();
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
    },
    loadItems() {
      $http.post("/api/shop/search", this.filter)
        .then(resp => {
          this.items = resp.data.items;
          this.totalPage = resp.data.totalPage;
          this.totalItems = resp.data.totalItems;
          console.log(resp.data);
          console.log(this.filter);

          if(resp.data.totalPage > 0 && this.filter.currentPage > resp.data.totalPage-1){
            this.filter.currentPage = resp.data.totalPage-1;
          }
        }).catch(err => {
          console.error(err)
        })
    }
  }

}).directive('repeatDirective', function () {
  return function (scope, element, attrs) {
    if (scope.$last) {
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

