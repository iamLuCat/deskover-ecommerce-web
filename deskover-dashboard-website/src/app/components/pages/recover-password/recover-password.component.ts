import {Component, HostBinding, OnDestroy, OnInit, Renderer2} from '@angular/core';
import {FormControl, FormGroup, Validators} from '@angular/forms';
import {AuthService} from "@services/auth.service";

@Component({
    selector: 'app-recover-password',
    templateUrl: './recover-password.component.html',
    styleUrls: ['./recover-password.component.scss']
})
export class RecoverPasswordComponent implements OnInit, OnDestroy {
    @HostBinding('class') class = 'login-box';

    public recoverPasswordForm: FormGroup;
    public isAuthLoading = false;

    constructor(
        private renderer: Renderer2,
        private authService: AuthService
    ) {}

    ngOnInit(): void {
        this.renderer.addClass(
            document.querySelector('app-root'),
            'login-page'
        );
        this.recoverPasswordForm = new FormGroup({
            password: new FormControl(null, Validators.required),
            confirmPassword: new FormControl(null, Validators.required)
        });
    }

    recoverPassword() {
        if (this.recoverPasswordForm.valid) {
        } else {
            // this.toastr.error('Hello world!', 'Toastr fun!');
        }
    }

    ngOnDestroy(): void {
        this.renderer.removeClass(
            document.querySelector('app-root'),
            'login-page'
        );
    }
}
