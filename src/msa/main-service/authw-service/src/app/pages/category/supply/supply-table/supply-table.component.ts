import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {DataTableDirective} from 'angular-datatables';
import {NbDialogService, NbToastrService} from '@nebular/theme';
import {DatatablesOutputDto} from '../../../../shared/models/dataTables/datatables-output-dto';
import * as moment from 'moment';
import {SupplyModalComponent} from '../supply-modal/supply-modal.component';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ResponseDto} from '../../../../shared/models/response-dto';
import {ActivatedRoute} from '@angular/router';
import {SupplyService} from '../../../../shared/services/category/supply/supply.service';
import {SupplyDto} from '../../../../shared/services/category/supply/dto/supply-dto';

@Component({
  selector: 'ngx-supply-table',
  templateUrl: './supply-table.component.html'
})

export class SupplyTableComponent implements OnInit, AfterViewInit {

  @ViewChild(DataTableDirective, {static: false})
  dtEle: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  supplierIdParam: number;

  constructor(private itemService: SupplyService
    , private dialogService: NbDialogService
    , private toastrService: NbToastrService
    , private activedRoute: ActivatedRoute
  ) {

  }

  ngOnInit(): void {
    this.activedRoute.params.subscribe(params => {
      this.supplierIdParam = params['supplierId'];
    });
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
        // console.log('dataTablesParameters ', dataTablesParameters);
        if (this.supplierIdParam && this.supplierIdParam > 0) {
          let supplierIdIndex = dataTablesParameters.columns.findIndex(p => p.data === "supplierId");
          if (supplierIdIndex !== -1) {
            dataTablesParameters.columns[supplierIdIndex].search.value = this.supplierIdParam;
          }
        }
        this.itemService.getAllSupplyPaging(dataTablesParameters)
          .subscribe({
            next: (res: ResponseDto) => {
              callback(<DatatablesOutputDto>res.data);
            },
            error: (err: any) => {
              this.toastrService.danger('Error ' + err.status, PrivilegeConst.SUPPLY);
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
          title: 'Supplier',
          data: "supplier",
          render: function (data, type, row, meta) {
            let val = data ? data.name : '';
            return val;
          },
          orderable: false,
          visible: this.supplierIdParam && this.supplierIdParam > 0 ? false : true
        },
        {
          title: 'SupplierId',
          data: "supplierId",
          orderable: false,
          visible: false
        },
        {
          title: 'Price',
          data: "price",
          render: $.fn.dataTable.render.number(',', ',', 0, '')
        },
        {
          title: 'Group item',
          data: "item",
          render: function (data, type, row, meta) {
            let val = data && data.groupItem ? data.groupItem.name : '';
            return val;
          },
          orderable: false,
        },
        {
          title: 'Item',
          data: "item",
          render: function (data, type, row, meta) {
            let val = data ? data.name : '';
            return val;
          },
          orderable: false,
        },
        {
          title: 'Unit',
          data: "unit",
          render: function (data, type, row, meta) {
            let val = data ? data.name : '';
            return val;
          },
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
          self.delete(<SupplyDto>data);
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

  delete(input: SupplyDto): void {
    if (confirm("Are you sure to delete " + input.id + ' ?')) {
      this.itemService.deleteSupplyById(input.id).subscribe({
          next: (res: ResponseDto) => {
            if (res && res.status === HttpStatusCode.Ok) {
              this.toastrService.success('Deleted successfully', PrivilegeConst.SUPPLY);
              this.reloadDataTable();
            } else {
              this.toastrService.warning('Error ' + res.message, PrivilegeConst.SUPPLY);
              console.log('deleteSupplyById ', res);
            }
          },
          error: (err: any) => {
            this.toastrService.danger('Error ' + err.status, PrivilegeConst.SUPPLY);
            console.error(err);
          },
          complete: () => {
          }
        }
      )
    }
  }

  openModal(input: any): void {
    this.dialogService.open(SupplyModalComponent, {
      context: {
        input: input,
        inputSupplierId: this.supplierIdParam ? this.supplierIdParam : null,
      }
    })
      .onClose.subscribe((res) => {
      if (res && res.event && res.event === 'save') {
        this.reloadDataTable();
      }
    });
  }

}
