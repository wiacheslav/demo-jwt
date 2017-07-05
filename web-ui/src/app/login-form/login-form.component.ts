import { Component, OnInit } from '@angular/core';
import { UserService } from '../user.service';

@Component({
  selector: 'app-login-form',
  templateUrl: './login-form.component.html',
  styleUrls: ['./login-form.component.css']
})
export class LoginFormComponent implements OnInit {
  loading = false;

  constructor(private userService: UserService) { }

  ngOnInit() {
  }

  login(email: HTMLInputElement, password: HTMLInputElement): any {
    this.loading = true;
    this.userService.login({
      username: email.value,
      password: password.value
    }).catch(reason => {
      this.loading = false;
      return false;
    });
    return false;
  }

}
