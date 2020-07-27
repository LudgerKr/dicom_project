import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';
import { Router} from '@angular/router';

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.scss']
})
export class PatientFormComponent implements OnInit {

  constructor(private router: Router) { }

  ngOnInit(): void {
  }

  patientForm(form: NgForm) {
  }

}
