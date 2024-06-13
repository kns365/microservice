import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {PrivilegeDto} from './dto/privilege-dto';
import {DatatablesInputDto} from '../../../models/dataTables/datatables-input-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})
export class PrivilegeService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAllPrivilege(): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/privileges/`).pipe();
  }

  getPrivilegeById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/privileges/${id}`).pipe();
  }

  createOrEditPrivilege(input: PrivilegeDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/privileges/`, input).pipe();
    else {
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/privileges/`, input).pipe();
    }
  }

  deletePrivilegeById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/privileges/${id}`).pipe();
  }

  getAllPrivilegePaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/privileges/getAllPrivilegePaging`, input).pipe();
  }

}
