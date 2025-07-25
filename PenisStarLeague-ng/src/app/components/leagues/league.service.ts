import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { LeagueCardDTO } from '../../dto/LeagueCardDTO';
import { LeagueDTO } from '../../dto/LeagueDTO';
import { AppService } from '../../app.service';
import { LeagueDictDTO } from '../../dto/LeagueDictDTO';
import { CreateLeagueDTO } from '../../dto/CreateLeagueDTO';

@Injectable({
    providedIn: 'root'
})
export class LeagueService {
    private url: string;

    public leagueTypeSub: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);
    public leagueCardsSub: BehaviorSubject<LeagueCardDTO[]> = new BehaviorSubject<LeagueCardDTO[]>([])
    public leagueSub: BehaviorSubject<LeagueDTO | undefined> = new BehaviorSubject<LeagueDTO | undefined>(undefined); 
    public ownedLeagueSub: BehaviorSubject<LeagueDictDTO[]> = new BehaviorSubject<LeagueDictDTO[]>([]);

    constructor(private http: HttpClient, 
                private appService: AppService) {
        this.url = appService.url;
    }

    saveLeague(createLeagueDTO: CreateLeagueDTO): Observable<any> | undefined{ 
        let idUser =  this.appService.userSub.value.idUser; 
        if(idUser == null){
            return;
        }

        const headers = new HttpHeaders({ "Authorization": "Bearer " + this.appService.tokenSub.value })
        return this.http.post<any>(this.url + "createLeague", createLeagueDTO, {headers: headers});
    }

    getLeagueCards(): void {
        this.http.get<LeagueCardDTO[]>(this.url + "public/getLeagueCards").subscribe((data: LeagueCardDTO[]) => { 
            if(data){
                this.leagueCardsSub.next(data); 
            } 
        });
    }

    getLeague(idLeague: number) {
        let headers; 
        if(this.appService.tokenSub.value || this.appService.tokenSub.value.trim().length !== 0){
            console.log("here")
            headers = new HttpHeaders({ "Authorization": "Bearer " + this.appService.tokenSub.value })
        }else{
            headers = new HttpHeaders();
        }
        let params = new HttpParams().set("idLeague", idLeague);

        this.http.get<LeagueDTO>(this.url + "public/getLeague", {headers: headers, params: params}).subscribe(res =>{
            this.leagueSub.next(res);
        });
    }

    deleteLeague(idLeague: number): Observable<any> {
        let headers = new HttpHeaders({ "Authorization": "Bearer " + this.appService.tokenSub.value })
        let params = new HttpParams().set("idLeague", idLeague);

        return this.http.delete<any>(this.url + "deleteLeague", {headers: headers, params: params});
    }

    editTitle(idLeague: number, title: string){
        let headers = new HttpHeaders({ "Authorization": "Bearer " + this.appService.tokenSub.value })
        let params = new HttpParams().set("idLeague", idLeague).set("title", title);

        return this.http.post<any>(this.url + "editTitle", null, {headers: headers, params: params});
    }

    editDescription(idLeague: number, description: string){
        let headers = new HttpHeaders({ "Authorization": "Bearer " + this.appService.tokenSub.value })
        let params = new HttpParams().set("idLeague", idLeague).set("description", description);

        return this.http.post<any>(this.url + "editDescription", null, {headers: headers, params: params});
    }

    editPrimaryGames(idLeague: number, idGames: number[]){
        let headers = new HttpHeaders({ "Authorization": "Bearer " + this.appService.tokenSub.value })
        let params = new HttpParams().set("idLeague", idLeague).set("primaryGames", idGames.join(","));

        return this.http.post<any>(this.url + "editPrimaryGames", null, {headers: headers, params: params});
    }


    getOwnedLeagues(){
        let headers = new HttpHeaders({ "Authorization": "Bearer " + this.appService.tokenSub.value })

        this.http.get<LeagueDictDTO[]>(this.url + "getOwnedLeagues", {headers: headers}).subscribe(res =>{
            this.ownedLeagueSub.next(res);
        })

    }

    requestToJoin(idLeague: number){
        let headers = new HttpHeaders({ "Authorization": "Bearer " + this.appService.tokenSub.value })
        let params = new HttpParams().set("idLeague", idLeague);

        this.http.post(this.url + "requestToJoin", null, {headers: headers, params: params}).subscribe( res =>{
            this.getLeague(idLeague); //refresh 
        })

    }
}