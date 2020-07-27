import { Component, OnInit } from '@angular/core';
import {NgForm} from '@angular/forms';
import { Router} from '@angular/router';
import {HttpClient  ,HttpRequest,  HttpEvent} from '@angular/common/http';

@Component({
  selector: 'app-patient-form',
  templateUrl: './patient-form.component.html',
  styleUrls: ['./patient-form.scss']
})
export class PatientFormComponent implements OnInit {
  img = null;
  fileToUpload: Array <File>;
  imageToShow: any;
  name: String;

  constructor(private router: Router, private http: HttpClient) { }

  ngOnInit(): void {
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
      const formData: FormData = new FormData();

    formData.append('file', this.fileToUpload[0]);
      this.http.post('http://localhost:8080/upload', formData,{ responseType: 'text' })
          .subscribe((response) => {
            this.name = response;
          });
    } else {
      alert('You must select a DICOM (.dcm) file for upload');
      return false;
    }
  }
  getImage(){
  this.http.get('http://localhost:8080/image', { responseType: 'blob' })
    .subscribe(result => {
      this.img = result;
      console.log(result)
      this.createImageFromBlob(this.img)
    });
    
  }

  createImageFromBlob(image: Blob) {
    let reader = new FileReader();
    reader.addEventListener("load", () => {
       this.imageToShow = reader.result;
       console.log(this.imageToShow)
    }, false);
 
    if (image) {
       reader.readAsDataURL(image);
    }
 }

}
