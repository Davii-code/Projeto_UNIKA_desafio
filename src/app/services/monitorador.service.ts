import {Injectable, NgModule} from '@angular/core';
import {HttpClient, HttpHeaders, HttpResponse} from "@angular/common/http";
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

  getEndCep(cep : string): Observable<Endereco> {
    return this.http.get<Endereco>(`http://localhost:8080/endereco/buscarCEP/`+ cep)
      .pipe(
        catchError((error) => {
          console.error('Erro na solicitação getMonitorador:', error);
          return throwError(error);
        })
      );
  }


  public putMonitorador( id: string ,dados: MonitoradorModels): Observable<MonitoradorModels> {
    const  urlPut: string = this.baseUrl+"/"+id;
    return this.http.put <MonitoradorModels>(urlPut,dados).pipe(
      catchError((error)=> {
        return throwError(error.error);
      })
    );

  }

  public deleteMonitorador ( id: string): Observable<MonitoradorModels> {
    const urlDeletar: string = this.baseUrl + "/" + id;
    return this.http.delete <MonitoradorModels>(urlDeletar).pipe();
  }


  public postMonitorador(dados: MonitoradorModels): Observable<MonitoradorModels> {
    const  urlPost: string = this.baseUrl;
    return this.http.post <MonitoradorModels>(urlPost,dados).pipe(
      catchError((error)=> {
        return throwError(error.error);
      })
    );

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

  getMonitoradorPDFFilter(tipo: string, valor: string): Observable<Blob> {
    return this.http.get('http://localhost:8080/monitorador/relatorio/pdfs?'+tipo+'='+valor, { responseType: 'blob' })
      .pipe(
        catchError((error) => {
          console.error('Erro na solicitação getMonitorador:', error);
          return throwError(error);
        })
      );
  }

  getMonitoradorExcelFilter(tipo: string, valor: string): Observable<Blob> {
    return this.http.get('http://localhost:8080/monitorador/relatorio/excel?'+tipo+'='+valor, { responseType: 'blob' })
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

  getMonitoradorExcelModel(): Observable<Blob> {
    return this.http.get('http://localhost:8080/monitorador/relatorio/excelModelo', { responseType: 'blob' })
      .pipe(
        catchError((error) => {
          console.error('Erro na solicitação getMonitorador:', error);
          return throwError(error);
        })
      );
  }

}
