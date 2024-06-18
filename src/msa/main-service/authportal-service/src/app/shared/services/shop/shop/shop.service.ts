import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DatatablesInputDto} from '../../../models/dataTables/datatables-input-dto';
import {ShopDto} from './dto/shop-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})

export class ShopService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAllShop(): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/shops/`).pipe();
  }

  getShopById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/shops/${id}`).pipe();
  }

  getShopByCode(code: string): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/shops?code=${code}`).pipe();
  }

  getAllShopPaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/shops/getAllShopPaging`, input).pipe();
  }

  createOrEditShop(input: ShopDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/shops/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/shops/`, input).pipe();
  }

  deleteShopById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/shops/${id}`).pipe();
  }

}
