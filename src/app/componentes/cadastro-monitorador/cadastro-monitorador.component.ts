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

  toggleCamposEndereco(dado: MonitoradorModels) {
    this.monitoradorService.postMonitorador(dado).subscribe(resposta => {

    })
  }

  toggleCamposMonitorador() {
    this.mostrarCamposMoni = !this.mostrarCamposMoni;
    this.mostrarCamposEndereco = true;

  }
  CadastrarEnd( dadoEnd: Endereco) {
    if (dadoEnd != null) {
      this.monitoradorService.postMonitoradorEndereco(dadoEnd).subscribe(resposta=>{
        this.dialogRef.close()
      })
    }



    console.log(dadoEnd)
  }
}
