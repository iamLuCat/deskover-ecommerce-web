import {Component, OnInit, ViewChild} from '@angular/core';
import {Router, RouterOutlet} from "@angular/router";

@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.scss']
})
export class AppComponent implements OnInit {
  title = "Deskover Dashboard";

  @ViewChild(RouterOutlet) outlet: RouterOutlet;

  constructor(private router: Router) {}

  ngOnInit(): void {
    // this.router.events.subscribe((event) => {
    //   if (event instanceof NavigationStart) {
    //     Block.circle('body');
    //   } else if (event instanceof NavigationEnd || event instanceof NavigationCancel || event instanceof NavigationError) {
    //     Block.remove('body');
    //   }
    // });

    // this.router.events.subscribe(
    //   event => {
    //     if(event instanceof RouteConfigLoadStart) {
    //       Block.circle('body');
    //       return;
    //     }
    //     if(event instanceof RouteConfigLoadEnd) {
    //       Block.remove('body');
    //       return;
    //     }
    //   }
    // );
  }
}
