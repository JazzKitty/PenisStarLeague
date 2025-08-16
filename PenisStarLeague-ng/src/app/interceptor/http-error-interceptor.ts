import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { catchError, Observable, throwError } from 'rxjs';
import { PopupDialog } from '../components/shared/popup-dialog/popup-dialog';

@Injectable()
export class HttpErrorInterceptor implements HttpInterceptor {
    constructor(private dialogMat: MatDialog){

    }

    intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {

        return next.handle(req).pipe(
            catchError((error: HttpErrorResponse) => {
                // Access the HTTP error code here
                const errorCode = error.status;
                console.error(`HTTP Error Code: ${errorCode}`);

                // You can also access other properties like error.message, error.statusText, or the actual error body from error.error
                if(errorCode === 0){
                    let dialogRef = this.dialogMat.open(PopupDialog);
                    dialogRef.componentInstance.bodyText = "HTTP request failed. Make sure you are connected to the internet"
                    dialogRef.componentInstance.title = "HTTP Request Failed"
                }
                else if (error.error instanceof ErrorEvent) {
                    // Client-side or network error
                    console.error('An error occurred:', error.error.message);
                    let dialogRef = this.dialogMat.open(PopupDialog);
                    dialogRef.componentInstance.bodyText = "Error " + error.status + ": " + error.error.message
                    dialogRef.componentInstance.title = "Client Side Error"

                } else {
                    // Server-side error
                    console.error(
                        `Backend returned code ${error.status}, ` +
                        `body was: ${JSON.stringify(error.error)}`
                    );
                    let dialogRef = this.dialogMat.open(PopupDialog);
                    dialogRef.componentInstance.bodyText = "Error " + error.status + ": " + JSON.stringify(error.error);
                    dialogRef.componentInstance.title = "Client Side Error"
                }

                return throwError(() => error); // Re-throw the error for further handling
            })
        );

    }
};
