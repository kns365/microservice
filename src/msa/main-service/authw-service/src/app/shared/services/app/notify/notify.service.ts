import {Injectable} from '@angular/core';
import {environment} from '../../../../../environments/environment';
import {HttpClient} from '@angular/common/http';
import {Observable} from 'rxjs';
import {NotifyDto} from './dto/notify-dto';
import {DatatablesInputDto} from '../../../models/dataTables/datatables-input-dto';
import {ResponseDto} from '../../../models/response-dto';

@Injectable({
  providedIn: 'root'
})
export class NotifyService {

  private baseUrl = `${environment.API_ENDPOINT}`;

  constructor(private httpClient: HttpClient) {
  }

  getAllNotify(): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/notifies/`).pipe();
  }

  getAllNotifyByUsername(username: string, max: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/notifies?username=${username}&max=${max}`).pipe();
  }

  getNotifyById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/notifies/${id}`).pipe();
  }

  readNotifyById(id: number): Observable<ResponseDto> {
    return this.httpClient.get<ResponseDto>(`${this.baseUrl}/notifies/readNotifyById/${id}`).pipe();
  }

  getAllNotifyPaging(input: DatatablesInputDto): Observable<ResponseDto> {
    return this.httpClient.post<ResponseDto>(`${this.baseUrl}/notifies/getAllNotifyPaging`, input).pipe();
  }

  createOrEditNotify(input: NotifyDto): Observable<ResponseDto> {
    if (input.id)
      return this.httpClient.put<ResponseDto>(`${this.baseUrl}/notifies/`, input).pipe();
    else
      return this.httpClient.post<ResponseDto>(`${this.baseUrl}/notifies/`, input).pipe();
  }

  deleteNotifyById(id: number): Observable<ResponseDto> {
    return this.httpClient.delete<ResponseDto>(`${this.baseUrl}/notifies/${id}`).pipe();
  }
}
