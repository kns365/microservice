import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {DataTableDirective} from 'angular-datatables';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {DatatablesOutputDto} from '../../../../shared/models/dataTables/datatables-output-dto';
import * as moment from 'moment';
import {AuditLogModalComponent} from '../audit-log-modal/audit-log-modal.component';
import {AuditLogService} from '../../../../shared/services/app/audit-log/audit-log.service';
import {ResponseDto} from '../../../../shared/models/response-dto';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';

@Component({
  selector: 'ngx-audit-log-table',
  templateUrl: './audit-log-table.component.html'
})

export class AuditLogTableComponent implements OnInit, AfterViewInit {

  @ViewChild(DataTableDirective, {static: false})
  dtEle: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  rangePickerDate = {
    start: moment().add(-30, 'days'),
    end: moment(),
  };

  constructor(private auditLogService: AuditLogService
    , private dialogService: NbDialogService
    , private toastrService: NbToastrService) {
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
        this.auditLogService.getAllAuditLogPaging(dataTablesParameters)
          .subscribe({
            next: (res: ResponseDto) => {
              callback(<DatatablesOutputDto>res.data);
            },
            error: (err: any) => {
              this.toastrService.danger('Error ' + err.status, PrivilegeConst.AUDITLOG);
              console.error(err);
            }
          });
      },
      dom: "<'row'<'col-md-4'l><'col-md-8'>><'row'<'col-md-12'tr>><'row'<'col-md-5'i><'col-md-7'p>>",
      lengthMenu: [5, 10, 25, 50, 100, 250, 500, 1000],
      pageLength: 10,
      columns: [
        {
          title: 'Action',
          data: 'id',
          render: function (data, type, row, meta) {
            let httpStatus = row.exception || !row.httpStatus || row.httpStatus != 200 ? 'btn-outline-danger' : 'btn-outline-info';
            let view = '<button nbButton class="btn ' + httpStatus + ' btn-view" title="View"><i class="fa fa-info"></i></button>';
            return '<div class="">' + view + '</div>';
          },
          orderable: false,
          className: 'text-center',
          width: '5%'
        },
        {
          title: 'Id',
          data: 'id',
          width: '5%',
        },
        {
          title: 'Status',
          data: "httpStatus",
          orderable: false,
          width: '5%',
        },
        {
          title: 'Created by',
          data: "createdBy",
          orderable: false,
        },
        {
          title: 'Path',
          data: "path",
          orderable: false,
        },
        {
          title: 'Client ip address',
          data: "clientIpAddress",
          orderable: false,
        },
        {
          title: 'Client name',
          data: "clientName",
          orderable: false,
        },
        {
          title: 'Duration',
          data: "execDuration",
          orderable: false,
          width: '8%',
        },
        {
          title: 'Created date',
          data: 'createdDate',
          render: function (data, type, row, meta) {
            let val = data ? moment(data).format('DD/MM/YYYY HH:mm:ss') : '';
            let timeAgo = data ? moment(data).fromNow() : '';
            // return val;
            return '<div title="' + timeAgo + '">' + val + '</div>';
          },
          orderable: false,
        },
      ],
      rowCallback: (row: Node, data: any[] | Object, index: number) => {
        const self = this;
        $('td', row).off('click');
        $('td .btn-view', row).on('click', () => {
          self.openModal(data['id']);
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

  openModal(input: any): void {
    this.dialogService.open(AuditLogModalComponent, {
      context: {
        input: input,
      },
      hasBackdrop: true,
      closeOnBackdropClick: false,
      closeOnEsc: true,
      hasScroll: true
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
