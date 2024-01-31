import {Component, Inject} from '@angular/core';
import {HttpClientModule} from "@angular/common/http";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {MonitoradorComponent} from "../monitorador/monitorador.component";
import {MatIcon} from "@angular/material/icon";

@Component({
  selector: 'app-mensagem-error',
  standalone: true,
  imports: [HttpClientModule, MatIcon],
  templateUrl: './mensagem-error.component.html',
  styleUrl: './mensagem-error.component.css'
})
export class MensagemErrorComponent {
  msg: string=''
  constructor(   @Inject(MAT_DIALOG_DATA) public data: any,
                 public dialogRef: MatDialogRef<MonitoradorComponent>,
  ) {
    this.msg = this.data
  }
  onNoClick(): void {
    this.dialogRef.close();
  }


}
