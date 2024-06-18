import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {HttpStatusCode} from '@angular/common/http';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {ResponseDto} from '../../../models/response-dto';
import {UnitService} from '../../../services/category/unit/unit.service';
import {UnitDto} from '../../../services/category/unit/dto/unit-dto';
import {UnitModalComponent} from '../../../../pages/category/unit/unit-modal/unit-modal.component';

@Component({
  selector: 'ngx-unit-select',
  templateUrl: './unit-select.component.html'
})

export class UnitSelectComponent implements OnInit {

  @Input('hideLabel') hideLabel: boolean = false;
  @Input('hideAdd') hideAdd: boolean = false;
  @Input('valid') valid: boolean = true;
  @Input('disabled') disabled: boolean = false;
  @Input('ngModel') selectedUnit: UnitDto;
  @Output('ngModelChange') selectedUnitEvent = new EventEmitter<UnitDto>();
  units: UnitDto[] = [];
  loading: boolean = false;

  constructor(private unitService: UnitService
    , private toastrService: NbToastrService
    , private dialogService: NbDialogService
  ) {
  }

  ngOnInit(): void {
    this.getUnitData();
  }

  onSelectedUnitChange(event: any) {
    this.selectedUnitEvent.emit(event);
  }

  getUnitData() {
    this.loading = true;
    this.unitService.getAllUnit().subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.units = <UnitDto[]>res.data;
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.UNIT);
            console.log('getAllUnit ', res);
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

  openModal(input: any): void {
    this.dialogService.open(UnitModalComponent, {
      context: {
        input: input,
      }
    })
      .onClose.subscribe((res) => {
      if (res && res.event && res.event === 'save') {
        this.getUnitData();
      }
    });
  }
}
