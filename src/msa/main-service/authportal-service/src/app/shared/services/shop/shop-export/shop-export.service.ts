import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DatatablesInputDto} from '../../../models/dataTables/datatables-input-dto';
import {ShopExportDto} from './dto/shop-export-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})

export class ShopExportService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAllShopExport(): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/shopExports/`).pipe();
  }

  getShopExportById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/shopExports/${id}`).pipe();
  }

  getAllShopExportPaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/shopExports/getAllShopExportPaging`, input).pipe();
  }

  createOrEditShopExport(input: ShopExportDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/shopExports/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/shopExports/`, input).pipe();
  }

  deleteShopExportById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/shopExports/${id}`).pipe();
  }

}
