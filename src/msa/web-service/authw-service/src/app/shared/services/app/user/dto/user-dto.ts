import {RoleDto} from '../../role/dto/role-dto';

export class UserDto {
  id: number | null;
  username: string;
  fullName: string;
  email: string;
  enabled: boolean;
  // roles: String[];
  rolesString: String[];
}
