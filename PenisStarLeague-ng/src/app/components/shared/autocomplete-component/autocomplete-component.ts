import { Component, EventEmitter, Input, Output } from '@angular/core';
import { faMagnifyingGlass } from '@fortawesome/free-solid-svg-icons';
import { cloneDeep } from 'lodash'; 
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-autocomplete-component',
  standalone: false,
  templateUrl: './autocomplete-component.html',
  styleUrl: './autocomplete-component.css'
})
export class AutocompleteComponent {
    @Input() valueSub
    : BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]); 
    @Input() value: any; 
    @Output() valueChange = new EventEmitter<any>();
    @Input() id: string = "";
    @Input() display: string = ""; 
    @Input() title: string ="";
    protected readonly faMagGlassIcon = faMagnifyingGlass; 

    public values: any[] = []; 
    public valuesCopy: any[] = []; 
    public searchTerm: any[] = []; 

    constructor(){

    }

    ngOnInit(){
        this.valueSub.subscribe(res =>{
            this.values = res; 
            this.valuesCopy = cloneDeep(res); 
            console.log(this.valuesCopy);
        })
    }

    onSearchChange(){
        this.valuesCopy = this.values.filter(val =>{
            return val[this.display].toString().includes(this.searchTerm);
        })
    }
    
    onValueChange(){
        this.valueChange.emit(this.value);
    }
}
