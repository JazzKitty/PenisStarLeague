import { Component, EventEmitter, Output } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-edit-textarea-dialog.component',
  standalone: false,
  templateUrl: './edit-textarea-dialog.component.html',
  styleUrl: './edit-textarea-dialog.component.css'
})

export class EditTextareaDialogComponent {
    public title: string = "";
    public value: string | undefined = "";
    public label: string = ""; 
    @Output() confirmed = new EventEmitter<string>();

    constructor(private dialogRef: MatDialogRef<EditTextareaDialogComponent>){

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
