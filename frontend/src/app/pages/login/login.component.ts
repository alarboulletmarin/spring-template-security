import { Component } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from 'src/app/core/auth/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
})
export class LoginComponent {
  loginForm: FormGroup;
  errorMessage: string = '';

  constructor(
    private formBuilder: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {
    this.loginForm = this.formBuilder.group({
      username: ['', Validators.required],
      password: ['', Validators.required],
    });
  }

  onSubmit() {
    if (this.loginForm.valid) {
      const { username, password } = this.loginForm.value;
      this.authService.login(username, password).subscribe(
        () => {
          // Ici, vous pouvez stocker le token JWT si nécessaire.
          // Par exemple : localStorage.setItem(this.JWT_TOKEN, token);
          this.router.navigate(['/']); // Redirige vers la page d'accueil
        },
        (error) => {
          // Gérez l'erreur ici. Par exemple, définissez un message d'erreur pour l'afficher dans le template.
          this.errorMessage = 'Identifiants incorrects';
        }
      );
    }
  }
}
