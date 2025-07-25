import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MatToolbarModule, MatToolbarRow } from '@angular/material/toolbar';
import { MatMenuModule } from '@angular/material/menu';
import { MatIconModule } from '@angular/material/icon';
import { FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import {
  MatDrawer,
  MatDrawerContainer,
  MatDrawerContent,
  MatSidenav,
  MatSidenavContainer,
} from '@angular/material/sidenav';
import { MatDialogModule } from '@angular/material/dialog';
import { MatListItem, MatNavList } from '@angular/material/list';
import { provideAnimationsAsync } from '@angular/platform-browser/animations/async';
import { MatSidenavModule } from '@angular/material/sidenav';
import { HomeComponent } from './components/home/home.component';
import { TournamentsComponent } from './components/tournaments/tournaments.component';
import { EventsComponent } from './components/events/events.component';
import { GamesComponent } from './components/games/games.component';
import { LeaguesComponent } from './components/leagues/leagues.component';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { provideHttpClient } from '@angular/common/http';
import { provideOAuthClient } from 'angular-oauth2-oidc';
import { LoadingComponent } from './components/loading/loading.component';
import { LoginDialogComponent } from './dialog/login-dialog.component';
import { FormsModule } from '@angular/forms';
import { ScrollingModule } from '@angular/cdk/scrolling';
import { LeagueCardComponent } from './components/leagues/league-card/league-card.component';
import { MatCardModule } from '@angular/material/card';
import { MatChipsModule } from '@angular/material/chips';
import { NewLeagueDialogComponent } from './components/leagues/new-league-dialog/new-league-dialog.component';
import { MatButtonModule } from '@angular/material/button';
import {MatInputModule} from '@angular/material/input'; 
import {MatFormFieldModule} from '@angular/material/form-field';
import {MatDatepickerModule} from '@angular/material/datepicker'; 
import { MatOption } from "@angular/material/core"; 
import { MatSelectModule} from '@angular/material/select';
import { LeagueComponent } from './components/leagues/league/league.component';
import { AgGridAngular } from 'ag-grid-angular'; // Angular Data Grid Component
import { AllCommunityModule, ModuleRegistry } from 'ag-grid-community';
import { ComfirmationDialogComponent } from './components/shared/comfirmation-dialog.component/comfirmation-dialog.component';
import { EditInputDialogComponent } from './components/shared/edit-input-dialog.component/edit-input-dialog.component';
import { EditTextareaDialogComponent } from './components/shared/edit-textarea-dialog.component/edit-textarea-dialog.component';
import { NewEventComponent } from './components/events/new-event-component/new-event-component';
import { provideNativeDateAdapter } from '@angular/material/core';
import { AutocompleteComponent } from './components/shared/autocomplete-component/autocomplete-component';
import { EventCardComponent } from './components/events/event-card-component/event-card-component';
import { EditAutocompleteDialog } from './components/shared/edit-autocomplete-dialog/edit-autocomplete-dialog';

// Register all Community features
ModuleRegistry.registerModules([AllCommunityModule]);

@NgModule({
  declarations: [
    AppComponent,
    HomeComponent,
    TournamentsComponent,
    EventsComponent,
    GamesComponent,
    LeaguesComponent,
    LoadingComponent,
    LoginDialogComponent,
    LeagueCardComponent,
    NewLeagueDialogComponent,
    LeagueComponent,
    ComfirmationDialogComponent,
    EditInputDialogComponent,
    EditTextareaDialogComponent,
    NewEventComponent,
    AutocompleteComponent,
    EventCardComponent,
    EditAutocompleteDialog,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    BrowserAnimationsModule,
    MatMenuModule,
    MatToolbarModule,
    MatToolbarRow,
    MatIconModule,
    FontAwesomeModule,
    MatSidenavContainer,
    MatDialogModule,
    MatNavList,
    MatListItem,
    MatSidenav,
    MatDrawerContainer,
    MatDrawer,
    MatDrawerContent,
    FormsModule,
    ScrollingModule,
    MatCardModule,
    MatChipsModule,
    MatButtonModule,
    MatInputModule,
    MatFormFieldModule,
    MatOption,
    MatSelectModule,
    AgGridAngular,
    MatDatepickerModule
],
  providers: [
    provideAnimationsAsync(),
    provideHttpClient(),
    provideOAuthClient(),
    provideNativeDateAdapter()
  ],
  bootstrap: [AppComponent],
})
export class AppModule {}
