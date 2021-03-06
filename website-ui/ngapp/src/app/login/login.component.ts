import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService, ClubService } from 'app/shared/services';
import { MessageService } from 'primeng/api';

@Component({
    selector: 'app-login',
    styleUrls: ['login.component.scss'],
    templateUrl: 'login.component.html',
})
export class LoginComponent implements OnInit {
    loginForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;
    errorMessage: string;

    constructor(
        private formBuilder: FormBuilder,
        private route: ActivatedRoute,
        private router: Router,
        private authenticationService: AuthenticationService,
        private messageService: MessageService,
        private clubService: ClubService
    ) {
        // redirect to home if already logged in
        if (this.authenticationService.currentUserValue) {
            this.router.navigate(['/']);
        }
    }

    get councilLogoUrl(): string {
        return this.clubService.getLogoForCouncilUrl();
    }

    ngOnInit() {
        this.loginForm = this.formBuilder.group({
            userEmail: ['', Validators.required],
            password: ['', Validators.required]
        });

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    // convenience getter for easy access to form fields
    get f() { return this.loginForm.controls; }

    onSubmit() {
        // reset alerts on submit
        this.messageService.clear();

        // stop here if form is invalid
        if (this.loginForm.invalid) {
            return;
        }

        this.loading = true;

        this.authenticationService.login(this.f.userEmail.value, this.f.password.value)
            .pipe(first())
            .subscribe(data => {
                if (data.userEmail !== null && data.userEmail !== undefined) {
                    this.submitted = true;
                    this.router.navigate(['home']);
                } else {
                    this.errorMessage = 'Incorrect credentials';
                }
            }, error => {
                this.submitted = true;
                this.messageService.add(error);
                this.errorMessage = error;
                this.loading = false;
            });
    }
}