import {Injectable, NgModule} from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";

@Injectable({
  providedIn: 'root'
})


@NgModule({
  providers: [
    MonitoradorService,
  ],
})
export class MonitoradorService {

  constructor(private http: HttpClient) {}

  private baseUrl = "http://localhost:8080/monitorador";

  getMonitorador():Observable<any>{
      return this.http.get(this.baseUrl);
  }


}
