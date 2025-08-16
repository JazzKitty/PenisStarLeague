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
import { LoadingService } from './components/shared/loading/loading-service';

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
    public eventIntervalTypeSub: BehaviorSubject<EventIntervalType[]> = new BehaviorSubject<EventIntervalType[]>([]);

    constructor(private http: HttpClient, private loadingService: LoadingService) {
        this.url = environment.apiUrl;
    }

    getAuthUrl(): void {
        this.loadingService.addLoad();
        this.http.get(this.url + "auth/url").subscribe({
            next: (data: any) => this.googleAuthUrl.next(data.url),
            complete: () => this.loadingService.removeLoad()
        });

    }

    getToken(code: string): void {
        this.http.get<any>(this.url + "auth/callback?code=" + code).subscribe( {
            
            next: res => {
                this.tokenSub.next(res.token);
                if (res) {
                    this.getUser();
                }
            },
            complete: () => this.loadingService.removeLoad()

        });
    }

    getUser(): void {
        this.loadingService.addLoad();
        this.http.get<UserDTO>(this.url + "getUser", { headers: new HttpHeaders({ "Authorization": "Bearer " + this.tokenSub.value }) }).subscribe({
            next: res => {
                this.isLoggedIn = true; 
                this.userSub.next(res)
            },
            complete: () => this.loadingService.removeLoad()
        });
    }

    createUserName(userName: string, gamerTag: string): Observable<CreateUserDTO> | undefined {
        if(!this.isLoggedIn){
            return;
        }

        const headers = new HttpHeaders({ "Authorization": "Bearer " + this.tokenSub.value });
        let params = new HttpParams().set("userName", userName).set("gamerTag", gamerTag);

        return this.http.get<CreateUserDTO>(this.url + "createUserName", { headers: headers, params: params});
    }

    getLeagueTypes(): void{
        this.loadingService.addLoad();
        this.http.get<LeagueType[]>(this.url + "public/getLeagueTypes" ).subscribe({
            next: res => this.leagueTypeSub.next(res),
            complete: () => this.loadingService.removeLoad()
        })
    }

    getGames(): void{
        this.loadingService.addLoad();
        this.http.get<Game[]>(this.url + "public/getGames" ).subscribe({
            next: res => this.gameSub.next(res),
            complete: () => this.loadingService.removeLoad()
        })
    }

    getEventIntervalTypes(): void {
        this.loadingService.addLoad();
        this.http.get<EventIntervalType[]>(this.url + "public/getEventIntervalTypes" ).subscribe({
            next: res => this.eventIntervalTypeSub.next(res),
            complete: () => this.loadingService.removeLoad()
        })
    }

    editUserName(userName: string): Observable<any> | undefined {
        if(!this.isLoggedIn){
            return;
        }
        const headers = new HttpHeaders({ "Authorization": "Bearer " + this.tokenSub.value });
        let params = new HttpParams().set("userName", userName)

        return this.http.post(this.url + "editUserName",null, { headers: headers, params: params}); 
    }

    editGamerTag(gamerTag: string): Observable<any> | undefined {
        if(!this.isLoggedIn){
            return;
        }
        const headers = new HttpHeaders({ "Authorization": "Bearer " + this.tokenSub.value });
        let params = new HttpParams().set("gamerTag", gamerTag)

        return this.http.post(this.url + "editGamerTag", null,{ headers: headers, params: params}); 
    }

    editBio(bio: string): Observable<any> | undefined {
        if(!this.isLoggedIn){
            return;
        }
        const headers = new HttpHeaders({ "Authorization": "Bearer " + this.tokenSub.value });
        let params = new HttpParams().set("bio", bio)

        return this.http.post(this.url + "editBio", null,{ headers: headers, params: params}); 
    }
    
}
