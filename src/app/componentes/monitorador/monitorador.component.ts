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
import {NgxMaskDirective, NgxMaskPipe, provideNgxMask} from 'ngx-mask';
import {DeletarMonitoradorComponent} from "../deletar-monitorador/deletar-monitorador.component";
import {data} from "jquery";
import {ImportaExcelComponent} from "../importa-excel/importa-excel.component";
import {FormBuilder, FormGroup, FormsModule, ReactiveFormsModule} from "@angular/forms";



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
    FormsModule, ReactiveFormsModule,
    NgxMaskDirective,
    NgxMaskPipe
  ],
  providers:[
    MonitoradorService,
    provideNgxMask()
  ],
  templateUrl: './monitorador.component.html',
  styleUrl: './monitorador.component.css'
})

export class MonitoradorComponent implements OnInit {
  form = false;
  formMonitoradorFilter !: FormGroup;
  displayedColumns: string[] = ['id', 'nome', 'cpf', 'cnpj', 'actions'];
  dataSource: MatTableDataSource<MonitoradorModels>;
  private monitorador!: MonitoradorModels[];


  // Adicione propriedades para armazenar os valores dos campos de filtro

  constructor(private monitoradorService: MonitoradorService, public dialog: MatDialog,
              private router: Router,
              private formBuilder: FormBuilder,
  ) {
    this.dataSource = new MatTableDataSource<MonitoradorModels>([]);
  }

  ngOnInit() {
    this.carregarMonitorador();
    this.formMonitoradorFilter = this.formBuilder.group({
      cnpj: [null],
      cpf: [null],
      nome: [null],
      id: [null]
    });

    // Adicione um ouvinte para o evento de mudança no formulário
    this.formMonitoradorFilter.valueChanges.subscribe(() => {
      this.applyFilter();
    });

  }



  carregarMonitorador() {
    this.monitoradorService.getMonitorador().subscribe((monitorador) => {
      this.monitorador = monitorador;
      this.dataSource.data = this.monitorador;
    });

  }

  applyFilter() {
    const filterValue = this.formMonitoradorFilter.value;

    // Lógica para aplicar o filtro
    let filteredData = this.monitorador;

    if (filterValue.id != null) {
      filteredData = filteredData.filter(item => item.id && item.id.toString().includes(filterValue.id));
    }

    if (filterValue.nome != null) {
      filteredData = filteredData.filter(item => item.nome.toLowerCase().includes(filterValue.nome.toLowerCase()));
    }

    if (filterValue.cpf !=null) {
      filteredData = filteredData.filter(item => item.cpf && item.cpf.replace(/[./-]/g, '').includes(filterValue.cpf));
    }

    if (filterValue.cnpj != null) {
      filteredData = filteredData.filter(item => item.cnpj && item.cnpj.replace(/[./-]/g, '').includes(filterValue.cnpj));
    }


    this.dataSource.data = filteredData;
  }

  isvalidForm(): boolean {
    const filterValue = this.formMonitoradorFilter.value;

    return filterValue.id == null && filterValue.cpf == null && filterValue.cnpj == null && filterValue.nome== null;
  }

  //-------------------------------------------------------------------------------------------------------
  openDialog() {
    const dialogRef = this.dialog.open(CadastroMonitoradorComponent, {
      width: '700px',
      height: '750px',
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
      height: '350px',
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
    const filterValue = this.formMonitoradorFilter.value;
    if (filterValue.id == null && filterValue.nome == null && filterValue.cpf ==null && filterValue.cnpj ==null){
      this.monitoradorService.getMonitoradorPDF().subscribe(
        (resposta: Blob) => {
          this.gerarPDF(resposta);
          console.log('Exportado com sucesso', resposta);
        },
        (error) => {
          console.error('Erro ao exportar PDF: ', error);
        }
      );
    }else if (filterValue.id != null){
      this.monitoradorService.getMonitoradorPDFFilter("id",filterValue.id.toString()).subscribe(
        (resposta: Blob) => {
          this.gerarPDF(resposta);
          console.log('Exportado com sucesso',resposta);
        },
        (error) => {
          console.error('Erro ao exportar PDF:', error);
        }
      );

    }else if (filterValue.nome != null){
      this.monitoradorService.getMonitoradorPDFFilter("nome",filterValue.nome).subscribe(
        (resposta: Blob) => {
          this.gerarPDF(resposta);
          console.log('Exportado com sucesso',resposta);
        },
        (error) => {
          console.error('Erro ao exportar PDF:', error);
        }
      );
    }else if (filterValue.cpf !=null){
      this.monitoradorService.getMonitoradorPDFFilter("cpf", filterValue.cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4')).subscribe(
        (resposta: Blob) => {
          this.gerarPDF(resposta);
          console.log('Exportado com sucesso');
        },
        (error) => {
          console.error('Erro ao exportar PDF:', error);
        }
      );
    }else if (filterValue.cnpj !=null){
      this.monitoradorService.getMonitoradorPDFFilter("cnpj",filterValue.cnpj.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/, '$1.$2.$3/$4-$5')).subscribe(
        (resposta: Blob) => {
          this.gerarPDF(resposta);
          console.log('Exportado com sucesso',resposta);
        },
        (error) => {
          console.error('Erro ao exportar PDF:', error);
        }
      );
    }

  }


  ExportarExcel() {
    const filterValue = this.formMonitoradorFilter.value;

    if (filterValue.id == null && filterValue.nome == null && filterValue.cpf == null && filterValue.cnpj == null) {
      this.monitoradorService.getMonitoradorExcel().subscribe(
        (resposta: Blob) => {
          this.gerarExcel(resposta);
          console.log('Exportado com sucesso');
        },
        (error) => {
          console.error('Erro ao exportar excel:', error);
        }
      );
    } else if (filterValue.id != null) {
      this.monitoradorService.getMonitoradorExcelFilter("id", filterValue.id.toString()).subscribe(
        (resposta: Blob) => {
          this.gerarExcel(resposta);
          console.log('Exportado com sucesso');
        },
        (error) => {
          console.error('Erro ao exportar excel:', error);
        }
      );

    } else if (filterValue.nome != null) {
      this.monitoradorService.getMonitoradorExcelFilter("nome", filterValue.nome).subscribe(
        (resposta: Blob) => {
          this.gerarExcel(resposta);
          console.log('Exportado com sucesso');
        },
        (error) => {
          console.error('Erro ao exportar excel:', error);
        }
      );
    } else if (filterValue.cpf != null) {
      this.monitoradorService.getMonitoradorExcelFilter("cpf", filterValue.cpf.replace(/(\d{3})(\d{3})(\d{3})(\d{2})/, '$1.$2.$3-$4')).subscribe(
        (resposta: Blob) => {
          this.gerarExcel(resposta);
          console.log('Exportado com sucesso');
        },
        (error) => {
          console.error('Erro ao exportar excel:', error);
        }
      );
    } else if (filterValue.cnpj != null) {
      this.monitoradorService.getMonitoradorExcelFilter("cnpj", filterValue.cnpj.replace(/(\d{2})(\d{3})(\d{3})(\d{4})(\d{2})/, '$1.$2.$3/$4-$5')).subscribe(
        (resposta: Blob) => {
          this.gerarExcel(resposta);
          console.log('Exportado com sucesso');
        },
        (error) => {
          console.error('Erro ao exportar excel:', error);
        }
      );
    }

  }
}

