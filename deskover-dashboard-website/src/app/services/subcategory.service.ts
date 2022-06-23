import { Injectable } from '@angular/core';
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";
import {DataTablesResponse} from "@/entites/data-tables-response";
import {Observable} from "rxjs";
import {Subcategory} from "@/entites/subcategory";
import {Category} from "@/entites/category";
import {SubcategoryDto} from "@/dtos/subcategory-dto";
import {CategoryService} from "@services/category.service";

@Injectable({
  providedIn: 'root'
})
export class SubcategoryService {
  private url = environment.globalUrl.subcategoryApi;

  constructor(private restApi: RestApiService, private categoryService: CategoryService) { }

  getAllForDatatable(tableQuery: any): Promise<DataTablesResponse> {
    return this.restApi.post(this.url + "/datatables", tableQuery).toPromise();
  }

  getOne(id: number): Observable<Subcategory> {
    return this.restApi.getOne(this.url, id);
  }

  create(subcategoryDto: SubcategoryDto): Observable<Subcategory> {
    return this.restApi.post(this.url, subcategoryDto);
  }

  update(subcategoryDto: SubcategoryDto): Observable<Subcategory> {
    return this.restApi.put(this.url, subcategoryDto);
  }

  changeActive(id: number) {
    return this.restApi.delete(this.url, id);
  }

  convertToDto(subcategory: Subcategory): SubcategoryDto {
    return {
      id: subcategory.id,
      name: subcategory.name,
      description: subcategory.description,
      slug: subcategory.slug,
      createdAt: subcategory.createdAt,
      modifiedAt: subcategory.modifiedAt,
      deletedAt: subcategory.deletedAt,
      actived: subcategory.actived,
      categoryId: subcategory.category.id
    };
  }
}
