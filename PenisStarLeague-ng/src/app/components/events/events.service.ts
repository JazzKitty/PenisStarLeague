import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { AppService } from '../../app.service';
import { Event } from '../../model/Event';
import { EventCardDTO } from '../../dto/EventCardDTO';

@Injectable({
    providedIn: 'root'
})
export class EventsService {
    private url: string;
    public eventCardSub: BehaviorSubject<EventCardDTO[]> = new BehaviorSubject<EventCardDTO[]>([]); 


    constructor(private http: HttpClient, 
                private appService: AppService) {
        this.url = appService.url;
    }

    saveEvent(event: Event): Observable<any> | undefined{ 
        let idUser =  this.appService.userSub.value.idUser; 
        if(idUser == null){
            return;
        }

        const headers = new HttpHeaders({ "Authorization": "Bearer " + this.appService.tokenSub.value })
        return this.http.post<any>(this.url + "createEvent", event, {headers: headers});
    }

    getEventCards(){
        let idUser =  this.appService.userSub.value.idUser; 
        if(idUser == null){
            return;
        }

        const headers = new HttpHeaders({ "Authorization": "Bearer " + this.appService.tokenSub.value })
        this.http.get<EventCardDTO[]>(this.url + "getEventCards",{headers: headers}).subscribe(res =>{
            this.eventCardSub.next(res);
        });
    }
}