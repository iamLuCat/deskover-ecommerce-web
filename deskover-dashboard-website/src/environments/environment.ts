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

    userImg: BASE_URL + '/img/admin/avatar',
    customerImg: BASE_URL + '/img/shop/account',
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
    customerApi: BASE_API + '/admin/customers',
    uploadFileApi: BASE_API + '/admin/upload-file',
    orderApi: BASE_API + '/admin/orders',
    userApi: BASE_API + '/admin/users',
  },
};
