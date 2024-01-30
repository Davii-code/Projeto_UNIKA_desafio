import {Component, OnInit} from '@angular/core';
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatDrawer, MatDrawerContainer} from "@angular/material/sidenav";
import {MatIcon} from "@angular/material/icon";
import {MatToolbar} from "@angular/material/toolbar";
import {NgForOf, NgIf, NgStyle} from "@angular/common";
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {MonitoradorService} from "../../services/monitorador.service";
import {MatDialog} from "@angular/material/dialog";
import {FormBuilder, FormGroup, ReactiveFormsModule} from "@angular/forms";
import {
  MatCell, MatCellDef,
  MatColumnDef,
  MatHeaderCell, MatHeaderCellDef,
  MatHeaderRow, MatHeaderRowDef, MatRow, MatRowDef,
  MatTable,
  MatTableDataSource
} from "@angular/material/table";
import {MonitoradorModels} from "../../Models/monitorador/monitorador.models";
import {NgxMaskDirective, NgxMaskPipe, provideNgxMask} from "ngx-mask";
import {HttpClientModule} from "@angular/common/http";
import {MatList, MatListItem} from "@angular/material/list";
import {Endereco} from "../../Models/monitorador/endereco.models";
import {MatAccordion, MatExpansionPanel, MatExpansionPanelDescription, MatExpansionPanelTitle, MatExpansionModule} from "@angular/material/expansion";
import {style} from "@angular/animations";
import {CadastroMonitoradorComponent} from "../cadastro-monitorador/cadastro-monitorador.component";
import {CadastroEnderecoComponent} from "../cadastro-endereco/cadastro-endereco.component";
import {EditarMonitoradorComponent} from "../editar-monitorador/editar-monitorador.component";
import {EditarEnderecoComponent} from "../editar-endereco/editar-endereco.component";
import {DeletarMonitoradorComponent} from "../deletar-monitorador/deletar-monitorador.component";
import {DeletarEnderecoComponent} from "../deletar-endereco/deletar-endereco.component";
import {data} from "jquery";

@Component({
  selector: 'app-endereco',
  standalone: true,
  imports: [
    MatButton,
    MatDrawer,
    MatDrawerContainer,
    MatIcon,
    MatIconButton,
    MatToolbar,
    NgIf,
    RouterLink,
    RouterLinkActive,
    NgxMaskDirective,
    NgxMaskPipe,
    MatTable,
    MatColumnDef,
    MatHeaderCell,
    MatCell,
    MatHeaderRow,
    MatRow, HttpClientModule, MatHeaderRowDef, MatRowDef, MatCellDef, MatHeaderCellDef, MatList, MatListItem, MatExpansionPanel,
    MatExpansionPanelTitle, MatExpansionPanelDescription, MatAccordion, MatExpansionModule, NgForOf, NgStyle, ReactiveFormsModule
  ],
  providers: [
    MonitoradorService,
    provideNgxMask()
  ],
  templateUrl: './endereco.component.html',
  styleUrl: './endereco.component.css'
})
export class EnderecoComponent implements OnInit {
  form = false;
  MostraEnd = false;
  displayedColumns: string[] = ['id', 'nome', 'cpf', 'cnpj', 'actions', 'visualizacao'];
  displayedColumnsEnd: string[] = ['id', 'cep','endereco', 'cidade', 'estado', 'actions'];
  dataSource: MatTableDataSource<MonitoradorModels>;
  dataSourceEnd: MatTableDataSource<Endereco>;
  private monitorador!: MonitoradorModels[];
  protected enderecos!: Endereco[];
  botaoClicadoId: string = '';
  formMonitoradorFilter !: FormGroup;


  constructor(private monitoradorService: MonitoradorService, public dialog: MatDialog,
              private router: Router,
              private formBuilder: FormBuilder,
  ) {
    this.dataSource = new MatTableDataSource<MonitoradorModels>([]);
    this.dataSourceEnd = new MatTableDataSource<Endereco>([])

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
  carregarEndereco(id: string) {
    this.monitoradorService.getEndereco(id).subscribe((endereco) => {
      this.enderecos = endereco;
      this.dataSourceEnd.data = this.enderecos;
    });
  if(!this.MostraEnd){
    this.MostraEnd = true;
  }else {
    this.MostraEnd = false;
  }
    this.botaoClicadoId = id;
  }

  openDialog(id : string) {
    const dialogRef = this.dialog.open(CadastroEnderecoComponent, {
      width: '700px',
      height: '850px',
      data : {monitorador : id}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.carregarMonitorador();

    });
  }

  openDialogCadastroMoni() {
    const dialogRef = this.dialog.open(CadastroMonitoradorComponent, {
      width: '700px',
      height: '750px',
    });

    dialogRef.afterClosed().subscribe(result => {
      this.carregarMonitorador();
    });
  }

  openDialogDetalheEnd(endereco: Endereco) {
    const dialogRef = this.dialog.open(EditarEnderecoComponent, {
      width: '700px',
      height: '850px',
      data : {endereco : endereco}
    });

    dialogRef.afterClosed().subscribe(result => {
      this.carregarMonitorador();
      this.carregarEndereco(endereco.id.toString())
    });
  }

  DeletarEndereco(endereco : Endereco){
    const dialogRef = this.dialog.open(DeletarEnderecoComponent,{
      width:'750px',
      height: '300px',
      data: endereco.id
    });

    dialogRef.afterClosed().subscribe(result=>{
      this.carregarMonitorador()
      this.carregarEndereco(endereco.id.toString())
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

  ExportarPDF(id: string) {
    this.monitoradorService.getEnderecoPDF(id).subscribe(
      (resposta: Blob) => {
        this.gerarPDF(resposta);
        console.log('Exportado com sucesso', resposta);
      },
      (error) => {
        console.error('Erro ao exportar PDF: ', error);
      }
    );
  }

  ExportarExcel(id: string) {
    this.monitoradorService.getEnderecoExcel(id).subscribe(
      (resposta: Blob) => {
        this.gerarExcel(resposta);
        console.log('Exportado com sucesso', resposta);
      },
      (error) => {
        console.error('Erro ao exportar : ', error);
      }
    );
  }




}
