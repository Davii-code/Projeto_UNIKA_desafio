import {Component, Inject, OnInit} from '@angular/core';
import {
  MAT_DIALOG_DATA,
  MatDialog,
  MatDialogActions,
  MatDialogClose,
  MatDialogContent,
  MatDialogRef
} from "@angular/material/dialog";
import {MonitoradorComponent} from "../monitorador/monitorador.component";
import {MatButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";
import {MonitoradorModels} from "../../Models/monitorador/monitorador.models";
import {MonitoradorService} from "../../services/monitorador.service";
import {HttpClientModule} from "@angular/common/http";
import {data} from "jquery";
import {Endereco} from "../../Models/monitorador/endereco.models";

@Component({
  selector: 'app-cadastro-monitorador',
  standalone: true,
  imports: [
    MatDialogActions,
    MatButton,
    MatDialogClose,
    MatDialogContent,
    MatIcon,
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    MonitoradorService,
  ],
  templateUrl: './cadastro-monitorador.component.html',
  styleUrl: './cadastro-monitorador.component.css'
})
export class CadastroMonitoradorComponent implements OnInit {
  moni!: MonitoradorModels;
  formMonitorador !: FormGroup;
  formEndereco !: FormGroup;
  mostrarCamposEndereco: boolean = false;
  mostrarCamposMoni: boolean = true;
  validacao: boolean = false;
  validacaoJ: boolean = false;
  validacaoEnd: boolean = false;


  constructor(
    public dialogRef: MatDialogRef<MonitoradorComponent>,
    private formBuilder: FormBuilder,
    private monitoradorService: MonitoradorService
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.formMonitorador = this.formBuilder.group({
      tipo: ['Fisica', [Validators.required]],
      nome: [, [Validators.required]],
      cpf: [, [Validators.required, Validators.pattern(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/)]],
      cnpj: [, [Validators.required, Validators.pattern(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/)]],
      email: [, [Validators.required, Validators.email]],
      rg: [, [Validators.required, Validators.pattern(/^\d{2}\.\d{3}\.\d{3}-\d{1}$/)]],
      inscricao: [, [Validators.required, Validators.pattern(/^\d{2}\.\d{3}\.\d{3}-\d{1}$/)]],
      ativo: [],
      data_nascimento: [, [Validators.required]],

    })
    this.formEndereco = this.formBuilder.group({
      endereco: [, [Validators.required]],
      numero: [, [Validators.required]],
      cep: [, [Validators.required, Validators.pattern(/^\d{8}-\d{1}$/)]],
      telefone: [, [Validators.required]],
      bairro: [, [Validators.required]],
      cidade: [, [Validators.required]],
      estado: [, [Validators.required]],
      principal: [true, [Validators.required]],
    })
  }

  Onchange(event: any){
      this.validacaoJ = false
      this.validacao = false;

  }

  //-----gets formGroup-----//
  get nome() {
    return this.formMonitorador.get('nome')!;
  }

  get cpf(){
    return this.formMonitorador.get('cpf')!;
  }

  get cnpj(){
    return this.formMonitorador.get('cnpj')!;
  }

  get email(){
    return this.formMonitorador.get('email')!;
  }

  get rg(){
    return this.formMonitorador.get('rg')!;
  }

  get inscricao(){
    return this.formMonitorador.get('inscricao')!;
  }

  get data_nascimento(){
    return this.formMonitorador.get('data_nascimento')!;
  }

  get ativo(){
    return this.formMonitorador.get('ativo')!;
  }


  //-----Endereco---//

  get endereco() {
    return this.formEndereco.get('endereco')!;
  }

  get numero() {
    return this.formEndereco.get('numero')!;
  }

  get cep() {
    return this.formEndereco.get('cep')!;
  }

  get telefone() {
    return this.formEndereco.get('telefone')!;
  }

  get bairro() {
    return this.formEndereco.get('bairro')!;
  }

  get cidade() {
    return this.formEndereco.get('cidade')!;
  }

  get estado() {
    return this.formEndereco.get('estado')!;
  }

  get principal() {
    return this.formEndereco.get('principal')!;
  }

//-----gets formGroup-----//
  CadastrarMonitorador(dado: MonitoradorModels) {
    if (this.formMonitorador.get('tipo')?.value === 'Fisica'){
      if (!this.nome.invalid && !this.cpf.invalid && !this.rg.invalid && !this.email.invalid && !this.ativo.invalid && !this.data_nascimento.invalid) {
        this.mostrarCamposMoni = !this.mostrarCamposMoni;
        this.mostrarCamposEndereco = true;
        this.monitoradorService.postMonitorador(dado).subscribe(resposta => {

        })
      } else {
        this.mostrarCamposMoni = this.mostrarCamposMoni;
        this.mostrarCamposEndereco = false;
        this.validacao = true;

      }

    }else {
      if (!this.nome.invalid && !this.cnpj.invalid && !this.inscricao.invalid && !this.email.invalid && !this.ativo.invalid) {
        this.mostrarCamposMoni = !this.mostrarCamposMoni;
        this.mostrarCamposEndereco = true;
        this.monitoradorService.postMonitorador(dado).subscribe(resposta => {

        })
      } else {
        this.mostrarCamposMoni = this.mostrarCamposMoni;
        this.mostrarCamposEndereco = false;
        this.validacaoJ = true;

      }
    }


  }

  CadastrarEnd(dadoEnd: Endereco) {
    if (!this.endereco.invalid && !this.numero.invalid && !this.cep.invalid && !this.telefone.invalid && !this.bairro.invalid && !this.cidade.invalid && !this.estado.invalid && !this.principal.invalid){
      if (dadoEnd != null) {
        this.monitoradorService.postMonitoradorEndereco(dadoEnd).subscribe(resposta => {
          this.dialogRef.close()
        })
      }
    }else {
      this.validacaoEnd = true;
    }




  }
}
