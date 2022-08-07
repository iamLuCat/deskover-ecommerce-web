angular
  .module('app', ['ngStorage', 'ngSweetAlert2'])
  .controller('mainCtrl', function ($scope, $http, $localStorage, $window, $sessionStorage, $filter) {
    $scope.wishlist = {
      list: [],
      change(p) {
        $http.post('/api/v1/ecommerce/product/wishlist', p)
          .then(function successCallback(resp) {
            $scope.wishlist.list = resp.data;
          }, function errorCallback(resp) {
          });
      },
      init() {
        $http({
          method: 'GET',
          url: '/api/v1/ecommerce/product/wishlist',
        }).then(function successCallback(resp) {
          $scope.wishlist.list = resp.data;
        });
      },
      page: {
        list: [],
        totalPage: 0,
        currentPage: 0,
        changePage(p) {
          console.log(p)
          if (p >= this.totalPage) return;
          else if (p < 0) return;
          this.currentPage = p;
          this.loadDatabase();
        },
        init() {
          this.loadDatabase();
        },
        loadDatabase() {
          $http({
            method: 'GET',
            url: '/api/v1/ecommerce/product/wishlist/page',
            params: {
              c: this.currentPage
            }
          }).then(function successCallback(resp) {
            $scope.wishlist.page.totalPage = resp.data.totalPage;
            $scope.wishlist.page.currentPage = resp.data.currentPage;
            $scope.wishlist.page.list = resp.data.items;
            console.log(resp.data);
          }, function errorCallback(resp) {
            console.error(resp);
          });
        },
        remove(p) {
          $http.post('/api/v1/ecommerce/product/wishlist', p)
            .then(function successCallback(resp) {
              $scope.wishlist.list = resp.data;
              $scope.wishlist.page.loadDatabase();
            }, function errorCallback(resp) {
            });
        }
      }
    }
    $scope.wishlist.init();

    $scope.amounts = [];
    if ($localStorage.items) {
      $localStorage.items.forEach(item => {
        $scope.amounts.push(item.amount);
      });
    }
    $scope.search = {
      select: new URL(location.href).searchParams.get('c'),
      init() {
        $http({
          method: 'GET',
          url: '/api/v1/ecommerce/category/all'
        }).then(function successCallback(response) {
          $scope.search.categories = response.data;
        }, function errorCallback(response) {
          console.error(response.statusText);
        });
      }
    }
    $scope.changePage = async function (p) {
      delete $sessionStorage.filter;
      $window.location.href = p;
    }
    $scope.cart = {
      itemPage: [],
      initItemPage() {
        $http({
          method: 'GET',
          url: '/api/v1/ecommerce/product/item',
          params: { s: new URL(location.href).searchParams.get('p') }
        }).then(function successCallback(resp) {
          $scope.cart.itemPage = resp.data;
          console.log(resp.data)
        }, function errorCallback(resp) {
          console.error(resp.statusText);
        });
      },
      loadCart() {
        $http({
          method: 'GET',
          url: '/api/v1/ecommerce/user/cart'
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
        this.initP();
      },
      initP() {
        $http.post('/api/v1/ecommerce/user/cart', $scope.cart.list)
          .then(async function successCallback(resp) {
            $localStorage.items = [];
            resp.data.forEach(i => {
              i.item.amount = i.quantity;
              $localStorage.items.push(i.item);
              console.log(i)
            })
          }, function errorCallback(resp) {
          });
      },
      get list() {
        return $localStorage.items.map(i => {
          return {
            slug: i.slug,
            quantity: i.amount
          }
        })
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
      get sumSale() {
        var total = 0;
        $localStorage.items.forEach(item => {
          if (item.sale) {
            total += (item.price - item.price_sale) * item.amount;
          }
        });
        return total;
      },
      get sumAll() {
        var total = 0;
        $localStorage.items.forEach(item => {
          if (item.sale) {
            total += item.price_sale * item.amount;
          } else {
            total += item.price * item.amount;
          }
        });
        return total;
      },
      add(i) {
        console.log(i)
        var item = $localStorage.items.find(item => item.slug == i.slug);
        if (item) {
          if (item.amount < 5) {
            item.amount++;
            swal.fire({
              position: 'top-end',
              title: 'Hàng hóa đã được thêm vào giỏ hàng',
              showConfirmButton: false,
              timer: 1500,
              toast: true
            })
          } else {
            swal.fire({
              position: 'top-end',
              icon: 'warning',
              title: 'Đã vượt mức cho phép ',
              showConfirmButton: false,
              timer: 1500,
              toast: true
            })
          }

        }
        else {
          i.amount = 1;
          $localStorage.items.push(i);
          swal.fire({
            position: 'top-end',
            title: 'Hàng hóa đã được thêm vào giỏ hàng',
            showConfirmButton: false,
            timer: 1500,
            toast: true
          })
        }
        this.addL(i);
      },
      addP() {
        var item = $localStorage.items.find(i => i.slug == $scope.cart.itemPage.slug);
        if (item) {
          if (item.amount + 1 <= 5){ 
            item.amount += 1;
            swal.fire({
              position: 'top-end',
              title: 'Hàng hóa đã được thêm vào giỏ hàng',
              showConfirmButton: false,
              timer: 1500,
              toast: true
            })
          } else {
            swal.fire({
              position: 'top-end',
              icon: 'warning',
              title: 'Đã vượt mức cho phép ',
              showConfirmButton: false,
              timer: 1500,
              toast: true
            })
          }
        }
        else {
          $scope.cart.itemPage.amount = 1;
          $localStorage.items.push($scope.cart.itemPage);
          swal.fire({
            position: 'top-end',
            title: 'Hàng hóa đã được thêm vào giỏ hàng',
            showConfirmButton: false,
            timer: 1500,
            toast: true
          })
        }
        this.addL(item);
      },
      addQ(itemInput) {
        var item = $localStorage.items.find(i => i.slug == itemInput.slug);
        if (item) {
          if (item.amount + 1 <= 5) {
            item.amount += 1;
            swal.fire({
              position: 'top-end',
              title: 'Hàng hóa đã được thêm vào giỏ hàng',
              showConfirmButton: false,
              timer: 1500,
              toast: true
            })
          } else {
            swal.fire({
              position: 'top-end',
              icon: 'warning',
              title: 'Đã vượt mức cho phép ',
              showConfirmButton: false,
              timer: 1500,
              toast: true
            })
          }
        }
        else {
          itemInput.amount = 1;
          $localStorage.items.push(itemInput);
          swal.fire({
            position: 'top-end',
            title: 'Hàng hóa đã được thêm vào giỏ hàng',
            showConfirmButton: false,
            timer: 1500,
            toast: true
          })
        }
        this.addL(itemInput);
      },
      addL(i){
        $http.post('/api/v1/ecommerce/user/cart/update', {
          slug: i.slug,
          quantity: i.amount
        })
          .then(async function successCallback(resp) {
            $localStorage.items = [];
            resp.data.forEach(i => {
              i.item.amount = i.quantity;
              $localStorage.items.push(i.item);
              console.log(i)
            })
          }, function errorCallback(resp) {
          });
      },
      remove(i) {
        var idx = $localStorage.items.indexOf(i);
        if (idx > -1) $localStorage.items.splice(idx, 1);
        this.removeP(i);
        swal.fire({
          position: 'top-end',
          title: 'Đã xóa hàng hóa khỏi giỏ hàng',
          showConfirmButton: false,
          timer: 1500,
          toast: true
        })
      },
      removeP(i) {
        $http.delete('/api/v1/ecommerce/user/cart/' + i.slug)
          .then(async function successCallback(resp) {
            $localStorage.items = [];
            resp.data.forEach(i => {
              i.item.amount = i.quantity;
              $localStorage.items.push(i.item);
              console.log(i)
            })
          }, function errorCallback(resp) {
          });
      },
      removeAll() {
        $localStorage.items = []
        this.removeAllp();
        swal.fire({
          position: 'top-end',
          title: 'Đã xóa hết hàng hóa khỏi giỏ hàng',
          showConfirmButton: false,
          timer: 1500,
          toast: true
        })
      },
      removeAllp() {
        $http.delete('/api/v1/ecommerce/user/cart/all')
          .then(async function successCallback(resp) {
          }, function errorCallback(resp) {
          });
      },
      valid: {
        amount(a) {
          if (!a.item.amount) a.item.amount = 1;
        }
      },
    }
    $http({
      method: "POST",
      url: "checkout",
      data: angular.toJson($scope.cart.items),
      headers: {
        'consumes': 'application/json'
      }
    }).then(function successCallback(response) {

    }, function errorCallback(response) {

    });
    $http({
      method: "POST",
      url: "amounts",
      data: angular.toJson($scope.amounts),
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(function (response) {
    }, function (response) {
    });
    let host = "http://localhost:8080";
    $scope.form = {
      "id": "a4",
      "pick_name": "HCM-nội thành",
      "pick_address": "590 CMT8 P.11",
      "pick_province": "TP. Hồ Chí Minh",
      "pick_district": "Quận 3",
      "pick_ward": "Phường 1",
      "pick_tel": "0911222333",
      "address": "123 nguyễn chí thanh",
      "ward": "Phường Bến Nghé",
      "hamlet": "Khác",
      "is_freeship": "1",
      "pick_date": "2016-09-30",
      "pick_money": 47000,
      "note": "Khối lượng tính cước tối đa: 1.00 kg",
      "value": 3000000,
      "transport": "fly",
      "pick_option": "cod",
      "deliver_option": "xteam",
      "pick_session": 2,
      "tags": [1]
    }
    $scope.change2 = function () {
      var item = angular.copy($scope.form);
      var url = `${host}/v1/api/ghtk/fee`;
      $http.post(url, item).then(resp => {
        $scope.ship = resp.data;
      }).catch(error => {
        console.log("Error", error)
      })
    }
    $http.get(`${host}/v0/client/province`).then(resp => {
      $scope.province = resp.data;
    }).catch(error => {
      console.log("Error", error)
    })
    $scope.change = function () {
      var newTemp = $filter("filter")($scope.province, { name: $scope.form.province });
      var id = newTemp[0].id;
      var url = `${host}/v0/client/district?provinceId=${id}`;
      $http.get(url).then(resp => {
        $scope.district = resp.data;
        console.log("Success", resp)
      }).catch(error => {
        console.log("Error", error)
      })
    }


  }).controller('shopCtrl', function ($scope, $http, $sessionStorage) {
    $scope.shop = {
      item: [],
      items: [],
      filter: {
        keyword: document.querySelector('#keyword').value,
        category: new URL(location.href).searchParams.get('c') ? new URL(location.href).searchParams.get('c') : '',
        subcategory: new URL(location.href).searchParams.get('s') ? new URL(location.href).searchParams.get('s') : '',
        brands: new URL(location.href).searchParams.get('b') ? new Array(new URL(location.href).searchParams.get('b')) : [],
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
          url: '/api/v1/ecommerce/category/all'
        }).then(function successCallback(response) {
          $scope.shop.categories = response.data;
        }, function errorCallback(response) {
          console.error(response.statusText);
        });
        $http({
          method: 'GET',
          url: '/api/v1/ecommerce/brand/all'
        }).then(function successCallback(response) {
          $scope.shop.brands = response.data;
        }, function errorCallback(response) {
          console.error(response.statusText);
        });
        if ($sessionStorage.filter) {
          this.filter.category = $sessionStorage.filter.category;
          this.filter.subcategory = $sessionStorage.filter.subcategory;
          this.filter.brands = $sessionStorage.filter.brands;
          this.filter.minPrice = $sessionStorage.filter.minPrice;
          this.filter.maxPrice = $sessionStorage.filter.maxPrice;
          this.filter.currentPage = $sessionStorage.filter.currentPage;
          this.filter.itemsPerPage = $sessionStorage.filter.itemsPerPage;
          this.filter.sort = $sessionStorage.filter.sort;
        }
      },
      loadItems() {
        $http.post("/api/v1/ecommerce/shop/search", this.filter)
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
        $sessionStorage.filter = this.filter
      },
      quickView(slug) {
        $http({
          method: 'GET',
          url: '/api/v1/ecommerce/product/item',
          params: { s: slug }
        }).then(function successCallback(resp) {
          $scope.shop.item = resp.data;
          console.log(resp.data)
        }, function errorCallback(resp) {
          console.error(resp.statusText);
        });
      }
    }
  }).controller('reviewCtrl', function ($scope, $http) {
    $scope.review = {
      content: [],
      totalPage: 0,
      currentPage: 0,
      changePage(p) {
        console.log(p)
        if (p >= this.totalPage) return;
        else if (p < 0) return;
        this.currentPage = p;
        this.loadDatabase();
      },
      init() {
        this.loadDatabase();
      },
      loadDatabase() {
        $http({
          method: 'GET',
          url: '/api/v1/ecommerce/product/review',
          params: {
            c: this.currentPage,
            s: new URL(location.href).searchParams.get('p'),
          }
        }).then(function successCallback(resp) {
          $scope.review.totalPage = resp.data.totalPage;
          $scope.review.content = resp.data.reviews;
          console.log(resp.data);
        }, function errorCallback(resp) {
          console.error(resp.statusText);
        });
      }
    }

    $scope.submitReview = {
      submited: false,
      form: {
        product: new URL(location.href).searchParams.get('p'),
        email: '',
        name: '',
        point: '',
        content: ''
      },
      submit(f) {
        if (f.$invalid) return;
        console.log(this.form)
        $http.post('/api/v1/ecommerce/product/review', this.form)
          .then(function successCallback(resp) {
            $scope.submitReview.submited = true;
            $scope.review.loadDatabase();
          }, function errorCallback(resp) {
            swal.fire({
              position: 'top-end',
              icon: 'warning',
              title: 'Có lỗi xảy ra',
              showConfirmButton: false,
              timer: 1500,
              toast: true
            })
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
  }).directive('repeatDirectiveCategory', function () {
    return function (scope, element, attrs) {
      if (scope.$last) {
        setTimeout(() => {
          for (var o = document.querySelectorAll('[data-bs-toggle="select"]'), e = 0; e < o.length; e++) ! function (e) {
            for (var t = o[e].querySelectorAll(".dropdown-item"), r = o[e].querySelector(".dropdown-toggle-label"), n = o[e].querySelector('input[type="hidden"]'), a = 0; a < t.length; a++) t[a].addEventListener("click", function (e) {
              e.preventDefault();
              e = this.querySelector(".dropdown-item-label").innerText;
              r.innerText = e, null !== n && (n.value = e)
            })
          }(e)
        }, 500)

      }
    };
  }).filter('trustHtml', function ($sce) { return $sce.trustAsHtml; });

