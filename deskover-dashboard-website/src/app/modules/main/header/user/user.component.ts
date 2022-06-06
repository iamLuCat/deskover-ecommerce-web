import {Component, OnInit} from '@angular/core';
import {DateTime} from 'luxon';
import {AuthService} from "@services/auth.service";
import { environment } from 'environments/environment';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {
  user: any;
  avatarURL: string;

  constructor(private authService: AuthService) {
  }

  ngOnInit(): void {
    this.user = this.authService.user;
    this.avatarURL = environment.imageURL + '/avatar/' + this.user.user.photo;
  }

  logout() {
    this.authService.logout();
  }

  formatDate(date) {
    return DateTime.fromISO(date).toFormat('dd/MM/yyyy');
  }
}
