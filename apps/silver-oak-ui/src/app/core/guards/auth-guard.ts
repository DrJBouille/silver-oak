import { inject, Injectable } from '@angular/core';
import {
  ActivatedRouteSnapshot,
  CanActivate,
  GuardResult,
  MaybeAsync,
  Router,
  RouterStateSnapshot,
} from '@angular/router';
import { AuthService } from '../services/auth/auth.service';
import { LocalStorageService } from '../services/local-storage/local-storage.service';

@Injectable({
  providedIn: 'root'
})
export class AuthGuard implements CanActivate {
  private router = inject(Router);
  private authService = inject(AuthService);
  private localStorageService = inject(LocalStorageService)

  canActivate(): boolean {
    const token = this.localStorageService.getItem(this.authService.localStorageKey);
    if (token) return true;

    this.router.navigate(['/login']);
    return false;
  }
}
