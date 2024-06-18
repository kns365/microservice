import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {HttpStatusCode} from '@angular/common/http';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {CountryDto} from '../../../services/category/country/dto/country-dto';
import {CountryService} from '../../../services/category/country/country.service';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {ResponseDto} from '../../../models/response-dto';
import {CountryModalComponent} from '../../../../pages/category/country/country-modal/country-modal.component';

@Component({
  selector: 'ngx-country-select',
  templateUrl: './country-select.component.html'
})

export class CountrySelectComponent implements OnInit {

  @Input('hideLabel') hideLabel: boolean = false;
  @Input('hideAdd') hideAdd: boolean = false;
  @Input('valid') valid: boolean = true;
  @Input('disabled') disabled: boolean = false;
  @Input('ngModel') selectedCountry: CountryDto;
  @Output('ngModelChange') selectedCountryEvent = new EventEmitter<CountryDto>();
  countries: CountryDto[] = [];
  loading: boolean = false;

  constructor(private countryService: CountryService
    , private toastrService: NbToastrService
    , private dialogService: NbDialogService) {
  }

  ngOnInit(): void {
    this.getCountryData();
  }

  onSelectedCountryChange(event: any) {
    this.selectedCountryEvent.emit(event);
  }

  getCountryData() {
    this.loading = true;
    this.countryService.getAllCountry().subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.countries = <CountryDto[]>res.data;
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.COUNTRY);
            console.log('getAllCountry ', res);
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

  openModal(input: any): void {
    this.dialogService.open(CountryModalComponent, {
      context: {
        input: input,
      }
    })
      .onClose.subscribe((res) => {
      if (res && res.event && res.event === 'save') {
        this.getCountryData();
      }
    });
  }
}
