import {Component, Input, OnInit} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {HttpStatusCode} from '@angular/common/http';
import {ShopImportDto} from '../../../../../shared/services/shop/shop-import/dto/shop-import-dto';
import {ShopImportService} from '../../../../../shared/services/shop/shop-import/shop-import.service';
import {ShopService} from '../../../../../shared/services/shop/shop/shop.service';
import {SupplierService} from '../../../../../shared/services/supplier/supplier.service';
import {SupplierDto} from '../../../../../shared/services/supplier/dto/supplier-dto';
import {ResponseDto} from '../../../../../shared/models/response-dto';
import {PrivilegeConst} from '../../../../../shared/constants/PrivilegeConst';

@Component({
  selector: 'ngx-shop-dt-import-modal',
  templateUrl: './shop-dt-import-modal.component.html'
})

export class ShopDtImportModalComponent implements OnInit {

  @Input() input: number;
  @Input() inputShopId: number;
  create: ShopImportDto = new ShopImportDto();
  loading: boolean = false;

  constructor(protected dialogRef: NbDialogRef<ShopDtImportModalComponent>
    , private shopImportService: ShopImportService
    , private shopService: ShopService
    , private supplierService: SupplierService
    , private toastrService: NbToastrService
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
      this.create.shopOrder.shopId = parseInt(shopId.toString());
      this.create.shopOrder.shop = new SupplierDto(this.create.shopOrder.shopId, "", "");
    }
  }

  getData(id: number) {
    this.loading = true;
    this.shopImportService.getShopImportById(id).subscribe({
      next: (res: ResponseDto) => {
        if (res && res.status === HttpStatusCode.Ok) {
          this.create = <ShopImportDto>res.data;
          // this.chRef.detectChanges();
        } else {
          this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOPIMPORT);
          console.log('getShopImportById ', res);
        }
      },
      error: (err: any) => {
        this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPIMPORT);
        console.error(err);
      },
      complete: () => {
        this.loading = false;
      }
    })
  }

  cancel() {
    this.dialogRef.close({event: 'close'});
  }


}
