import {Component, Input, OnChanges, OnInit, SimpleChanges} from '@angular/core';
import {HttpStatusCode} from '@angular/common/http';
import {NbToastrService} from '@nebular/theme';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {ResponseDto} from '../../../models/response-dto';
import {UntypedFormBuilder} from '@angular/forms';
import {ShopOrderDto} from '../../../services/shop/shop-order/dto/shop-order-dto';
import {ShopOrderService} from '../../../services/shop/shop-order/shop-order.service';
import {NbAccessChecker} from '@nebular/security';
import {ShopOrderStepConst} from '../../../constants/ShopOrderStepConst';
import {ShopImportService} from '../../../services/shop/shop-import/shop-import.service';
import {ShopImportDto} from '../../../services/shop/shop-import/dto/shop-import-dto';

@Component({
  selector: 'ngx-shop-order-step',
  templateUrl: './shop-order-step.component.html',
  styleUrls: ['./shop-order-step.component.scss']
})

export class ShopOrderStepComponent implements OnInit, OnChanges {

  @Input('hide') hide: boolean = false;
  @Input('ngModel') shopOrder: ShopOrderDto;
  // @Output('ngModelChange') shopOrderEvent = new EventEmitter<ShopExportDto>();
  stepIndex: number;
  loading: boolean = false;
  steps = [
    {
      index: 0,
      label: 'INIT',
      role: 'SHOP_EDIT'
    },
    {
      index: 1,
      label: 'SENT',
      role: 'SHOP_EDIT'
    },
    {
      index: 2,
      label: 'CONFIRM',
      role: 'SUPPLIER_EDIT'
    },
    {
      index: 3,
      label: 'PREPARING',
      role: 'SUPPLIER_EDIT'
    },
    {
      index: 4,
      label: 'DELIVERING',
      role: 'SUPPLIER_EDIT'
    },
    {
      index: 5,
      label: 'RECEIVED',
      role: 'SHOP_EDIT'
    },
    {
      index: 6,
      label: 'IMPORTED',
      role: 'SHOP_EDIT'
    }
  ]

  constructor(private fb: UntypedFormBuilder
    , private shopOrderService: ShopOrderService
    , private shopImportService: ShopImportService
    , private toastrService: NbToastrService
  ) {
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes && changes.shopOrder) {
      if (!changes.shopOrder.firstChange) {
        if (changes.shopOrder.currentValue) {
          this.setStep(changes.shopOrder.currentValue.step);
        }
      }
    }
  }

  setStep(step: number) {
    this.stepIndex = step;
  }

  stepChange(step: any) {
    if (step.index != this.stepIndex) {
      if (step.index > 5) {
        let input = new ShopImportDto(this.shopOrder);
        this.import(input, step.index);
      } else {
        this.submitStep(step.index);
      }
    }
  }

  submitStep(step: number) {
    this.loading = true;
    this.shopOrder.step = step;
    this.shopOrderService.createOrEditShopOrder(this.shopOrder).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.SHOPORDER);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOPORDER);
            console.log('createOrEditShopOrder ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPORDER);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        }
      }
    )
  }

  import(input: ShopImportDto, step: number) {
    this.loading = true;
    this.shopImportService.createOrEditShopImport(input).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.SHOPIMPORT);
            this.submitStep(step);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOPIMPORT);
            console.log('createOrEditShopImport ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPIMPORT);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        }
      }
    )
  }
}
