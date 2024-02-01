import { Component, Inject, OnInit } from '@angular/core';
import { MatIcon } from "@angular/material/icon";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import { MonitoradorComponent } from "../monitorador/monitorador.component";
import { MonitoradorService } from "../../services/monitorador.service";
import {FormBuilder, FormGroup, ReactiveFormsModule, Validators} from "@angular/forms";
import { MonitoradorModels } from "../../Models/monitorador/monitorador.models";
import {HttpClientModule, HttpHeaders} from "@angular/common/http";
import { ActivatedRoute, Router } from "@angular/router";
import { data } from "jquery";
import {CommonModule} from "@angular/common";
import {update} from "@angular-devkit/build-angular/src/tools/esbuild/angular/compilation/parallel-worker";
import {NgxMaskDirective, NgxMaskPipe, provideNgxMask} from "ngx-mask";
import {MensagemSucessoComponent} from "../mensagem-sucesso/mensagem-sucesso.component";
import {MatTab, MatTabGroup} from "@angular/material/tabs";
import {Endereco} from "../../Models/monitorador/endereco.models";
import {EditarEnderecoComponent} from "../editar-endereco/editar-endereco.component";
import {DeletarEnderecoComponent} from "../deletar-endereco/deletar-endereco.component";
import {CadastroEnderecoComponent} from "../cadastro-endereco/cadastro-endereco.component";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTable,
  MatTableDataSource
} from "@angular/material/table";
import {MatButton} from "@angular/material/button";
import {MensagemErrorComponent} from "../mensagem-error/mensagem-error.component";

@Component({
  selector: 'app-editar-monitorador',
  standalone: true,
  imports: [
    MatIcon,
    ReactiveFormsModule, HttpClientModule,
    CommonModule, NgxMaskDirective, NgxMaskPipe, MatTabGroup, MatTab
    , MatButton, MatCell, MatCellDef, MatColumnDef, MatHeaderCell, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef, MatTable, MatHeaderCellDef
  ],
  providers:[
    MonitoradorService,
    provideNgxMask()
  ],
  templateUrl: './editar-monitorador.component.html',
  styleUrls: ['./editar-monitorador.component.css'] // Corrija 'styleUrl' para 'styleUrls'
})
export class EditarMonitoradorComponent implements OnInit {
  moni!: MonitoradorModels;
  formMonitorador !: FormGroup;
  validacao: boolean = false;
  validacaoJ: boolean = false;
  validacaoB: boolean = false;
  msg: string = '' ;
  MostraEnd = false;
  displayedColumnsEnd: string[] = ['id', 'cep','endereco', 'cidade', 'estado', 'actions'];
  dataSourceEnd: MatTableDataSource<Endereco>;
  protected enderecos!: Endereco[];


  constructor(
    public dialogRef: MatDialogRef<MonitoradorComponent>,
    private monitoradorService: MonitoradorService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private formBuilder: FormBuilder,
    public dialog: MatDialog

  ) {

    this.dataSourceEnd = new MatTableDataSource<Endereco>([])

  }
  onNoClick(): void {
    this.dialogRef.close();
  }
  ngOnInit() {
    this.carregarEndereco()
    this.moni = this.data.monitorador;
    // Inicialize o formulário com os valores desejados
    this.formMonitorador = this.formBuilder.group({
      id: [this.moni.id, [Validators.required]],
      nome: [this.moni.nome, [Validators.required]],
      cpf: [this.moni.cpf, [Validators.required]],
      cnpj: [this.moni.cnpj, [Validators.required, ]],
      email: [this.moni.email, [Validators.required]],
      rg: [this.moni.rg, [Validators.required,Validators.pattern(/^[\d.-]+$/)]],
      inscricao: [this.moni.inscricao, [Validators.required,Validators.pattern(/^[\d.-]+$/)]],
      tipo: [this.moni.tipo, [Validators.required]],
      ativo: [this.moni.ativo],
      data_nascimento: [this.moni.data_nascimento, [Validators.required]],



    });

  }

  Onchange(event: any) {
    this.validacaoJ = false
    this.validacao = false;

  }

  //-----gets formGroup-----//
  get nome() {
    return this.formMonitorador.get('nome')!;
  }

  get cpf() {
    return this.formMonitorador.get('cpf')!;
  }

  get cnpj() {
    return this.formMonitorador.get('cnpj')!;
  }

  get email() {
    return this.formMonitorador.get('email')!;
  }

  get rg() {
    return this.formMonitorador.get('rg')!;
  }

  get inscricao() {
    return this.formMonitorador.get('inscricao')!;
  }

  get data_nascimento() {
    return this.formMonitorador.get('data_nascimento')!;
  }

