import {ChangeDetectorRef, Component, Input, OnInit} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {ItemDto} from '../../../../shared/services/category/item/dto/item-dto';
import {ItemService} from '../../../../shared/services/category/item/item.service';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ResponseDto} from '../../../../shared/models/response-dto';

@Component({
  selector: 'ngx-item-modal',
  templateUrl: './item-modal.component.html',
})

export class ItemModalComponent implements OnInit {

  @Input() input: number;
  @Input() inputSupplierId: number;
  create: ItemDto = new ItemDto();
  loading: boolean = false;

  constructor(protected dialogRef: NbDialogRef<ItemModalComponent>
    , private itemService: ItemService
    , private toastrService: NbToastrService
    , private chRef: ChangeDetectorRef
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
      // this.chRef.detectChanges(); //Whenever you need to force update view
    }
  }

  getData(id: number) {
    this.loading = true;
    this.itemService.getItemById(id).subscribe({
      next: (res: ResponseDto) => {
        if (res && res.status === HttpStatusCode.Ok) {
          this.create = <ItemDto>res.data;
        } else {
          this.toastrService.warning('Error ' + res.message, PrivilegeConst.ITEM);
          console.log('getItemById ', res);
        }
      },
      error: (err: any) => {
        this.toastrService.danger('Error ' + err.status, PrivilegeConst.ITEM);
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

  save(item: ItemDto) {
    this.loading = true;
    this.itemService.createOrEditItem(item).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.ITEM);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.ITEM);
            console.log('createOrEditItem ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.ITEM);
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
