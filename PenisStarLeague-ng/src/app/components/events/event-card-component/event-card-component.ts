import { Component, Input } from '@angular/core';
import { EventCardDTO } from '../../../dto/EventCardDTO';

@Component({
    selector: 'app-event-card-component',
    standalone: false,
    templateUrl: './event-card-component.html',
    styleUrl: './event-card-component.css'
})
export class EventCardComponent {
    @Input() eventCard: EventCardDTO | undefined;

}
