import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AppService } from '../../app.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NewEventComponent } from './new-event-component/new-event-component';
import { EventsService } from './events.service';
import { CdkVirtualScrollViewport } from '@angular/cdk/scrolling';
import { EventCardDTO } from '../../dto/EventCardDTO';
import { CalendarEvent } from 'angular-calendar';
import { Subject } from 'rxjs';

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
                private route: ActivatedRoute) {
    }

    public eventCards: EventCardDTO[] = [];     
    viewDate: Date = new Date();
    events: CalendarEvent[] = []; 
    refresh: Subject<any> = new Subject();




    ngOnInit(){

        this.events = [
            {
                title: 'Editable event',
                start: new Date(),
                actions: [
                    {
                        label: '<i class="fa fa-fw fa-pencil"></i>',
                        onClick: ({ event }: { event: CalendarEvent }): void => {
                            console.log('Edit event', event);
                        }
                    }
                ]
            },
        ];

    }

    newEvent(){
        this.dialogRef.open(NewEventComponent);
    }

    newEventDisable(){
        return !this.appService.isLoggedIn; 
    }

    scrollRight(){
        this.viewDate.setMonth(this.viewDate.getMonth()+1); 
        this.refresh.next(true);
    }

    scrollLeft(){
        this.viewDate.setMonth(this.viewDate.getMonth()-1); 
        this.refresh.next(true);
    }


}
