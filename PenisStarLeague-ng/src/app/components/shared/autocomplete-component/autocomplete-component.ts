import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatSelectChange } from '@angular/material/select';
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
    @Input() valueSub: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]); 
    @Input() value: any; 
    @Output() valueChange = new EventEmitter<any>();
    @Input() id: string = "";
    @Input() display: string = ""; 
    @Input() title: string ="";
    @Input() isMultiSelect: boolean = false; 
    

    protected readonly faMagGlassIcon = faMagnifyingGlass; 

    public values: any[] = []; 
    public valuesCopy: any[] = []; 
    public valueMap: Map<any, any> = new Map();
    public searchTerm: any[] = []; 

    constructor(){
        this.value = undefined; 
    }

    ngOnInit(){
        this.valueSub.subscribe(res =>{
            this.values = res; 
            this.valuesCopy = cloneDeep(res); 
            
            this.valuesCopy.forEach(value =>{
                this.valueMap.set(value[this.id], value[this.display]);
            })

        })
    }

    onSearchChange(){
        this.valuesCopy.sort((a, b) =>{
            if(a[this.display].toString().toLowerCase().includes(this.searchTerm.toString().toLowerCase()) && b[this.display].toString().toLowerCase().includes(this.searchTerm.toString().toLowerCase())){
                return a[this.display].localeCompare(b[this.display]); 
            }
            else if(a[this.display].toString().toLowerCase().includes(this.searchTerm.toString().toLowerCase())) {
                return -1; 
            }
            else if(b[this.display].toString().toLowerCase().includes(this.searchTerm.toString().toLowerCase())) {
                return 1; 
            }
            return a[this.display].localeCompare(b[this.display]);
        }
        )
    
    }
    
    onValueChange(event : MatSelectChange){
        this.valueChange.emit(this.value);
    }

}
