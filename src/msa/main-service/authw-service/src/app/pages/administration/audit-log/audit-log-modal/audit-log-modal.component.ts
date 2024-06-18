import {Component, Input, OnInit} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {AuditLogService} from '../../../../shared/services/app/audit-log/audit-log.service';
import {AuditLogDto} from '../../../../shared/services/app/audit-log/dto/audit-log-dto';
import {ResponseDto} from '../../../../shared/models/response-dto';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';

@Component({
  selector: 'ngx-audit-log-modal',
  templateUrl: './audit-log-modal.component.html'
})

export class AuditLogModalComponent implements OnInit {

  @Input() input: number;
  create: AuditLogDto = new AuditLogDto();
  loading: boolean = false;

  constructor(protected dialogRef: NbDialogRef<AuditLogModalComponent>
    , private auditLogService: AuditLogService
    , private toastrService: NbToastrService
  ) {
  }

  ngOnInit(): void {
    if (this.input) {
      this.getData(this.input);
    }
  }

  tryParseJson(input) {
    let output;
    try {
      output = JSON.parse(input);
    } catch (e) {
      return input;
    }
    return output;
  }

  getData(id: number) {
    this.loading = true;
    this.auditLogService.getAuditLogById(id).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.create = <AuditLogDto>res.data;
            let input = this.tryParseJson(this.create.input);
            let output = this.tryParseJson(this.create.output);
            this.create.input = JSON.stringify(input, undefined, 4);
            this.create.output = JSON.stringify(output, undefined, 4);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.AUDITLOG);
            console.log('getAuditLogById ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.AUDITLOG);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        }
      }
    )
  }

  cancel() {
    this.dialogRef.close({event: 'close'});
  }

}
