import {Component, Inject, OnInit} from '@angular/core';
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule, Validators} from "@angular/forms";
import {MatIcon} from "@angular/material/icon";
import {MatButton, MatIconButton} from "@angular/material/button";
import {NgIf} from "@angular/common";
import {MonitoradorService} from "../../services/monitorador.service";
import {NgxMaskDirective, NgxMaskPipe, provideNgxMask} from "ngx-mask";
import {HttpClientModule} from "@angular/common/http";
import {MAT_DIALOG_DATA, MatDialog, MatDialogRef} from "@angular/material/dialog";
import {MonitoradorComponent} from "../monitorador/monitorador.component";
import {Endereco} from "../../Models/monitorador/endereco.models";
import {MensagemSucessoComponent} from "../mensagem-sucesso/mensagem-sucesso.component";
import {MensagemErrorComponent} from "../mensagem-error/mensagem-error.component";

@Component({
  selector: 'app-cadastro-endereco',
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
  templateUrl: './cadastro-endereco.component.html',
  styleUrl: './cadastro-endereco.component.css'
})
export class CadastroEnderecoComponent implements OnInit{
  formEndereco !: FormGroup;

  validacaoEnd: boolean = false;
  validacaoB: boolean = false;
  msg: string = '' ;
  end!: Endereco;
  constructor(
    public dialogRef: MatDialogRef<MonitoradorComponent>,
    private formBuilder: FormBuilder,
    private monitoradorService: MonitoradorService,
    @Inject(MAT_DIALOG_DATA) public data: any,
    public dialog: MatDialog
  ) {
  }
  ngOnInit() {
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
    console.log(this.data)
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

  CadastrarEnd(dadoEnd: Endereco) {
    if (!this.endereco?.invalid && !this.numero?.invalid && !this.cep?.invalid && !this.telefone?.invalid && !this.bairro?.invalid && !this.cidade?.invalid && !this.estado?.invalid && !this.principal?.invalid) {
      this.monitoradorService.postMonitoradorEnderecoPorMonitorador(dadoEnd, this.data).subscribe(resposta => {
        const dialog = this.dialog.open(MensagemSucessoComponent)

        dialog.afterClosed().subscribe(() => {
          this.dialogRef.close();
        });

      }, error => {
        if (error.status == 409){
          const dialog = this.dialog.open(MensagemErrorComponent, {
            data: "Dados Já Cadastrados: Validar campo Endereço"
          });
        }else {
          this.msg = error.message;
          const dialog = this.dialog.open(MensagemErrorComponent, {
            data: this.msg
          });
        }
      });
    } else {
      this.validacaoEnd = true;
    }
  }



}
