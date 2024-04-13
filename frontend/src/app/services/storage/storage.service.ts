import {Injectable} from '@angular/core';
import {User} from "../../../../types/global-types";

const USER_KEY = 'auth-user';
const TOKEN_KEY = 'auth-token';

@Injectable({
  providedIn: 'root'
})
export class StorageService {

  constructor() {
  }

  public clean(): void {
    window.sessionStorage.clear();
  }

  public saveUser(user: User): void {
    window.sessionStorage.removeItem(USER_KEY);
    window.sessionStorage.setItem(USER_KEY, JSON.stringify(user));
  }

  public getUser(): User | null {
    const user = window.sessionStorage.getItem(USER_KEY);
    if (user) {
      return JSON.parse(user);
    }
    return null;
  }

  public saveToken(token: string): void {
    window.sessionStorage.removeItem(TOKEN_KEY);
    window.localStorage.setItem(TOKEN_KEY, token);
  }

  public getToken() {
    return window.sessionStorage.getItem(TOKEN_KEY);
  }

  public getUserId(): string {
    const user = this.getUser();
    if (user == null) {
      return '';
    }
    return user.id.toString();
  }

  public getUserRole(): string {
    const user = this.getUser();
    if (user == null) {
      return '';
    }
    return user.role;
  }

  public isAdminLoggedIn(): boolean {
    if (this.getToken() == null) {
      return false;
    }
    const role = this.getUserRole();
    return role === 'admin';
  }

  public isUserLoggedIn(): boolean {
    if (this.getToken() == null) {
      return false;
    }
    const role = this.getUserRole();
    return role === 'user';
  }

}
