import {AfterContentInit, AfterViewInit, Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {HttpStatusCode} from '@angular/common/http';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {ResponseDto} from '../../../models/response-dto';
import {SupplierDto} from '../../../services/supplier/dto/supplier-dto';
import {SupplierService} from '../../../services/supplier/supplier.service';
import {Router} from '@angular/router';

@Component({
  selector: 'ngx-supplier-select',
  templateUrl: './supplier-select.component.html'
})

export class SupplierSelectComponent implements OnInit {

  @Input('hideLabel') hideLabel: boolean = false;
  @Input('hideEdit') hideEdit: boolean = false;
  @Input('valid') valid: boolean = false;
  @Input('disabled') disabled: boolean = false;
  @Input('ngModel') selectedSupplier: SupplierDto;
  @Output('ngModelChange') selectedSupplierEvent = new EventEmitter<SupplierDto>();
  suppliers: SupplierDto[] = [];
  loading: boolean = false;

  constructor(private supplierService: SupplierService
    , private toastrService: NbToastrService
    , private dialogService: NbDialogService
    , private router: Router
  ) {
  }

  ngOnInit(): void {
    if (!this.hideEdit) {
      this.getSupplierData();
    }
  }

  onSelectedSupplierChange(event: any) {
    this.selectedSupplierEvent.emit(event);
  }

  getSupplierData() {
    this.loading = true;
    this.supplierService.getAllSupplier().subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.suppliers = <SupplierDto[]>res.data;
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
