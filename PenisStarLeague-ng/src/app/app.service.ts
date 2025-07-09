import { HttpClient, HttpHeaders, HttpParams } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';
import { OAuthService } from 'angular-oauth2-oidc';
import { BehaviorSubject, Observable } from 'rxjs';
import { UserDTO } from './dto/UserDTO';
import { CreateUserDTO } from './dto/CreateUserDTO';

@Injectable({
    providedIn: 'root'
})
export class AppService {
    private url: string;

    public tokenSub: BehaviorSubject<string> = new BehaviorSubject<string>("");
    public googleAuthUrl: BehaviorSubject<string> = new BehaviorSubject<string>("");
    public userSub: BehaviorSubject<UserDTO> = new BehaviorSubject<UserDTO>(new UserDTO());

    constructor(private http: HttpClient, private router: Router, private oauthService: OAuthService) {
        this.url = 'http://localhost:8080/';

    }

    getAuthUrl(): void {
        this.http.get(this.url + "auth/url").subscribe((data: any) => {
            this.googleAuthUrl.next(data.url);
        });
    }



    getToken(code: string): void {
        this.http.get<any>(this.url + "auth/callback?code=" + code).subscribe(res => {
            this.tokenSub.next(res.token);
            if (res) {
                this.getUser();
            }
        });
    }

    getUser(): void {
        this.http.get<UserDTO>(this.url + "getUser", { headers: new HttpHeaders({ "Authorization": "Bearer " + this.tokenSub.value }) }).subscribe(res => {
            this.userSub.next(res)
        })
    }


    createUserName(userName: string) {
        let idUser =  this.userSub.value.idUser; 
        if(idUser == null){
            return;
        }
        const headers = new HttpHeaders({ "Authorization": "Bearer " + this.tokenSub.value })
        let params = new HttpParams().set("userName", userName).set("idUser", idUser.toString())

        return this.http.get<CreateUserDTO>(this.url + "createUserName", { headers: headers, params: params});
    }

}
