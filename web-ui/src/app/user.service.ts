import { Injectable } from '@angular/core';
import { Headers, Http } from '@angular/http';
import 'rxjs/add/operator/toPromise';

export class LoginRequest {
  constructor (public username: string, public password: string) {}
}

export class LoginResponse {
  token: string
}

@Injectable()
export class UserService {

  private headers = new Headers({'Content-Type': 'application/json'});

  constructor(private http: Http) { }

  login(loginRequest: LoginRequest): Promise<LoginResponse> {
    return this
      .http
      .post('/v1.0/auth/login', JSON.stringify(loginRequest), {headers: this.headers})
      .toPromise()
      .then(res => res.json() as LoginResponse);
      //.catch(this.handleError);
  }

  handleError(error: any): Promise<any> {

    return Promise.reject('');
  }

}
