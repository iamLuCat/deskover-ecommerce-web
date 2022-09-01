import {Component, OnInit, ViewChild} from '@angular/core';
import {User} from "@/entites/user";
import {AuthService} from "@services/auth.service";
import {environment} from "../../../../environments/environment";
import {TabsetComponent} from "ngx-bootstrap/tabs";
import {UserService} from "@services/user.service";
import {NotiflixUtils} from "@/utils/notiflix-utils";
import {FormControlDirective} from "@angular/forms";
import {ModalDirective} from "ngx-bootstrap/modal";
import {UploadService} from "@services/upload.service";

@Component({
    selector: 'app-profile',
    templateUrl: './profile.component.html',
    styleUrls: ['./profile.component.scss']
})
export class ProfileComponent implements OnInit {
  defaultAvatar: string = 'assets/images/avatar/default-profile.png';
  avatarPreview: any;
  user: User;

  userUpdated: User;

  currentPassword: string = "";
  newPassword: string = "";
  confirmNewPassword: string = "";

  @ViewChild('changePasswordForm') changePasswordForm: FormControlDirective;
  @ViewChild('staticTabs', { static: false }) staticTabs?: TabsetComponent;
  @ViewChild("userModal") userModal: ModalDirective;

  constructor(private authService: AuthService, private userService: UserService, private uploadService: UploadService) {
  }

  ngOnInit(): void {
    this.user = this.authService.user;
    this.userUpdated = <User> {
      username: '',
      fullname: '',
    }
    this.avatarPreview = this.user.avatar ? this.getSrc(this.user.avatar) : this.defaultAvatar;
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
      this.resetChangePasswordForm();
    });
  }

  openModal() {
    this.userModal.show();
  }

  closeModal() {
    this.userModal.hide();
  }

  resetChangePasswordForm() {
    this.changePasswordForm.control.reset();
  }

  editUser() {
    this.userUpdated = Object.assign({}, this.user);
    this.openModal();
  }

  saveUser(user: User) {
    this.userService.update(user).subscribe(data => {
      this.closeModal();
      this.user = data;
      NotiflixUtils.successNotify('Cập nhật thành công');
    });
  }

  selectedImageChanged($event: Event) {
    const file = $event.target['files'][0];
    this.uploadService.uploadImage(file).subscribe(data => {
      this.userUpdated.avatar = data.filename;
      this.avatarPreview = `${environment.globalUrl.tempFolder}/${data.filename}`;
      $event.target['value'] = '';
    });
  }
}
