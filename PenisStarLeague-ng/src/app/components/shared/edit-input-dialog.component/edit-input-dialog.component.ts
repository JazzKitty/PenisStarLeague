import { Component, EventEmitter, Output } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-input-dialog.component',
  standalone: false,
  templateUrl: './edit-input-dialog.component.html',
  styleUrl: './edit-input-dialog.component.css'
})
export class EditInputDialogComponent {
    public title: string = "";
    public label: string = "";
    public value: string | undefined = "";
    @Output() confirmed = new EventEmitter<string>();

    constructor(private dialogRef: MatDialogRef<EditInputDialogComponent>){

    }

    ngOnInit(){
        this.value = this.value
    }

    onConfirm(){
        console.log("here")
        this.confirmed.emit(this.value); 
    }

    onClose(){
        this.dialogRef.close();
    }

}
