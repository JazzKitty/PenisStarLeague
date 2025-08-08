import { Component } from '@angular/core';
import { AppService } from '../../../app.service';
import { Event } from '../../../model/Event'
import { Game } from '../../../model/Game';
import { Week } from '../../../model/Week';
import { Month } from '../../../model/Month';
import { EventIntervalType } from '../../../model/EventIntervalType';
import { LeagueService } from '../../leagues/league.service';
import { LeagueDictDTO } from '../../../dto/LeagueDictDTO';
import { BehaviorSubject } from 'rxjs';
import { MatDialogRef } from '@angular/material/dialog';
import { EventsService } from '../events.service';

@Component({
  selector: 'app-new-event-component',
  standalone: false,
  templateUrl: './new-event-component.html',
  styleUrl: './new-event-component.css'
})
export class NewEventComponent {
    public event: Event = new Event();

    //dictionary subs 
    public gameDictSub: BehaviorSubject<Game[]>;
    public eventIntervalTypeDictSub: BehaviorSubject<EventIntervalType[]>;
    public ownedLeaguesSub: BehaviorSubject<LeagueDictDTO[]>; 
    public yesNoDict: any[] = [];

    constructor(private appService: AppService, private leagueService: LeagueService, private dialogRef: MatDialogRef<NewEventComponent>, private eventService: EventsService){
        this.yesNoDict.push({value: "Y", display: "Yes"});
        this.yesNoDict.push({value: "N", display: "No"});

        this.ownedLeaguesSub = leagueService.ownedLeagueSub; 
        this.gameDictSub = appService.gameSub;
        this.eventIntervalTypeDictSub = appService.eventIntervalTypeSub;
    }

    ngOnInit(){
        this.leagueService.getOwnedLeagues();
    }

    saveEvent(){
        this.eventService.saveEvent(this.event)?.subscribe(res =>{
            if(this.event.idLeague){
                this.leagueService.getLeague(this.event.idLeague)
            }
            this.dialogRef.close();
        })
    }

    onClose(){
        this.dialogRef.close();
    }

    isSavingDisabled(){
        if(this.event.event == undefined || this.event.event.length === 0){
            return true;
        }
        if(this.event.idLeague == undefined){
            return true; 
        }
        if(this.event.idGame == undefined){
            return true; 
        }
        if(this.event.description == undefined || this.event.description.length === 0){
            return true;
        }
        if(this.event.isReaccuring == undefined || this.event.isReaccuring.length === 0){
            return true;
        }
        if(this.event.date == undefined){
            return true; 
        }

        return false; 
    }
}
