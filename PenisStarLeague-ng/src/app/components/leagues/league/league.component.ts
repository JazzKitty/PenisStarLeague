import { Component } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { LeagueService } from '../league.service';
import { LeagueDTO } from '../../../dto/LeagueDTO';
import { ColDef, GridApi, GridReadyEvent, themeQuartz } from 'ag-grid-community';
import { faPencil, faTrash } from '@fortawesome/free-solid-svg-icons';
import { ComfirmationDialogComponent } from '../../shared/comfirmation-dialog.component/comfirmation-dialog.component';
import { MatDialog } from '@angular/material/dialog';
import { EditInputDialogComponent } from '../../shared/edit-input-dialog.component/edit-input-dialog.component';
import { EditTextareaDialogComponent } from '../../shared/edit-textarea-dialog.component/edit-textarea-dialog.component';
import { AppService } from '../../../app.service';
import { BehaviorSubject } from 'rxjs';
import { Game } from '../../../model/Game';
import { EditAutocompleteDialog } from '../../shared/edit-autocomplete-dialog/edit-autocomplete-dialog';
import { NewEventComponent } from '../../events/new-event-component/new-event-component';

@Component({
    selector: 'app-league',
    standalone: false,
    templateUrl: './league.component.html',
    styleUrl: './league.component.css',
})
export class LeagueComponent {
    public league: LeagueDTO | undefined;
    public memberColDef: ColDef[] | undefined;
    public pendingMemberColDef: ColDef[] | undefined; 
    public eventColDef: ColDef[] | undefined;

    public memberColApi: GridApi | undefined;
    public pendingMemberColApi: GridApi | undefined;

    public gridOptions: any; 
    protected readonly faPencil = faPencil;
    protected readonly faTrash = faTrash;

    public selectedMembersToAdd: any[] | undefined = [];
    public selectedMembersToRemoved: any[] | undefined = []; 

    public readonly theme = themeQuartz.withParams({
        backgroundColor: '#4E4E4E',
        foregroundColor: '#7EB900',
        headerTextColor: '#FFFFFF',
        headerBackgroundColor: '#424242',
        oddRowBackgroundColor: '#424242',
        headerColumnResizeHandleColor: '#7EB900',
        cellTextColor: '#FFFFFF',
    });

    constructor(
        private leagueService: LeagueService,
        private route: ActivatedRoute,
        private dialogRef: MatDialog,
        private router: Router,
        private appService: AppService
        ) { 

    }

    ngOnInit() {
        this.route.queryParams.subscribe((params) => {
            if (params['idLeague'] !== undefined) {
                this.leagueService.getLeague(params['idLeague']);
            }
        });
        this.leagueService.leagueSub.subscribe(
            (data) => {
                if (data) {
                    this.league = data;
                }
            }
        );

        this.setUpColDef();
    }

    setUpColDef() {

        this.memberColDef = [
            { field: 'userName', flex: 1},
            { field: 'gamerTag', flex: 1 },
            { field: 'joinDate', flex: 1 },
            { field: 'bio', flex: 2},
        ];

        this.pendingMemberColDef = [
            { field: 'userName', flex: 1},
            { field: 'gamerTag', flex: 1 },
            { field: 'bio', flex: 2},
        ];

        this.eventColDef = [
            {field: 'event', flex: 1},
            {field: 'occuring', flex: 1}
        ]

        this.gridOptions = {
            rowSelection: { 
                mode: 'multiRow' 
            },
        }
    }

    deleteLeague() {
        let confirmationDialog = this.dialogRef.open(ComfirmationDialogComponent);
        confirmationDialog.componentInstance.title = 'Delete League';
        confirmationDialog.componentInstance.confirmed.subscribe((res) => {
            if (res) {
                this.leagueService
                    .deleteLeague(Number(this.league?.idLeague))
                    .subscribe(
                        (res) => {
                            this.leagueService.getLeagueCards();
                            confirmationDialog.close();
                            this.router.navigate(['/leagues']);
                        },
                        (error: any) => {
                            console.error(error);
                        }
                    );
            }
        });
    }

