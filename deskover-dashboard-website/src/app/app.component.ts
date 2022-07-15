import {Component} from '@angular/core';
import {NavigationCancel, NavigationEnd, NavigationError, NavigationStart, Router} from "@angular/router";
import { NgxSpinnerService } from "ngx-spinner";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent {
  title = "Deskover Dashboard";
  loading = false;

  constructor(private router: Router, private spinner: NgxSpinnerService) {
    this.router.events.subscribe((event: Event | any) => {
      switch (true) {
        case event instanceof NavigationStart: {
          this.spinner.show();
          break;
        }

        case event instanceof NavigationEnd:
        case event instanceof NavigationCancel:
        case event instanceof NavigationError: {
          setTimeout(() => {
            this.spinner.hide();
          });
          break;
        }
        default: {
          break;
        }
      }
    });
  }
}
