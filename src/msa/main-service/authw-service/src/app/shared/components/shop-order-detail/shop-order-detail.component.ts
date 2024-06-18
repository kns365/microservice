import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {NbToastrService} from '@nebular/theme';
import {ResponseDto} from '../../models/response-dto';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../constants/PrivilegeConst';
import {ShopOrderDetailDto} from '../../services/shop/shop-order-detail/dto/shop-order-detail-dto';
import {ShopOrderDetailService} from '../../services/shop/shop-order-detail/shop-order-detail.service';
import {SupplyService} from '../../services/category/supply/supply.service';
import {SupplyDto} from '../../services/category/supply/dto/supply-dto';
import {ShopDto} from '../../services/shop/shop/dto/shop-dto';
import {SupplierDto} from '../../services/supplier/dto/supplier-dto';

@Component({
  selector: 'ngx-shop-order-detail',
  templateUrl: './shop-order-detail.component.html',
  styleUrls: ['./shop-order-detail.component.scss']
})

export class ShopOrderDetailComponent implements OnInit, OnChanges {

  @Input('hideEdit') hideEdit: boolean = false;
  @Input('ngModel') shopOrderDetails: ShopOrderDetailDto[];
  @Input('shop') shop: ShopDto;
  @Input('supplier') supplier: SupplierDto;
  @Output('ngModelChange') shopOrderDetailsEvent = new EventEmitter<boolean>();
  loading: boolean = false;
  sumAmount: number = 0;
  sumQuantity: number = 0;

  constructor(private shopOrderDetailService: ShopOrderDetailService,
              private supplyService: SupplyService,
              private toastrService: NbToastrService
  ) {
  }

  ngOnInit(): void {
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes && changes.shopOrderDetails) {
      this.calculateSumAmount();
    }
  }

  addShopOrderDetail(event: any) {
    event.stopPropagation();
    this.shopOrderDetails.push(new ShopOrderDetailDto());
    this.calculateSumAmount();
  }

  deleteShopOrderDetail(index: number, input: ShopOrderDetailDto) {
    if (input.id) {
      if (confirm("Are you sure to delete " + input.supply.item.code + '-' + input.supply.item.name + ' ?')) {
        this.shopOrderDetailService.deleteShopOrderDetailById(input.id).subscribe({
            next: (res: ResponseDto) => {
              if (res && res.status === HttpStatusCode.Ok) {
                this.shopOrderDetails.splice(index, 1);
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
      this.shopOrderDetails.splice(index, 1);
      this.calculateSumAmount();
    }
  }

  calculateSumAmount() {
    this.sumAmount = this.shopOrderDetails.reduce((a, b) => a + (b.quantity * b.price), 0);
    this.sumQuantity = this.shopOrderDetails.reduce((a, b) => a + b.quantity * 1, 0);
    this.onShopOrderDetailsChange();
  }

  onShopOrderDetailsChange() {
    this.shopOrderDetailsEvent.emit(true);
  }
}
