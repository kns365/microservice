import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DatatablesInputDto} from '../../../models/dataTables/datatables-input-dto';
import {ShopOrderDetailDto} from './dto/shop-order-detail-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})

export class ShopOrderDetailService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAllShopOrderDetail(): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/shopOrderDetails/`).pipe();
  }

  getShopOrderDetailById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/shopOrderDetails/${id}`).pipe();
  }

  getAllShopOrderDetailPaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/shopOrderDetails/getAllShopOrderDetailPaging`, input).pipe();
  }

  createOrEditShopOrderDetail(input: ShopOrderDetailDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/shopOrderDetails/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/shopOrderDetails/`, input).pipe();
  }

  deleteShopOrderDetailById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/shopOrderDetails/${id}`).pipe();
  }

}
