import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';
import { Router} from '@angular/router';
import {HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.scss']
})
export class PatientFormComponent implements OnInit {

  fileToUpload: Array <File>;

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit(): void {
  }

  patientForm(form: NgForm) {
      console.log(form.value);
  }

  handleFileInput(element) {
    this.fileToUpload = element.target.files;
  }

  upload() {
    const fileElement = document.getElementById('DICOMfile') as HTMLInputElement;
    let fileExtension = '';
    console.log(fileElement)
    if (fileElement.value.lastIndexOf('.') > 0) {
      fileExtension = fileElement.value.substring(fileElement.value.lastIndexOf('.') + 1, fileElement.value.length);
    }
    if (fileExtension.toLowerCase() === 'dcm') {
      this.http.post('http://127.0.0.1:4200/upload', this.fileToUpload)
          .subscribe((response) => {
            console.log('response received is ', response);
          });
    } else {
      alert('You must select a DICOM (.dcm) file for upload');
      return false;
    }
  }
}
