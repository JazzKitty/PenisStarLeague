import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { AppService } from '../../app.service';
import { Event } from '../../model/Event';
import { CalenderEventDTO } from '../../dto/CalenderEventDTO';
import { EventCardDTO } from '../../dto/EventCardDTO';

@Injectable({
    providedIn: 'root'
})
export class EventsService {
    private url: string;
    public calenderEventSub: BehaviorSubject<CalenderEventDTO[]> = new BehaviorSubject<CalenderEventDTO[]>([]);
    public eventCardSub: BehaviorSubject<EventCardDTO[]> = new BehaviorSubject<EventCardDTO[]>([]);


    constructor(private http: HttpClient,
        private appService: AppService) {
        this.url = appService.url;
    }

    saveEvent(event: Event): Observable<any> | undefined {
        if(!this.appService.isLoggedIn){
            return;
        }

        const headers = new HttpHeaders({ "Authorization": "Bearer " + this.appService.tokenSub.value })
        return this.http.post<any>(this.url + "createEvent", event, { headers: headers });
    }

    getCalenderEvents(startDate: Date, endDate: Date) {
        if(!this.appService.isLoggedIn){
            return;
        }

        const headers = new HttpHeaders({ "Authorization": "Bearer " + this.appService.tokenSub.value });
        let params = new HttpParams().set("startDate", startDate.toUTCString()).set("endDate", endDate.toUTCString());

        this.http.get<CalenderEventDTO[]>(this.url + "getCalenderEvents", { headers: headers, params: params }).subscribe(res => {
            this.calenderEventSub.next(res);
        });
    }

    getEventCards(idEvents: number[]){
        if(!this.appService.isLoggedIn){
            return;
        }

        const headers = new HttpHeaders({ "Authorization": "Bearer " + this.appService.tokenSub.value });
        let params = new HttpParams().set("idEvents", idEvents.join(","));

        this.http.get<EventCardDTO[]>(this.url + "getEventCards", { headers: headers, params: params }).subscribe(res => {
            this.eventCardSub.next(res);
        });
    }
}