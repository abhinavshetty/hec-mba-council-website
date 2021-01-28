import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { first } from 'rxjs/operators';
import { AuthenticationService, ClubService } from 'app/shared/services';
import { MessageService } from 'primeng/api';

@Component({
    selector: 'app-forgot',
    styleUrls: ['forgot.component.scss'],
    templateUrl: 'forgot.component.html',
})
export class ForgotComponent implements OnInit {
    forgotForm: FormGroup;
    loading = false;
    submitted = false;
    returnUrl: string;

    get councilLogoUrl(): string {
        return this.clubService.getLogoForCouncilUrl();
    }

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

    ngOnInit() {
        this.forgotForm = this.formBuilder.group({
            userEmail: ['', Validators.required]
        });

        // get return url from route parameters or default to '/'
        this.returnUrl = this.route.snapshot.queryParams['returnUrl'] || '/';
    }

    // convenience getter for easy access to form fields
    get f() { return this.forgotForm.controls; }

    onSubmit() {

        // reset alerts on submit
        this.messageService.clear();

        // stop here if form is invalid
        if (this.forgotForm.invalid) {
            return;
        }

        this.loading = true;
        this.authenticationService.login(this.f.username.value, this.f.password.value)
            .pipe(first())
            .subscribe(
                data => {
                    this.loading = false;
                    this.submitted = true;
                },
                error => {
                    this.messageService.add(error);
                    this.loading = false;
                });
    }
}