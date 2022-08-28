import {NgModule} from '@angular/core';
import {RouterModule, Routes} from '@angular/router';
import {MainComponent} from '@components/home/main.component';
import {AuthGuard} from '@guards/auth.guard';
import {NonAuthGuard} from '@guards/non-auth.guard';
import {DashboardComponent} from "@components/home/dashboard/dashboard.component";
import {ProfileComponent} from "@components/home/profile/profile.component";
import {CategoriesComponent} from "@components/home/manage/categories/categories.component";
import {SubcategoriesComponent} from "@components/home/manage/subcategories/subcategories.component";
import {BrandsComponent} from "@components/home/manage/brands/brands.component";
import {PromotionsComponent} from "@components/home/manage/promotions/promotions.component";
import {ProductsComponent} from "@components/home/manage/products/products.component";
import {LoginComponent} from "@components/pages/login/login.component";
import {ForgotPasswordComponent} from "@components/pages/forgot-password/forgot-password.component";
import {RecoverPasswordComponent} from "@components/pages/recover-password/recover-password.component";
import {PrivacyPolicyComponent} from "@components/pages/privacy-policy/privacy-policy.component";
import {CustomersComponent} from "@components/home/manage/customers/customers.component";
import {OrdersComponent} from "@components/home/manage/orders/orders.component";
import {FlashSalesComponent} from "@components/home/manage/flash-sales/flash-sales.component";
import {UsersComponent} from "@components/home/manage/users/users.component";

const routes: Routes = [
  {
    path: '',
    component: MainComponent,
    canActivate: [AuthGuard],
    canActivateChild: [AuthGuard],
    children: [
      {
        path: 'dashboard',
        component: DashboardComponent
      },
      {
        path: 'profile',
        component: ProfileComponent
      },
      {
        path: 'categories',
        component: CategoriesComponent,
      },
      {
        path: 'subcategories',
        component: SubcategoriesComponent,
      },
      {
        path: 'brands',
        component: BrandsComponent,
      },
      {
        path: 'promotions',
        component: PromotionsComponent,
      },
      {
        path: 'flash-sales',
        component: FlashSalesComponent,
      },
      {
        path: 'products',
        component: ProductsComponent,
      },
      {
        path: 'orders',
        component: OrdersComponent,
      },
      {
        path: 'orders/:statusCode',
        component: OrdersComponent,
      },
      {
        path: 'customers',
        component: CustomersComponent,
      },
      {
        path: 'users',
        component: UsersComponent,
      },
      {
        path: '',
        pathMatch: 'full',
        redirectTo: '/dashboard'
      },
    ]
  },
  {
    path: 'login',
    component: LoginComponent,
    canActivate: [NonAuthGuard]
  },
  {
    path: 'forgot-password',
    component: ForgotPasswordComponent,
    canActivate: [NonAuthGuard]
  },
  {
    path: 'recover-password',
    component: RecoverPasswordComponent,
    canActivate: [NonAuthGuard]
  },
  {
    path: 'privacy-policy',
    component: PrivacyPolicyComponent,
    canActivate: [NonAuthGuard]
  },
  {path: '**', redirectTo: ''}
];

@NgModule({
  imports: [RouterModule.forRoot(routes, {relativeLinkResolution: 'legacy'})],
  exports: [RouterModule]
})
export class AppRoutingModule {
}
