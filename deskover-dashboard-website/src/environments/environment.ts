// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
export const BASE_API = 'http://localhost:8080/v1/api';
export const BASE_URL = 'http://localhost:8080';

export const environment = {
  production: false,

  ghtkToken: '2C925D6789957674DcC9121bf419Df1a2F7b0BC3',
  globalUrl: {
    ckeditor: 'https://cdn.ckeditor.com/4.19.1/full/ckeditor.js',
    productItemPage: BASE_URL + '/shop/item',
    qrCode : BASE_URL + '/img/qrcode',

    tempFolder: BASE_URL + '/temp',

    adminImg: BASE_URL + '/img/admin/avatar',
    userImg: BASE_URL + '/img/shop/account',
    productImg: BASE_URL + '/img/shop/products',
    brandImg: BASE_URL + '/img/shop/brands',
    categoryImg: BASE_URL + '/img/shop/categories',
    subcategoryImg: BASE_URL + '/img/shop/subcategories',

    login: BASE_API + '/admin/auth/login',
    getPrincipal: BASE_API + '/admin/auth/get-principal',

    ghtkApi: BASE_API + '/ghtk',
    adminApi: BASE_API + '/admin',
    categoryApi: BASE_API + '/admin/categories',
    subcategoryApi: BASE_API + '/admin/subcategories',
    brandApi: BASE_API + '/admin/brands',
    discountApi: BASE_API + '/admin/discounts',
    productApi: BASE_API + '/admin/products',
    userApi: BASE_API + '/admin/users',
    uploadFileApi: BASE_API + '/admin/upload-file',
    orderApi: BASE_API + '/admin/orders',
  },
};

/*
 * For easier debugging in development mode, you can import the following file
 * to ignore zone related error stack frames such as `zone.run`, `zoneDelegate.invokeTask`.
 *
 * This import should be commented out in production mode because it will have a negative impact
 * on performance if an error is thrown.
 */
// import 'zone.js/plugins/zone-error';  // Included with Angular CLI.
