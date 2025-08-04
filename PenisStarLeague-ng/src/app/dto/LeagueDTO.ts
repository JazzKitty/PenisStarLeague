import { Game } from "../model/Game";
import { LeagueEventDTO } from "./LeagueEventDTO";

export class LeagueDTO {
    public idLeague: number | undefined;
    public league: string | undefined;
    public owner: number | undefined;
    public isOwner: string | undefined;
    public isMember: string | undefined;
    public isPrivate: string | undefined;
    public isPending: string | undefined;
    public memberCount: string | undefined;
    public description: string | undefined
    public users: any[] = []; 
    public games: Game[] = [];
    public events: LeagueEventDTO[] = [] 
}