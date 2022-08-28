import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from "@/entites/user";
import {AuthService} from "@services/auth.service";
import {environment} from "../../../../environments/environment";
import {TabsetComponent} from "ngx-bootstrap/tabs";
import {UserService} from "@services/user.service";
import {NotiflixUtils} from "@/utils/notiflix-utils";
import {FormControlDirective} from "@angular/forms";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  defaultAvatar: string = 'assets/images/avatar/default-profile.png';
  user: User;

  currentPassword: string = "";
  newPassword: string = "";
  confirmNewPassword: string = "";

  @ViewChild('changePasswordForm') changePasswordForm: FormControlDirective;
  @ViewChild('staticTabs', { static: false }) staticTabs?: TabsetComponent;

  constructor(private authService: AuthService, private userService: UserService) {
  }

  ngOnInit(): void {
    this.user = this.authService.user;
  }

  getSrc(image: string) {
    return image ? `${environment.globalUrl.userImg}/${image}` : this.defaultAvatar;
  }

  selectTab(tabId: number) {
    if (this.staticTabs?.tabs[tabId]) {
      this.staticTabs.tabs[tabId].active = true;
    }
  }

  changePassword() {
    if (this.newPassword !== this.confirmNewPassword) {
      NotiflixUtils.warningNotify('Mật khẩu xác nhận không khớp');
      return;
    }
    this.userService.changePassword(this.currentPassword, this.newPassword).subscribe(res => {
      NotiflixUtils.successNotify('Đổi mật khẩu thành công');
      this.changePasswordForm.control.reset();
    });
  }
}
