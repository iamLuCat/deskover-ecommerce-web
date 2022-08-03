import {Component, OnInit} from '@angular/core';
import {DateTime} from 'luxon';
import {AuthService} from "@services/auth.service";
import {User} from "@/entites/user";
import {NotiflixUtils} from "@/utils/notiflix-utils";

@Component({
  selector: 'app-user-info',
  templateUrl: './user-info.component.html',
  styleUrls: ['./user-info.component.scss']
})
export class UserInfoComponent implements OnInit {
  public user: User;

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.getProfile();
  }

  logout() {
    this.authService.logout();
  }

  formatDate(date) {
    return DateTime.fromISO(date).toFormat('dd/MM/yyyy');
  }

  getProfile() {
    this.authService.getProfile().subscribe({
      next: (data) => {
        this.user = data;
      },
      error: (err) => {
        this.logout();
      }
    });
  }
}
