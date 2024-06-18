import {Component, Input, OnInit} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {HttpStatusCode} from '@angular/common/http';
import {ShopExportService} from '../../../../../shared/services/shop/shop-export/shop-export.service';
import {ShopService} from '../../../../../shared/services/shop/shop/shop.service';
import {SupplierService} from '../../../../../shared/services/supplier/supplier.service';
import {SupplierDto} from '../../../../../shared/services/supplier/dto/supplier-dto';
import {ResponseDto} from '../../../../../shared/models/response-dto';
import {PrivilegeConst} from '../../../../../shared/constants/PrivilegeConst';
import {ShopExportDto} from '../../../../../shared/services/shop/shop-export/dto/shop-export-dto';

@Component({
  selector: 'ngx-shop-dt-export-modal',
  templateUrl: './shop-dt-export-modal.component.html'
})

export class ShopDtExportModalComponent implements OnInit {

  @Input() input: number;
  @Input() inputShopId: number;
  create: ShopExportDto = new ShopExportDto();
  loading: boolean = false;

  constructor(protected dialogRef: NbDialogRef<ShopDtExportModalComponent>
    , private shopExportService: ShopExportService
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
      this.create.shopId = parseInt(shopId.toString());
      this.create.shop = new SupplierDto(this.create.shopId, "", "");
    }
  }

  getData(id: number) {
    this.loading = true;
    this.shopExportService.getShopExportById(id).subscribe({
      next: (res: ResponseDto) => {
        if (res && res.status === HttpStatusCode.Ok) {
          this.create = <ShopExportDto>res.data;
          // this.chRef.detectChanges();
        } else {
          this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOPEXPORT);
          console.log('getShopExportById ', res);
        }
      },
      error: (err: any) => {
        this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPEXPORT);
        console.error(err);
      },
      complete: () => {
        this.loading = false;
      }
    })
  }

  save(shopExport: ShopExportDto) {
    this.loading = true;
    this.shopExportService.createOrEditShopExport(shopExport).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.SHOPEXPORT);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOPEXPORT);
            console.log('createOrEditShopExport ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPEXPORT);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
          this.dialogRef.close({event: 'save'});
        }
      }
    )
  }

  cancel() {
    this.dialogRef.close({event: 'close'});
  }


}
