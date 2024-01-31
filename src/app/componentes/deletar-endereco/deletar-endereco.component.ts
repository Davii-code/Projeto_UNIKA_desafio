import {Component, Inject} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {
  MAT_DIALOG_DATA, MatDialog,
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
import {MensagemSucessoComponent} from "../mensagem-sucesso/mensagem-sucesso.component";
import {MensagemErrorComponent} from "../mensagem-error/mensagem-error.component";

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

  msg: string ='';
  constructor(private monitoradorServices: MonitoradorService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<Endereco>,
              public dialog: MatDialog) {
  }
  onNoClick(): void {
    this.dialogRef.close();
  }
  DeletarEndereco(){
    this.monitoradorServices.deleteEndereco(this.data.toString()).subscribe(resposta=>{
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
      });
  }
}
