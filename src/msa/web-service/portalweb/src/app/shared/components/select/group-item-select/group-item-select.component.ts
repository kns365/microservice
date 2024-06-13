import {Component, EventEmitter, Input, OnInit, Output} from '@angular/core';
import {GroupItemDto} from '../../../services/category/group-item/dto/group-item-dto';
import {GroupItemService} from '../../../services/category/group-item/group-item.service';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {ResponseDto} from '../../../models/response-dto';
import {DistrictModalComponent} from '../../../../pages/category/district/district-modal/district-modal.component';

@Component({
  selector: 'ngx-group-item-select',
  templateUrl: './group-item-select.component.html'
})

export class GroupItemSelectComponent implements OnInit {

  @Input('hideLabel') hideLabel: boolean = false;
  @Input('hideAdd') hideAdd: boolean = false;
  @Input('valid') valid: boolean = true;
  @Input('disabled') disabled: boolean = false;
  @Input('ngModel') selectedGroupItem: GroupItemDto;
  @Output('ngModelChange') selectedGroupItemEvent = new EventEmitter<GroupItemDto>();
  groupItems: GroupItemDto[] = [];
  loading: boolean = false;

  constructor(private groupItemService: GroupItemService
    , private toastrService: NbToastrService
    , private dialogService: NbDialogService) {
  }

  ngOnInit(): void {
    this.getGroupItemData();
  }

  onSelectedGroupItemChange(event: any) {
    this.selectedGroupItemEvent.emit(event);
  }

  getGroupItemData() {
    this.loading = true;
    this.groupItemService.getAllGroupItem().subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.groupItems = <GroupItemDto[]>res.data;
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.GROUPITEM);
            console.log('getPrivilegeById ', res);
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

  openModal(input: any): void {
    this.dialogService.open(DistrictModalComponent, {
      context: {
        input: input,
      }
    })
      .onClose.subscribe((res) => {
      if (res && res.event && res.event === 'save') {
        this.getGroupItemData();
      }
    });
  }
}
