import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {ResponseDto} from '../../models/response-dto';
import {DatatablesInputDto} from '../../models/dataTables/datatables-input-dto';
import {ContactDto} from '../contact/dto/contact-dto';

@Injectable({
  providedIn: 'root'
})

export class ContactService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAllContact(): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/contacts/`).pipe();
  }

  getContactById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/contacts/${id}`).pipe();
  }

  getAllContactPaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/contacts/getAllContactPaging`, input).pipe();
  }

  createOrEditContact(input: ContactDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/contacts/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/contacts/`, input).pipe();
  }

  deleteContactById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/contacts/${id}`).pipe();
  }

}
