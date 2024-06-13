import {AfterContentInit, Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'ngx-contact-type-select',
  templateUrl: './contact-type-select.component.html'
})

export class ContactTypeSelectComponent {

  @Input('ngModel') selectedContactTypeId: string;
  @Input('hideLabel') hideLabel: boolean = false;
  @Output('ngModelChange') selectedContactTypeEvent = new EventEmitter<string>();
  items: string[] = [];
  loading: boolean = false;

  constructor() {
    this.getContactTypeData();
  }

  getContactTypeData() {
    this.items.push("Order", "Issue");
  }

  onContactTypeChange(event: any) {
    this.selectedContactTypeEvent.emit(event);
  }
}
