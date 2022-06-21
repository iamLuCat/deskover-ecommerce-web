// This file can be replaced during build by using the `fileReplacements` array.
// `ng build --prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.

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
