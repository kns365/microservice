import {BaseDto} from '../../../../models/base-dto';

export class NotifyDto extends BaseDto {
  template: string;
  username: string;
  data: string;
  state: boolean;
}
