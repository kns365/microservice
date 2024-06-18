import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {DataTableDirective} from 'angular-datatables';
import {NbDateService, NbDialogService, NbToastrService} from '@nebular/theme';
import * as moment from 'moment';
import {ShopDtExportModalComponent} from '../shop-dt-export-modal/shop-dt-export-modal.component';
import {HttpStatusCode} from '@angular/common/http';
import {ShopExportService} from '../../../../../shared/services/shop/shop-export/shop-export.service';
import {ActivatedRoute} from '@angular/router';
import {DatatablesOutputDto} from '../../../../../shared/models/dataTables/datatables-output-dto';
import {PrivilegeConst} from '../../../../../shared/constants/PrivilegeConst';
import {ShopExportDto} from '../../../../../shared/services/shop/shop-export/dto/shop-export-dto';
import {ResponseDto} from '../../../../../shared/models/response-dto';
import {SupplyDto} from '../../../../../shared/services/category/supply/dto/supply-dto';
import {ShopExportDetailDto} from '../../../../../shared/services/shop/shop-export-detail/dto/shop-export-detail-dto';

@Component({
  selector: 'ngx-shop-dt-export-table',
  templateUrl: './shop-dt-export-table.component.html'
})

export class ShopDtExportTableComponent implements OnInit, AfterViewInit {

  @ViewChild(DataTableDirective, {static: false})
  dtEle: DataTableDirective;
  dtOptions: DataTables.Settings = {};
  shopIdParam: number;

  selectedSupply: SupplyDto;
  shopExportDetails: ShopExportDetailDto[] = [];

  constructor(private shopExportService: ShopExportService
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

  onSelectedSupplyChange(event: SupplyDto) {
    if (event && event.id) {
      console.log('parent onSelectedSupplyChange ', event);
      this.shopExportDetails.push(new ShopExportDetailDto(event));
    }
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
        $('#shopExportStepSelect', this.footer()).on('change', function (e) {
          if (that.search() !== this['value']) {
            that.search($('#shopExportStepSelect').find(":selected").val().toString()).draw();
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
        this.shopExportService.getAllShopExportPaging(dataTablesParameters)
          .subscribe({
            next: (res: ResponseDto) => {
              callback(<DatatablesOutputDto>res.data);
            },
            error: (err: any) => {
              this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPEXPORT);
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
          title: 'Code',
          data: "code",
        },
        // {
        //   title: 'Supply',
        //   data: "shopOrder",
        //   render: function (data, type, row, meta) {
        //     let val = data ? data.supply.name : '';
        //     return val;
        //   },
        //   orderable: false,
        // },
        {
          title: 'Shop',
          data: "shopOrder",
          render: function (data, type, row, meta) {
            let val = data ? data.shop.name : '';
            return val;
          },
          orderable: false,
        },
        {
          title: 'Supplier',
          data: "shopOrder",
          render: function (data, type, row, meta) {
            let val = data ? data.supplier.name : '';
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
          self.delete(<ShopExportDto>data);
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

  delete(input: ShopExportDto): void {
    if (confirm("Are you sure to delete " + input.code + ' ?')) {
      this.shopExportService.deleteShopExportById(input.id).subscribe({
          next: (res: ResponseDto) => {
            if (res && res.status === HttpStatusCode.Ok) {
              this.toastrService.success('Deleted successfully', PrivilegeConst.SHOPEXPORT);
              this.reloadDataTable();
            } else {
              this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOPEXPORT);
              console.log('deleteShopExportById ', res);
            }
          },
          error: (err: any) => {
            this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOPEXPORT);
            console.error(err);
          },
          complete: () => {
          }
        }
      )
    }
  }

  openModal(input: any): void {
    this.dialogService.open(ShopDtExportModalComponent, {
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
