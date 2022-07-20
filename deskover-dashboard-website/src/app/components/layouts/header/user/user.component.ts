import {Component, OnInit} from '@angular/core';
import {DateTime} from 'luxon';
import {AuthService} from "@services/auth.service";
import { environment } from 'environments/environment';
import {Admin} from "@/entites/admin";

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  public user: Admin;

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.user = this.authService.user;
  }

  logout() {
    this.authService.logout();
  }

  formatDate(date) {
    return DateTime.fromISO(date).toFormat('dd/MM/yyyy');
  }
}
