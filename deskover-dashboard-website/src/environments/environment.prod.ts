export const BASE_URL = 'http://localhost:8080';
// export const BASE_URL = 'https://deskover-deloy.herokuapp.com';
export const BASE_API = BASE_URL + '/v1/api';
export const ADMIN_API = BASE_URL + '/v1/api/admin';

export const environment = {
  production: false,

  ghtkToken: '2C925D6789957674DcC9121bf419Df1a2F7b0BC3',
  globalUrl: {
    ckeditor: 'https://cdn.ckeditor.com/4.19.1/full/ckeditor.js',
    productItemPage: BASE_URL + '/shop/item',
    qrCode : BASE_URL + '/img/qrcode',

    tempFolder: BASE_URL + '/temp',

    userImg: BASE_URL + '/img/admin',
    customerImg: BASE_URL + '/img/users',
    productImg: BASE_URL + '/img/shop/products',
    brandImg: BASE_URL + '/img/shop/brands',
    categoryImg: BASE_URL + '/img/shop/categories',
    subcategoryImg: BASE_URL + '/img/shop/subcategories',

    ghtkApi: BASE_API + '/ghtk',

    login: ADMIN_API + '/auth/login',
    getPrincipal: ADMIN_API + '/auth/get-principal',

    dashboardApi: ADMIN_API + '/dashboard',
    categoryApi: ADMIN_API + '/categories',
    subcategoryApi: ADMIN_API + '/subcategories',
    brandApi: ADMIN_API + '/brands',
    discountApi: ADMIN_API + '/discounts',
    flashSaleApi: ADMIN_API + '/flash-sales',
    productApi: ADMIN_API + '/products',
    customerApi: ADMIN_API + '/customers',
    uploadFileApi: ADMIN_API + '/upload-file',
    orderApi: ADMIN_API + '/orders',
    userApi: ADMIN_API + '/users',
  },
};
