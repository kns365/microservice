import {AfterContentInit, Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'ngx-alias-select',
  templateUrl: './alias-select.component.html'
})

export class AliasSelectComponent {

  @Input('ngModel') selectedAliasId: string;
  @Input('hideLabel') hideLabel: boolean = false;
  @Output('ngModelChange') selectedAliasEvent = new EventEmitter<string>();
  items: string[] = [];
  loading: boolean = false;

  constructor() {
    this.getAliasData();
  }

  getAliasData() {
    this.items.push("MR", "MRS");
  }

  onAliasChange() {
    this.selectedAliasEvent.emit(this.selectedAliasId);
  }
}
