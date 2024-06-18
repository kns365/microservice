import {Component, EventEmitter, Input, Output} from '@angular/core';
import {ShopOrderStepConst} from '../../../constants/ShopOrderStepConst';

@Component({
  selector: 'ngx-shop-order-step-select',
  templateUrl: './shop-order-step-select.component.html'
})

export class ShopOrderStepSelectComponent {

  @Input('ngModel') selectedShopOrderStepId: number = -1;
  @Input('hideLabel') hideLabel: boolean = false;
  @Output('ngModelChange') selectedShopOrderStepEvent = new EventEmitter<number>();
  items: any;
  loading: boolean = false;

  constructor() {
    this.getShopOrderStepData();
  }

  getShopOrderStepData() {
    this.items = ShopOrderStepConst
  }

  onShopOrderStepChange() {
    this.selectedShopOrderStepEvent.emit(this.selectedShopOrderStepId);
  }
}
