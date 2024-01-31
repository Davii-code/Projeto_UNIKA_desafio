import {RouterModule, Routes} from '@angular/router';
import {MonitoradorComponent} from "./componentes/monitorador/monitorador.component";
import {NgModule} from "@angular/core";
import {EditarMonitoradorComponent} from "./componentes/editar-monitorador/editar-monitorador.component";

export const routes: Routes = [
  {path: '', redirectTo:'monitorador', pathMatch:'full'},
  {path:'monitorador', component: MonitoradorComponent},
];
@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
