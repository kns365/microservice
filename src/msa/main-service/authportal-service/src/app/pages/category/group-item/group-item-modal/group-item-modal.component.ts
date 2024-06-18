import {Component, Input, OnInit} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {GroupItemDto} from '../../../../shared/services/category/group-item/dto/group-item-dto';
import {GroupItemService} from '../../../../shared/services/category/group-item/group-item.service';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ResponseDto} from '../../../../shared/models/response-dto';

@Component({
  selector: 'ngx-group-item-modal',
  templateUrl: './group-item-modal.component.html'
})

export class GroupItemModalComponent implements OnInit {

  @Input() input: number;
  create: GroupItemDto = new GroupItemDto();
  loading: boolean = false;

  constructor(protected dialogRef: NbDialogRef<GroupItemModalComponent>
    , private groupItemService: GroupItemService
    , private toastrService: NbToastrService
  ) {
  }

  ngOnInit(): void {
    if (this.input) {
      this.getData(this.input);
    }
  }

  getData(id: number) {
    this.loading = true;
    this.groupItemService.getGroupItemById(id).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.create = <GroupItemDto>res.data;
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.GROUPITEM);
            console.log('getGroupItemById ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.GROUPITEM);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        }
      }
    )
  }

  cancel() {
    this.dialogRef.close({event: 'close'});
  }

  save(groupItem: GroupItemDto) {
    this.loading = true;
    this.groupItemService.createOrEditGroupItem(groupItem).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.GROUPITEM);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.GROUPITEM);
            console.log('createOrEditGroupItem ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.GROUPITEM);
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
