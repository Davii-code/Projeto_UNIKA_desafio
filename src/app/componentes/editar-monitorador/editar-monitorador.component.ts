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
import {NgxMaskDirective} from "ngx-mask";

@Component({
  selector: 'app-editar-monitorador',
  standalone: true,
  imports: [
    MatIcon,
    ReactiveFormsModule, HttpClientModule,
    CommonModule, NgxMaskDirective
  ],
  providers:[
    MonitoradorService,
  ],
  templateUrl: './editar-monitorador.component.html',
  styleUrls: ['./editar-monitorador.component.css'] // Corrija 'styleUrl' para 'styleUrls'
})
export class EditarMonitoradorComponent implements OnInit {
  moni!: MonitoradorModels;
  formMonitorador !: FormGroup;


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
      cpf: [this.moni.cpf, [Validators.required, Validators.pattern(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/)]],
      cnpj: [this.moni.cnpj, [Validators.required, Validators.pattern(/^\d{3}\.\d{3}\.\d{3}-\d{2}$/)]],
      email: [this.moni.email, [Validators.required, Validators.email]],
      rg: [this.moni.rg, [Validators.required, Validators.pattern(/^\d{2}\.\d{3}\.\d{3}-\d{1}$/)]],
      inscricao: [this.moni.inscricao, [Validators.required, Validators.pattern(/^\d{2}\.\d{3}\.\d{3}-\d{1}$/)]],
      tipo: [this.moni.tipo, [Validators.required]],
      ativo: [this.moni.ativo],
      data_nascimento: [this.moni.data_nascimento, [Validators.required]],



    });

  }

  EditarMonitorador(dado: MonitoradorModels) {
      this.monitoradorService.putMonitorador(dado.id.toString(),dado).subscribe(resposta =>{
       this.dialogRef.close()

      })
  }


}
