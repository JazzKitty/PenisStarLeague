import { Component, ViewChild } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AppService } from '../../app.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NewEventComponent } from './new-event-component/new-event-component';
import { EventsService } from './events.service';
import { CdkVirtualScrollViewport } from '@angular/cdk/scrolling';
import { EventCardDTO } from '../../dto/EventCardDTO';

@Component({
    selector: 'app-events',
    templateUrl: './events.component.html',
    styleUrl: './events.component.css'
})
export class EventsComponent {
    constructor(private dialogRef: MatDialog,
                private appService: AppService, 
                private eventService: EventsService,
                private router: Router, 
                private route: ActivatedRoute) {
    }


    @ViewChild(CdkVirtualScrollViewport) viewport!: CdkVirtualScrollViewport;
    public eventCards: EventCardDTO[] = [];     

    ngOnInit(){
        this.eventService.getEventCards();
        this.eventService.eventCardSub.subscribe(res => {
            this.eventCards = res;
        });
    }

    newEvent(){
        this.dialogRef.open(NewEventComponent);

    }

    newEventDisable(){
        return false; //TODO: change 
    }

    scrollRight(){
        this.viewport.scrollToOffset(this.viewport.measureScrollOffset()+1400);
    }

    scrollLeft(){
        this.viewport.scrollToOffset(this.viewport.measureScrollOffset()-1400);
    }


}
