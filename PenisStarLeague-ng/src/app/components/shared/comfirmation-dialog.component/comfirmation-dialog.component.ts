import { Component, EventEmitter, Output } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-comfirmation-dialog.component',
  standalone: false,
  templateUrl: './comfirmation-dialog.component.html',
  styleUrl: './comfirmation-dialog.component.css'
})
export class ComfirmationDialogComponent {
    public title: string = "";
    @Output() confirmed = new EventEmitter<any>(true);

    constructor(private dialogRef: MatDialogRef<ComfirmationDialogComponent>){

    }

    onConfirm(){
        this.confirmed.emit(true); 
    }

    onClose(){
        this.dialogRef.close();
    }

}
