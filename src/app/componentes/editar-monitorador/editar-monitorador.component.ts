import { Component, Inject, OnInit } from '@angular/core';
import { MatIcon } from "@angular/material/icon";
import { MAT_DIALOG_DATA, MatDialogRef } from "@angular/material/dialog";
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

@Component({
  selector: 'app-editar-monitorador',
  standalone: true,
  imports: [
    MatIcon,
    ReactiveFormsModule, HttpClientModule,
    CommonModule, NgxMaskDirective,NgxMaskPipe
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


  constructor(
    public dialogRef: MatDialogRef<MonitoradorComponent>,
    private monitoradorService: MonitoradorService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    private formBuilder: FormBuilder,


  ) {}
  onNoClick(): void {
    this.dialogRef.close();
  }
  ngOnInit() {
    this.moni = this.data.monitorador;
    // Inicialize o formulário com os valores desejados
    this.formMonitorador = this.formBuilder.group({
      id: [this.moni.id, [Validators.required]],
      nome: [this.moni.nome, [Validators.required]],
      cpf: [this.moni.cpf, [Validators.required,Validators.pattern(/^[\d.-]{14}$/)]],
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
          this.dialogRef.close()

        },error => {
            if (error.status === 500){
              this.validacaoB = true;
              this.msg = "CPF/CNPJ Invalido";
            }else{
              this.validacaoB = true;
              this.msg = error.message;
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
            this.dialogRef.close()

          },error => {
          if (error.status === 500){
            this.validacaoB = true;
            this.msg = "CPF/CNPJ Invalido";
          }else{
            this.validacaoB = true;
            this.msg = error.message;
          }

          }
        );
      }else {
        this.validacaoJ = true;
      }

    }
  }


}
