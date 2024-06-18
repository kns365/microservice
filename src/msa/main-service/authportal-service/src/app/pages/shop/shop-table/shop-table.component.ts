import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {DataTableDirective} from 'angular-datatables';
import {environment} from '../../../../environments/environment';
import {NbDateService, NbDialogService, NbToastrService} from '@nebular/theme';
import {ResponseDto} from '../../../shared/models/response-dto';
import {DatatablesOutputDto} from '../../../shared/models/dataTables/datatables-output-dto';
import {PrivilegeConst} from '../../../shared/constants/PrivilegeConst';
import {HttpStatusCode} from '@angular/common/http';
import {ShopService} from '../../../shared/services/shop/shop/shop.service';
import * as moment from 'moment';
import {ShopDto} from '../../../shared/services/shop/shop/dto/shop-dto';
import {Router} from '@angular/router';

@Component({
  selector: 'ngx-shop-table',
  templateUrl: './shop-table.component.html'
})

export class ShopTableComponent implements OnInit, AfterViewInit {

  @ViewChild(DataTableDirective, {static: false})
  dtEle: DataTableDirective;
  dtOptions: DataTables.Settings = {};

  constructor(private shopService: ShopService
    , private dialogService: NbDialogService
    , private toastrService: NbToastrService
    , private dateService: NbDateService<Date>
    , private router: Router) {

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
        this.shopService.getAllShopPaging(dataTablesParameters)
          .subscribe({
            next: (res: ResponseDto) => {
              callback(<DatatablesOutputDto>res.data);
            },
            error: (err: any) => {
              this.toastrService.danger('Error ' + err.status, PrivilegeConst.COUNTRY);
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
        {
          title: 'Name',
          data: "name",
        },
        {
          title: 'Country',
          data: 'country',
          render: function (data, type, row, meta) {
            let val = data ? data.name : '';
            return val;
          },
          orderable: false,
        },
        {
          title: 'Province',
          data: 'province',
          render: function (data, type, row, meta) {
            let val = data ? data.name : '';
            return val;
          },
          orderable: false,
        },
        {
          title: 'District',
          data: 'district',
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
          self.openDetail(data['id']);
        });
        $('td .btn-delete', row).on('click', () => {
          self.delete(<ShopDto>data);
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

  delete(input: ShopDto): void {
    if (confirm("Are you sure to delete " + input.name + ' ?')) {
      this.shopService.deleteShopById(input.id).subscribe({
          next: (res: ResponseDto) => {
            if (res && res.status === HttpStatusCode.Ok) {
              this.toastrService.success('Deleted successfully', PrivilegeConst.COUNTRY);
              this.reloadDataTable();
            } else {
              this.toastrService.warning('Error ' + res.message, PrivilegeConst.COUNTRY);
              console.log('createOrEditShop ', res);
            }
          },
          error: (err: any) => {
            this.toastrService.danger('Error ' + err.status, PrivilegeConst.COUNTRY);
            console.error(err);
          },
          complete: () => {
          }
        }
      )
    }
  }

  openDetail(input: any): void {
    if (input) {
      this.router.navigate(['/pages/shop', input, 'info'])
    } else {
      this.router.navigate(['/pages/shop', 0, 'info'])
    }
  }


}


