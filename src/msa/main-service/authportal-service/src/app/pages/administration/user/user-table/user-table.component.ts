import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {DataTableDirective} from 'angular-datatables';
import {environment} from '../../../../../environments/environment';
import {HttpStatusCode} from '@angular/common/http';
import {NbDateService, NbDialogService, NbToastrService} from '@nebular/theme';
import {UserModalComponent} from '../user-modal/user-modal.component';
import {UserDto} from '../../../../shared/services/app/user/dto/user-dto';
import {UserService} from '../../../../shared/services/app/user/user.service';
import {DatatablesOutputDto} from '../../../../shared/models/dataTables/datatables-output-dto';

import * as moment from 'moment';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ResponseDto} from '../../../../shared/models/response-dto';

@Component({
  selector: 'ngx-user-table',
  templateUrl: './user-table.component.html',
  styleUrls: ['./user-table.component.scss']
})

export class UserTableComponent implements OnInit, AfterViewInit {

  @ViewChild(DataTableDirective, {static: false})
  dtEle: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  rangePickerDate = {
    start: moment().add(-365, 'days'),
    end: moment(),
  };

  constructor(private userService: UserService
    , private dialogService: NbDialogService
    , private toastrService: NbToastrService
    , private dateService: NbDateService<Date>) {

  }

  ngOnInit(): void {
    this.loadDataTable();
  }

  ngAfterViewInit(): void {
    this.dtEle.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.columns().every(function () {
        const that = this;
        $('input', this.footer()).on('keyup change', function (e) {
          if (e.keyCode === 13) {
            if (that.search() !== this['value']) {
              that.search(this['value']).draw();
            }
          }
        });
      });
    });
  }

  loadDataTable(): void {
    this.dtOptions = {
      retrieve: true,
      processing: true,
      responsive: true,
      serverSide: true,
      order: [[1, 'desc']],
      ajax: (dataTablesParameters: any, callback) => {
        if (this.rangePickerDate) {
          dataTablesParameters.filter = {
            fromDate: this.rangePickerDate ? moment(this.rangePickerDate.start).format('YYYYMMDD') : null,
            toDate: this.rangePickerDate ? moment(this.rangePickerDate.end).format('YYYYMMDD') : null,
          };
        }
        this.userService.getAllUserPaging(dataTablesParameters)
          .subscribe({
            next: (res: ResponseDto) => {
              callback(<DatatablesOutputDto>res.data);
            },
            error: (err: any) => {
              this.toastrService.danger('Error ' + err.status, PrivilegeConst.USER);
              console.error(err);
            }
          });
      },
      dom: "<'row'<'col-md-4'l><'col-md-8'>><'row'<'col-md-12'tr>><'row'<'col-md-5'i><'col-md-7'p>>",
      lengthMenu: [5, 10, 25, 50, 100, 250, 500, 1000],
      pageLength: 5,
      columns: [
        {
          title: 'Action',
          data: 'id',
          render: function (data, type, row, meta) {
            let edit = '<button class="btn btn-outline-success btn-edit" style="margin-right: 0.5rem;" title="Edit"><i class="fa fa-edit" aria-hidden="true"></i></button>';
            let del = '<button class="btn btn-outline-danger btn-delete" title="Delete"><i class="fa fa-trash" aria-hidden="true"></i></button>';
            return '<div class="btn-group">' + edit + del + '</div>';
          },
          orderable: false,
          className: 'text-center',
          width: '10%'
        },
        {
          title: 'Id',
          data: 'id',
          width: '10%',
        },
        {
          title: 'Username',
          data: "username",
        },
        {
          title: 'Full name',
          data: "fullName",
        },
        {
          title: 'Email',
          data: "email",
        },
        {
          title: 'Roles',
          data: "rolesString",
          orderable: false,
        },
        {
          title: 'Created date',
          data: 'createdDate',
          render: function (data, type, row, meta) {
            let val = data ? moment(data).format('DD/MM/YYYY') : '';
            return val;
          },
          orderable: false,
        },
      ],
      rowCallback: (row: Node, data: any[] | Object, index: number) => {
        const self = this;
        $('td', row).off('click');
        $('td .btn-edit', row).on('click', () => {
          self.openModal(data['id']);
        });
        $('td .btn-delete', row).on('click', () => {
          self.delete(<UserDto>data);
        });
        return row;
      },
    };
  }

  reloadDataTable() {
    this.dtEle.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload()
    });
  }

  delete(input: UserDto): void {
    if (confirm("Are you sure to delete " + input.username + ' ?')) {
      this.userService.deleteUserById(input.id).subscribe({
          next: (res: ResponseDto) => {
            if (res && res.status === HttpStatusCode.Ok) {
              this.toastrService.success('Deleted successfully', PrivilegeConst.USER);
              this.reloadDataTable();
            } else {
              this.toastrService.warning('Error ' + res.message, PrivilegeConst.USER);
              console.log('deleteUserById ', res);
            }
          },
          error: (err: any) => {
            this.toastrService.danger('Error ' + err.status, PrivilegeConst.USER);
            console.error(err);
          },
          complete: () => {
          }
        }
      )
    }
  }

  openModal(input: any): void {
    this.dialogService.open(UserModalComponent, {
      context: {
        input: input,
      }
    })
      .onClose.subscribe((res) => {
      if (res && res.event && res.event === 'save') {
        this.reloadDataTable();
      }
    });
  }

  onDateChange(event: any): void {
    // console.log('event ', event);
    // console.log('start ', moment(event.start).format('DD/MM/YYYY'));
    // console.log('end ', moment(event.end).format('DD/MM/YYYY'));
  }

}
