import {RouterModule, Routes} from '@angular/router';
import {MonitoradorComponent} from "./componentes/monitorador/monitorador.component";
import {EnderecoComponent} from "./componentes/endereco/endereco.component";
import {NgModule} from "@angular/core";

export const routes: Routes = [
  {path: '', redirectTo:'monitorador', pathMatch:'full'},
  {path:'monitorador', component: MonitoradorComponent},
  {path: 'endereco',component: EnderecoComponent}
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
