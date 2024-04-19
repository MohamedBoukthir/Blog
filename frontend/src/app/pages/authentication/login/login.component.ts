import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../../services/authentication/authentication.service";
import {Router} from "@angular/router";
import {loginRequest} from "../../../../../types/global-types";
import {StorageService} from "../../../services/storage/storage.service";
import {ToastrService} from "ngx-toastr";

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrl: './login.component.css'
})
export class LoginComponent {

  loginForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private storageService: StorageService,
    private router: Router,
    private toastr: ToastrService
  ) {
  }

  ngOnInit(): void {
    this.loginForm = this.formBuilder.group({
      username: ['', [Validators.required]],
      password: ['', [Validators.required]],
    });
  }

  login() {
    console.log(this.loginForm.value);

    this.authenticationService.login(this.loginForm.value as loginRequest).subscribe({
      next: (response) => {
        console.log(response);
        this.toastr.success('Login successful', 'Success');
        this.storageService.saveUser(response.user);
        this.authenticationService.setUser(response.user);
        if (this.storageService.isAdminLoggedIn()) {
          this.router.navigateByUrl('/admin/dashboard')
        } else (this.storageService.isUserLoggedIn())
        this.router.navigateByUrl('/user');
      },
      error: (error) => {
        console.log(error);
        this.toastr.error('Login failed', 'Bad credentials');
      }
    })
  }

}
