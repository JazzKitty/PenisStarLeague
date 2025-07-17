import { Component, EventEmitter, Input, Output } from '@angular/core';
import { LeagueCardDTO } from '../../../dto/LeagueCardDTO';
import { faShield, faUnlock, faLock, IconDefinition } from '@fortawesome/free-solid-svg-icons';
import { AppService } from '../../../app.service';

@Component({
  selector: 'app-league-card',
  templateUrl: './league-card.component.html',
  styleUrl: './league-card.component.css'
})
export class LeagueCardComponent {
    @Input() leagueCard: LeagueCardDTO | undefined; 
    @Output() onCLick = new EventEmitter<number>(); 
    protected readonly faUnlockIcon = faUnlock;
    protected readonly faSheildIcon = faShield;
    protected readonly faLockIcon = faLock; 

    public icon: IconDefinition | undefined; 
    

    constructor(private appService: AppService){

    }

    ngOnInit(){
        this.appService.leagueTypeSub.forEach( res =>{
            res.forEach(leagueType =>{
                if(leagueType.idLeagueType === this.leagueCard?.idLeagueType){
                    console.log(leagueType.code)
                    switch(leagueType.code){
                        case "PRIV":
                            this.icon = this.faSheildIcon;
                            break;
                        case "PUB":
                            this.icon = this.faUnlockIcon;
                            break; 
                        case "PRO":
                            this.icon = this.faLockIcon;
                            break; 
                    }
                    return; 
                }
            });
        })
    }

}
