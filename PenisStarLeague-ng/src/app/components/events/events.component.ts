import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AppService } from '../../app.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NewEventComponent } from './new-event-component/new-event-component';
import { EventsService } from './events.service';
import { CdkVirtualScrollViewport } from '@angular/cdk/scrolling';
import { CalenderEventDTO } from '../../dto/CalenderEventDTO';
import { CalendarEvent, CalendarMonthViewBeforeRenderEvent } from 'angular-calendar';
import { Subject } from 'rxjs';
import { LeagueService } from '../leagues/league.service';
import { EventCardDTO } from '../../dto/EventCardDTO';

@Component({
    selector: 'app-events',
    templateUrl: './events.component.html',
    styleUrl: './events.component.css',
    standalone: false
})
export class EventsComponent {
    constructor(private dialogRef: MatDialog,
        private appService: AppService,
        private eventService: EventsService,
        private router: Router,
        private route: ActivatedRoute,
        private leagueService: LeagueService) {
    }

    public calenderEvents: CalenderEventDTO[] = [];
    public eventCards: EventCardDTO[] = []; 
    public viewDate: Date = new Date();
    public events: CalendarEvent[] = [];
    public refresh: Subject<any> = new Subject();
    public rendered: boolean  = false;   
    public endDate: Date | undefined;
        
    @ViewChild(CdkVirtualScrollViewport) viewport!: CdkVirtualScrollViewport;

    ngOnInit() {
        this.eventService.calenderEventSub.subscribe(res => {
            this.events = [];
            res.forEach(value => {
                if (value.name != undefined && value.instant != undefined && value.interval != undefined) {
                    let color; 
                    switch (value.interval){
                        case "single":
                            color = {primary: '#5F939B' ,secondary: '#304B4F'}
                            break;
                        case "yearly":
                            color = {primary: '#C343F0' ,secondary: '#842EA3'}
                            break;
                        case "monthly":
                            color = {primary: '#43D9F0' ,secondary: '#2E94A3'}
                                break;
                        case "weekly": 
                            color = {primary: '#F09043' ,secondary: '#A3632E'}
                            break;
                        case "daily":
                            color = {primary: '#7EB900' ,secondary: '#4B6E00'}
                            break;
                    }
                    this.events.push({
                        title: value.name,
                        start: new Date(value.instant),
                        id: value.idEvent,
                        color: color
                    })
                }
            });
            this.refresh.next(true);
        });
        
        this.eventService.eventCardSub.subscribe(cards =>{
            this.eventCards = cards; 
        });

    }

    newEvent() {
        this.dialogRef.open(NewEventComponent);
    }

    newEventDisable() {
        return !this.appService.isLoggedIn;
    }

    nextMonth() {
        this.viewDate.setMonth(this.viewDate.getMonth() + 1);
        this.rendered = false;
        this.refresh.next(true);
    }

    prevMonth() {
        this.viewDate.setMonth(this.viewDate.getMonth() - 1);
        this.rendered = false;
        this.refresh.next(true);
    }

    scrollRight(){
        this.viewport.scrollToOffset(this.viewport.measureScrollOffset()+1400);
    }

    scrollLeft(){
        this.viewport.scrollToOffset(this.viewport.measureScrollOffset()-1400);
    }


    onDayClicked(test: any) {
        let arr = test.day.events as CalendarEvent[];
        let idEvents: number[] = []

        arr.forEach(value => {
            idEvents.push(Number(value.id));
        });

        this.eventService.getEventCards(idEvents);
    }

    beforeViewRender(beforeRenderEvent: CalendarMonthViewBeforeRenderEvent){
        if (this.appService.isLoggedIn && !this.rendered) {
            this.rendered = true;
            this.eventService.getCalenderEvents(beforeRenderEvent.period.start, beforeRenderEvent.period.end);
        }
    }   
}
