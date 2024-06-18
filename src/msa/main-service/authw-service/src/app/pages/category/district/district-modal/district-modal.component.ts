import {Component, Input, OnInit} from '@angular/core';
import {DistrictDto} from '../../../../shared/services/category/district/dto/district-dto';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {DistrictService} from '../../../../shared/services/category/district/district.service';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ResponseDto} from '../../../../shared/models/response-dto';

@Component({
  selector: 'ngx-district-modal',
  templateUrl: './district-modal.component.html'
})

export class DistrictModalComponent implements OnInit {

  @Input() input: number;
  create: DistrictDto = new DistrictDto();
  loading: boolean = false;
  selectedCountryId: number;

  constructor(protected dialogRef: NbDialogRef<DistrictModalComponent>
    , private districtService: DistrictService
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
    this.districtService.getDistrictById(id).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.create = <DistrictDto>res.data;
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.DISTRICT);
            console.log('getDistrictById ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.DISTRICT);
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

  save(district: DistrictDto) {
    this.loading = true;
    this.districtService.createOrEditDistrict(district).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.DISTRICT);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.DISTRICT);
            console.log('getPrivilegeById ', res);
          }


        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.DISTRICT);
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
