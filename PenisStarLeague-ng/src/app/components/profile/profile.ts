import { Component } from '@angular/core';
import { faPencil } from '@fortawesome/free-solid-svg-icons';

@Component({
  selector: 'app-profile',
  standalone: false,
  templateUrl: './profile.html',
  styleUrl: './profile.css'
})
export class Profile {

    protected readonly faPencil = faPencil;
    
    editUserName(){

    }

    editGamerTag(){

    }

    editBio(){

    }
}
