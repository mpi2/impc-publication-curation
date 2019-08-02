import { environment } from './../../../environments/environment';
import { AuthService } from '../../shared/services/auth.service';
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import {FormControl, Validators} from '@angular/forms';

@Component({
  selector: 'impc-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent implements OnInit {

  user = {
    username: '',
    password: ''
  };

  title = '';

  userNameFormControl = new FormControl('', [
    Validators.required
  ]);

  passwordFormControl = new FormControl('', [
    Validators.required
  ]);

  constructor(private authService: AuthService, private router: Router) { }

  ngOnInit() {
    this.title = environment.title;
  }

  login() {
    this.user.username = this.userNameFormControl.value;
    this.user.password = this.passwordFormControl.value;
    this.authService.login(this.user).then( data => {
      this.router.navigate(['']);
    }
    );
  }

}
