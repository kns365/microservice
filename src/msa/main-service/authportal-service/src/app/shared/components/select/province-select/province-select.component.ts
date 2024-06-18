import {AfterContentInit, Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {ProvinceDto} from '../../../services/category/province/dto/province-dto';
import {ProvinceService} from '../../../services/category/province/province.service';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {ResponseDto} from '../../../models/response-dto';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {ProvinceModalComponent} from '../../../../pages/category/province/province-modal/province-modal.component';
import {CountryDto} from '../../../services/category/country/dto/country-dto';

@Component({
  selector: 'ngx-province-select',
  templateUrl: './province-select.component.html'
})
export class ProvinceSelectComponent implements OnInit, OnChanges {

  @Input('hideLabel') hideLabel: boolean = false;
  @Input('hideAdd') hideAdd: boolean = false;
  @Input('valid') valid: boolean = true;
  @Input('disabled') disabled: boolean = false;
  @Input('selectedCountry') selectedCountry: CountryDto;
  @Input('ngModel') selectedProvince: ProvinceDto;
  @Output('ngModelChange') selectedProvinceEvent = new EventEmitter<ProvinceDto>();
  provinces: ProvinceDto[] = [];
  loading: boolean = false;

  constructor(private provinceService: ProvinceService
    , private toastrService: NbToastrService
    , private dialogService: NbDialogService) {
  }

  ngOnInit(): void {
    this.getProvinceData(this.selectedCountry.id);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes && changes.selectedCountry) {
      if (!changes.selectedCountry.firstChange) {
        if (changes.selectedCountry.currentValue) {
          this.getProvinceData(changes.selectedCountry.currentValue.id);
        }
      }
    }
  }

  onSelectedProvinceChange(event: any) {
    this.selectedProvinceEvent.emit(event);
  }

  getProvinceData(countryId: number | null) {
    this.loading = true;
    this.provinceService.getAllProvince(countryId).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.provinces = <ProvinceDto[]>res.data;
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.PRIVILEGE);
            console.log('getPrivilegeById ', res);
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

  openModal(input: any): void {
    this.dialogService.open(ProvinceModalComponent, {
      context: {
        input: input,
      }
    })
      .onClose.subscribe((res) => {
      if (res && res.event && res.event === 'save') {
        this.getProvinceData(this.selectedCountry.id);
      }
    });
  }
}
