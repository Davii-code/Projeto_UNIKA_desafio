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
  public getFilterMonitoradorNome(params: string): Observable<MonitoradorModels[]> {
    const url: string = "http://localhost:8080/monitorador/filtroNome/" + params;
    return this.http.get<MonitoradorModels[]>(url).pipe(
      catchError((error) => {
        console.error("Erro na requisição:", error);
        return throwError("Erro ao obter dados. Por favor, tente novamente mais tarde.");
      })
    );
  }

  public MonitoradorCPF(params: string): Observable<MonitoradorModels[]> {
    const url: string = "http://localhost:8080/monitorador/filtroCpf/" + params;
    return this.http.get<MonitoradorModels[]>(url).pipe(
      catchError((error) => {
        console.error("Erro na requisição:", error);
        return throwError("Erro ao obter dados. Por favor, tente novamente mais tarde.");
      })
    );
  }

  public getFilterMonitoradorCnpj(params: string): Observable<MonitoradorModels[]> {
    const url: string = "http://localhost:8080/monitorador/filtroCnpj/" + params;
    return this.http.get<MonitoradorModels[]>(url).pipe(
      catchError((error) => {
        console.error("Erro na requisição:", error);
        return throwError("Erro ao obter dados. Por favor, tente novamente mais tarde.");
      })
    );
  }

  public getFilterMonitoradorID( params: string): Observable<MonitoradorModels[]> {
    const ur: string = this.baseUrl + "/"+params;
    console.log(ur)
    return this.http.get <MonitoradorModels[]>(ur).pipe(
      catchError((error)=>{
        console.error("URL invalida", + ur)
        return throwError(error())
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

  uploadExcel(file: File): Observable<any> {
    const formData: FormData = new FormData();
    formData.append('file', file, file.name);

    return this.http.post(`${this.baseUrl}/importar-excel`, formData);
  }

  getMonitoradorPDF(): Observable<Blob> {
    return this.http.get('http://localhost:8080/monitorador/relatorio/pdfs', { responseType: 'blob' })
      .pipe(
        catchError((error) => {
          console.error('Erro na solicitação getMonitorador:', error);
          return throwError(error);
        })
      );
  }

  getMonitoradorExcel(): Observable<Blob> {
    return this.http.get('http://localhost:8080/monitorador/relatorio/excel', { responseType: 'blob' })
      .pipe(
        catchError((error) => {
          console.error('Erro na solicitação getMonitorador:', error);
          return throwError(error);
        })
      );
  }

}
