import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService, ClubService } from 'app/shared/services';
import { MessageService } from 'primeng/api';
import { UserService } from 'app/shared/services/user/user.service';

@Component({
    selector: 'app-register',
    styleUrls: ['register.component.scss'],
    templateUrl: 'register.component.html',
})
export class RegisterComponent implements OnInit {
    registerForm: FormGroup;
    loading = false;
    submitted = false;
    errorMessage = undefined;

    constructor(
        private formBuilder: FormBuilder,
        private router: Router,
        private authenticationService: AuthenticationService,
        private userService: UserService,
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
        this.registerForm = this.formBuilder.group({
            userName: ['', Validators.required],
            userEmail: ['', Validators.required]
        });
    }

    // convenience getter for easy access to form fields
    get f() { return this.registerForm.controls; }

    onSubmit() {
        // reset alerts on submit
        this.messageService.clear();

        // stop here if form is invalid
        if (this.registerForm.invalid) {
            return;
        }

        this.loading = true;
        this.userService.register(this.registerForm.value).pipe(first()).subscribe(
            data => {
                this.loading = false;
                if (data.responseMessage === 'SUCCESS') {
                    // this.messageService.add('Registration successful', true);
                    this.submitted = true;
                } else {
                    this.submitted = false;
                    
                    this.errorMessage = data.responseDetail;
                }
            },
            error => {
                // this.alertService.error(error);
                this.loading = false;
            });
    }
}