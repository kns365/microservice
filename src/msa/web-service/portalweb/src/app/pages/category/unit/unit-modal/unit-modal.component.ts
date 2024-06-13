import {Component, Input, OnInit} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ResponseDto} from '../../../../shared/models/response-dto';
import {UnitDto} from '../../../../shared/services/category/unit/dto/unit-dto';
import {UnitService} from '../../../../shared/services/category/unit/unit.service';

@Component({
  selector: 'ngx-unit-modal',
  templateUrl: './unit-modal.component.html'
})

export class UnitModalComponent implements OnInit {

  @Input() input: number;
  create: UnitDto = new UnitDto();
  loading: boolean = false;

  constructor(protected dialogRef: NbDialogRef<UnitModalComponent>
    , private unitService: UnitService
    , private toastrService: NbToastrService
  ) {
  }

  ngOnInit(): void {
    if (this.input) {
      this.getData(this.input);
    }
  }

  getData(id: number) {
    this.loading = true;
    this.unitService.getUnitById(id).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.create = <UnitDto>res.data;
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.UNIT);
            console.log('getUnitById ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.UNIT);
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

  save(unit: UnitDto) {
    this.loading = true;
    this.unitService.createOrEditUnit(unit).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.UNIT);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.UNIT);
            console.log('createOrEditUnit ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.UNIT);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
          this.dialogRef.close({event: 'save'});
        }
      }
    )
  }
}
