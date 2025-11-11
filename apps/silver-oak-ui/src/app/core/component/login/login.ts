import { Component, inject, OnInit } from '@angular/core';
import { AuthService } from '../../services/auth/auth.service';
import { LocalStorageService } from '../../services/local-storage/local-storage.service';
import { Router } from '@angular/router';
import { FormBuilder, FormGroup, FormsModule, ReactiveFormsModule } from '@angular/forms';
import { LoginRequest } from '../../model/LoginRequest';

@Component({
  selector: 'app-login',
  imports: [FormsModule, ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.css',
})
export class Login implements OnInit {
  private authService = inject(AuthService);
  private localStorageService = inject(LocalStorageService);
  private router = inject(Router);
  private formBuilder = inject(FormBuilder);

  protected loginForm!: FormGroup;

  ngOnInit() {
    this.loginForm = this.formBuilder.group({
      username: '',
      password: '',
    });
  }

  onSubmit() {
    if (this.loginForm.invalid) return;

    this.authService
      .login(
        new LoginRequest(
          this.loginForm.value.username,
          this.loginForm.value.password
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
