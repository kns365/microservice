import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DatatablesInputDto} from '../../../models/dataTables/datatables-input-dto';
import {ProvinceDto} from './dto/province-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})
export class ProvinceService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAllProvince(countryId: number | null): Observable<ResponseDto> {
    if (countryId) {
      return this.httpClient.get<ResponseDto>(`${this.baseUrl}/provinces/getAllProvinceByCountryId/${countryId}`).pipe();
    } else {
      return this.httpClient.get<ResponseDto>(`${this.baseUrl}/provinces/`).pipe();
    }
  }

  getProvinceById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/provinces/${id}`).pipe();
  }

  getAllProvincePaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/provinces/getAllProvincePaging`, input).pipe();
  }

  createOrEditProvince(input: ProvinceDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/provinces/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/provinces/`, input).pipe();
  }

  deleteProvinceById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/provinces/${id}`).pipe();
  }

}
