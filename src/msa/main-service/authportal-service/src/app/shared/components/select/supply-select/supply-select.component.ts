import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {HttpStatusCode} from '@angular/common/http';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {ResponseDto} from '../../../models/response-dto';
import {Router} from '@angular/router';
import {SupplyService} from '../../../services/category/supply/supply.service';
import {SupplyDto} from '../../../services/category/supply/dto/supply-dto';
import {UnitModalComponent} from '../../../../pages/category/unit/unit-modal/unit-modal.component';
import {SupplyModalComponent} from '../../../../pages/category/supply/supply-modal/supply-modal.component';
import {SupplierDto} from '../../../services/supplier/dto/supplier-dto';

@Component({
  selector: 'ngx-supply-select',
  templateUrl: './supply-select.component.html'
})

export class SupplySelectComponent implements OnInit, OnChanges {

  @Input('hideLabel') hideLabel: boolean = false;
  @Input('hideEdit') hideEdit: boolean = false;
  @Input('valid') valid: boolean = false;
  @Input('disabled') disabled: boolean = false;
  @Input('size') size: string = 'medium';
  @Input('selectedSupplier') selectedSupplier: SupplierDto;
  @Input('ngModel') selectedSupply: SupplyDto;
  @Output('ngModelChange') selectedSupplyEvent = new EventEmitter<SupplyDto>();
  supplies: SupplyDto[] = [];
  loading: boolean = false;

  constructor(private supplyService: SupplyService
    , private toastrService: NbToastrService
    , private dialogService: NbDialogService
    , private router: Router
  ) {
  }

  ngOnInit(): void {
    if (!this.hideEdit) {
      this.getSupplyData(this.selectedSupplier.id);//chay 2 lan => đã check đúng r, detail bao nhiêu supply nó chạy bấy nhiêu lần
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes && changes.selectedSupplier) {
      if (!changes.selectedSupplier.firstChange) {
        if (changes.selectedSupplier.currentValue) {
          this.getSupplyData(changes.selectedSupplier.currentValue.id);
        }
      }
    }
  }

  onSelectedSupplyChange(event: any) {
    this.selectedSupplyEvent.emit(event);
  }

  getSupplyData(supplierId: number | null) {
    this.loading = true;
    this.supplyService.getAllSupply(supplierId).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.supplies = <SupplyDto[]>res.data;
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.SUPPLY);
            console.log('getAllSupply ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.SUPPLY);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        }
      }
    )
  }

  openModal(input: any): void {
    this.dialogService.open(SupplyModalComponent, {
      context: {
        input: input,
      }
    })
      .onClose.subscribe((res) => {
      if (res && res.event && res.event === 'save') {
        this.getSupplyData(this.selectedSupplier.id);
      }
    });
  }
}
