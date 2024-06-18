import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {HttpStatusCode} from '@angular/common/http';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {ResponseDto} from '../../../models/response-dto';
import {Router} from '@angular/router';
import {Observable, of} from 'rxjs';
import * as removeAccents from 'remove-accents';
import {SupplyDto} from '../../../services/category/supply/dto/supply-dto';
import {SupplyService} from '../../../services/category/supply/supply.service';

@Component({
  selector: 'ngx-supply-autocomplete',
  templateUrl: './supply-autocomplete.component.html'
})

export class SupplyAutocompleteComponent implements OnInit, OnChanges {

  @Input('ngModel') selectedSupplyCode: string;
  @Input('hideLabel') hideLabel: boolean = false;
  @Input('disabled') disabled: boolean = false;
  @Input('required') required: boolean = false;
  @Input('invalid') invalid: boolean = true;
  @Output('ngModelChange') selectedSupplyEvent = new EventEmitter<number>();
  items: SupplyDto[] = [];
  loading: boolean = false;
  valueInput: string = '';
  filteredItems$: Observable<SupplyDto[]>;

  constructor(private supplyService: SupplyService
    , private toastrService: NbToastrService
    , private dialogService: NbDialogService
    , private router: Router
  ) {
  }

  ngOnInit(): void {
    this.getSupplyData();
    if (this.required) {
      this.invalid = false;
    }
  }

  ngOnChanges(changes: SimpleChanges): void {
    // if (changes && changes.selectedSupplyCode && changes.selectedSupplyCode.currentValue) {
    //   this.setSupply(changes.selectedSupplyCode.currentValue);
    // }
  }

  setSupply(code: string) {
    if (code) {
      this.valueInput = code;
    }
  }

  getSupplyData() {
    this.loading = true;
    this.supplyService.getAllSupply(null).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.items = <SupplyDto[]>res.data;
            this.filteredItems$ = of(this.items);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.SUPPLY);
            console.log('getAllSupply ', res);
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
    )
  }

  onSupplyChange(event: any) {
    if (event) {
      this.selectedSupplyEvent.emit(event);
    }
  }

  filter(value: string): SupplyDto[] {
    const filterVal = value.toLowerCase();
    return this.items.filter(optVal => removeAccents(optVal.item.code).toLowerCase().includes(filterVal) || removeAccents(optVal.item.name).toLowerCase().includes(filterVal));
  }

  onModelChange(event: string) {
    this.filteredItems$ = of(this.filter(event));
    this.onSupplyChange(event);
  }

  openDetailNewTab(input: any): void {
    let url = '';
    if (input) {
      url = this.router.serializeUrl(this.router.createUrlTree(['/pages/supply', input, 'info']));
    } else {
      url = this.router.serializeUrl(this.router.createUrlTree(['/pages/supply', 0, 'info']));
    }
    window.open(url, '_blank');
  }
}
