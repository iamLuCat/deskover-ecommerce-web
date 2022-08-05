import {environment} from '../../environments/environment';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {RestApiService} from '@services/rest-api.service';
import {StorageConstants} from "@/constants/storage-constants";
import {User} from "@/entites/user";
import {firstValueFrom, lastValueFrom} from "rxjs";

@Injectable({
  providedIn: 'root'
})
export class AuthService {
  public user: User = null;

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

  async getProfile2() {
    try {
      this.user = await lastValueFrom(this.restApiService.get(environment.globalUrl.getPrincipal));
    } catch (error) {
      this.logout();
      throw error;
    }
  }

  logout() {
    localStorage.removeItem(StorageConstants.TOKEN);
    this.router.navigate(['/login']);
  }
}
