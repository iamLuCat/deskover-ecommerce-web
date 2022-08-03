import {Component, OnInit, ViewChild} from '@angular/core';
import {DataTableDirective} from "angular-datatables";
import {NotiflixUtils} from "@/utils/notiflix-utils";
import {environment} from "../../../../../environments/environment";
import {User, UserRole} from "@/entites/user";
import {UserService} from "@services/user.service";

@Component({
  selector: 'app-staff',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.scss']
})
export class UsersComponent implements OnInit {
  users: User[] = [];
  user: User = <User>{};

  roles: UserRole[] = [];
  role: UserRole = <UserRole>{};
  roleFilter: UserRole = null;

  isActive: boolean = true;

  dtOptions: any = {};

  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(private userService: UserService) {
    this.getRoles();
  }

  ngOnInit(): void {
    const self = this;

    this.dtOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      lengthMenu: [5, 10, 25, 50, 100],
      serverSide: true,
      processing: true,
      stateSave: true,
      columnDefs: [{
        "defaultContent": "-",
        "targets": "_all",
      }],
      ajax: (dataTablesParameters: any, callback) => {
        this.userService.getByActiveForDatatable(dataTablesParameters, this.isActive).subscribe(resp => {
          self.users = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
            data: resp.data
          });
        });
      },
      columns: [
        {data: 'avatar', orderable: false, searchable: false},
        {data: 'username'},
        {data: 'fullname'},
        {data: 'authorities'},
        {data: 'modifiedAt'},
        {data: 'modifiedBy'},
        {data: 'lastLogin'},
        {data: null, orderable: false, searchable: false}
      ],
      order: [[3, 'asc']],
    }
  }

  rerender() {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload(null, false);
    });
  }

  filter() {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.draw();
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

  getSrc(image: string) {
    return image ? `${environment.globalUrl.categoryImg}/${image}` : 'assets/images/no-image.png';
  }

  getRoles() {
    this.userService.getRoles().subscribe(data => {
      this.roles = data;
    });
  }

}
