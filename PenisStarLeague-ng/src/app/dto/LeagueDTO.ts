export class LeagueDTO {
    public idLeague: number | undefined;
    public league: string | undefined;
    public owner: number | undefined;
    public isOwner: string | undefined;
    public memberCount: string | undefined;
    public description: string | undefined
    public users: any[] = []; 
    public events: any[] = [];
}