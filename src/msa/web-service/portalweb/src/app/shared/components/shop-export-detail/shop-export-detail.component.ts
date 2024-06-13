import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {NbToastrService} from '@nebular/theme';
import {ResponseDto} from '../../models/response-dto';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../constants/PrivilegeConst';
import {SupplyService} from '../../services/category/supply/supply.service';
import {SupplyDto} from '../../services/category/supply/dto/supply-dto';
import {ShopDto} from '../../services/shop/shop/dto/shop-dto';
import {SupplierDto} from '../../services/supplier/dto/supplier-dto';
import {ShopExportDetailDto} from '../../services/shop/shop-export-detail/dto/shop-export-detail-dto';
import {ShopExportDetailService} from '../../services/shop/shop-export-detail/shop-export-detail.service';

@Component({
  selector: 'ngx-shop-export-detail',
  templateUrl: './shop-export-detail.component.html',
  styleUrls: ['./shop-export-detail.component.scss']
})

export class ShopExportDetailComponent implements OnInit, OnChanges {

  @Input('hideEdit') hideEdit: boolean = false;
  @Input('ngModel') shopExportDetails: ShopExportDetailDto[];
  @Output('ngModelChange') shopExportDetailsEvent = new EventEmitter<boolean>();
  loading: boolean = false;
  sumAmount: number = 0;
  sumQuantity: number = 0;

  constructor(private shopExportDetailService: ShopExportDetailService,
              private toastrService: NbToastrService
  ) {
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes && changes.shopExportDetails) {
      this.calculateSumAmount();
    }
  }

  addShopExportDetail(event: any) {
    event.stopPropagation();
    this.shopExportDetails.push(new ShopExportDetailDto());
    this.calculateSumAmount();
  }

  deleteShopExportDetail(index: number, input: ShopExportDetailDto) {
    if (input.id) {
      if (confirm("Are you sure to delete " + input.supply.item.code + '-' + input.supply.item.name + ' ?')) {
        this.shopExportDetailService.deleteShopExportDetailById(input.id).subscribe({
            next: (res: ResponseDto) => {
              if (res && res.status === HttpStatusCode.Ok) {
                this.shopExportDetails.splice(index, 1);
                this.calculateSumAmount();
                this.toastrService.success('Deleted successfully', PrivilegeConst.SHOPORDERDETAIL);
              } else {
                this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOPORDERDETAIL);
              }
            },
            error: (err: any) => {
              this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPORDERDETAIL);
              console.error(err);
            },
            complete: () => {
            }
          }
        )
      } else {
      }
    } else {
      this.shopExportDetails.splice(index, 1);
      this.calculateSumAmount();
    }
  }

  calculateSumAmount() {
    this.sumAmount = this.shopExportDetails.reduce((a, b) => a + (b.quantity * b.price), 0);
    this.sumQuantity = this.shopExportDetails.reduce((a, b) => a + b.quantity * 1, 0);
    this.onShopExportDetailsChange();
  }

  onShopExportDetailsChange() {
    this.shopExportDetailsEvent.emit(true);
  }
}
