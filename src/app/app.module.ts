import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { FormsModule} from '@angular/forms';
import { AuthService } from './services/auth.service';
import { AuthComponent } from './auth/auth.component';
import { RouterModule, Routes } from '@angular/router';
import { FourOhFourComponent } from './four-oh-four/four-oh-four.component';
import { AuthGuard } from './services/auth-guard.service';
import { PatientComponent } from './patient/patient/patient.component';
import { HttpClientModule } from '@angular/common/http';
import { PatientFormComponent } from './patient/patient-form/patient-form.component';


const appRoutes: Routes = [
  { path: 'patient', canActivate: [AuthGuard], component: PatientComponent },
  { path: 'create', canActivate: [AuthGuard], component: PatientFormComponent },
  { path: '', component: AuthComponent },
  { path: 'not-found', component: FourOhFourComponent },
  { path: '**', redirectTo: 'not-found' }
];

@NgModule({
  declarations: [
    AppComponent,
    AuthComponent,
    FourOhFourComponent,
    PatientComponent,
    PatientFormComponent,
  ],
  imports: [
    BrowserModule,
    FormsModule,
    AppRoutingModule,
    RouterModule.forRoot(appRoutes),
    HttpClientModule
  ],
  providers: [
    AuthService,
    AuthGuard
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
