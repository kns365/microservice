import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ResponseDto} from '../../../../shared/models/response-dto';
import {SupplyDto} from '../../../../shared/services/category/supply/dto/supply-dto';
import {SupplyService} from '../../../../shared/services/category/supply/supply.service';
import {SupplierDto} from '../../../../shared/services/supplier/dto/supplier-dto';

@Component({
  selector: 'ngx-supply-modal',
  templateUrl: './supply-modal.component.html'
})

export class SupplyModalComponent implements OnInit {

  @Input() input: number;
  @Input() inputSupplierId: number;
  create: SupplyDto = new SupplyDto();
  loading: boolean = false;

  constructor(protected dialogRef: NbDialogRef<SupplyModalComponent>
    , private supplyService: SupplyService
    , private toastrService: NbToastrService
  ) {
  }

  ngOnInit(): void {
    if (this.input) {
      this.getData(this.input);
    } else {
      if (this.inputSupplierId) {
        this.setSupplier(this.inputSupplierId);
      }
    }
  }

  setSupplier(supplierId: any) {
    if (supplierId) {
      this.create.supplierId = parseInt(supplierId.toString());
      this.create.supplier = new SupplierDto(this.create.supplierId, "", "");
    }
  }

  getData(id: number) {
    this.loading = true;
    this.supplyService.getSupplyById(id).subscribe({
      next: (res: ResponseDto) => {
        if (res && res.status === HttpStatusCode.Ok) {
          this.create = <SupplyDto>res.data;
        } else {
          this.toastrService.warning('Error ' + res.message, PrivilegeConst.SUPPLY);
          console.log('getSupplyById ', res);
        }
      },
      error: (err: any) => {
        this.toastrService.danger('Error ' + err.status, PrivilegeConst.SUPPLY);
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

  save(supply: SupplyDto) {
    this.loading = true;
    this.supplyService.createOrEditSupply(supply).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.SUPPLY);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.SUPPLY);
            console.log('createOrEditSupply ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.SUPPLY);
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
