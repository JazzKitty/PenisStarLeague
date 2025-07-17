import { Component } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { AppService } from '../../app.service';
import { ActivatedRoute, Router } from '@angular/router';
import { NewEventComponent } from './new-event-component/new-event-component';

@Component({
    selector: 'app-events',
    templateUrl: './events.component.html',
    styleUrl: './events.component.css'
})
export class EventsComponent {
    constructor(private dialogRef: MatDialog,
                private appService: AppService, 
                private router: Router, 
                private route: ActivatedRoute) {

    }

    newEvent(){
        this.dialogRef.open(NewEventComponent);

    }

    newEventDisable(){
        return false; //TODO: change 
    }


}
