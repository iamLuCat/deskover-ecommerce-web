import {AppState} from '@/store/state';
import {UiState} from '@/store/ui/state';
import {Component, HostBinding, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {AuthService} from "@services/auth.service";
import {PermissionContants} from "@/constants/permission-contants";
import {User} from "@/entites/user";

const BASE_CLASSES = 'main-sidebar elevation-4';

@Component({
  selector: 'app-menu-sidebar',
  templateUrl: './menu-sidebar.component.html',
  styleUrls: ['./menu-sidebar.component.scss']
})
export class MenuSidebarComponent implements OnInit {
  @HostBinding('class') classes: string = BASE_CLASSES;
  public ui: Observable<UiState>;
  public user: User;
  public menu: MenuItem[] = MENU;

  constructor(
    public authService: AuthService,
    private store: Store<AppState>
  ) {
    this.user = this.authService.user;
  }

  ngOnInit() {
    this.ui = this.store.select('ui');
    this.ui.subscribe((state: UiState) => {
      this.classes = `${BASE_CLASSES} ${state.sidebarSkin}`;
    });
  }

  logout() {
    this.authService.logout();
  }
}

export interface MenuItem {
  header?: string;
  label: string;
  icon: string;
  path?: string[];
  children?: MenuItem[];
  permissions?: string[];
}

export const MENU: MenuItem[] = [
  {
    label: 'Trang chủ',
    path: ['/'],
    icon: 'fa-duotone fa-gauge-max',
    permissions: [PermissionContants.ALL]
  },
  {
    header: 'SẢN PHẨM',
    label: 'Danh mục',
    icon: 'fa-duotone fa-layer-group',
    permissions: [
      PermissionContants.ADMIN,
      PermissionContants.MANAGER
    ],
    children: [
      {
        label: 'Danh mục chính',
        path: ['/categories'],
        icon: 'fa-duotone fa-circle-dot',
        permissions: [
          PermissionContants.ADMIN,
          PermissionContants.MANAGER
        ],
      },

      {
        label: 'Danh mục con',
        path: ['/subcategories'],
        icon: 'fa-duotone fa-circle-dot',
        permissions: [
          PermissionContants.ADMIN,
          PermissionContants.MANAGER
        ],
      }
    ]
  },
  {
    label: 'Thương hiệu',
    path: ['/brands'],
    icon: 'fa-duotone fa-copyright',
    permissions: [
      PermissionContants.ADMIN,
      PermissionContants.MANAGER
    ],
  },
  {
    label: 'Chương trình khuyến mãi',
    icon: 'fa-duotone fa-badge-percent',
    permissions: [
      PermissionContants.ADMIN,
      PermissionContants.MANAGER,
      PermissionContants.SELLER
    ],
    children: [
      {
        label: 'Giảm giá',
        path: ['/promotions'],
        icon: 'fa-duotone fa-circle-dot',
        permissions: [
          PermissionContants.ADMIN,
          PermissionContants.MANAGER,
          PermissionContants.SELLER
        ],
      },
      {
        label: 'Flash Sale',
        path: ['/flash-sales'],
        icon: 'fa-duotone fa-circle-dot',
        permissions: [
          PermissionContants.ADMIN,
          PermissionContants.MANAGER,
          PermissionContants.SELLER
        ],
      }
    ]
  },
  {
    label: 'Sản phẩm',
    path: ['/products'],
    icon: 'fa-duotone fa-cart-flatbed-boxes',
    permissions: [
      PermissionContants.ADMIN,
      PermissionContants.MANAGER,
      PermissionContants.SELLER
    ],
  },
  {
    label: 'Đơn hàng',
    path: ['/orders'],
    icon: 'fa-duotone fa-file-invoice',
    permissions: [
      PermissionContants.ADMIN,
      PermissionContants.SHIPPER
    ],
  },
  {
    header: 'TÀI KHOẢN',
    label: 'Người dùng',
    path: ['/customers'],
    icon: 'fa-duotone fa-users',
    permissions: [
      PermissionContants.ADMIN
    ],
  },
  {
    label: 'Nhân viên',
    path: ['/users'],
    icon: 'fa-duotone fa-user-lock',
    permissions: [
      PermissionContants.ADMIN
    ],
  },
];
