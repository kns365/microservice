import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {RoleDto} from './dto/role-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})
export class RoleService {

  private baseUrl = `${environment.API_ENDPOINT}/api/roles`;

  constructor(private httpClient: HttpClient) {
  }

  getAllRole(): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/`).pipe();
  }

  getAllRolePaging(input: any): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/getAllRolePaging`, input).pipe();
  }

  getRoleById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/${id}`).pipe();
  }

  createOrEditRole(input: RoleDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/`, input).pipe();
  }

  deleteRoleById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/${id}`).pipe();
  }
}
