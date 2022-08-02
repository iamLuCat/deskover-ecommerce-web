import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { BrandsRoutingModule } from './brands-routing.module';
import {BrandsComponent} from "@components/home/manage/brands/brands.component";
import {FormsModule} from "@angular/forms";
import {DataTablesModule} from "angular-datatables";
import {ModalModule} from "ngx-bootstrap/modal";

@NgModule({
  declarations: [
    BrandsComponent
  ],
  imports: [
    CommonModule,
    BrandsRoutingModule,
    FormsModule,
    DataTablesModule,
    ModalModule
  ],
  exports: []
})
export class BrandsModule { }
