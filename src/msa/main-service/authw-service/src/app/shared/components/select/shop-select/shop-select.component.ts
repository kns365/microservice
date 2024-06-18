import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {HttpStatusCode} from '@angular/common/http';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {ResponseDto} from '../../../models/response-dto';
import {ShopDto} from '../../../services/shop/shop/dto/shop-dto';
import {ShopService} from '../../../services/shop/shop/shop.service';
import {Router} from '@angular/router';

@Component({
  selector: 'ngx-shop-select',
  templateUrl: './shop-select.component.html'
})

export class ShopSelectComponent implements OnInit {

  @Input('hideLabel') hideLabel: boolean = false;
  @Input('hideEdit') hideEdit: boolean = false;
  @Input('valid') valid: boolean = false;
  @Input('disabled') disabled: boolean = false;
  @Input('ngModel') selectedShop: ShopDto;
  @Output('ngModelChange') selectedShopEvent = new EventEmitter<ShopDto>();
  shops: ShopDto[] = [];
  loading: boolean = false;

  constructor(private shopService: ShopService
    , private toastrService: NbToastrService
    , private dialogService: NbDialogService
    , private router: Router
  ) {
  }

  ngOnInit(): void {
    if (!this.hideEdit) {
      this.getShopData();
    }
  }

  onSelectedShopChange(event: any) {
    this.selectedShopEvent.emit(event);
  }

  getShopData() {
    this.loading = true;
    this.shopService.getAllShop().subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.shops = <ShopDto[]>res.data;
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOP);
            console.log('getAllShop ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOP);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        }
      }
    )
  }

  openDetailNewTab(input: any): void {
    let url = '';
    if (input) {
      url = this.router.serializeUrl(this.router.createUrlTree(['/pages/shop', input, 'info']));
    } else {
      url = this.router.serializeUrl(this.router.createUrlTree(['/pages/shop', 0, 'info']));
    }
    window.open(url, '_blank');
  }
}
