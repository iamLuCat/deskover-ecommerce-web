import {Component, OnInit, ViewChild} from '@angular/core';
import {DataTableDirective} from "angular-datatables";
import {NotiflixUtils} from "@/utils/notiflix-utils";
import {environment} from "../../../../../environments/environment";
import {User, UserRole} from "@/entites/user";
import {UserService} from "@services/user.service";
import {HttpParams} from "@angular/common/http";
import {ModalDirective} from "ngx-bootstrap/modal";
import {FormControlDirective} from "@angular/forms";
import {UploadService} from "@services/upload.service";
import {AuthService} from "@services/auth.service";

@Component({
  selector: 'app-staff',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  @ViewChild("userModal") userModal: ModalDirective;
  @ViewChild('userForm') userForm: FormControlDirective;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  currentUser: User;

  users: User[] = [];
  user: User = <User>{};
  avatarPreview: any;

  roles: UserRole[] = [];
  role: UserRole = <UserRole>{};
  roleFilter: UserRole = null;

  isActive: boolean = true;

  dtOptions: any = {};

  constructor(private userService: UserService, private uploadServive: UploadService, private authService: AuthService) {
    this.currentUser = this.authService.user;
    this.getRoles();
  }

  ngOnInit() {
    const self = this;

    self.dtOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      serverSide: true,
      processing: true,
      stateSave: true,
      columnDefs: [{
        "defaultContent": "",
        "targets": "_all",
      }],
      ajax: (dataTablesParameters: any, callback) => {
        const params = new HttpParams()
          .set("isActive", this.isActive.toString())
          .set("roleId", this.roleFilter ? this.roleFilter.id.toString() : '');
        this.userService.getByActiveForDatatable(dataTablesParameters, params).subscribe(resp => {
          self.users = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
            data: []
          });
        });
      },
      columns: [
        {data: 'avatar', orderable: false, searchable: false},
        {data: 'username'},
        {data: 'fullname'},
        {data: 'authority.role.name'},
        {data: 'modifiedAt'},
        {data: 'modifiedBy'},
        {data: 'lastLogin'},
        {data: null, orderable: false, searchable: false}
      ]
    }
  }

  rerender() {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload(null, false);
    });
  }

  changeActive(user: User) {
    if (user.actived) {
      NotiflixUtils.showConfirm('Xác nhận xoá', 'Nhân viên này sẽ bị khoá', () => {
        this.userService.changeActive(user.id).subscribe(data => {
          NotiflixUtils.successNotify('Khoá tài khoản thành công');
          this.rerender();
        });
      });
    } else {
      this.userService.changeActive(user.id).subscribe(data => {
        NotiflixUtils.successNotify('Kích hoạt tài khoản thành công');
        this.rerender();
      });
    }
  }

  getRoles() {
    this.userService.getRoles().subscribe(data => {
      this.roles = data;
    });
  }

  editUser(user: User) {
    this.user = user;
    this.openModal();
  }

  saveUser(user: User) {
    if (user.id) {
      this.userService.update(user).subscribe(data => {
        this.closeModal();
        NotiflixUtils.successNotify('Cập nhật thành công');
        this.rerender();
      }).add(() => {
        this.user = <User>{};
      });
    }
  }

  /* Utils */
<<<<<<< HEAD
  isAdmin(user: User): boolean {
    return user.authority.role.roleId === 'ROLE_ADMIN';
  }

  compareFn(c1: any, c2: any): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

=======
>>>>>>> parent of 6aceb176 (Fix UI)
  openModal() {
    this.userModal.show();
  }

  closeModal() {
    this.userModal.hide();
  }

  getSrc(image: string) {
    return image ? `${environment.globalUrl.userImg}/${image}` : 'assets/images/no-image.png';
  }

  selectedImageChanged($event: Event) {
    const file = $event.target['files'][0];
    this.uploadServive.uploadImage(file).subscribe(data => {
      this.user.avatar = data.filename;
      this.avatarPreview = `${environment.globalUrl.tempFolder}/${data.filename}`;
      $event.target['value'] = '';
    });
  }
}
