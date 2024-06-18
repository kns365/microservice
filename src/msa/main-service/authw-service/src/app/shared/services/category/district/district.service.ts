import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DistrictDto} from '../district/dto/district-dto';
import {DatatablesInputDto} from '../../../models/dataTables/datatables-input-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})
export class DistrictService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAllDistrict(provinceId: number | null): Observable<ResponseDto> {
    if (provinceId) {
      return this.httpClient.get<ResponseDto>(`${this.baseUrl}/districts/getAllDistrictByProvinceId/${provinceId}`).pipe();
    } else {
      return this.httpClient.get<ResponseDto>(`${this.baseUrl}/districts/`).pipe();
    }
  }

  getDistrictById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/districts/${id}`).pipe();
  }

  getAllDistrictPaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/districts/getAllDistrictPaging`, input).pipe();
  }

  createOrEditDistrict(input: DistrictDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/districts/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/districts/`, input).pipe();
  }

  deleteDistrictById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/districts/${id}`).pipe();
  }
}