  get ativo() {
    return this.formMonitorador.get('ativo')!;
  }
  get tipo() {
    return this.formMonitorador.get('tipo')!;
  }



  EditarMonitorador(dado: MonitoradorModels) {
    if (this.formMonitorador.get('tipo')?.value === 'Fisica'){
      if (!this.nome.invalid && !this.cpf.invalid && !this.rg.invalid && !this.email.invalid && !this.ativo.invalid && !this.data_nascimento.invalid){
        this.monitoradorService.putMonitorador(dado.id.toString(),dado).subscribe(resposta =>{
            const dialog = this.dialog.open(MensagemSucessoComponent)
            dialog.afterClosed().subscribe(() => {
              this.dialogRef.close();
            });

        },error => {
            if (error.status != 500){
              this.msg = error.errors;
              const dialog = this.dialog.open(MensagemErrorComponent, {
                data: this.msg
              });
            }else {
              this.msg = "CPF/CNPJ Invalido";
              const dialog = this.dialog.open(MensagemErrorComponent, {
                data: this.msg
              });
            }
        }
        );
      }else{
        this.validacao = true;
      }
    }else if (this.formMonitorador.get('tipo')?.value == 'Juridica') {
      console.log('Tipo Jurídica selecionado');
      if (!this.nome.invalid && !this.cnpj.invalid && !this.inscricao.invalid && !this.email.invalid && !this.ativo.invalid){
        this.monitoradorService.putMonitorador(dado.id.toString(),dado).subscribe(resposta =>{
          const dialog = this.dialog.open(MensagemSucessoComponent)
          dialog.afterClosed().subscribe(() => {
            this.dialogRef.close();
          });

          },error => {
          if (error.status != 500){
            this.msg = error.errors;
            const dialog = this.dialog.open(MensagemErrorComponent, {
              data: this.msg
            });
          }else {
            this.msg = "CPF/CNPJ Invalido";
            const dialog = this.dialog.open(MensagemErrorComponent, {
              data: this.msg
            });
          }
          }
        );
      }else {
        this.validacaoJ = true;
      }

    }
  }


  //Endereço
  //-------------------------------------------------------------------------------------------------------
  carregarEndereco() {
    this.monitoradorService.getEndereco(this.data.monitorador.id).subscribe((endereco) => {
      this.enderecos = endereco;
      this.dataSourceEnd.data = this.enderecos;
    });
    if(!this.MostraEnd){
      this.MostraEnd = true;
    }else {
      this.MostraEnd = false;
    }

  }

  openDialogDetalheEnd(endereco: Endereco) {
    const dialogRef = this.dialog.open(EditarEnderecoComponent, {
      width: '700px',
      height: '850px',
      data : {endereco : endereco}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.carregarEndereco()
    });
  }

  DeletarEndereco(endereco : Endereco){
    const dialogRef = this.dialog.open(DeletarEnderecoComponent,{
      width:'750px',
      height: '300px',
      data: endereco.id
    });

    dialogRef.afterClosed().subscribe(result=>{

      this.carregarEndereco();
    });
  }

  openDialog() {
    const dialogRef = this.dialog.open(CadastroEnderecoComponent, {
      width: '700px',
      height: '850px',
      data : this.data.monitorador.id
    });

    dialogRef.afterClosed().subscribe(result => {
      this.carregarEndereco();

    });
  }

  private gerarPDF(blob: Blob) {
    const file = new Blob([blob], { type: 'application/pdf' });
    const fileURL = URL.createObjectURL(file);

    window.open(fileURL, '_blank');
  }

  private gerarExcel(blob: Blob) {
    const file = new Blob([blob], { type: 'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet' });
    const fileURL = URL.createObjectURL(file);

    window.open(fileURL, '_blank');
  }

  ExportarPDF() {
    this.monitoradorService.getEnderecoPDF(this.data.monitorador.id).subscribe(
      (resposta: Blob) => {
        this.gerarPDF(resposta);
        console.log('Exportado com sucesso', resposta);
      },
      (error) => {
        this.msg = "Exportação invalida, sem dados";
        const dialog = this.dialog.open(MensagemErrorComponent, {
          data: this.msg
        });
      }
    );
  }

  ExportarExcel() {
    this.monitoradorService.getEnderecoExcel(this.data.monitorador.id).subscribe(
      (resposta: Blob) => {
        this.gerarExcel(resposta);
        console.log('Exportado com sucesso', resposta);
      },
      (error) => {
        this.msg = error;
        const dialog = this.dialog.open(MensagemErrorComponent, {
          data: this.msg
        });
      }
    );
  }

}
