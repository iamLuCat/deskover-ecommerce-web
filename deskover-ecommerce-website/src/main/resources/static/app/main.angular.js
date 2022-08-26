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
      addK(i) {
        var item = $localStorage.items.find(item => item.slug == i.slug);
        console.log(item)

        this.addL(item);
      },
      addL(i) {
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
      url: "cartItem",
      data: angular.toJson($scope.cart.items),
      headers: {
        'consumes': 'application/json'
      }
    }).then(function successCallback(response) {

    }, function errorCallback(response) {

    });
    $http({
      method: "POST",
      url: "cartAmounts",
      data: angular.toJson($scope.amounts),
      headers: {
        'Content-Type': 'application/json'
      }
    }).then(function (response) {
    }, function (response) {
    });
    let host = "http://localhost:8080";
	console.log($scope.amounts)
	
    $scope.form = {
        "value": 3000000,
        "deliver_option" : "xteam",
 		"address" : ""
	}

	$scope.change2 = function(){
	    var item = angular.copy($scope.form);
	    var url = `${host}/v1/api/ghtk/fee`;
	    var newTemp = $filter("filter")($scope.province, {name: $scope.form.province});
    	var pid =  newTemp[0].id;
    	newTemp = $filter("filter")($scope.district, {name: $scope.form.district});
    	var did =  newTemp[0].id;
	    $http.post(url, item).then(resp => {
		    $localStorage.ship = resp.data;
			$scope.ship = resp.data;
	    }).catch(error => {
	        console.log("Error",error)
	    });
	    $http.get(`${host}/v0/client/ward?provinceId=${pid}&districtId=${did}`).then(resp => {
        	$scope.ward = resp.data;
	    }).catch(error => { })
    }
    $scope.address = function(){
    	address ="Tỉnh: "+ $scope.form.province+", Xã: " + $scope.form.district +", Đường: "+$scope.form.ward + ", Số nhà: " + $scope.number + " , Việt Nam";
    	$scope.address.address = address;
    	$scope.form.address = address;
    }
    $http.get(`${host}/v0/client/province`).then(resp => {
      $scope.province = resp.data;
    }).catch(error => {
      console.log("Error", error)
    })
    $scope.checkbox = function(){
		if ($scope.check == true)
			$scope.ship = { "fee": { "fee": 0 }}
		else
			$scope.ship = $localStorage.ship
	}
    $scope.ship = { "fee": { "fee": 0 }}
	$scope.checked = true;
	$scope.change = function () {
      var newTemp = $filter("filter")($scope.province, { name: $scope.form.province });
      var id = newTemp[0].id;
      var url = `http://localhost:8080/v0/client/district?provinceId=${id}`;
	  $scope.ship = { "fee": {  "fee": 0 }  }
      $http.get(url).then(resp => {
        $scope.district = resp.data;
        if($scope.district[0].provinceId == 1){
			$scope.checked = false;
		}else
			$scope.checked = true;
      })
    }
	$scope.cancel = function(code){
		var url = `/api/test/order/cancel/${code}?statusOrder=C-HUY`;
		var item = [];

		$http.post(url,item).then( resp => {
			$window.location.reload();
			$scope.msg = resp.data; 
		}).catch(error => { })
	}

	$scope.checkout = function (){
	  	for(i = 0; i < $localStorage.items.length; i++){
			console.log($scope.amounts[i])
			$localStorage.items[i].quantity = $scope.amounts[i];
		}
		var url = "http://localhost:8080/api/test/vnpaycheckout";
		var item = {
		    "items" : $localStorage.items,
		    "entity": $scope.form,
	        "total" : $scope.cart.total + $scope.ship.fee.fee
		};

		$http.post(url,item).then(resp => {
			console.log("success");
		}).catch(error => { })
	}

  }).controller('accCtrl', function ($scope, $http) {
    $scope.account = {
      detail: [],
      loadDatabase() {
        $http({
          method: 'GET',
          url: '/api/v1/ecommerce/user/account/info'
        }).then(function successCallback(response) {
          $scope.account.detail = response.data;
          $scope.account.detail.avatar = response.data.avatar + '?t=' + new Date().getTime();
          $scope.profile.form.fullname = response.data.fullname;
          $scope.profile.form.phone = response.data.phone;
          $scope.profile.avatar = '/img/users/' + response.data.avatar;
          console.log(response.data.fullname)
        }, function errorCallback(response) {
          console.error(response.statusText);
        });
      }
    }

    $scope.password = {
      submit(f) {
        if (f.$invalid) return;
        $http.post('/api/v1/ecommerce/user/account/password', $scope.password.form)
          .then(function successCallback(resp) {
            console.log(resp.data)
            $scope.password.message = resp.data.message;
            $scope.password.error = '';
          }, function errorCallback(resp) {
            console.error(resp.data)
            $scope.password.message = '';
            $scope.password.error = resp.data.message;
          });
        $scope.password.form = {
          oldPassword: '',
          newPassword: '',
          confirmPassword: '',
        }
      },
      message: '',
      error: ''
    }

    $scope.order = {
      list:[],
      currentPage: 0,
      totalPage: 0,
      filter: "0",
      loadDatabase() {
        $http({
          method: 'GET',
          url: '/api/v1/ecommerce/user/account/order',
          params: {
            c: this.currentPage,
            f: this.filter
          }
        }).then(function successCallback(response) {
          console.log(response.data);
          $scope.order.list = response.data.list;
          $scope.order.totalPage = response.data.totalPage;
        }, function errorCallback(response) {
          console.error(response);
        });
      },
      detail(f){
        window.location.href = "/account/order/detail?id=" + f;
      },
      changePage(p) {
        console.log(p)
        if (p >= this.totalPage) return;
        else if (p < 0) return;
        this.currentPage = p;
        this.loadDatabase();
      }
    }

    $scope.profile = {
      avatar: '',
      form: {
        fullname: '',
        phone: ''
      },
      async submit(f) {
        file = document.querySelector('#myFileInput');
        console.log(file.files[0])
        if (f.$invalid) return;
        var data = new FormData();
        var requestData = {
          fullname: $scope.profile.form.fullname,
          phone: $scope.profile.form.phone
        }
        data.append('form', new Blob([JSON.stringify(requestData)], {
            type: "application/json"
        }));
        var config = {
          transformRequest: angular.identity,
          transformResponse: angular.identity,
          headers: {
              'Content-Type': undefined
          }
        }
        await $http.post('/api/v1/ecommerce/user/account/profile', data, config)
          .then(function successCallback(resp) {
            console.log(resp)
            $scope.profile.message = 'Cập nhật thông tin thành công';
            $scope.profile.error = '';
          }, function errorCallback(resp) {
            console.error(resp)
            $scope.profile.message = '';
            $scope.profile.error = 'Cập nhật thông tin không thành công';
          });

        if(file.files.length > 0) {
          var image = new FormData();
          image.append('file', file.files[0]);
          await $http.post('/api/v1/ecommerce/user/account/image', image, config)
          .then(function successCallback(resp) {
            console.log(resp)
            $scope.profile.message = 'Cập nhật thông tin thành công';
            $scope.profile.error = '';
          }, function errorCallback(resp) {
            console.error(resp)
            $scope.profile.message = '';
            $scope.profile.error = 'Cập nhật thông tin không thành công';
          });
        }
        $scope.account.loadDatabase();

        
      },
      message: '',
      error: ''
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
  }).directive("ngFileSelect", function (fileReader, $timeout) {
    return {
      scope: {
        ngModel: '='
      },
      link: function ($scope, el) {
        function getFile(file) {
          fileReader.readAsDataUrl(file, $scope)
            .then(function (result) {
              $timeout(function () {
                $scope.ngModel = result;
              });
            });
        }

        el.bind("change", function (e) {
          var file = (e.srcElement || e.target).files[0];
          getFile(file);
        });
      }
    };
  }).factory("fileReader", function ($q, $log) {
    var onLoad = function (reader, deferred, scope) {
      return function () {
        scope.$apply(function () {
          deferred.resolve(reader.result);
        });
      };
    };

    var onError = function (reader, deferred, scope) {
      return function () {
        scope.$apply(function () {
          deferred.reject(reader.result);
        });
      };
    };

    var onProgress = function (reader, scope) {
      return function (event) {
        scope.$broadcast("fileProgress", {
          total: event.total,
          loaded: event.loaded
        });
      };
    };

    var getReader = function (deferred, scope) {
      var reader = new FileReader();
      reader.onload = onLoad(reader, deferred, scope);
      reader.onerror = onError(reader, deferred, scope);
      reader.onprogress = onProgress(reader, scope);
      return reader;
    };

    var readAsDataURL = function (file, scope) {
      var deferred = $q.defer();

      var reader = getReader(deferred, scope);
      reader.readAsDataURL(file);

      return deferred.promise;
    };

    return {
      readAsDataUrl: readAsDataURL
    };
  }).filter('trustHtml', function ($sce) { return $sce.trustAsHtml; });

