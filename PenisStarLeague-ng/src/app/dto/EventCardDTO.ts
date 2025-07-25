export class EventCardDTO{
    public idEvent: number | undefined;
    public league: string | undefined;
    public game: number | undefined;
    public event: string | undefined;
    public description: string | undefined;
    public isReaccuring: string | undefined; 
    public minute: number | undefined;
    public hour: number | undefined; 
    public year: number | undefined;
    public day: number | undefined;
    public month: number | undefined;
    public weekDay: string | undefined; 
    public eventInterval: string | undefined; 
    public amPm: string | undefined; 
}