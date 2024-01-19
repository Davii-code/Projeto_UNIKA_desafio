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
import {RouterLink, RouterLinkActive} from "@angular/router";
import {MonitoradorService} from "../../services/monitorador.service";
import {HttpClientModule} from "@angular/common/http";
import {MatDialog} from "@angular/material/dialog";
import {EnderecoComponent} from "../endereco/endereco.component";
import {CadastroMonitoradorComponent} from "../cadastro-monitorador/cadastro-monitorador.component";


export interface Monitorador {
  id: number;
  nome: string;
  cpf: number;
  cnpj: number;
}

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
    MatDrawer, NgIf, RouterLink, RouterLinkActive, MatColumnDef, MatHeaderCell, MatCell, MatCellDef, MatHeaderCellDef, MatHeaderRowDef, MatRowDef, MatHeaderRow, MatRow, HttpClientModule
  ],
  providers:[
    MonitoradorService,
  ],
  templateUrl: './monitorador.component.html',
  styleUrl: './monitorador.component.css'
})


export class MonitoradorComponent implements OnInit {
  form = false;
  monitorador!: Monitorador[];
  displayedColumns: string[] = ['id', 'nome', 'cpf', 'cnpj', 'actions'];
  dataSource: MatTableDataSource<Monitorador>;

  constructor(private monitoradorService: MonitoradorService, public dialog: MatDialog) {
    this.dataSource = new MatTableDataSource<Monitorador>();
  }

  ngOnInit() {
    this.monitoradorService.getMonitorador().subscribe((monitorador) => {
      this.monitorador = monitorador;
      this.dataSource.data = this.monitorador;
    });
  }

  openDialog() {
    const dialogRef = this.dialog.open(CadastroMonitoradorComponent, {
      width: '700px', // Defina a largura desejada
      height: '650px', // Defina a altura desejada
    });

    dialogRef.afterClosed().subscribe(result => {
      console.log(`Dialog result: ${result}`);
    });
  }
}

