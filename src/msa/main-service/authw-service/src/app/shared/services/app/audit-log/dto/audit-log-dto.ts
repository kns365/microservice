export class AuditLogDto {
  id: number | null;
  createdDate: Date;
  createdBy: string;
  execDuration: number;
  clientIpAddress: string;
  clientName: string;
  browserInfo: string;
  path: string;
  httpStatus: string;
  exception: string;
  input: string;
  output: string;
}
