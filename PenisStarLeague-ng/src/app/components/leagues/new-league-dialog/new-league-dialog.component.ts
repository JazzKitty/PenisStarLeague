import { Component } from '@angular/core';
import { AppService } from '../../../app.service';
import { LoginDialogComponent } from '../../../dialog/login-dialog.component';
import { MatDialogRef } from '@angular/material/dialog';
import { LeagueService } from '../league.service';

@Component({
  selector: 'app-new-league-dialog',
  templateUrl: './new-league-dialog.component.html',
  styleUrl: './new-league-dialog.component.css'
})
export class NewLeagueDialogComponent {
    public leagueName: string = ""; 
    public description: string = ""; 
    public types: any[] = [];
    public selectedType: number | null = null; 

    constructor(private appService: AppService, 
                private dialogRef: MatDialogRef<NewLeagueDialogComponent>,
                private leagueService: LeagueService){
        
    }

    ngOnInit(){
        this.appService.leagueTypeSub.subscribe(res => {
            this.types = res;
        });

    }

    saveLeague(){
        if(this.selectedType != null && this.leagueName.length > 3){
            this.leagueService.saveLeague(this.leagueName, this.selectedType, this.description)?.subscribe(res =>{
                this.leagueService.getLeagueCards(); //get cards when new shit is saved
                this.dialogRef.close();
            }); 
        }
    }

    onClose(){
        this.dialogRef.close();
    }

    isSavingDisabled(): boolean{
        return this.selectedType == null || this.leagueName.length < 3;
    }

}
