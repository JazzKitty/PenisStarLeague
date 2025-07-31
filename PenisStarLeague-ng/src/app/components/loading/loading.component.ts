import { Component } from '@angular/core';
import { AppService } from '../../app.service';
import { ActivatedRoute, Router } from '@angular/router';
import { MatDialog, MatDialogConfig } from '@angular/material/dialog';
import { LoginDialogComponent } from '../../dialog/login-dialog.component';


@Component({
    selector: 'app-loading',
    templateUrl: './loading.component.html',
    styleUrl: './loading.component.css',
    standalone: false
})
export class LoadingComponent {

    constructor(private appService: AppService,
                private router: Router, 
                private route: ActivatedRoute, 
                private dialog: MatDialog){
        this.route.queryParams.subscribe(params => {
            if (params["code"] !== undefined) {
                this.appService.getToken(params["code"]);
            }
        });

        this.appService.userSub.subscribe(res =>{
            if(res.idUser != undefined){
                if(!res.userName || res.userName === ""){
                    const dialogConfig = new MatDialogConfig();
                    dialogConfig.disableClose = true;

                    let dialogRef = this.dialog.open(LoginDialogComponent, dialogConfig); 
                    dialogRef.afterClosed().subscribe(res =>{
                        this.router.navigate(['/home']);
                    })
                }else{
                    this.router.navigate(['/home']);
                }
            }
        })
    }

    


}
