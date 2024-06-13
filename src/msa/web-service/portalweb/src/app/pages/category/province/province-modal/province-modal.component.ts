import {Component, Input, OnInit} from '@angular/core';
import * as _ from 'lodash';
import {ProvinceDto} from '../../../../shared/services/category/province/dto/province-dto';
import {RoleDto} from '../../../../shared/services/app/role/dto/role-dto';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {RoleService} from '../../../../shared/services/app/role/role.service';
import {ProvinceService} from '../../../../shared/services/category/province/province.service';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeDto} from '../../../../shared/services/app/privilege/dto/privilege-dto';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ResponseDto} from '../../../../shared/models/response-dto';

@Component({
  selector: 'ngx-province-modal',
  templateUrl: './province-modal.component.html'
})

export class ProvinceModalComponent implements OnInit {

  @Input() input: number;
  create: ProvinceDto = new ProvinceDto();
  loading: boolean = false;

  constructor(protected dialogRef: NbDialogRef<ProvinceModalComponent>
    , private provinceService: ProvinceService
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
    this.provinceService.getProvinceById(id).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.create = <ProvinceDto>res.data;
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.PROVINCE);
            console.log('getProvinceById ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.PROVINCE);
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

  save(province: ProvinceDto) {
    this.loading = true;
    this.provinceService.createOrEditProvince(province).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.PROVINCE);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.PROVINCE);
            console.log('createOrEditProvince ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.PROVINCE);
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
