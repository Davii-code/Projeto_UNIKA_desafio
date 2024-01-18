import { Component } from '@angular/core';
import {MatButton, MatIconButton} from "@angular/material/button";
import {MatDrawer, MatDrawerContainer} from "@angular/material/sidenav";
import {MatIcon} from "@angular/material/icon";
import {MatToolbar} from "@angular/material/toolbar";
import {NgIf} from "@angular/common";
import {RouterLink, RouterLinkActive} from "@angular/router";

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
        RouterLinkActive
    ],
  templateUrl: './endereco.component.html',
  styleUrl: './endereco.component.css'
})
export class EnderecoComponent {
  form = false;
}
