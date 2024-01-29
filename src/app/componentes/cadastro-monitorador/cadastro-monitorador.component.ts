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
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {NgIf} from "@angular/common";
import {MonitoradorModels} from "../../Models/monitorador/monitorador.models";
import {MonitoradorService} from "../../services/monitorador.service";
import {HttpClientModule} from "@angular/common/http";
import {data} from "jquery";
import {Endereco} from "../../Models/monitorador/endereco.models";
import {NgxMaskDirective, NgxMaskPipe, provideNgxMask} from "ngx-mask";
import { IConfig } from 'ngx-mask'


@Component({
  imports: [
    MatDialogActions,
    MatButton,
    MatDialogClose,
    MatDialogContent,
    MatIcon,
    FormsModule,
    NgIf,
    ReactiveFormsModule,
    HttpClientModule,
    NgxMaskDirective,
    NgxMaskPipe,
    MatIconButton
  ],
  providers: [
    MonitoradorService,
    provideNgxMask()
  ],
  selector: 'app-cadastro-monitorador',
  standalone: true,
  styleUrl: './cadastro-monitorador.component.css',
  templateUrl: './cadastro-monitorador.component.html'
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
  validacaoB: boolean = false;
  msg: string = '' ;
  end!: Endereco;


  constructor(
    public dialogRef: MatDialogRef<MonitoradorComponent>,
    private formBuilder: FormBuilder,
    private monitoradorService: MonitoradorService,
  ) {
  }

  onNoClick(): void {
    this.dialogRef.close();
  }

  ngOnInit() {
    this.formMonitorador = this.formBuilder.group({
      tipo: ['Fisica', [Validators.required]],
      nome: [, [Validators.required]],
      cpf: [, [Validators.required]], // Permitir números, pontos e traços, com tamanho 11
      cnpj: [, [Validators.required]], // Permitir números, pontos e traços, com tamanho 14
      email: [, [Validators.required, Validators.email]],
      rg: [, [Validators.required, Validators.pattern(/^[\d.-]+$/)]], // Permitir números, pontos e traços
      inscricao: [, [Validators.required, Validators.pattern(/^[\d.-]+$/)]], // Permitir números, pontos e traços
      ativo: [],
      data_nascimento: [, [Validators.required, Validators.pattern(/^[\d.-]+$/)]],
    });

    this.formEndereco = this.formBuilder.group({
      endereco: [, [Validators.required]],
      numero: [, [Validators.required]],
      cep: [, [Validators.required, Validators.pattern(/^[\d.-]{11}$/)]], // Permitir números, pontos e traços, com tamanho 8
      telefone: [, [Validators.required]], // Permitir números, pontos e traços
      bairro: [, [Validators.required]],
      cidade: [, [Validators.required]],
      estado: [, [Validators.required]],
      principal: [true, [Validators.required]],
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
    console.log('Formulário:', this.formMonitorador.value);

    if (this.formMonitorador.get('tipo')?.value === 'Fisica') {
      console.log('Tipo Física selecionado');
      if (!this.nome.invalid && !this.cpf.invalid && !this.rg.invalid && !this.email.invalid && !this.ativo.invalid && !this.data_nascimento.invalid) {
        this.monitoradorService.postMonitorador(dado).subscribe(resposta => {
            this.mostrarCamposMoni = !this.mostrarCamposMoni;
            this.mostrarCamposEndereco = true;
          }, error => {
            this.validacaoB = true;
            this.msg = error.message;
          }
        );
      } else {
        this.mostrarCamposMoni = true;
        this.mostrarCamposEndereco = false;
        this.validacao = true;

      }

    } else {
      if (this.formMonitorador.get('tipo')?.value == 'Juridica') {
        console.log('Tipo Jurídica selecionado');
        if (!this.nome.invalid && !this.cnpj.invalid && !this.inscricao.invalid && !this.email.invalid && !this.ativo.invalid) {
          this.monitoradorService.postMonitorador(dado).subscribe(resposta => {
            this.mostrarCamposMoni = !this.mostrarCamposMoni;
            this.mostrarCamposEndereco = true;
          }, errors => {
            this.validacaoB = true;
            this.msg = errors.message;
          });
        } else {
          this.mostrarCamposMoni = true;
          this.mostrarCamposEndereco = false;
          this.validacaoJ = true;

        }
      }

    }

  }
  OnclickBucasCep(cep: string) {
    this.monitoradorService.getEndCep(cep).subscribe(
      (resposta: Endereco) => {
        this.end = resposta;
        this.formEndereco = this.formBuilder.group({
          endereco: [this.end.endereco ],
          numero: [this.end.numero ],
          bairro: [this.end.bairro ],
          cidade: [this.end.cidade ],
          estado: [this.end.estado ],
          telefone: [this.end.telefone],
          principal: [true],
          cep: [this.end.cep]
        });
      },
      (error) => {
        console.error("Erro na busca de CEP", error);
      }
    );
  }



  CadastrarEnd(dadoEnd: Endereco) {
    if (!this.endereco?.invalid && !this.numero?.invalid && !this.cep?.invalid && !this.telefone?.invalid && !this.bairro?.invalid && !this.cidade?.invalid && !this.estado?.invalid && !this.principal?.invalid) {
      this.monitoradorService.postMonitoradorEndereco(dadoEnd).subscribe(resposta => {
        this.dialogRef.close()
      }, error => {
        this.validacaoB = true;
        this.msg = error.message;
      });
    } else {
      this.validacaoEnd = true;
    }
  }


}
