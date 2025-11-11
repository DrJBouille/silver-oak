import { Component, inject, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth/auth.service';
import { LocalStorageService } from '../../services/local-storage/local-storage.service';
import { Router } from '@angular/router';
import { LoginRequest } from '../../model/LoginRequest';
import { SigninRequest } from '../../model/SigninRequest';

@Component({
  selector: 'app-signin',
  imports: [
    FormsModule,
    ReactiveFormsModule
  ],
  templateUrl: './signin.html',
  styleUrl: './signin.css',
})
export class Signin implements OnInit {
  private authService = inject(AuthService);
  private localStorageService = inject(LocalStorageService);
  private router = inject(Router);
  private formBuilder = inject(FormBuilder);

  protected signinForm!: FormGroup;

  ngOnInit() {
    this.signinForm = this.formBuilder.group({
      username: '',
      password: '',
      confirmPassword: '',
    });
  }

  onSubmit() {
    if (this.signinForm.invalid || this.signinForm.value.password != this.signinForm.value.confirmPassword) return;

    this.authService
      .signIn(
        new SigninRequest(
          this.signinForm.value.username,
          this.signinForm.value.password
        )
      )
      .subscribe((tokenDTO) => {
        this.localStorageService.setItem(
          this.authService.localStorageKey,
          tokenDTO
        );
        this.router.navigate(['']);
      });
  }
}
