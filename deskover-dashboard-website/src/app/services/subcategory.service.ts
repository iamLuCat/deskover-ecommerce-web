import {Injectable} from '@angular/core';
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";
import {DataTablesResponse} from "@/entites/data-tables-response";
import {Observable} from "rxjs";
import {Subcategory} from "@/entites/subcategory";
import {SubcategoryDto} from "@/dtos/subcategory-dto";
import {CategoryService} from "@services/category.service";
import {HttpParams} from "@angular/common/http";

@Injectable({
  providedIn: 'root'
})
export class SubcategoryService {
  private url = environment.globalUrl.subcategoryApi;

  constructor(private restApi: RestApiService, private categoryService: CategoryService) { }

  getByActiveForDatatable(tableQuery: any, isActive: boolean): Promise<DataTablesResponse> {
    const params = new HttpParams().set("isActive", isActive.toString());
    return this.restApi.postWithParams(this.url + "/datatables", tableQuery, params).toPromise();
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
      actived: subcategory.actived,
      categoryId: subcategory.category.id
    };
  }
}
