import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DatatablesInputDto} from '../../../models/dataTables/datatables-input-dto';
import {ShopOrderDto} from './dto/shop-order-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})

export class ShopOrderService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAllShopOrder(): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/shopOrders/`).pipe();
  }

  getShopOrderById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/shopOrders/${id}`).pipe();
  }

  getAllShopOrderPaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/shopOrders/getAllShopOrderPaging`, input).pipe();
  }

  createOrEditShopOrder(input: ShopOrderDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/shopOrders/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/shopOrders/`, input).pipe();
  }

  deleteShopOrderById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/shopOrders/${id}`).pipe();
  }

}
