import {Component, Inject} from '@angular/core';
import {MatButton} from "@angular/material/button";
import {
  MAT_DIALOG_DATA, MatDialog,
  MatDialogActions,
  MatDialogContent,
  MatDialogRef,
  MatDialogTitle
} from "@angular/material/dialog";
import {MatIcon} from "@angular/material/icon";
import {MonitoradorService} from "../../services/monitorador.service";
import {MonitoradorComponent} from "../monitorador/monitorador.component";
import {HttpClientModule, HttpHeaders, HttpStatusCode} from "@angular/common/http";
import {MensagemSucessoComponent} from "../mensagem-sucesso/mensagem-sucesso.component";
import {NgIf} from "@angular/common";
import {MensagemErrorComponent} from "../mensagem-error/mensagem-error.component";

@Component({
  selector: 'app-importa-excel',
  standalone: true,
  imports: [
    MatButton,
    MatDialogActions,
    MatDialogContent,
    MatDialogTitle,
    MatIcon,
    HttpClientModule,
    NgIf
  ],
  providers:[
    MonitoradorService,
  ],
  templateUrl: './importa-excel.component.html',
  styleUrl: './importa-excel.component.css'
})
export class ImportaExcelComponent {
msg: string = '';
validacao : boolean= false;

  constructor(private monitoradorServices: MonitoradorService,
              @Inject(MAT_DIALOG_DATA) public data: any,
              public dialogRef: MatDialogRef<MonitoradorComponent>,
              public dialog: MatDialog) {
  }

  onFileChange(event: any) {
    const file = event.target.files[0];
    if (file) {
      this.monitoradorServices.uploadExcel(file).subscribe(
        resposta => {

            const dialog = this.dialog.open(MensagemSucessoComponent)
          dialog.afterClosed().subscribe(() => {
            this.dialogRef.close();
          });

        },
        error => {
          if (error.status != 400){
            const dialog = this.dialog.open(MensagemErrorComponent, {
              data: "Formato de arquivo inválido. Apenas arquivos XLSX são suportados."
            });
          }else {
            console.error(error);
            this.msg = error.error;
            const dialog = this.dialog.open(MensagemErrorComponent, {
              data: this.msg
            });
          }

        }
      );

    }
    this.dialogRef.close()
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  private gerarExcel(blob: Blob) {
    const file = new Blob([blob], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    const fileURL = URL.createObjectURL(file);

    window.open(fileURL, '_blank');
  }


  OnClickModeloExcel(){
    this.monitoradorServices.getMonitoradorExcelModel().subscribe(
      (resposta: Blob) => {
        this.gerarExcel(resposta);
        console.log('Exportado com sucesso');
        const dialog = this.dialog.open(MensagemSucessoComponent)
    });
  }

}
