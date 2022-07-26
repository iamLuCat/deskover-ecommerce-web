import {Component, HostBinding, OnDestroy, OnInit, Renderer2} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from "@services/auth.service";
import {Router} from "@angular/router";
import {StorageConstants} from "@/constants/storage-constants";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit, OnDestroy {
  @HostBinding('class') class = 'login-box';
  public loginForm: FormGroup;
  public isAuthLoading = false;

  constructor(
    private renderer: Renderer2,
    private authService: AuthService,
    private router: Router
  ) {
  }

  ngOnInit() {
    this.renderer.addClass(
      document.querySelector('app-root'),
      'login-page'
    );
    this.loginForm = new FormGroup({
      username: new FormControl('minhnh', Validators.required),
      password: new FormControl('123456', Validators.required)
    });
  }

  loginByAuth() {
    this.isAuthLoading = true;
    this.authService.login(this.loginForm.value).subscribe({
      next: (data) => {
        this.isAuthLoading = false;
        localStorage.setItem(StorageConstants.TOKEN, data.token);
        this.router.navigate(['/']);
      },
      error: (err) => {
        this.isAuthLoading = false;
      }
    });
  }

  ngOnDestroy() {
    this.renderer.removeClass(
      document.querySelector('app-root'),
      'login-page'
    );
  }
}
