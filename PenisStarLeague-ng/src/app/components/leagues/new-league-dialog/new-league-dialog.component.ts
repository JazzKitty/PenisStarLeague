import { Component } from '@angular/core';
import { AppService } from '../../../app.service';
import { LoginDialogComponent } from '../../../dialog/login-dialog.component';
import { MatDialogRef } from '@angular/material/dialog';
import { LeagueService } from '../league.service';
import { BehaviorSubject } from 'rxjs';
import { Game } from '../../../model/Game';
import { CreateLeagueDTO } from '../../../dto/CreateLeagueDTO';

@Component({
    selector: 'app-new-league-dialog',
    templateUrl: './new-league-dialog.component.html',
    styleUrl: './new-league-dialog.component.css',
    standalone: false
})
export class NewLeagueDialogComponent {
    public createLeagueDTO: CreateLeagueDTO = new CreateLeagueDTO();

    public types: any[] = [];
    public gameDictSub: BehaviorSubject<Game[]>;

    constructor(private appService: AppService, 
                private dialogRef: MatDialogRef<NewLeagueDialogComponent>,
                private leagueService: LeagueService){
        this.gameDictSub = appService.gameSub;        
    }

    ngOnInit(){
        this.appService.leagueTypeSub.subscribe(res => {
            this.types = res;
        });

    }

    saveLeague(){
        if(this.createLeagueDTO.idLeagueType != null && this.createLeagueDTO.league.length > 3){
            this.leagueService.saveLeague(this.createLeagueDTO)?.subscribe(res =>{
                this.leagueService.getLeagueCards(); //get cards when new shit is saved
                this.dialogRef.close();
            }); 
        }
    }

    onClose(){
        this.dialogRef.close();
    }

    isSavingDisabled(): boolean{
        return this.createLeagueDTO.idLeagueType == null || this.createLeagueDTO.league.length < 3;
    }

}
