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
    public weekDictSub: BehaviorSubject<Week[]>;
    public monthDictSub: BehaviorSubject<Month[]>;
    public eventIntervalTypeDictSub: BehaviorSubject<EventIntervalType[]>;
    public ownedLeaguesSub: BehaviorSubject<LeagueDictDTO[]>; 
    public yesNoDict: any[] = [];
    public amPmDict: any[] = []; 
    public date: Date | undefined = undefined;

    constructor(private appService: AppService, private leagueService: LeagueService, private dialogRef: MatDialogRef<NewEventComponent>, private eventService: EventsService){
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
        this.eventService.saveEvent(this.event)?.subscribe(res =>{
            
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

        if(this.event.isReaccuring === "Y"){
            if(this.checkBadReaccuring()){
                return true;
            }
        }
        
        if(this.event.isReaccuring === "N"){ // just check date
            if(this.checkIfBadDate()){
                return true;
            }
        }

        if(this.event.hour == undefined || this.event.hour > 12 || this.event.hour < 1){
            return true;
        }

        if(this.event.minute == undefined || this.event.minute > 60 || this.event.minute < 0){
            return true;
        }

        if(this.event.amPm == undefined || this.event.amPm.length === 0){
            return true;
        }

        return false; 
    }

    checkIfBadDate(): boolean{
        if(this.event.day == undefined || this.event.day < 1 || this.event.day > 31 ){
            return true; 
        }

        if(this.event.idMonth == undefined || this.event.idMonth < 0 || this.event.idMonth > 11 ){
            return true; 
        }

        if(this.event.year == undefined || this.event.year < 1900 || this.event.year > 2100 ){
            return true; 
        }

        return false; 
    }

    checkBadReaccuring(): boolean{
        if(this.event.idEventIntervalType == null){
            return true;
        }

        // todo: change from magic numbers 
        switch(this.event.idEventIntervalType){
            case 1: //yearly 
                if(this.event.idMonth == undefined || this.event.idMonth < 0 || this.event.idMonth > 11 ){
                    return true; 
                }
                if(this.event.day == undefined || this.event.day < 1 || this.event.day > 31 ){
                    return true; 
                }
                break;
                // fall through
            case 2: //Monthly
                if(this.event.day == undefined || this.event.day < 1 || this.event.day > 31 ){
                    return true; 
                }
                break;
            case 3: //Weekly 
                if(this.event.idWeek == undefined || this.event.idWeek < 0 || this.event.idWeek > 7 ){
                    return true; 
                }
        } 
        return false; 

        
    }

    onDateChange(){
        this.event.day = this.date?.getDate();
        this.event.idMonth = this.date?.getMonth();
        this.event.year = this.date?.getFullYear(); 
    }


}
