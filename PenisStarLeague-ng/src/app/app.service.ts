import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';

import { BehaviorSubject, Observable } from 'rxjs';
import { UserDTO } from './dto/UserDTO';
import { CreateUserDTO } from './dto/CreateUserDTO';
import { LeagueType } from './model/LeagueType';
import { Game } from './model/Game';
import { Month } from './model/Month';
import { Week } from './model/Week'
import { EventIntervalType } from './model/EventIntervalType';
import { environment } from './../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class AppService {
    public url: string;
    public isLoggedIn = false;

    public tokenSub: BehaviorSubject<string> = new BehaviorSubject<string>("");
    public googleAuthUrl: BehaviorSubject<string> = new BehaviorSubject<string>("");
    public userSub: BehaviorSubject<UserDTO> = new BehaviorSubject<UserDTO>(new UserDTO());
    public leagueTypeSub: BehaviorSubject<LeagueType[]> = new BehaviorSubject<LeagueType[]>([]);
    public gameSub: BehaviorSubject<Game[]> = new BehaviorSubject<Game[]>([]);
    public monthSub: BehaviorSubject<Month[]> = new BehaviorSubject<Month[]>([]);
    public weekSub: BehaviorSubject<Week[]> = new BehaviorSubject<Week[]>([]);
    public eventIntervalTypeSub: BehaviorSubject<EventIntervalType[]> = new BehaviorSubject<EventIntervalType[]>([]);

    constructor(private http: HttpClient) {
        this.url = environment.apiUrl;
    }

    getAuthUrl(): void {
        this.http.get(this.url + "auth/url").subscribe((data: any) => {
            this.googleAuthUrl.next(data.url);
        });
    }

    getToken(code: string): void {
        this.http.get<any>(this.url + "auth/callback?code=" + code).subscribe(res => {
            this.tokenSub.next(res.token);
            if (res) {
                this.getUser();
            }
        });
    }

    getUser(): void {
        this.http.get<UserDTO>(this.url + "getUser", { headers: new HttpHeaders({ "Authorization": "Bearer " + this.tokenSub.value }) }).subscribe(res => {
            this.isLoggedIn = true; 
            this.userSub.next(res)
        })
    }

    createUserName(userName: string, gamerTag: string): Observable<CreateUserDTO> | undefined {
        let idUser =  this.userSub.value.idUser; 
        if(idUser == null){
            return;
        }
        const headers = new HttpHeaders({ "Authorization": "Bearer " + this.tokenSub.value })
        let params = new HttpParams().set("userName", userName).set("gamerTag", gamerTag);

        return this.http.get<CreateUserDTO>(this.url + "createUserName", { headers: headers, params: params});
    }

    getLeagueTypes(): void{
        this.http.get<LeagueType[]>(this.url + "public/getLeagueTypes" ).subscribe(res => {
            this.leagueTypeSub.next(res)
        })
    }

    getGames(): void{
        this.http.get<Game[]>(this.url + "public/getGames" ).subscribe(res => {
            this.gameSub.next(res)
        })
    }

    
    getMonthes(): void{
        this.http.get<Month[]>(this.url + "public/getMonthes" ).subscribe(res => {
            this.monthSub.next(res)
        })
    }

    getWeeks(): void{
        this.http.get<Week[]>(this.url + "public/getWeeks" ).subscribe(res => {
            this.weekSub.next(res)
        })
    }

    getEventIntervalTypes(): void{
        this.http.get<EventIntervalType[]>(this.url + "public/getEventIntervalTypes" ).subscribe(res => {
            this.eventIntervalTypeSub.next(res);
        })
    }
    
}
