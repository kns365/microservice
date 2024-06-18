import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {HttpStatusCode} from '@angular/common/http';
import {ShopImportDetailDto} from '../../../../../shared/services/shop/shop-import-detail/dto/shop-import-detail-dto';
import {ShopImportDetailService} from '../../../../../shared/services/shop/shop-import-detail/shop-import-detail.service';
import {ShopService} from '../../../../../shared/services/shop/shop/shop.service';
import {SupplierService} from '../../../../../shared/services/supplier/supplier.service';
import {ResponseDto} from '../../../../../shared/models/response-dto';
import {PrivilegeConst} from '../../../../../shared/constants/PrivilegeConst';

@Component({
  selector: 'ngx-shop-dt-import-detail-modal',
  templateUrl: './shop-dt-import-detail-modal.component.html'
})

export class ShopDtImportDetailModalComponent implements OnInit {

  @Input() input: number;
  @Input() inputShopId: number;
  create: ShopImportDetailDto = new ShopImportDetailDto();
  loading: boolean = false;
  supplySelectedDetailInvalid: boolean = true;
  duplicateDetailInvalid: boolean = true;
  initStep: number;

  constructor(protected dialogRef: NbDialogRef<ShopDtImportDetailModalComponent>
    , private shopImportService: ShopImportDetailService
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
      // this.create.shopId = parseInt(shopId.toString());
      // this.create.shop = new SupplierDto(this.create.shopId, "", "");
    }
  }


  getData(id: number) {
    this.loading = true;
    this.shopImportService.getShopImportDetailById(id).subscribe({
      next: (res: ResponseDto) => {
        if (res && res.status === HttpStatusCode.Ok) {
          this.create = <ShopImportDetailDto>res.data;
          // this.chRef.detectChanges();
        } else {
          this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOPIMPORTDETAIL);
          console.log('getShopImportDetailById ', res);
        }
      },
      error: (err: any) => {
        this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPIMPORTDETAIL);
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

  save(shopImport: ShopImportDetailDto) {
    this.loading = true;
    this.shopImportService.createOrEditShopImportDetail(shopImport).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.SHOPIMPORTDETAIL);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOPIMPORTDETAIL);
            console.log('createOrEditShopImportDetail ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPIMPORTDETAIL);
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
