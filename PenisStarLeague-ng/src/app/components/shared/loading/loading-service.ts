import { Injectable } from '@angular/core';
import { BehaviorSubject } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class LoadingService {
    private loadingCount = 0;
    public isLoadingSub = new BehaviorSubject<boolean>(false); 

    addLoad(){
        this.loadingCount ++;
        this.checkLoading();
    }

    removeLoad(){
        if(this.loadingCount > 0){
            this.loadingCount --; 
        }

        this.checkLoading();
    }

    private checkLoading(){
        this.isLoadingSub.next(this.loadingCount > 0); 
    }

  
}
