import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {HttpStatusCode} from '@angular/common/http';
import {SupplierDto} from '../../../../../shared/services/supplier/dto/supplier-dto';
import {PrivilegeConst} from '../../../../../shared/constants/PrivilegeConst';
import {ShopOrderDto} from '../../../../../shared/services/shop/shop-order/dto/shop-order-dto';
import {ShopOrderStepConst} from '../../../../../shared/constants/ShopOrderStepConst';
import {ResponseDto} from '../../../../../shared/models/response-dto';
import {ShopOrderService} from '../../../../../shared/services/shop/shop-order/shop-order.service';
import {ShopService} from '../../../../../shared/services/shop/shop/shop.service';
import {SupplierService} from '../../../../../shared/services/supplier/supplier.service';

@Component({
  selector: 'ngx-shop-dt-order-modal',
  templateUrl: './shop-dt-order-modal.component.html'
})

export class ShopDtOrderModalComponent implements OnInit {

  @Input() input: number;
  @Input() inputShopId: number;
  create: ShopOrderDto = new ShopOrderDto();
  loading: boolean = false;
  supplySelectedDetailInvalid: boolean = true;
  duplicateDetailInvalid: boolean = true;
  initStep: number;
  steps: any = ShopOrderStepConst;

  constructor(protected dialogRef: NbDialogRef<ShopDtOrderModalComponent>
    , private shopOrderService: ShopOrderService
    , private shopService: ShopService
    , private supplierService: SupplierService
    , private toastrService: NbToastrService
    , private chRef: ChangeDetectorRef
  ) {
  }

  ngOnInit(): void {
    if (this.input) {
      this.getData(this.input);
    } else {
      if (this.inputShopId) {
        this.setShop(this.inputShopId);
      }
    }
  }

  setShop(shopId: any) {
    if (shopId) {
      this.create.shopId = parseInt(shopId.toString());
      this.create.shop = new SupplierDto(this.create.shopId, "", "");
    }
  }


  getData(id: number) {
    this.loading = true;
    this.shopOrderService.getShopOrderById(id).subscribe({
      next: (res: ResponseDto) => {
        if (res && res.status === HttpStatusCode.Ok) {
          this.create = <ShopOrderDto>res.data;
          this.initStep = this.create.step;
          // this.chRef.detectChanges();
        } else {
          this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOPORDER);
          console.log('getShopOrderById ', res);
        }
      },
      error: (err: any) => {
        this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPORDER);
        console.error(err);
      },
      complete: () => {
        this.loading = false;
      }
    })
  }

  cancel() {
    if (this.initStep === this.create.step) {
      this.dialogRef.close({event: 'close'});
    }
    {
      this.dialogRef.close({event: 'save'});
    }
  }

  save(shopOrder: ShopOrderDto) {
    this.loading = true;
    this.shopOrderService.createOrEditShopOrder(shopOrder).subscribe({
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
          this.dialogRef.close({event: 'save'});
        }
      }
    )
  }

  copy(shopOrder: ShopOrderDto) {
    if (confirm('Are you sure copy ' + shopOrder.code + ' ?')) {
      shopOrder.id = null;
      shopOrder.code = null;
      shopOrder.step = ShopOrderStepConst.INIT;
      shopOrder.shopOrderDetails.forEach(p => {
        p.id = null;
        p.shopOrderId = null;
      })
      this.save(shopOrder);
    }
  }

  checkValidDetails() {
    this.supplySelectedDetailInvalid = this.create.shopOrderDetails.every(function (p) {
      return !p.supplyId ? false : true;
    });
    this.duplicateDetailInvalid = new Set(this.create.shopOrderDetails.map(v => v.supplyId)).size !== this.create.shopOrderDetails.length ? false : true;
  }
}
