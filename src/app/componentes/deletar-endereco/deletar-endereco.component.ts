import {Component, Inject} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {
  MAT_DIALOG_DATA,
  MatDialogActions, MatDialogClose,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {MatIcon} from "@angular/material/icon";
import {MonitoradorService} from "../../services/monitorador.service";
import {MonitoradorComponent} from "../monitorador/monitorador.component";
import {Endereco} from "../../Models/monitorador/endereco.models";
import {HttpClientModule} from "@angular/common/http";

@Component({
  selector: 'app-deletar-endereco',
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
  templateUrl: './deletar-endereco.component.html',
  styleUrl: './deletar-endereco.component.css'
})
export class DeletarEnderecoComponent {
  constructor(private monitoradorServices: MonitoradorService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<Endereco>,) {
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
  DeletarEndereco(){
    this.monitoradorServices.deleteEndereco(this.data.toString()).subscribe(resposta=>{
      this.dialogRef.close();
    });
  }
}
