import {AfterContentInit, Component, OnInit} from '@angular/core';
import {ShopDto} from '../../../../shared/services/shop/shop/dto/shop-dto';
import {ContactDto} from '../../../../shared/services/contact/dto/contact-dto';
import {ShopService} from '../../../../shared/services/shop/shop/shop.service';
import {NbToastrService} from '@nebular/theme';
import {ResponseDto} from '../../../../shared/models/response-dto';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ActivatedRoute, ParamMap, Router} from '@angular/router';

@Component({
  selector: 'ngx-shop-dt-info',
  templateUrl: './shop-dt-info.component.html',
  styleUrls: ['./shop-dt-info.component.scss']
})
export class ShopDtInfoComponent implements OnInit,AfterContentInit {
  loading: boolean = false;
  create: ShopDto = new ShopDto();
  shopIdParam: number;

  constructor(private shopSerivce: ShopService
    , private toastrService: NbToastrService
    , private router: Router
    , private activedRoute: ActivatedRoute
  ) {

  }

  ngOnInit(): void {
    this.activedRoute.params.subscribe(params => {
      this.shopIdParam = params['shopId'];
      // if (this.shopIdParam && this.shopIdParam > 0) {
      //   this.getData(this.shopIdParam);
      // }
    });
  }

  ngAfterContentInit(): void {
    if (this.shopIdParam && this.shopIdParam > 0) {
      this.getData(this.shopIdParam);
    }
  }

  getData(id: number) {
    this.loading = true;
    this.shopSerivce.getShopById(id).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.create = <ShopDto>res.data;
            console.log('getShopById ', this.create);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOP);
            console.log('getShopById ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOP);
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
      if (id) {
        this.router.navigateByUrl('/', {skipLocationChange: true}).then(() => {
          this.router.navigate(['/pages/shop', id, 'info'])
        });
      } else {
        this.router.navigate(['/pages/supplier']);
      }
    } else {
      this.router.navigate(['/pages/shop']);
    }
  }

  save() {
    this.loading = true;
    console.log('this.create ', this.create);
    this.shopSerivce.createOrEditShop(this.create).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.SHOP);
            if (res.data) {
              this.goto(parseInt(res.data.toString()));
            }
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.SHOP);
            console.log('createOrEditShop ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.SHOP);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        }
      }
    )
  }

}
