import { Component, NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { HomeComponent } from './components/home/home.component';
import { TournamentsComponent } from './components/tournaments/tournaments.component';
import { EventsComponent } from './components/events/events.component';
import { GamesComponent } from './components/games/games.component';
import { LeaguesComponent } from './components/leagues/leagues.component';
import { LoadingComponent } from './components/loading/loading.component';
import { LeagueComponent } from './components/leagues/league/league.component';
import { Profile } from './components/profile/profile';
import { AuthGuard } from './components/shared/AuthGaurd';


export const routes: Routes = [

    {path: 'loading', component: LoadingComponent},
    {path: 'home', component: LeaguesComponent },
    {path: 'tournaments', component: TournamentsComponent },
    {path: 'events', component: EventsComponent },
    {path: 'leagues', component: LeaguesComponent,
        children: [{
            path: 'league',
            component: LeagueComponent,
        }]
     },
    {path: 'games', component: GamesComponent},
    {path: 'profile', component: Profile, canActivate: [AuthGuard]}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }

