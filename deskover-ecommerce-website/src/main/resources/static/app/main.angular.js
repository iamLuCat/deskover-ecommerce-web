angular
  .module('app', ['ngStorage'])
  .controller('mainCtrl', function ($scope, $http, $localStorage, $location) {
    $scope.cart = {
      itemPage: [],
      loadCart() {
        $http({
          method: 'GET',
          url: '/api/user/cart'
        }).then(function successCallback(response) {
          $scope.shop.brands = response.data;
        }, function errorCallback(response) {
          console.error(response.statusText);
        });
      },
      init() {
        if (!$localStorage.items) {
          $localStorage.items = [];
        }
      },
      initP() {
        $http({
          method: 'GET',
          url: '/api/product/item',
          params: { s: location.search.substring(3) }
        }).then(function successCallback(resp) {
          console.log(resp.data.item);
          $scope.cart.itemPage = resp.data.item;
        }, function errorCallback(resp) {
          console.error(resp.statusText);
        });
      },
      get items() {
        return $localStorage.items;
      },
      get count() {
        var amount = 0;
        $localStorage.items.forEach(item => {
          amount += item.amount;
        });
        return amount;
      },
      get total() {
        var total = 0;
        $localStorage.items.forEach(item => {
          total += item.price * item.amount;
        });
        return total;
      },
      add(i) {
        console.log(i)
        var item = $localStorage.items.find(item => item.slug == i.slug);
        if (item) {
          if (item.amount <= 5) item.amount++;
        }
        else {
          i.amount = 1;
          $localStorage.items.push(i);
        }
      },
      addP() {
        var select = parseInt($scope.cart.select)
        var item = $localStorage.items.find(i => i.slug == $scope.cart.itemPage.slug);
        if (item) {
          if (item.amount + select <= 5) item.amount += select;
        }
        else {
          $scope.cart.itemPage.amount = select;
          $localStorage.items.push($scope.cart.itemPage);
        }
      },
      addQ(itemInput) {
        var select = parseInt($scope.cart.select)
        var item = $localStorage.items.find(i => i.slug == itemInput.slug);
        if (item) {
          if (item.amount + select <= 5) item.amount += select;
        }
        else {
          itemInput.amount = select;
          $localStorage.items.push(itemInput);
        }
      },
      remove(i) {
        var idx = $localStorage.items.indexOf(i);
        if (idx > -1) $localStorage.items.splice(idx, 1);
      },
      valid: {
        amount(a) {
          if (!a.item.amount) a.item.amount = 1;
        }
      }
    }
  }).controller('shopCtrl', function ($scope, $http) {
    $scope.shop = {
      item: [],
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
        categoryToggleSelection(c, sc) {
          this.category = c;
          this.subcategory = sc;
          $scope.shop.loadItems();
        },
        changePage(p) {
          if (p > $scope.shop.totalPage - 1 || p < 0) return;

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

            if (resp.data.totalPage > 0 && this.filter.currentPage > resp.data.totalPage - 1) {
              this.filter.currentPage = resp.data.totalPage - 1;
            }
          }).catch(err => {
            console.error(err)
          })
      },
      quickView(slug) {
        $http({
          method: 'GET',
          url: '/api/product/item',
          params: { s: slug }
        }).then(function successCallback(resp) {
          $scope.shop.item = resp.data;


          console.log(resp.data)
        }, function errorCallback(resp) {
          console.error(resp.statusText);
        });
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
  }).directive('repeatDirectiveQuickview', function () {
    return function (scope, element, attrs) {
      if (scope.$last) {
        setTimeout(() => {
          var c = document.querySelectorAll(".product-gallery");
          if (c.length)
            for (var e = 0; e < c.length; e++) ! function (r) {
              for (var n = c[r].querySelectorAll(".product-gallery-thumblist-item:not(.video-item)"), a = c[r].querySelectorAll(".product-gallery-preview-item"), e = c[r].querySelectorAll(".product-gallery-thumblist-item.video-item"), t = 0; t < n.length; t++) n[t].addEventListener("click", o);

              function o(e) {
                e.preventDefault();
                for (var t = 0; t < n.length; t++) a[t].classList.remove("active"), n[t].classList.remove("active");
                this.classList.add("active"), c[r].querySelector(this.getAttribute("href")).classList.add("active")
              }
              for (var l = 0; l < e.length; l++) lightGallery(e[l], {
                selector: "this",
                download: !1,
                videojs: !0,
                youtubePlayerParams: {
                  modestbranding: 1,
                  showinfo: 0,
                  rel: 0,
                  controls: 0
                },
                vimeoPlayerParams: {
                  byline: 0,
                  portrait: 0,
                  color: "fe696a"
                }
              })
            }(e)
        }, 500)

      }
    };
  })

