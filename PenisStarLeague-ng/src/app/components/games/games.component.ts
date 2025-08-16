import { Component } from '@angular/core';
import { AgGridTheme } from '../shared/ag-grid-theme';
import { ColDef } from 'ag-grid-community';
import { GameInfoDTO } from '../../dto/GameInfoDTO';
import { GameService } from './game.service';

@Component({
    selector: 'app-games',
    templateUrl: './games.component.html',
    styleUrl: './games.component.css',
    standalone: false
})
export class GamesComponent {

    public gamesColDef: ColDef[] | undefined;
    public gameInfo: GameInfoDTO[] | undefined; 
    
    public readonly theme = AgGridTheme.theme; 
    constructor(private gameService: GameService){

    }

    ngOnInit(){
        this.gameService.gameInfoSub.subscribe(res =>{
            if(res){
                this.gameInfo = res;
            }
        });

        if(this.gameService.gameInfoSub.value == null || this.gameService.gameInfoSub.value.length === 0){
            this.gameService.getGameInfo();
        }

        this.gamesColDef = [
            { field: 'game', headerName: 'Game' ,flex: 1},
            { field: 'leagueNumbers', headerName: 'Number of Leagues',  flex: 1 },
            { field: 'eventNumbers', headerName: 'Number of Events', flex: 1 },
        ];
    }

}
