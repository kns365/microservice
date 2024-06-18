import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {DatatablesInputDto} from '../../../models/dataTables/datatables-input-dto';
import {AuditLogDto} from './dto/audit-log-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})
export class AuditLogService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAuditLogById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/auditLogs/${id}`).pipe();
  }

  createOrEditAuditLog(input: AuditLogDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/auditLogs/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/auditLogs/`, input).pipe();
  }

  deleteAuditLogById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/auditLogs/${id}`).pipe();
  }

  getAllAuditLogPaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/auditLogs/getAllAuditLogPaging`, input).pipe();
  }
}
