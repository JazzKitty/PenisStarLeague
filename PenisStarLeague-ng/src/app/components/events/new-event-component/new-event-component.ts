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
    public weekDictSub: BehaviorSubject<Week[]>;
    public monthDictSub: BehaviorSubject<Month[]>;
    public eventIntervalTypeDictSub: BehaviorSubject<EventIntervalType[]>;
    public ownedLeaguesSub: BehaviorSubject<LeagueDictDTO[]>; 
    public yesNoDict: any[] = [];
    public amPmDict: any[] = []; 

    constructor(private appService: AppService, private leagueService: LeagueService){
        this.yesNoDict.push({value: "Y", display: "Yes"});
        this.yesNoDict.push({value: "N", display: "No"});

        // if i acknowledge this is dumb, is it really dumb.... basically drunk me doesnt want to do it right
        this.amPmDict.push({value: "AM", display: "AM"});
        this.amPmDict.push({value: "PM", display: "PM"});

        this.ownedLeaguesSub = leagueService.ownedLeagueSub; 
        this.gameDictSub = appService.gameSub;
        this.weekDictSub = appService.weekSub;
        this.monthDictSub = appService.monthSub;
        this.eventIntervalTypeDictSub = appService.eventIntervalTypeSub;

    }

    ngOnInit(){

        this.leagueService.getOwnedLeagues();
    }

    saveEvent(){
        console.log(this.event)
    }

    onClose(){

    }

    isSavingDisabled(){
        return false; 
    }

    filterMyOptions(test: any){

    }

}
