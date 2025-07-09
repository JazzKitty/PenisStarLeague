import { CdkVirtualScrollViewport } from '@angular/cdk/scrolling';
import { Component, ViewChild } from '@angular/core';
import {ChangeDetectionStrategy, ViewEncapsulation} from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { LeagueCardComponent } from './league-card/league-card.component';
import { NewLeagueDialogComponent } from './new-league-dialog/new-league-dialog.component';

@Component({
  selector: 'app-leagues',
  templateUrl: './leagues.component.html',
  styleUrl: './leagues.component.css'
})
export class LeaguesComponent {
    public items: number[] = []; 
    @ViewChild(CdkVirtualScrollViewport) viewport!: CdkVirtualScrollViewport;

    constructor(private dialogRef: MatDialog){
        for(let i = 0; i< 25; i++){
            this.items.push(i);
        }
    }

    scrollRight(){
        this.viewport.scrollToOffset(this.viewport.measureScrollOffset()+1400)
    }

    scrollLeft(){
        this.viewport.scrollToOffset(this.viewport.measureScrollOffset()-1400)
    }

    newLeague(){
        this.dialogRef.open(NewLeagueDialogComponent)
    }

}
