import {AppState} from '@/store/state';
import {UiState} from '@/store/ui/state';
import {Component, HostBinding, OnInit} from '@angular/core';
import {Store} from '@ngrx/store';
import {Observable} from 'rxjs';
import {AuthService} from "@services/auth.service";
import {environment} from "../../../../environments/environment";

const BASE_CLASSES = 'main-sidebar elevation-4';

@Component({
  selector: 'app-menu-sidebar',
  templateUrl: './menu-sidebar.component.html',
  styleUrls: ['./menu-sidebar.component.scss']
})
export class MenuSidebarComponent implements OnInit {
  @HostBinding('class') classes: string = BASE_CLASSES;
  public ui: Observable<UiState>;
  public user: any;
  public menu = MENU;

  constructor(
    public authService: AuthService,
    private store: Store<AppState>
  ) {
  }

  ngOnInit() {
    this.ui = this.store.select('ui');
    this.ui.subscribe((state: UiState) => {
      this.classes = `${BASE_CLASSES} ${state.sidebarSkin}`;
    });
    this.user = this.authService.user;
  }
}

export const MENU = [
  {
    name: 'Bảng điều khiển',
    path: ['/'],
    icon: 'fas fa-tachometer-alt'
  },
  {
    header: 'QUẢN LÝ',
    name: 'Danh mục',
    icon: 'fas fa-layer-group',
    children: [
      {
        name: 'Danh mục chính',
        path: ['/category']
      },

      {
        name: 'Danh mục con',
        path: ['/subcategory']
      }
    ]
  },
  {
    name: 'Thương hiệu',
    path: ['/brand'],
    icon: 'fas fa-copyright'
  },

];
