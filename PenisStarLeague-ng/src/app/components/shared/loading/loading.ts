import { Component } from '@angular/core';
import { LoadingService } from './loading-service';

@Component({
  selector: 'loading-overlay',
  standalone: false,
  templateUrl: './loading.html',
  styleUrl: './loading.css'
})
export class Loading {
    public isLoading = false;

    constructor(private loadingService: LoadingService){

    }

    ngOnInit(){
        this.loadingService.isLoadingSub.subscribe(value =>{
            this.isLoading = value; 
        });
    }

}
