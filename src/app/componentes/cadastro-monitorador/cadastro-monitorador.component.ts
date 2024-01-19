import { Component } from '@angular/core';
import {MatDialog, MatDialogActions, MatDialogClose, MatDialogContent} from "@angular/material/dialog";
import {MonitoradorComponent} from "../monitorador/monitorador.component";
import {MatButton} from "@angular/material/button";

@Component({
  selector: 'app-cadastro-monitorador',
  standalone: true,
  imports: [
    MatDialogActions,
    MatButton,
    MatDialogClose,
    MatDialogContent
  ],
  templateUrl: './cadastro-monitorador.component.html',
  styleUrl: './cadastro-monitorador.component.css'
})
export class CadastroMonitoradorComponent {

}
