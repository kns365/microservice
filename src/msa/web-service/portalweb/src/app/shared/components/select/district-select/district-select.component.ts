import {AfterContentInit, Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {DistrictDto} from '../../../services/category/district/dto/district-dto';
import {DistrictService} from '../../../services/category/district/district.service';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {ResponseDto} from '../../../models/response-dto';
import {DistrictModalComponent} from '../../../../pages/category/district/district-modal/district-modal.component';
import {ProvinceDto} from '../../../services/category/province/dto/province-dto';

@Component({
  selector: 'ngx-district-select',
  templateUrl: './district-select.component.html'
})
export class DistrictSelectComponent implements OnInit, OnChanges {

  @Input('hideLabel') hideLabel: boolean = false;
  @Input('hideAdd') hideAdd: boolean = false;
  @Input('valid') valid: boolean = true;
  @Input('disabled') disabled: boolean = false;
  @Input('selectedProvince') selectedProvince: ProvinceDto;
  @Input('ngModel') selectedDistrict: DistrictDto;
  @Output('ngModelChange') selectedDistrictEvent = new EventEmitter<DistrictDto>();
  districts: DistrictDto[] = [];
  loading: boolean = false;

  constructor(private districtService: DistrictService
    , private toastrService: NbToastrService
    , private dialogService: NbDialogService) {
  }

  ngOnInit(): void {
    this.getDistrictData(this.selectedProvince.id);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes && changes.selectedProvince) {
      if (!changes.selectedProvince.firstChange) {
        if (changes.selectedProvince.currentValue) {
          this.getDistrictData(changes.selectedProvince.currentValue.id);
        }
      }
    }
  }

  onSelectedDistrictChange(event: any) {
    this.selectedDistrictEvent.emit(event);
  }

  getDistrictData(provinceId: number | null) {
    this.loading = true;
    this.districtService.getAllDistrict(provinceId).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.districts = <DistrictDto[]>res.data;
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.DISTRICT);
            console.log('getAllDistrict ', res);
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

  openModal(input: any): void {
    this.dialogService.open(DistrictModalComponent, {
      context: {
        input: input,
      }
    })
      .onClose.subscribe((res) => {
      if (res && res.event && res.event === 'save') {
        this.getDistrictData(this.selectedProvince.id);
      }
    });
  }
}
