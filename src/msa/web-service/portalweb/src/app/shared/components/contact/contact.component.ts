import {Component, Input, OnInit} from '@angular/core';
import {ContactDto} from '../../services/contact/dto/contact-dto';
import {NbToastrService} from '@nebular/theme';
import {ContactService} from '../../services/contact/contact.service';
import {ResponseDto} from '../../models/response-dto';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../constants/PrivilegeConst';

@Component({
  selector: 'ngx-contact',
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})

export class ContactComponent implements OnInit {

  @Input('ngModel') contacts: ContactDto[];
  @Input('shopId') shopId: number | null;
  @Input('supplierId') supplierId: number | null;
  loading: boolean = false;

  constructor(private contactService: ContactService
    , private toastrService: NbToastrService) {
  }

  ngOnInit(): void {
  }

  addContact(event: any) {
    event.stopPropagation();
    this.contacts.push(new ContactDto(null, '', this.shopId, this.supplierId));
  }

  deleteContact(index: number, input: ContactDto) {
    if (input.id) {
      if (confirm("Are you sure to delete " + input.name + ' ?')) {
        this.contactService.deleteContactById(input.id).subscribe({
            next: (res: ResponseDto) => {
              if (res && res.status === HttpStatusCode.Ok) {
                this.contacts.splice(index, 1);
                this.toastrService.success('Deleted successfully', PrivilegeConst.CONTACT);
              } else {
                this.toastrService.warning('Error ' + res.message, PrivilegeConst.CONTACT);
              }
            },
            error: (err: any) => {
              this.toastrService.danger('Error ' + err.status, PrivilegeConst.CONTACT);
              console.error(err);
            },
            complete: () => {
            }
          }
        )
      } else {
      }
    } else {
      this.contacts.splice(index, 1);
    }
  }


}
