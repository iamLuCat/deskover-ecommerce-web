import {Injectable} from '@angular/core';
import {Observable} from "rxjs";
import {UploadedImage} from "@/entites/uploaded-image";
import {environment} from "../../environments/environment";
import {RestApiService} from "@services/rest-api.service";

@Injectable({
  providedIn: 'root'
})
export class UploadService {
  url = environment.globalUrl.uploadFileApi;

  constructor(private restApi: RestApiService) { }

  uploadImage(file: File): Observable<UploadedImage> {
    return this.restApi.uploadFile(this.url, file);
  }
}
