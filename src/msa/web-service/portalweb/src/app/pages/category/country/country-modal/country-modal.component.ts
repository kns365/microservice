import {Component, Input, OnInit} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {CountryDto} from '../../../../shared/services/category/country/dto/country-dto';
import {CountryService} from '../../../../shared/services/category/country/country.service';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ResponseDto} from '../../../../shared/models/response-dto';

@Component({
  selector: 'ngx-country-modal',
  templateUrl: './country-modal.component.html'
})

export class CountryModalComponent implements OnInit {

  @Input() input: number;
  create: CountryDto = new CountryDto();
  loading: boolean = false;

  constructor(protected dialogRef: NbDialogRef<CountryModalComponent>
    , private countryService: CountryService
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
    this.countryService.getCountryById(id).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.create = <CountryDto>res.data;
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.COUNTRY);
            console.log('getCountryById ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.COUNTRY);
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

  save(country: CountryDto) {
    this.loading = true;
    this.countryService.createOrEditCountry(country).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.COUNTRY);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.COUNTRY);
            console.log('createOrEditCountry ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.COUNTRY);
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
