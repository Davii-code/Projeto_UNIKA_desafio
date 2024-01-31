import {Component, Inject} from '@angular/core';
import {
  MAT_DIALOG_DATA, MatDialog,
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
import {MensagemSucessoComponent} from "../mensagem-sucesso/mensagem-sucesso.component";
import {MensagemErrorComponent} from "../mensagem-error/mensagem-error.component";

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

  msg: string ='';
  constructor(private monitoradorServices: MonitoradorService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<MonitoradorComponent>,
              public dialog: MatDialog) {
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
  DeletarMonitorador(){
    this.monitoradorServices.deleteMonitorador(this.data.toString()).subscribe(resposta=>{
      const dialog = this.dialog.open(MensagemSucessoComponent)
      dialog.afterClosed().subscribe(() => {
        this.dialogRef.close();
      });
    },
      error => {
        console.error(error);
        this.msg = error.error;
        const dialog = this.dialog.open(MensagemErrorComponent, {
          data: this.msg
        });
      }
      );
  }

}
