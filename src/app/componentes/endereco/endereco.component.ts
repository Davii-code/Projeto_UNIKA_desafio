import {Component, OnInit} from '@angular/core';
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatDrawer, MatDrawerContainer} from "@angular/material/sidenav";
import {MatIcon} from "@angular/material/icon";
import {MatToolbar} from "@angular/material/toolbar";
import {NgForOf, NgIf} from "@angular/common";
import {Router, RouterLink, RouterLinkActive} from "@angular/router";
import {MonitoradorService} from "../../services/monitorador.service";
import {MatDialog} from "@angular/material/dialog";
import {FormBuilder} from "@angular/forms";
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
    MatExpansionPanelTitle, MatExpansionPanelDescription, MatAccordion, MatExpansionModule, NgForOf
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


  constructor(private monitoradorService: MonitoradorService, public dialog: MatDialog,
              private router: Router,
              private formBuilder: FormBuilder,
  ) {
    this.dataSource = new MatTableDataSource<MonitoradorModels>([]);
    this.dataSourceEnd = new MatTableDataSource<Endereco>([])

  }

  ngOnInit() {
    this.carregarMonitorador();
    // this.formMonitoradorFilter = this.formBuilder.group({
    //   cnpj: [null],
    //   cpf: [null],
    //   nome: [null],
    //   id: [null]
    // });
  }

  carregarMonitorador() {
    this.monitoradorService.getMonitorador().subscribe((monitorador) => {
      this.monitorador = monitorador;
      this.dataSource.data = this.monitorador;
    });

  }
  carregarEndereco(id: string) {
    let idAntigo  = id;
    this.monitoradorService.getEndereco(id).subscribe((endereco) => {
      this.enderecos = endereco;
      this.dataSourceEnd.data = this.enderecos;
    });
if (idAntigo != id){
  if(!this.MostraEnd){
    this.MostraEnd = true;
  }else {
    this.MostraEnd = false;
  }
}else {
  this.MostraEnd = true;
}

  }

}
