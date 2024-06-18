import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DatatablesInputDto} from '../../../models/dataTables/datatables-input-dto';
import {ItemDto} from './dto/item-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})

export class ItemService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAllItem(groupItemId: number | null): Observable<ResponseDto> {
    if (groupItemId) {
      return this.httpClient.get<ResponseDto>(`${this.baseUrl}/items/getAllItemByGroupItemId/${groupItemId}`).pipe();
    } else {
      return this.httpClient.get<ResponseDto>(`${this.baseUrl}/items/`).pipe();
    }

  }F

  getItemById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/items/${id}`).pipe();
  }

  getAllItemPaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/items/getAllItemPaging`, input).pipe();
  }

  createOrEditItem(input: ItemDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/items/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/items/`, input).pipe();
  }

  deleteItemById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/items/${id}`).pipe();
  }

}
