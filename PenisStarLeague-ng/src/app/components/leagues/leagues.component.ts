import { CdkVirtualScrollViewport } from '@angular/cdk/scrolling';
import { Component, ViewChild } from '@angular/core';
import {ChangeDetectionStrategy, ViewEncapsulation} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LeagueCardComponent } from './league-card/league-card.component';
import { NewLeagueDialogComponent } from './new-league-dialog/new-league-dialog.component';
import { AppService } from '../../app.service';
import { BehaviorSubject } from 'rxjs';
import { UserDTO } from '../../dto/UserDTO';
import { LeagueService } from './league.service';
import { LeagueCardDTO } from '../../dto/LeagueCardDTO';
import { ActivatedRoute, NavigationExtras, Router } from '@angular/router';
import { faPencil } from '@fortawesome/free-solid-svg-icons';

@Component({
    selector: 'app-leagues',
    templateUrl: './leagues.component.html',
    styleUrl: './leagues.component.css',
    standalone: false
})
export class LeaguesComponent {

    public leagueCards: LeagueCardDTO[] = []; 
    public user: UserDTO | null = null; 


    @ViewChild(CdkVirtualScrollViewport) viewport!: CdkVirtualScrollViewport;

    constructor(private dialogRef: MatDialog, private appService: AppService, private leagueService: LeagueService, private router: Router, private route: ActivatedRoute){

    }

    ngOnInit(){
        this.leagueService.getLeagueCards(); 

        this.appService.userSub.subscribe(res =>{
            this.user = res;   
        })

        this.leagueService.leagueCardsSub.subscribe(cards =>{
            this.leagueCards = cards; 
        });

    }

    scrollRight(){
        this.viewport.scrollToOffset(this.viewport.measureScrollOffset()+1400);
    }

    scrollLeft(){
        this.viewport.scrollToOffset(this.viewport.measureScrollOffset()-1400);
    }

    newLeague(){
        this.dialogRef.open(NewLeagueDialogComponent)
    }

    newLeagueDisable(): boolean{
        return !this.appService.isLoggedIn; 
    }

    cardClick(idLeague: number) {
        const navExtras: NavigationExtras = {
            queryParams: {'idLeague': idLeague}
        };
        this.router.navigate(['/leagues/league'], navExtras);
    }

}
