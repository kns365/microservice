import {Component, Input, OnInit} from '@angular/core';
import {NbDialogRef, NbToastrService} from '@nebular/theme';
import {UserService} from '../../../../shared/services/app/user/user.service';
import {UserDto} from '../../../../shared/services/app/user/dto/user-dto';
import {RoleService} from '../../../../shared/services/app/role/role.service';
import {RoleDto} from '../../../../shared/services/app/role/dto/role-dto';
import * as _ from 'lodash';
import {HttpStatusCode} from '@angular/common/http';
import {PrivilegeDto} from '../../../../shared/services/app/privilege/dto/privilege-dto';
import {PrivilegeConst} from '../../../../shared/constants/PrivilegeConst';
import {ResponseDto} from '../../../../shared/models/response-dto';
import {ResponseStatus} from '../../../../shared/constants/ResponseStatus';

@Component({
  selector: 'ngx-user-modal',
  templateUrl: './user-modal.component.html',
  styleUrls: ['./user-modal.component.scss'],
})

export class UserModalComponent implements OnInit {

  @Input() input: number;
  create: UserDto = new UserDto();
  roles: RoleDto[] = [];
  defaultRoleCheckedStatus = false;
  checkedRolesMap: { [key: string]: boolean } = {};
  loading: boolean = false;

  constructor(protected dialogRef: NbDialogRef<UserModalComponent>
    , private roleService: RoleService
    , private userService: UserService
    , private toastrService: NbToastrService,
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
    this.userService.getUserById(id).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === ResponseStatus.SUCCESS) {
            this.create = <UserDto>res.data;
            this.setInitialRolesStatus();
          } else {
            this.toastrService.warning('Error ' + res.errorMessage, PrivilegeConst.USER);
            console.error('getUserById ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.USER);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
        },
      },
    );
  }

  getAllRole(): void {
    this.loading = true;
    this.roleService.getAllRole().subscribe({
      next: (res: ResponseDto) => {
        if (res && res.status === ResponseStatus.SUCCESS) {
          this.roles = <RoleDto[]>res.data;
          this.setInitialRolesStatus();
        } else {
          this.toastrService.warning('Error ' + res.errorMessage, PrivilegeConst.ROLE);
          console.error('getAllRole ', res);
        }
      },
      error: (err: any) => {
        this.toastrService.danger('Error ' + err.status, PrivilegeConst.ROLE);
        console.error(err);
      },
      complete: () => {
        this.loading = false;
      },
    });
  }

  setInitialRolesStatus(): void {
    _.map(this.roles, item => {
      this.checkedRolesMap[item.name] = this.isRoleChecked(
        item.name,
      );
    });
  }

  isRoleChecked(name: string): boolean {
    // just return default role checked status
    // it's better to use a setting
    if (this.create.id) {
      // let roleString = Object.assign(this, this.create.roles.map(x => x.name));
      return _.includes(this.create.rolesString, name);
    } else {
      return this.defaultRoleCheckedStatus;
    }
  }

  onRoleChange(input: RoleDto, $event: any) {
    this.checkedRolesMap[input.name] = $event.target.checked;
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

  save(user: UserDto) {
    this.loading = true;
    user.rolesString = this.getCheckedRoles(); // ['ROLE_USER'];
    console.error('save user', user);
    this.userService.createOrEditUser(user).subscribe({
        next: (res: ResponseDto) => {
          if (res && res.status === ResponseStatus.SUCCESS) {
            this.toastrService.success('Saved successfully', PrivilegeConst.USER);
          } else {
            this.toastrService.warning('Error ' + res.errorMessage, PrivilegeConst.USER);
            console.error('createOrEditUser ', res);
          }
        },
        error: (err: any) => {
          this.toastrService.danger('Error ' + err.status, PrivilegeConst.USER);
          console.error(err);
        },
        complete: () => {
          this.loading = false;
          this.dialogRef.close({event: 'save'});
        },
      },
    );
  }


}
