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
  monitorador!: Monitorador;
  displayedColumns: string[] = ['id', 'nome', 'cpf', 'cnpj'];
  dataSource: MatTableDataSource<Monitorador> = new MatTableDataSource<Monitorador>();

  constructor(private monitoradorServices: MonitoradorService) {}

  ngOnInit() {
    this.monitoradorServices.getMonitorador().subscribe((monitorador) => {
      this.monitorador = monitorador;
      this.dataSource = new MatTableDataSource(this.monitorador);
    });
  }
}

