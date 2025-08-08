import { Component } from '@angular/core';
import { faPencil } from '@fortawesome/free-solid-svg-icons';
import { EditInputDialogComponent } from '../shared/edit-input-dialog.component/edit-input-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { AppService } from '../../app.service';
import { UserDTO } from '../../dto/UserDTO';
import { EditTextareaDialogComponent } from '../shared/edit-textarea-dialog.component/edit-textarea-dialog.component';

@Component({
  selector: 'app-profile',
  standalone: false,
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class Profile {

    protected readonly faPencil = faPencil;
    public user: UserDTO | undefined;

    constructor(private dialogRef: MatDialog, private appService: AppService) {
        this.appService.userSub.subscribe(res =>{
            this.user = res; 
        })

    }

    ngOnInit() {

    }

    
    editUserName(){
        let userNameDialog = this.dialogRef.open(EditInputDialogComponent);
        userNameDialog.componentInstance.title = 'Edit User Name';
        userNameDialog.componentInstance.label = "User Name"
        userNameDialog.componentInstance.value = this.user?.userName;

        userNameDialog.componentInstance.confirmed.subscribe((res) => {

            if (res) {
                this.appService.editUserName(res)?.subscribe(
                    (res) => {
                        this.appService.getUser(); 
                        userNameDialog.close();
                    },
                    (error: any) => {
                        console.error(error);
                    }
                );
            }
        });
    }

    editGamerTag(){
        let gamerTagDialog = this.dialogRef.open(EditInputDialogComponent);
        gamerTagDialog.componentInstance.title = 'Edit Gamer Tag';
        gamerTagDialog.componentInstance.label = "Gamer Tag";
        gamerTagDialog.componentInstance.value = this.user?.gamerTag;

        gamerTagDialog.componentInstance.confirmed.subscribe((res) => {
            if (res) {
                this.appService.editGamerTag(res)?.subscribe(
                    (res) => {
                        this.appService.getUser(); 
                        gamerTagDialog.close();
                    },
                    (error: any) => {
                        console.error(error);
                    }
                );
            }
        });
    }

    editBio(){
        let bioDialog = this.dialogRef.open(EditTextareaDialogComponent);
        bioDialog.componentInstance.title = 'Edit Bio';
        bioDialog.componentInstance.label = "Bio";
        bioDialog.componentInstance.value = this.user?.bio;

        bioDialog.componentInstance.confirmed.subscribe((res) => {
            if (res) {
                this.appService.editBio(res)?.subscribe(
                    (res) => {
                        this.appService.getUser(); 
                        bioDialog.close();
                    },
                    (error: any) => {
                        console.error(error);
                    }
                );
            }
        });
    }
}
