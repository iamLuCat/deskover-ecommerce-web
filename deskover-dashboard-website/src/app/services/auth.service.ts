import {environment} from '../../environments/environment';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {RestApiService} from '@services/rest-api.service';
import {Admin} from "@/entites/admin";
import {NotiflixUtils} from "@/utils/notiflix-utils";
import {StorageConstants} from "@/constants/storage-constants";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  constructor(
    private restApiService: RestApiService,
    private router: Router
  ) {
  }

  login(body: any) {
    return this.restApiService.post(`${environment.globalUrl.login}`, body);
  }

  getProfile() {
    return this.restApiService.get(environment.globalUrl.getPrincipal);
  }

  logout() {
    localStorage.removeItem(StorageConstants.TOKEN);
    this.router.navigate(['/login']);
  }
}
