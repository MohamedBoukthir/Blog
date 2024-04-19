import {Component} from '@angular/core';
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {AuthenticationService} from "../../../services/authentication/authentication.service";
import {Router} from "@angular/router";
import {ToastrService} from "ngx-toastr";
import {CustomValidators, UsernameValidators} from "../../../../../utils/CustomValidator";

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrl: './register.component.css'
})
export class RegisterComponent {

  registerForm!: FormGroup;

  constructor(
    private formBuilder: FormBuilder,
    private authenticationService: AuthenticationService,
    private router: Router,
    private toastr: ToastrService
  ) {
  }

  ngOnInit() {
    this.registerForm = this.formBuilder.group({
      username: ['', [Validators.required, UsernameValidators.validUsername()]],
      email: ['', [Validators.required, Validators.email]],
      password: ['', [Validators.required, CustomValidators.passwordStrength()]],
      firstName: ['', [Validators.required]],
      lastName: ['', [Validators.required]],
      age: ['', [Validators.required]],
      phone: ['', [Validators.required]],
    });
  }

  register() {
    console.log(this.registerForm.value);
    this.authenticationService.register(this.registerForm.value).subscribe((res: any) => {
      console.log(res);
      if (!res.error) {
        if (res.message && res.userDto) {
          this.toastr.success(res.message, 'Success');
          this.router.navigateByUrl('/login');
        } else {
          this.toastr.error('Unexpected response from server');
        }
      } else {
        this.toastr.error(res.error);
      }
    })
  }


}
