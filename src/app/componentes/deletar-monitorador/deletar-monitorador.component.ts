import {Component, Inject} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent, MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {MatButton} from "@angular/material/button";
import {MonitoradorService} from "../../services/monitorador.service";
import {MonitoradorModels} from "../../Models/monitorador/monitorador.models";
import {MonitoradorComponent} from "../monitorador/monitorador.component";
import {MatIcon} from "@angular/material/icon";
import {HttpClientModule} from "@angular/common/http";

@Component({
  selector: 'app-deletar-monitorador',
  standalone: true,
  imports: [
    MatDialogActions,
    MatDialogTitle,
    MatDialogContent,
    MatButton,
    MatDialogClose,
    MatIcon,
    HttpClientModule
  ],
  providers:[
    MonitoradorService,
  ],
  templateUrl: './deletar-monitorador.component.html',
  styleUrl: './deletar-monitorador.component.css'
})
export class DeletarMonitoradorComponent {
  constructor(private monitoradorServices: MonitoradorService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<MonitoradorComponent>,) {
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
  DeletarMonitorador(){
    this.monitoradorServices.deleteMonitorador(this.data.toString()).subscribe(resposta=>{
      this.dialogRef.close();
    });
  }

}
