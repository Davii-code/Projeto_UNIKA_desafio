import {Component, Inject} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {MatIcon} from "@angular/material/icon";
import {MonitoradorService} from "../../services/monitorador.service";
import {MonitoradorComponent} from "../monitorador/monitorador.component";
import {HttpClientModule} from "@angular/common/http";

@Component({
  selector: 'app-importa-excel',
  standalone: true,
  imports: [
    MatButton,
    MatDialogActions,
    MatDialogContent,
    MatDialogTitle,
    MatIcon,
    HttpClientModule
  ],
  providers:[
    MonitoradorService,
  ],
  templateUrl: './importa-excel.component.html',
  styleUrl: './importa-excel.component.css'
})
export class ImportaExcelComponent {


  constructor(private monitoradorServices: MonitoradorService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<MonitoradorComponent>) {
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.monitoradorServices.uploadExcel(file).subscribe(
        response => {
          console.log(response);
        },
        error => {
          console.error(error);
        }
      );

    }
    this.dialogRef.close()
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

}
