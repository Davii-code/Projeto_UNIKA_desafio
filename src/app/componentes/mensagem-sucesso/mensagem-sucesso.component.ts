import { Component } from '@angular/core';
import {MatDialog, MatDialogRef} from "@angular/material/dialog";
import {MonitoradorComponent} from "../monitorador/monitorador.component";
import {HttpClientModule} from "@angular/common/http";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-mensagem-sucesso',
  standalone: true,
  imports: [HttpClientModule,MatIcon],
  templateUrl: './mensagem-sucesso.component.html',
  styleUrl: './mensagem-sucesso.component.css'
})
export class MensagemSucessoComponent {

  constructor(  public dialogRef: MatDialogRef<MonitoradorComponent>) {
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
}
