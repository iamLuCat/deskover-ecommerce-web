import {environment} from '../../environments/environment';
import {Injectable} from '@angular/core';
import {Router} from '@angular/router';
import {RestApiService} from '@services/rest-api.service';
import {StorageConstants} from "@/constants/storage-constants";
import {User} from "@/entites/user";
import {firstValueFrom, lastValueFrom} from "rxjs";
import {PermissionContants} from "@/constants/permission-contants";

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

  async getProfile() {
    try {
      this.user = await lastValueFrom(this.restApiService.get(environment.globalUrl.getPrincipal));
    } catch (error) {
      this.logout();
      throw error;
    }
  }

  hasPermissions(permissions: string[]) {
    if (!this.user) {
      return false;
    }
    if(permissions.some(permission => permission === PermissionContants.ALL)) {
      return true
    }
    return permissions.some(permission => permission === this.user.authority.role.roleId);
  }

  logout() {
    localStorage.removeItem(StorageConstants.TOKEN);
    this.user = null;
    this.router.navigate(['/login']);
  }
}
