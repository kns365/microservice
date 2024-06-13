import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {DataTableDirective} from 'angular-datatables';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {DatatablesOutputDto} from '../../../../shared/models/dataTables/datatables-output-dto';
import * as moment from 'moment';
import {UnitModalComponent} from '../unit-modal/unit-modal.component';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ResponseDto} from '../../../../shared/models/response-dto';
import {UnitService} from '../../../../shared/services/category/unit/unit.service';
import {UnitDto} from '../../../../shared/services/category/unit/dto/unit-dto';

@Component({
  selector: 'ngx-unit-table',
  templateUrl: './unit-table.component.html'
})

export class UnitTableComponent implements OnInit, AfterViewInit {

  @ViewChild(DataTableDirective, {static: false})
  dtEle: DataTableDirective;
  dtOptions: DataTables.Settings = {};

  constructor(private unitService: UnitService
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
        this.unitService.getAllUnitPaging(dataTablesParameters)
          .subscribe({
            next: (res: ResponseDto) => {
              callback(<DatatablesOutputDto>res.data);
            },
            error: (err: any) => {
              this.toastrService.danger('Error ' + err.status, PrivilegeConst.UNIT);
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
          title: 'Name',
          data: "name",
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
          self.delete(<UnitDto>data);
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

  delete(input: UnitDto): void {
    if (confirm("Are you sure to delete " + input.name + ' ?')) {
      this.unitService.deleteUnitById(input.id).subscribe({
          next: (res: ResponseDto) => {
            if (res && res.status === HttpStatusCode.Ok) {
              this.toastrService.success('Deleted successfully', PrivilegeConst.UNIT);
              this.reloadDataTable();
            } else {
              this.toastrService.warning('Error ' + res.message, PrivilegeConst.UNIT);
              console.log('deleteUnitById ', res);
            }
          },
          error: (err: any) => {
            this.toastrService.danger('Error ' + err.status, PrivilegeConst.UNIT);
            console.error(err);
          },
          complete: () => {
          }
        }
      )
    }
  }

  openModal(input: any): void {
    this.dialogService.open(UnitModalComponent, {
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

}
