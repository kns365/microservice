import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges, ViewChild} from '@angular/core';
import {HttpStatusCode} from '@angular/common/http';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {ResponseDto} from '../../../models/response-dto';
import {SupplierDto} from '../../../services/supplier/dto/supplier-dto';
import {SupplierService} from '../../../services/supplier/supplier.service';
import {Router} from '@angular/router';
import {Observable, of} from 'rxjs';
import {map} from 'rxjs/operators';
import * as removeAccents from 'remove-accents';

@Component({
  selector: 'ngx-supplier-autocomplete',
  templateUrl: './supplier-autocomplete.component.html'
})

export class SupplierAutocompleteComponent implements OnInit, OnChanges {

  @Input('ngModel') selectedSupplierCode: string;
  @Input('hideLabel') hideLabel: boolean = false;
  @Input('disabled') disabled: boolean = false;
  @Input('required') required: boolean = false;
  @Input('invalid') invalid: boolean = true;
  @Output('ngModelChange') selectedSupplierEvent = new EventEmitter<number>();
  items: SupplierDto[] = [];
  loading: boolean = false;
  valueInput: string = '';
  filteredItems$: Observable<SupplierDto[]>;

  constructor(private supplierService: SupplierService
    , private toastrService: NbToastrService
    , private dialogService: NbDialogService
    , private router: Router
  ) {
  }

  ngOnInit(): void {
    this.getSupplierData();
    if (this.required) {
      this.invalid = false;
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes && changes.selectedSupplierCode && changes.selectedSupplierCode.currentValue) {
      this.setSupplierCode(changes.selectedSupplierCode.currentValue);
    }
  }

  setSupplierCode(code: string) {
    if (code) {
      this.valueInput = code;
    }
  }

  getSupplierData() {
    this.loading = true;
    this.supplierService.getAllSupplier().subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.items = <SupplierDto[]>res.data;
            this.filteredItems$ = of(this.items);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.SUPPLIER);
            console.log('getAllSupplier ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.SUPPLIER);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        }
      }
    )
  }

  onSupplierChange(event: any) {
    if (event) {
      this.selectedSupplierEvent.emit(event);
    }
  }

  filter(value: string): SupplierDto[] {
    const filterVal = value.toLowerCase();
    return this.items.filter(optVal => removeAccents(optVal.code).toLowerCase().includes(filterVal) || removeAccents(optVal.name).toLowerCase().includes(filterVal));
  }

  onModelChange(event: string) {
    this.filteredItems$ = of(this.filter(event));
    this.onSupplierChange(event);
  }

  openDetailNewTab(input: any): void {
    let url = '';
    if (input) {
      url = this.router.serializeUrl(this.router.createUrlTree(['/pages/supplier', input, 'info']));
    } else {
      url = this.router.serializeUrl(this.router.createUrlTree(['/pages/supplier', 0, 'info']));
    }
    window.open(url, '_blank');
  }
}
