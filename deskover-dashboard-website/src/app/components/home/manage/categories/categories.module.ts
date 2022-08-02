import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { CategoriesRoutingModule } from './categories-routing.module';
import {FormsModule} from "@angular/forms";
import {DataTablesModule} from "angular-datatables";
import {ModalModule} from "ngx-bootstrap/modal";
import {CategoriesComponent} from "@components/home/manage/categories/categories.component";
import {SubcategoriesComponent} from "@components/home/manage/categories/subcategories/subcategories.component";


@NgModule({
  declarations: [CategoriesComponent],
  imports: [
    CommonModule,
    CategoriesRoutingModule,
    FormsModule,
    DataTablesModule,
    ModalModule
  ]
})
export class CategoriesModule { }
