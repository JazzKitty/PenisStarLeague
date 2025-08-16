// src/app/auth.guard.ts
import { Injectable } from '@angular/core';
import { CanActivate, Router, ActivatedRouteSnapshot, RouterStateSnapshot, UrlTree } from '@angular/router';
import { AppService } from '../../app.service'; // Assume you have an AuthService
import { MatDialog } from '@angular/material/dialog';
import { EditInputDialogComponent } from './edit-input-dialog.component/edit-input-dialog.component';
import { PopupDialog } from './popup-dialog/popup-dialog';

@Injectable({
    providedIn: 'root'
})
export class AuthGuard implements CanActivate {
    constructor(private appService: AppService, private router: Router, private dialogMat: MatDialog) { }

    canActivate(): boolean | UrlTree {
        if (this.appService.isLoggedIn) {
            return true; // Allow access if authenticated
        } else {
            let dialogRef = this.dialogMat.open(PopupDialog);
            dialogRef.componentInstance.title = "Not Authenicated";
            dialogRef.componentInstance.bodyText = "Please login to view this content";
            return false;
        }
    }
}