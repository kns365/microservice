import {AfterContentInit, Component, OnInit} from '@angular/core';
import {SupplierDto} from '../../../../shared/services/supplier/dto/supplier-dto';
import {SupplierService} from '../../../../shared/services/supplier/supplier.service';
import {NbToastrService} from '@nebular/theme';
import {ResponseDto} from '../../../../shared/models/response-dto';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'ngx-supplier-dt-info',
  templateUrl: './supplier-dt-info.component.html',
  styleUrls: ['./supplier-dt-info.component.scss']
})

export class SupplierDtInfoComponent implements OnInit, AfterContentInit {
  loading: boolean = false;
  create: SupplierDto = new SupplierDto();
  supplierIdParam: number;

  constructor(private supplierSerivce: SupplierService
    , private toastrService: NbToastrService
    , private router: Router
    , private activedRoute: ActivatedRoute
  ) {

  }

  ngOnInit(): void {
    this.activedRoute.params.subscribe(params => {
      this.supplierIdParam = params['supplierId'];
      // if (this.supplierIdParam && this.supplierIdParam > 0) {
      //   this.getData(this.supplierIdParam);
      // }
    });
  }

  ngAfterContentInit(): void {
    if (this.supplierIdParam && this.supplierIdParam > 0) {
      this.getData(this.supplierIdParam);
    }
  }

  getData(id: number) {
    this.loading = true;
    this.supplierSerivce.getSupplierById(id).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.create = <SupplierDto>res.data;
            console.log('getSupplierById ', this.create);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.COUNTRY);
            console.log('getSupplierById ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.COUNTRY);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        }
      }
    )
  }

  goto(id: number) {
    if (id) {
      this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
        this.router.navigate(['/pages/supplier', id, 'info'])
      });
    } else {
      this.router.navigate(['/pages/supplier']);
    }
  }

  save() {
    this.loading = true;
    console.log('this.create ', this.create);
    this.supplierSerivce.createOrEditSupplier(this.create).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.SUPPLIER);
            if (res.data) {
              this.goto(parseInt(res.data.toString()));
            }
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.SUPPLIER);
            console.log('createOrEditSupplier ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.SUPPLIER);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        }
      }
    )
  }

}
