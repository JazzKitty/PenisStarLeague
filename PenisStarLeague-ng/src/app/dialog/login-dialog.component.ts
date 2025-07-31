import { Component } from '@angular/core';
import { AppService } from '../app.service';
import { debounceTime, distinctUntilChanged, Subject, switchMap } from 'rxjs';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
    selector: 'app-login-dialog',
    templateUrl: './login-dialog.component.html',
    styleUrl: './login-dialog.component.css',
    standalone: false
})
export class LoginDialogComponent {
    public userName: string = ""; 
    public message: string = ""; 
    public gamerTag: string = "";

    constructor(private appService: AppService, public dialogRef: MatDialogRef<LoginDialogComponent>){

    }

    ngOnInit(){

    }

    onCreate(){
        this.appService.createUserName(this.userName, this.gamerTag)?.subscribe(res =>{
            if(res.proceed === "Y"){
                this.dialogRef.close();
            }else{
                this.message = res.message;
            }
        })

    }
}
