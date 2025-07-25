import { Component, EventEmitter, Input, Output } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';
import { BehaviorSubject } from 'rxjs';

@Component({
  selector: 'app-edit-autocomplete-dialog',
  standalone: false,
  templateUrl: './edit-autocomplete-dialog.html',
  styleUrl: './edit-autocomplete-dialog.css'
})
export class EditAutocompleteDialog {
    public title: string = "";
    public value: any | any[] | undefined;
    @Input() multiSelect: boolean = false;
    @Input() dictSub: BehaviorSubject<any[]> = new BehaviorSubject<any[]>([]);
    @Input() idCol: string = "";
    @Input() displayCol: string = ""; 
    @Output() confirmed = new EventEmitter<any | any[]>();

    constructor(private dialogRef: MatDialogRef<EditAutocompleteDialog>){

    }

    ngOnInit(){
        this.value = this.value
    }

    onConfirm(){
        this.confirmed.emit(this.value); 
    }

    onClose(){
        this.dialogRef.close();
    }
}
