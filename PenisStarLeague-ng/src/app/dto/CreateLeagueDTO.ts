export class CreateLeagueDTO{
    public league:  string = "";
    public description: string = "";
    public idLeagueType: number | undefined;
    public idGames: number[] = [];
}
