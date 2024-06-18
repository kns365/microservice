import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {HttpStatusCode} from '@angular/common/http';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {ResponseDto} from '../../../models/response-dto';
import {Router} from '@angular/router';
import {Observable, of} from 'rxjs';
import * as removeAccents from 'remove-accents';
import {SupplyDto} from '../../../services/category/supply/dto/supply-dto';
import {SupplyService} from '../../../services/category/supply/supply.service';
import {ShopImportDetailService} from '../../../services/shop/shop-import-detail/shop-import-detail.service';
import {ContactDto} from '../../../services/contact/dto/contact-dto';
import {ShopExportDetailService} from '../../../services/shop/shop-export-detail/shop-export-detail.service';
import {ShopExportDetailDto} from '../../../services/shop/shop-export-detail/dto/shop-export-detail-dto';

@Component({
  selector: 'ngx-supply-input-dt',
  templateUrl: './supply-input-dt.component.html'
})

export class SupplyInputDtComponent implements OnInit, OnChanges {

  // @Input('ngModel') supplies: SupplyDto[];
  @Input('ngModel') shopExportDetails: ShopExportDetailDto[];
  @Input('hideLabel') hideLabel: boolean = false;
  @Input('disabled') disabled: boolean = false;
  @Input('required') required: boolean = false;
  @Input('invalid') invalid: boolean = true;
  @Output('ngModelChange') selectedSupplyEvent = new EventEmitter<SupplyDto>();
  items: SupplyDto[] = [];
  loading: boolean = false;
  valueInput: string = '';
  filteredItems$: Observable<SupplyDto[]>;
  shopIdParam: number = 0;

  constructor(private shopExportDetailService: ShopExportDetailService
    , private toastrService: NbToastrService
    , private dialogService: NbDialogService
    , private router: Router
  ) {
  }

  ngOnInit(): void {
    this.shopIdParam = this.router.url.split('/')[3] ? parseInt(this.router.url.split('/')[3]) : 0;
    // this.getSupplyData();
    // if (this.required) {
    //   this.invalid = false;
    // }
  }

  ngOnChanges(changes: SimpleChanges): void {
    // if (changes && changes.selectedSupplyCode && changes.selectedSupplyCode.currentValue) {
    //   this.setSupply(changes.selectedSupplyCode.currentValue);
    // }
  }


  deleteSupply(index: number, input: ShopExportDetailDto) {
    if (input.id) {
      if (confirm("Are you sure to delete " + input.supply.barcode + ' ?')) {
        this.shopExportDetailService.deleteShopExportDetailById(input.id).subscribe({
            next: (res: ResponseDto) => {
              if (res && res.status === HttpStatusCode.Ok) {
                this.shopExportDetails.splice(index, 1);
                this.toastrService.success('Deleted successfully', PrivilegeConst.SHOPEXPORTDETAIL);
              } else {
                this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOPEXPORTDETAIL);
              }
            },
            error: (err: any) => {
              this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPEXPORTDETAIL);
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
    }
  }


  getSupplyData() {
    this.loading = true;
    /*this.shopImportDetailService.getAllSupplylByShopId(this.shopIdParam).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.items = <SupplyDto[]>res.data;
            this.filteredItems$ = of(this.items);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.SUPPLY);
            console.log('getAllSupplylByShopId ', res);
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
    )*/
  }

  setSupply(code: string) {
    if (code) {
      this.valueInput = code;
    }
  }

  // addSupply() {
  //   if (this.valueInput) {
  //     console.log('addSupply this.valueInput ', this.valueInput);
  //     console.log('addSupply this.selectedSupply ', this.selectedSupply);
  //     this.selectedSupplyEvent.emit(this.selectedSupply);
  //   }
  // }

  onSupplyChange(event: any) {
    debugger
    if (event) {
      this.selectedSupplyEvent.emit(event);
    }
  }

  // filter(value: string): SupplyDto[] {
  //   const filterVal = value.toLowerCase();
  //   let output = this.items.filter(optVal => removeAccents(optVal.item.code).toLowerCase().includes(filterVal) || removeAccents(optVal.item.name).toLowerCase().includes(filterVal));
  //   console.log('filter output ', output);
  //   if (output.length == 1) {
  //     if (this.valueInput == output[0].item.code) {
  //       this.selectedSupply = output[0];
  //     }
  //   } else {
  //     this.selectedSupply = null;
  //   }
  //   return output;
  // }

  // onFilteredChange(event: string) {
  //   this.filteredItems$ = of(this.filter(event));
  //   this.onSupplyChange(event);
  // }

}
