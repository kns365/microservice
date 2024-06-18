export class CountryDto {
  id: number | null;
  name: string;
  code: string;

  constructor(id?: number | null, name?: string) {
    this.id = id;
    this.name = name;
  }
}
