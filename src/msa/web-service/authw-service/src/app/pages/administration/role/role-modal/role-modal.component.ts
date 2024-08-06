import {Component, Input, OnInit} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {RoleDto} from '../../../../shared/services/app/role/dto/role-dto';
import {RoleService} from '../../../../shared/services/app/role/role.service';
import {PrivilegeService} from '../../../../shared/services/app/privilege/privilege.service';
import {PrivilegeDto} from '../../../../shared/services/app/privilege/dto/privilege-dto';
import * as _ from 'lodash';
import {ResponseDto} from '../../../../shared/models/response-dto';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ResponseStatus} from '../../../../shared/constants/ResponseStatus';

@Component({
  selector: 'ngx-role-modal',
  templateUrl: './role-modal.component.html',
  styleUrls: ['./role-modal.component.scss'],
})

export class RoleModalComponent implements OnInit {

  @Input() input: number;
  create: RoleDto = new RoleDto();
  privileges: PrivilegeDto[] = [];
  privilegesChecked: string[] = [];
  defaultPrivilegeCheckedStatus = false;
  checkedPrivilegesMap: { [key: string]: boolean } = {};
  loading: boolean = false;

  constructor(protected dialogRef: NbDialogRef<RoleModalComponent>
    , private roleService: RoleService
    , private privilegeService: PrivilegeService
    , private toastrService: NbToastrService,
  ) {
  }

  ngOnInit(): void {
    this.getAllPrivilege();
    if (this.input) {
      this.getData(this.input);
    }
  }

  getData(id: number) {
    this.loading = true;
    this.roleService.getRoleById(id).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === ResponseStatus.SUCCESS) {
            this.create = <RoleDto>res.data;
            this.setInitialPrivilegesStatus();
          } else {
            this.toastrService.warning('Error ' + res.errorMessage, PrivilegeConst.ROLE);
            console.error('getRoleById ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.ROLE);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        },
      },
    );
  }

  getAllPrivilege(): void {
    this.loading = true;
    this.privilegeService.getAllPrivilege().subscribe({
      next: (res: ResponseDto) => {
        if (res && res.status === ResponseStatus.SUCCESS) {
          this.privileges = <PrivilegeDto[]>res.data;
          this.setInitialPrivilegesStatus();
        } else {
          this.toastrService.warning('Error ' + res.errorMessage, PrivilegeConst.PRIVILEGE);
          console.error('getAllPrivilege ', res);
        }
      },
      error: (err: any) => {
        this.toastrService.danger('Error ' + err.status, PrivilegeConst.PRIVILEGE);
        console.error(err);
      },
      complete: () => {
        this.loading = false;
      },
    });
  }

  setInitialPrivilegesStatus(): void {
    _.map(this.privileges, item => {
      this.checkedPrivilegesMap[item.name] = this.isPrivilegeChecked(
        item.name,
      );
    });
    this.privilegesChecked = this.getCheckedPrivileges();
  }

  isPrivilegeChecked(name: string): boolean {
    // just return default role checked status
    // it's better to use a setting
    if (this.create.id) {
      // let roleString = Object.assign(this, this.create.roles.map(x => x.name));
      return _.includes(this.create.privilegesString, name);
    } else {
      return this.defaultPrivilegeCheckedStatus;
    }
  }

  onPrivilegeChange(input: RoleDto, $event: any) {
    this.checkedPrivilegesMap[input.name] = $event;
    this.privilegesChecked = this.getCheckedPrivileges();
  }

  checkAllPrivilegeChange($event: any) {
    _.map(this.privileges, item => {
      this.checkedPrivilegesMap[item.name] = $event;
    });
    this.privilegesChecked = this.getCheckedPrivileges();
  }

  cancel() {
    this.dialogRef.close({event: 'close'});
  }

  getCheckedPrivileges(): string[] {
    const privileges: string[] = [];
    _.forEach(this.checkedPrivilegesMap, function (value, key) {
      if (value) {
        privileges.push(key);
      }
    });
    return privileges;
  }

  save(role: RoleDto) {
    this.loading = true;
    role.privilegesString = this.getCheckedPrivileges(); // ['ROLE_USER'];
    this.roleService.createOrEditRole(role).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === ResponseStatus.SUCCESS) {
            this.toastrService.success('Saved successfully', PrivilegeConst.ROLE);
          } else {
            this.toastrService.warning('Error ' + res.errorMessage, PrivilegeConst.ROLE);
            console.error('createOrEditRole ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.ROLE);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
          this.dialogRef.close({event: 'save'});
        },
      },
    );
  }

  copy(role: RoleDto) {
    const res = prompt('Enter name: ', role.name);
    if (prompt('Enter name: ', role.name)) {
      if (confirm('Are you sure copy ' + role.name + ' to ' + res + ' ?')) {
        if (res !== role.name) {
          role.name = res;
          role.id = null;
          this.save(role);
        } else {
          this.toastrService.warning('Error duplicate name', PrivilegeConst.ROLE);
        }
      }
    }
  }
}
