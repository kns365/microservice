import {Component, OnInit} from '@angular/core';
import {ShopService} from '../../../shared/services/shop/shop/shop.service';
import {NbToastrService} from '@nebular/theme';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'ngx-shop-dt',
  templateUrl: './shop-dt.component.html',
  styleUrls: ['./shop-dt.component.scss']
})

export class ShopDtComponent implements OnInit {
  shopIdParam: number = 0;
  selectedTabIndex: number = 0;
  tabs = [{
    title: 'Info',
    route: `/pages/shop/${this.shopIdParam}/info`,
    index: 0,
  }];
  loading: boolean = false;

  constructor(private shopSerivce: ShopService
    , private toastrService: NbToastrService
    , private activedRoute: ActivatedRoute
    , private router: Router
  ) {
  }

  ngOnInit(): void {
    this.shopIdParam = this.router.url.split('/')[3] ? parseInt(this.router.url.split('/')[3]) : 0;
    if (this.shopIdParam > 0) {
      this.tabs = [
        {
          title: 'Info',
          route: `/pages/shop/${this.shopIdParam}/info`,
          index: 0,
        },
        {
          title: 'Contract',
          route: `/pages/shop/${this.shopIdParam}/contract`,
          index: 1,
        },
        {
          title: 'Order',
          route: `/pages/shop/${this.shopIdParam}/order`,
          index: 2,
        },
        {
          title: 'Import',
          route: `/pages/shop/${this.shopIdParam}/import`,
          index: 3,
        },
        {
          title: 'import detail',
          route: `/pages/shop/${this.shopIdParam}/importDetail`,
          index: 4,
        },
        {
          title: 'Export',
          route: `/pages/shop/${this.shopIdParam}/export`,
          index: 5,
        }
      ];
    }
  }

  onChangeTab(event: any) {
    this.selectedTabIndex = event.index;
  }

}
