import {Component, EventEmitter, Input, OnChanges, OnInit, Output, SimpleChanges} from '@angular/core';
import {ItemDto} from '../../../services/category/item/dto/item-dto';
import {ItemService} from '../../../services/category/item/item.service';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../constants/PrivilegeConst';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {ResponseDto} from '../../../models/response-dto';
import {ItemModalComponent} from '../../../../pages/category/item/item-modal/item-modal.component';
import {GroupItemDto} from '../../../services/category/group-item/dto/group-item-dto';

@Component({
  selector: 'ngx-item-select',
  templateUrl: './item-select.component.html'
})

export class ItemSelectComponent implements OnInit, OnChanges {

  @Input('hideLabel') hideLabel: boolean = false;
  @Input('hideAdd') hideAdd: boolean = false;
  @Input('required') required: boolean = false;
  @Input('valid') valid: boolean = false;
  @Input('disabled') disabled: boolean = false;
  @Input('selectedGroupItemId') selectedGroupItem: GroupItemDto;
  @Input('ngModel') selectedItem: ItemDto;
  @Output('ngModelChange') selectedItemEvent = new EventEmitter<ItemDto>();
  items: ItemDto[] = [];
  loading: boolean = false;

  constructor(private itemService: ItemService
    , private toastrService: NbToastrService
    , private dialogService: NbDialogService) {
  }

  ngOnInit(): void {
    this.getItemData(this.selectedGroupItem.id);
  }

  ngOnChanges(changes: SimpleChanges): void {
    if (changes && changes.selectedGroupItem) {
      if (!changes.selectedGroupItem.firstChange) {
        if (changes.selectedGroupItem.currentValue) {
          this.getItemData(changes.selectedGroupItem.currentValue.id);
        }
      }
    }
  }

  onSelectedItemChange(event: any) {
    this.selectedItemEvent.emit(event);
  }

  getItemData(groupItemId: number | null) {
    this.loading = true;
    this.itemService.getAllItem(groupItemId).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.items = <ItemDto[]>res.data;
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.ITEM);
            console.log('getAllItem ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.ITEM);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        }
      }
    )
  }

  openModal(input: any): void {
    this.dialogService.open(ItemModalComponent, {
      context: {
        input: input,
      }
    })
      .onClose.subscribe((res) => {
      if (res && res.event && res.event === 'save') {
        this.getItemData(this.selectedGroupItem.id);
      }
    });
  }
}


