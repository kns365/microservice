import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DatatablesInputDto} from '../../../models/dataTables/datatables-input-dto';
import {ShopImportDetailDto} from './dto/shop-import-detail-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})

export class ShopImportDetailService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAllShopImportDetail(shopId: number | null): Observable<ResponseDto> {
    if (shopId) {
      return this.httpClient.get<ResponseDto>(`${this.baseUrl}/shopImportDetails/getAllShopImportDetailByShopId/${shopId}`).pipe();
    } else {
      return this.httpClient.get<ResponseDto>(`${this.baseUrl}/shopImportDetails/`).pipe();
    }
  }

  getAllSupplylByShopId(shopId: number | null): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/shopImportDetails/getAllSupplylByShopId/${shopId}`).pipe();
  }

  getShopImportDetailById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/shopImportDetails/${id}`).pipe();
  }

  getAllShopImportDetailPaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/shopImportDetails/getAllShopImportDetailPaging`, input).pipe();
  }

  createOrEditShopImportDetail(input: ShopImportDetailDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/shopImportDetails/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/shopImportDetails/`, input).pipe();
  }

  deleteShopImportDetailById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/shopImportDetails/${id}`).pipe();
  }

}
