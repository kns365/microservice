import {Injectable} from '@angular/core';
import {environment} from '../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DatatablesInputDto} from '../../models/dataTables/datatables-input-dto';
import {SupplierDto} from './dto/supplier-dto';
import {ResponseDto} from '../../models/response-dto';

@Injectable({
  providedIn: 'root'
})

export class SupplierService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAllSupplier(): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/suppliers/`).pipe();
  }

  getSupplierById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/suppliers/${id}`).pipe();
  }

  getSupplierByCode(code: string): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/suppliers?code=${code}`).pipe();
  }

  getAllSupplierPaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/suppliers/getAllSupplierPaging`, input).pipe();
  }

  createOrEditSupplier(input: SupplierDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/suppliers/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/suppliers/`, input).pipe();
  }

  deleteSupplierById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/suppliers/${id}`).pipe();
  }

}