    requestToJoin(){
        if(this.league?.idLeague)
            this.leagueService.requestToJoin(this.league?.idLeague)
    }

    addMember(){
        if(this.league?.idLeague && this.selectedMembersToAdd){
            let idMembers: number[] = [];
            this.selectedMembersToAdd.forEach(value =>{
                idMembers.push(value.idUser);
            })
            this.leagueService.addMember(this.league.idLeague, idMembers);
        }      
    }

    removeMember(){
        if(this.league?.idLeague && this.selectedMembersToRemoved){
            let idMembers: number[] = [];
            this.selectedMembersToRemoved.forEach(value =>{
                idMembers.push(value.idUser);
            });
            this.leagueService.removeMember(this.league.idLeague, idMembers);
        }
    }

    onMemberSelectionChange(){
        this.selectedMembersToRemoved = this.memberColApi?.getSelectedRows();
    }

    onPendingMemberSelectionChange(){
        this.selectedMembersToAdd = this.pendingMemberColApi?.getSelectedRows();
    }

    onMemberGridReady(gridReadyEvent: GridReadyEvent){
        this.memberColApi = gridReadyEvent.api;
    }

    onPendingMemberGridReady(gridReadyEvent: GridReadyEvent){
        this.pendingMemberColApi = gridReadyEvent.api;
    }

    newEvent() {
        let newEventDialog = this.dialogRef.open(NewEventComponent);
        newEventDialog.componentInstance.event.idLeague = this.league?.idLeague; 
    }

    editTitle() {
        let titleDialog = this.dialogRef.open(EditInputDialogComponent);
        titleDialog.componentInstance.title = 'Edit Title';
        titleDialog.componentInstance.value = this.league?.league;

        titleDialog.componentInstance.confirmed.subscribe((res) => {
            if (res) {
                let idLeague = Number(this.league?.idLeague);
                this.leagueService.editTitle(idLeague, res).subscribe(
                    (res) => {
                        this.leagueService.getLeagueCards();
                        this.leagueService.getLeague(idLeague);
                        titleDialog.close();
                    },
                    (error: any) => {
                        console.error(error);
                    }
                );
            }
        });
    }

    editDescription() {
        let descriptionDialog = this.dialogRef.open(EditTextareaDialogComponent);
        descriptionDialog.componentInstance.title = 'Edit Descritpion';
        descriptionDialog.componentInstance.value = this.league?.description;
        descriptionDialog.componentInstance.confirmed.subscribe((res) => {
            if (res) {
                let idLeague = Number(this.league?.idLeague);
                this.leagueService.editDescription(idLeague, res).subscribe(
                    (res) => {
                        this.leagueService.getLeague(idLeague);
                        descriptionDialog.close();
                    },
                    (error: any) => {
                        console.error(error);
                    }
                );
            }
        });
    }

    editPrimaryGames() {
        let primaryGameDialog = this.dialogRef.open(EditAutocompleteDialog);
        primaryGameDialog.componentInstance.title = 'Edit Primary Games';

        let idGames: number[] = [];
        this.league?.games.forEach(val =>{
            if(val.idGame)
                idGames.push(val.idGame);
        })
        primaryGameDialog.componentInstance.value = idGames;
        primaryGameDialog.componentInstance.idCol = "idGame";
        primaryGameDialog.componentInstance.displayCol = "game";
        primaryGameDialog.componentInstance.dictSub = this.appService.gameSub; 
        primaryGameDialog.componentInstance.confirmed.subscribe((res) => {
            if (res) {
                let idLeague = Number(this.league?.idLeague);
                this.leagueService.editPrimaryGames(idLeague, res).subscribe(
                    (res) => {
                        this.leagueService.getLeague(idLeague);
                        primaryGameDialog.close();
                    },
                    (error: any) => {
                        console.error(error);
                    }
                );
            }
        });
    }
}
