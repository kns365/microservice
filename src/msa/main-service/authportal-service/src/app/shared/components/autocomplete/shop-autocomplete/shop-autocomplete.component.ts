import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {HttpStatusCode} from '@angular/common/http';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {ResponseDto} from '../../../models/response-dto';
import {ShopDto} from '../../../services/shop/shop/dto/shop-dto';
import {ShopService} from '../../../services/shop/shop/shop.service';
import {Router} from '@angular/router';
import {Observable, of} from 'rxjs';
import * as removeAccents from 'remove-accents';

@Component({
  selector: 'ngx-shop-autocomplete',
  templateUrl: './shop-autocomplete.component.html'
})

export class ShopAutocompleteComponent implements OnInit, OnChanges {

  @Input('hideLabel') hideLabel: boolean = false;
  @Input('valid') valid: boolean = false;
  @Input('disabled') disabled: boolean = false;
  @Input('ngModel') selectedShopCode: string;
  @Output('ngModelChange') selectedShopEvent = new EventEmitter<number>();
  items: ShopDto[] = [];
  loading: boolean = false;
  valueInput: string = '';
  filteredItems$: Observable<ShopDto[]>;

  constructor(private shopService: ShopService
    , private toastrService: NbToastrService
    , private dialogService: NbDialogService
    , private router: Router
  ) {
  }

  ngOnInit(): void {
    this.getShopData();
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes && changes.selectedShopCode && changes.selectedShopCode.currentValue) {
      this.setShop(changes.selectedShopCode.currentValue);
    }
  }


  setShop(code: string) {
    if (code) {
      this.valueInput = code;
    }
  }

  getShopData() {
    this.loading = true;
    this.shopService.getAllShop().subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.items = <ShopDto[]>res.data;
            this.filteredItems$ = of(this.items);
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

  onShopChange(event: any) {
    if (event) {
      this.selectedShopEvent.emit(event);
    }
  }

  filter(value: string): ShopDto[] {
    const filterVal = value.toLowerCase();
    return this.items.filter(optVal => removeAccents(optVal.code).toLowerCase().includes(filterVal) || removeAccents(optVal.name).toLowerCase().includes(filterVal));
  }

  onModelChange(event: string) {
    this.filteredItems$ = of(this.filter(event));
    this.onShopChange(event);
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
