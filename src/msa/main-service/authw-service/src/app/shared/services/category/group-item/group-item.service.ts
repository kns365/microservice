import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DatatablesInputDto} from '../../../models/dataTables/datatables-input-dto';
import {GroupItemDto} from './dto/group-item-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})

export class GroupItemService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAllGroupItem(): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/groupItems/`).pipe();
  }

  getGroupItemById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/groupItems/${id}`).pipe();
  }

  getAllGroupItemPaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/groupItems/getAllGroupItemPaging`, input).pipe();
  }

  createOrEditGroupItem(input: GroupItemDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/groupItems/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/groupItems/`, input).pipe();
  }

  deleteGroupItemById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/groupItems/${id}`).pipe();
  }

}
