import { inject, Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from '../../model/LoginRequest';
import { TokensDTO } from '../../model/TokensDTO';
import { SigninRequest } from '../../model/SigninRequest';
import { RefreshTokenRequest } from '../../model/RefreshTokenRequest';

@Injectable({
  providedIn: 'root',
})
export class AuthService {
  private http = inject(HttpClient);
  public readonly localStorageKey = "TOKENS";

  login(loginRequest: LoginRequest) {
    return this.http.post<TokensDTO>('http://localhost:8080/auth/login', loginRequest);
  }

  signIn(signinRequest: SigninRequest) {
    return this.http.post<TokensDTO>('http://localhost:8080/auth/signin', signinRequest);
  }

  refreshToken(refreshTokenRequest: RefreshTokenRequest) {
    return this.http.post<TokensDTO>('http://localhost:8080/auth/refresh-token', refreshTokenRequest);
  }
}
