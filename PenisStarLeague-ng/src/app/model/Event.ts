export class Event{
    public idLeague: number | undefined;
    public idGame: number | undefined;
    public idUser: number | undefined; 
    public event: string | undefined;
    public description: string | undefined;
    public startDate: Date | undefined; // ??? do i need this
    public isReaccuring: string | undefined; 
    public reaccuringInterval: number | undefined;
    public minute: number | undefined;
    public hour: number | undefined; 
    public day: number | undefined;
    public idMonth: number | undefined;
    public idWeek: number | undefined; 
    public idEventIntervalType: number | undefined; 
}