import {Component, OnInit} from '@angular/core';
import {SupplierService} from '../../../shared/services/supplier/supplier.service';
import {NbToastrService} from '@nebular/theme';
import {ActivatedRoute, Router} from '@angular/router';

@Component({
  selector: 'ngx-supplier-dt',
  templateUrl: './supplier-dt.component.html',
  styleUrls: ['./supplier-dt.component.scss']
})

export class SupplierDtComponent implements OnInit {
  supplierIdParam: number = 0;
  selectedTabIndex: number = 0;
  tabs = [{
    title: 'Info',
    route: `/pages/supplier/${this.supplierIdParam}/info`,
    index: 0,
  },];
  loading: boolean = false;

  constructor(private supplierSerivce: SupplierService
    , private toastrService: NbToastrService
    , private activedRoute: ActivatedRoute
    , private router: Router
  ) {
  }

  ngOnInit(): void {
    this.supplierIdParam = this.router.url.split('/')[3] ? parseInt(this.router.url.split('/')[3]) : 0;
    if (this.supplierIdParam > 0) {
      this.tabs = [
        {
          title: 'Info',
          route: `/pages/supplier/${this.supplierIdParam}/info`,
          index: 0,
        },
        {
          title: 'Contract',
          route: `/pages/supplier/${this.supplierIdParam}/contract`,
          index: 1,
        },
        {
          title: 'Supply',
          route: `/pages/supplier/${this.supplierIdParam}/supply`,
          index: 2,
        },
        {
          title: 'Shop order',
          route: `/pages/supplier/${this.supplierIdParam}/shopOrder`,
          index: 3,
        },
      ];
    }
  }

  onChangeTab(event: any) {
    this.selectedTabIndex = event.index;
  }

}
