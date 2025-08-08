import { Component } from '@angular/core';
import { faBars, faSignIn } from "@fortawesome/free-solid-svg-icons";
import { AppService } from './app.service';
import { ActivatedRoute, NavigationExtras, Router, RouterOutlet } from '@angular/router';
import { environment } from './../environments/environment';


@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css', '../styles.scss'],
    standalone: false
})
export class AppComponent {
    title = 'PenisStarLeague';
    protected readonly faSignIn = faSignIn;
    protected readonly faBars = faBars;

    public authURL: string = "";
    public userName: string | null = null; 

    public isSidebarOpen: boolean = false;
    constructor(private appService: AppService, private router: Router, private route: ActivatedRoute) {
        this.appService.getAuthUrl();
        this.appService.googleAuthUrl.subscribe(res => {
            this.authURL = res;
        });


        this.route.queryParams.subscribe(params => {
            if (params["code"] !== undefined) {
                const navigateToNewWithUser: NavigationExtras = {
                    queryParams: {'code': params["code"]}
                };
                this.router.navigate(['/loading'], navigateToNewWithUser)
            }
        });
    }

    onBarsClicked() {
        this.isSidebarOpen = !this.isSidebarOpen;
    }


    ngOnInit(): void {
        this.appService.userSub.subscribe(res =>{
            if(res.userName !== ""){
                this.userName = res.userName;
            }
        })
        this.loadDictionaries(); 
    }

    onLogin(){
        window.location.href = this.authURL; 
    }

    /**
     * Load dictionary values 
     */
    loadDictionaries(){
        this.appService.getLeagueTypes();
        this.appService.getGames(); // I guess this is a dictionary now... maybe need to refactor some names eventually 
        this.appService.getEventIntervalTypes();
    }

}
