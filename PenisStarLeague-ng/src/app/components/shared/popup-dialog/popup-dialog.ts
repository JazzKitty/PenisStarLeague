import { Component, Input } from '@angular/core';
import { MatDialogRef } from '@angular/material/dialog';

@Component({
  selector: 'app-popup-dialog',
  standalone: false,
  templateUrl: './popup-dialog.html',
  styleUrl: './popup-dialog.css'
})
export class PopupDialog {
    @Input() title = ""; 
    @Input() bodyText = ""; 

    constructor(private dialogRef: MatDialogRef<PopupDialog>){

    }
    
    onClose(){
        this.dialogRef.close();
    }

}
