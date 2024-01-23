import {Component, NgModule, OnInit} from '@angular/core';
import {MatToolbar} from "@angular/material/toolbar";
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatIcon} from "@angular/material/icon";
import {NgFor, NgIf} from "@angular/common";
import {
  MatCell,
  MatCellDef,
  MatColumnDef,
  MatHeaderCell,
  MatHeaderCellDef, MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable, MatTableDataSource,
  MatTableModule
} from "@angular/material/table";
import {MatMenu, MatMenuItem, MatMenuTrigger} from "@angular/material/menu";
import {MatDrawer, MatDrawerContainer} from "@angular/material/sidenav";
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {MonitoradorService} from "../../services/monitorador.service";
import {HttpClientModule} from "@angular/common/http";
import {MatDialog} from "@angular/material/dialog";
import {EnderecoComponent} from "../endereco/endereco.component";
import {CadastroMonitoradorComponent} from "../cadastro-monitorador/cadastro-monitorador.component";
import {EditarMonitoradorComponent} from "../editar-monitorador/editar-monitorador.component";
import {MonitoradorModels} from "../../Models/monitorador/monitorador.models";
import { NgxMaskDirective, provideNgxMask } from 'ngx-mask';
import {DeletarMonitoradorComponent} from "../deletar-monitorador/deletar-monitorador.component";
import {data} from "jquery";
import {ImportaExcelComponent} from "../importa-excel/importa-excel.component";
import {FormsModule} from "@angular/forms";



export interface Monitorador extends Array<Monitorador>{}


@Component({
  selector: 'app-monitorador',
  standalone: true,
  imports: [
    MatToolbar,
    MatIconButton,
    MatIcon,
    MatTable,
    MatButton,
    MatMenuTrigger,
    MatMenu,
    MatMenuItem,
    MatDrawerContainer,
    MatDrawer, NgIf, RouterLink, RouterLinkActive, MatColumnDef, MatHeaderCell, MatCell, MatCellDef, MatHeaderCellDef, MatHeaderRowDef, MatRowDef,
    MatHeaderRow, MatRow, HttpClientModule,
    NgxMaskDirective, FormsModule,
  ],
  providers:[
    MonitoradorService,
    provideNgxMask({ /* opções de cfg */ })
  ],
  templateUrl: './monitorador.component.html',
  styleUrl: './monitorador.component.css'
})

export class MonitoradorComponent implements OnInit {
  form = false;

  displayedColumns: string[] = ['id', 'nome', 'cpf', 'cnpj', 'actions'];
  dataSource: MatTableDataSource<MonitoradorModels>;

  private monitorador!: MonitoradorModels[];
  private monitoradorFiltrado!: MonitoradorModels[];


  // Adicione propriedades para armazenar os valores dos campos de filtro
  codigoFiltro: string = '';
  nome: string = '';
  cpf: string = '';
  cnpj: string = '';
  constructor(private monitoradorService: MonitoradorService, public dialog: MatDialog,
              private router: Router) {
    this.dataSource = new MatTableDataSource<MonitoradorModels>([]);
  }

  ngOnInit() {
      this.carregarMonitorador()

  }

  carregarMonitorador() {
    this.monitoradorService.getMonitorador().subscribe((monitorador) => {
      this.monitorador = monitorador;
      this.dataSource.data = this.monitorador;
    });

  }


  filtro() {

    if (this.codigoFiltro == null && this.nome == null && this.cnpj == null && this.cpf == null) {
      this.carregarMonitorador();
    }
    if (this.nome != null) {
      this.monitoradorService.getFilterMonitoradorNome(this.nome).subscribe((monitoradorName) => {
        this.monitorador= monitoradorName;
        this.dataSource.data = this.monitorador
      });
    }
    if (this.cnpj!= null){
      this.monitoradorService.getFilterMonitoradorCnpj(this.cnpj).subscribe((monitoradorCnpj)=>{
        this.monitorador = monitoradorCnpj;
        this.dataSource.data = this.monitorador;

      });
    }
    if (this.cpf != null){
      this.monitoradorService.MonitoradorCPF(this.cpf).subscribe(monitoradorCpf=>{
        this.monitorador = monitoradorCpf;
        this.dataSource.data = this.monitorador;
        console.log("Requisição feita")
      });
    }

  }
  openDialog() {
    const dialogRef = this.dialog.open(CadastroMonitoradorComponent, {
      width: '700px',
      height: '650px',
    });

    dialogRef.afterClosed().subscribe(result => {
      // Atualize a tela aqui
      this.carregarMonitorador();
    });
  }

  openDialogDetalhe(monitorador1: MonitoradorModels) {
    const dialogRef = this.dialog.open(EditarMonitoradorComponent, {
      width: '700px',
      height: '650px',
      data: { monitorador: monitorador1 }
    });

    dialogRef.afterClosed().subscribe(result => {
      this.carregarMonitorador();
    });

  }

  DeletarMonitorador(monitorador1 : MonitoradorModels){
      const dialogRef = this.dialog.open(DeletarMonitoradorComponent,{
        width:'750px',
        height: '300px',
        data: monitorador1.id
      });

    dialogRef.afterClosed().subscribe(result=>{
      this.carregarMonitorador()
    });
  }

  ImportaExcel(){
    const dialogRef = this.dialog.open(ImportaExcelComponent,{
      width:'650px',
      height: '200px',
    });

    dialogRef.afterClosed().subscribe(result=>{
      this.carregarMonitorador()
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
    this.monitoradorService.getMonitoradorPDF().subscribe(
      (resposta: Blob) => {
        this.gerarPDF(resposta);
        console.log('Exportado com sucesso');
      },
      (error) => {
        console.error('Erro ao exportar PDF:', error);
      }
    );
  }


  ExportarExcel() {
    this.monitoradorService.getMonitoradorExcel().subscribe(
      (resposta: Blob) => {
        this.gerarExcel(resposta);
        console.log('Exportado com sucesso');
      },
      (error) => {
        console.error('Erro ao exportar Excel:', error);
      }
    );
  }

}

