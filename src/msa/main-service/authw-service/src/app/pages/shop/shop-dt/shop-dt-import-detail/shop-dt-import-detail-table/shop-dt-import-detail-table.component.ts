import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {DataTableDirective} from 'angular-datatables';
import {NbDateService, NbDialogService, NbToastrService} from '@nebular/theme';
import {HttpStatusCode} from '@angular/common/http';
import {ActivatedRoute} from '@angular/router';
import {ShopDtImportDetailModalComponent} from '../shop-dt-import-detail-modal/shop-dt-import-detail-modal.component';

import * as moment from 'moment';
import * as jsbarcode from 'jsbarcode';
import {ShopImportDetailService} from '../../../../../shared/services/shop/shop-import-detail/shop-import-detail.service';
import {DatatablesOutputDto} from '../../../../../shared/models/dataTables/datatables-output-dto';
import {ResponseDto} from '../../../../../shared/models/response-dto';
import {PrivilegeConst} from '../../../../../shared/constants/PrivilegeConst';
import {ShopImportDetailDto} from '../../../../../shared/services/shop/shop-import-detail/dto/shop-import-detail-dto';

@Component({
  selector: 'ngx-shop-dt-import-detail-table',
  templateUrl: './shop-dt-import-detail-table.component.html'
})

export class ShopDtImportDetailTableComponent implements OnInit, AfterViewInit {

  @ViewChild(DataTableDirective, {static: false})
  dtEle: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  shopIdParam: number;

  constructor(private shopImportDetailService: ShopImportDetailService
    , private dialogService: NbDialogService
    , private toastrService: NbToastrService
    , private dateService: NbDateService<Date>
    , private activedRoute: ActivatedRoute
  ) {

  }

  ngOnInit(): void {
    this.activedRoute.params.subscribe(params => {
      this.shopIdParam = params['shopId'];
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
        $('#shopImportDetailStepSelect', this.footer()).on('change', function (e) {
          if (that.search() !== this['value']) {
            that.search($('#shopImportDetailStepSelect').find(":selected").val().toString()).draw();
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
        if (this.shopIdParam && this.shopIdParam > 0) {
          let shopIdIndex = dataTablesParameters.columns.findIndex(p => p.data === "shopId");
          if (shopIdIndex !== -1) {
            dataTablesParameters.columns[shopIdIndex].search.value = this.shopIdParam;
          }
        }
        this.shopImportDetailService.getAllShopImportDetailPaging(dataTablesParameters)
          .subscribe({
            next: (res: ResponseDto) => {
              callback(<DatatablesOutputDto>res.data);
              jsbarcode(".barcode").init();
            },
            error: (err: any) => {
              this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPIMPORTDETAIL);
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
            // let del = '<button class="btn btn-outline-danger btn-delete" title="Delete"><i class="fa fa-trash" aria-hidden="true"></i></button>';
            return '<div class="btn-group">' + edit + '</div>';
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
          title: 'Shop order',
          data: "shopImport.shopOrder.code",
          render: function (data, type, row, meta) {
            let val = data ? data : '';
            return val;
          }
        },
        {
          title: 'Shop import',
          data: "shopImport.code",
          render: function (data, type, row, meta) {
            let val = data ? data : '';
            return val;
          }
        },
        {
          title: 'Supplier',
          data: "supplier",
          render: function (data, type, row, meta) {
            let val = data ? (data.code + '-' + data.name) : '';
            return val;
          }
        },
        {
          title: 'Item',
          data: "supply.item",
          render: function (data, type, row, meta) {
            let val = data ? (data.code + '-' + data.name) : '';
            return val;
          }
        },
        {
          title: 'Supply barcode',
          data: "supply.barcode",
          render: function (data, type, row, meta) {
            let val = data ? ('<svg class="barcode" jsbarcode-format="CODE128" jsbarcode-width="1" jsbarcode-height="50" jsbarcode-margin="0" jsbarcode-value="' + data + '" ></svg>') : '';
            return val;
          }
        },
        {
          title: 'Quantity',
          data: "quantity",
          render: $.fn.dataTable.render.number(',', ',', 0, '')
        },
        {
          title: 'Price',
          data: "price",
          render: $.fn.dataTable.render.number(',', ',', 0, '')
        },
        {
          title: 'Shop',
          data: "shop",
          render: function (data, type, row, meta) {
            let val = data ? data.name : '';
            return val;
          },
          orderable: false,
          visible: this.shopIdParam && this.shopIdParam > 0 ? false : true
        },
        {
          title: 'Shop id',
          data: "shopId",
          orderable: false,
          visible: false
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
          self.delete(<ShopImportDetailDto>data);
        });
        return row;
      },
      initComplete: (settings, json) => {
        // jsbarcode(".barcode").init();
      }
    };
  }

  reloadDataTable() {
    this.dtEle.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload()
    });
  }

  delete(input: ShopImportDetailDto): void {
    if (confirm("Are you sure to delete " + input.supply.barcode + ' ?')) {
      this.shopImportDetailService.deleteShopImportDetailById(input.id).subscribe({
          next: (res: ResponseDto) => {
            if (res && res.status === HttpStatusCode.Ok) {
              this.toastrService.success('Deleted successfully', PrivilegeConst.SHOPIMPORTDETAIL);
              this.reloadDataTable();
            } else {
              this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOPIMPORTDETAIL);
              console.log('deleteShopImportDetailById ', res);
            }
          },
          error: (err: any) => {
            this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPIMPORTDETAIL);
            console.error(err);
          },
          complete: () => {
          }
        }
      )
    }
  }

  openModal(input: any): void {
    this.dialogService.open(ShopDtImportDetailModalComponent, {
      context: {
        input: input,
        inputShopId: this.shopIdParam ? this.shopIdParam : null,
      }
    })
      .onClose.subscribe((res) => {
      if (res && res.event && res.event === 'save') {
        this.reloadDataTable();
      }
    });
  }

}
