import { Game } from "../model/Game";
import { LeagueEventDTO } from "./LeagueEventDTO";

export class LeagueDTO {
    public idLeague: number | undefined;
    public league: string | undefined;
    public owner: number | undefined;
    public isPrivate: string | undefined;
    public leaguePositionCode: string | undefined; 
    public memberCount: string | undefined;
    public description: string | undefined
    public users: any[] = []; 
    public pendingUsers: any[] = []; 
    public games: Game[] = [];
    public events: LeagueEventDTO[] = [] 
}