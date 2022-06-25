export const environment = {
  production: false,
  globalUrl: {
    login: 'http://localhost:8080/authenticate',
    profile: 'http://localhost:8080/get-principal',
    avatar: 'http://localhost:8080/assets/user/img/avatar',

    baseApi: 'http://localhost:8080/v1/api',
    adminApi: 'http://localhost:8080/v1/api/admin',
    categoryApi: 'http://localhost:8080/v1/api/admin/categories',
    subcategoryApi: 'http://localhost:8080/v1/api/admin/subcategories',
    brandApi: 'http://localhost:8080/v1/api/admin/brands',
  },
};
