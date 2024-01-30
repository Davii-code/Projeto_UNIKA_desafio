import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {Endereco} from "../../Models/monitorador/endereco.models";
import {MAT_DIALOG_DATA, MatDialogRef} from "@angular/material/dialog";
import {MonitoradorComponent} from "../monitorador/monitorador.component";
import {MonitoradorService} from "../../services/monitorador.service";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatIconButton} from "@angular/material/button";
import {NgIf} from "@angular/common";
import {HttpClientModule} from "@angular/common/http";
import {NgxMaskDirective, NgxMaskPipe, provideNgxMask} from "ngx-mask";
import {MonitoradorModels} from "../../Models/monitorador/monitorador.models";

@Component({
  selector: 'app-editar-endereco',
  standalone: true,
  imports: [
    FormsModule,
    MatIcon,
    MatIconButton,
    NgIf,
    ReactiveFormsModule,
    HttpClientModule,
    NgxMaskDirective,
    NgxMaskPipe,
    MatButton,
  ],
  providers: [
    MonitoradorService,
    provideNgxMask()
  ],
  templateUrl: './editar-endereco.component.html',
  styleUrl: './editar-endereco.component.css'
})
export class EditarEnderecoComponent implements OnInit{
  formEndereco !: FormGroup;

  validacaoEnd: boolean = false;
  validacaoB: boolean = false;
  msg: string = '' ;
  end!: Endereco;
  constructor(
    public dialogRef: MatDialogRef<MonitoradorComponent>,
    private formBuilder: FormBuilder,
    private monitoradorService: MonitoradorService,
    @Inject(MAT_DIALOG_DATA) public data: any
  ) {
  }
  ngOnInit() {
    this.formEndereco = this.formBuilder.group({
      endereco: [this.data.endereco.endereco, [Validators.required]],
      numero: [this.data.endereco.numero, [Validators.required]],
      cep: [this.data.endereco.cep, [Validators.required]],
      telefone: [this.data.endereco.telefone, [Validators.required]],
      bairro: [this.data.endereco.bairro, [Validators.required]],
      cidade: [this.data.endereco.cidade, [Validators.required]],
      estado: [this.data.endereco.estado, [Validators.required]],
      principal: [this.data.endereco.principal, [Validators.required]],
    });
    console.log(this.data.endereco.id)
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

  onNoClick(): void {
    this.dialogRef.close();
  }

  EditarEndereco(dadoEnd: Endereco) {
    if (!this.endereco?.invalid && !this.numero?.invalid && !this.cep?.invalid && !this.telefone?.invalid && !this.bairro?.invalid && !this.cidade?.invalid && !this.estado?.invalid && !this.principal?.invalid) {
      this.monitoradorService.putEndereco(this.data.endereco.id,dadoEnd).subscribe(resposta => {
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
