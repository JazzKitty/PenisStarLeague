import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable } from 'rxjs';
import { AppService } from '../../app.service';
import { GameInfoDTO } from '../../dto/GameInfoDTO';
import { LoadingService } from '../shared/loading/loading-service';

@Injectable({
    providedIn: 'root'
})
export class GameService {
    private url: string;
    public gameInfoSub: BehaviorSubject<GameInfoDTO[]> = new BehaviorSubject<GameInfoDTO[]>([]);

    constructor(private http: HttpClient,
        private appService: AppService,
        private loadingService: LoadingService) {
        this.url = appService.url;
    }

    getGameInfo(): void {
        this.loadingService.addLoad();
        this.http.get<GameInfoDTO[]>(this.url + "public/getGameInfo").subscribe({
            next: (data: GameInfoDTO[]) => { 
                if(data){
                    this.gameInfoSub.next(data); 
                } 
            },
            complete: () => this.loadingService.removeLoad()    
        });
    }
}
