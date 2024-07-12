import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {UserDto} from './dto/user-dto';
import {DatatablesInputDto} from '../../../models/dataTables/datatables-input-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  private baseUrl = `${environment.API_ENDPOINT}/api/users`;

  constructor(private httpClient: HttpClient) {
  }

  getAllUser(): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/`).pipe();
  }

  getUserById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/${id}`).pipe();
  }

  getAllUserPaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/getAllUserPaging`, input).pipe();
  }

  createOrEditUser(input: UserDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/`, input).pipe();
  }

  deleteUserById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/${id}`).pipe();
  }
}
