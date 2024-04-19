import {Injectable} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {Observable} from "rxjs";
import {StorageService} from "../storage/storage.service";
import {User} from "../../../../types/global-types";

const BASE_URL = 'http://localhost:8080/api/v1/auth/';
const httpOptions = {
  headers: new HttpHeaders({'Content-Type': 'application/json'}),
};

@Injectable({
  providedIn: 'root'
})
export class AuthenticationService {

  constructor(
    private http: HttpClient,
    private storageService: StorageService,
  ) {
  }

  private user: User | null = this.storageService.getUser();

  getUser(): User | null {
    return this.user;
  }

  setUser(user: User): void {
    this.user = user;
  }

  // register user
  register(registerRequest: any): Observable<any> {
    return this.http.post(BASE_URL + 'register', registerRequest);
  }

  // login user
  login(loginRequest: any): Observable<any> {
    return this.http.post(BASE_URL + 'login', loginRequest)
  }

  // logout user
  logout(): Observable<any> {
    this.storageService.clean();
    this.user = null;
    return this.http.post(BASE_URL + 'logout', httpOptions);
  }

}
