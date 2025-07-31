// src/app/auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AppService } from '../../app.service'; // Assume you have an AuthService

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {
    constructor(private appService: AppService, private router: Router) { }

    canActivate(): boolean | UrlTree {
        if (this.appService.isLoggedIn) {
            return true; // Allow access if authenticated
        } else {
            return false;
        }
    }
}