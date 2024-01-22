import {Injectable, NgModule} from '@angular/core';
import {HttpClient, HttpHeaders} from "@angular/common/http";
import {BehaviorSubject, catchError, Observable, throwError} from "rxjs";
import {MonitoradorModels} from "../Models/monitorador/monitorador.models";
import {error} from "jquery";
import {Endereco} from "../Models/monitorador/endereco.models";

@Injectable({
  providedIn: 'root'
})

export class MonitoradorService {



  constructor(private http: HttpClient) {}

  private baseUrl = "http://localhost:8080/monitorador";


  getMonitorador(): Observable<MonitoradorModels[]> {
    return this.http.get<MonitoradorModels[]>(this.baseUrl)
      .pipe(
        catchError((error) => {
          console.error('Erro na solicitação getMonitorador:', error);
          return throwError(error);
        })
      );
  }

  public putMonitorador( id: string ,dados: MonitoradorModels): Observable<MonitoradorModels> {
    const  urlPut: string = this.baseUrl+"/"+id;
    return this.http.put <MonitoradorModels>(urlPut,dados).pipe();

  }

  public deleteMonitorador ( id: string): Observable<MonitoradorModels> {
    const urlDeletar: string = this.baseUrl + "/" + id;
    return this.http.delete <MonitoradorModels>(urlDeletar).pipe();
  }


  public postMonitorador(dados: MonitoradorModels): Observable<MonitoradorModels> {
    const  urlPost: string = this.baseUrl;
    return this.http.post <MonitoradorModels>(urlPost,dados).pipe();

  }
  public postMonitoradorEndereco(dados: Endereco): Observable<Endereco> {
    const  urlPost: string = "http://localhost:8080/endereco";
    return this.http.post <Endereco>(urlPost,dados).pipe();

  }

}
