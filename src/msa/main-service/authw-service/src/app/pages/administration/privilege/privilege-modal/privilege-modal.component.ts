import {Component, Input, OnInit} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {PrivilegeService} from '../../../../shared/services/app/privilege/privilege.service';
import {PrivilegeDto} from '../../../../shared/services/app/privilege/dto/privilege-dto';
import {RoleService} from '../../../../shared/services/app/role/role.service';
import {RoleDto} from '../../../../shared/services/app/role/dto/role-dto';
import * as _ from 'lodash';
import {ResponseDto} from '../../../../shared/models/response-dto';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';

@Component({
  selector: 'ngx-privilege-modal',
  templateUrl: './privilege-modal.component.html',
  styleUrls: ['./privilege-modal.component.scss']
})

export class PrivilegeModalComponent implements OnInit {

  @Input() input: number;
  create: PrivilegeDto = new PrivilegeDto();
  roles: RoleDto[] = [];
  defaultRoleCheckedStatus = false;
  checkedRolesMap: { [key: string]: boolean } = {};
  loading: boolean = false;

  constructor(protected dialogRef: NbDialogRef<PrivilegeModalComponent>
    , private roleService: RoleService
    , private privilegeService: PrivilegeService
    , private toastrService: NbToastrService
  ) {
  }

  ngOnInit(): void {
    this.getAllRole();
    if (this.input) {
      this.getData(this.input);
    }
  }

  getData(id: number) {
    this.loading = true;
    this.privilegeService.getPrivilegeById(id).subscribe({
        // next: (res: PrivilegeDto) => {
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.create = <PrivilegeDto>res.data;
            this.setInitialRolesStatus();
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.PRIVILEGE);
            console.error('getPrivilegeById ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.PRIVILEGE);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        }
      }
    )
  }

  getAllRole(): void {
    this.loading = true;
    this.roleService.getAllRole().subscribe({
      next: (res: ResponseDto) => {
        if (res && res.status === HttpStatusCode.Ok) {
          this.roles = <RoleDto[]>res.data;
          this.setInitialRolesStatus();
        } else {
          this.toastrService.warning('Error ' + res.message, PrivilegeConst.ROLE);
          console.error('getPrivilegeById ', res);
        }
      },
      error: (err: any) => {
        this.toastrService.danger('Error ' + err.status, PrivilegeConst.PRIVILEGE);
        console.error(err);
      },
      complete: () => {
        this.loading = false;
      }
    })
  }

  setInitialRolesStatus(): void {
    _.map(this.roles, item => {
      this.checkedRolesMap[item.name] = this.isRoleChecked(
        item.name
      );
    });
  }

  isRoleChecked(name: string): boolean {
    // just return default role checked status
    // it's better to use a setting
    if (this.create.id) {
      // let roleString = Object.assign(this, this.create.roles.map(x => x.name));
      return _.includes(this.create.privilegesString, name);
    } else {
      return this.defaultRoleCheckedStatus;
    }
  }

  onRoleChange(role: RoleDto, $event: any) {
    this.checkedRolesMap[role.name] = $event.target.checked;
    console.error('this.checkedRolesMap ', this.checkedRolesMap);
  }

  getCheckedRoles(): string[] {
    const roles: string[] = [];
    _.forEach(this.checkedRolesMap, function (value, key) {
      if (value) {
        roles.push(key);
      }
    });
    return roles;
  }

  cancel() {
    this.dialogRef.close({event: 'close'});
  }

  save(privilege: PrivilegeDto) {
    this.loading = true;
    privilege.privilegesString = this.getCheckedRoles();// ['ROLE_USER'];
    console.error('save privilege', privilege);
    this.privilegeService.createOrEditPrivilege(privilege).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === HttpStatusCode.Ok) {
            this.toastrService.success('Saved successfully', PrivilegeConst.PRIVILEGE);
          } else {
            this.toastrService.warning('Error ' + res.message, PrivilegeConst.PRIVILEGE);
            console.log(res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.PRIVILEGE);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
          this.dialogRef.close({event: 'save'});
        }
      }
    )
  }


}
