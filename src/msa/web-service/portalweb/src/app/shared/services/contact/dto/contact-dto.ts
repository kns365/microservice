export class ContactDto {
  id: number | null;
  name: string;
  email: string;
  phone: string;
  title: string;
  alias: string;
  contactType: number;
  supplierId: number;
  shopId: number;

  constructor(id?: number | null, name?: string, shopId?: number | null, supplierId?: number | null) {
    this.id = id;
    this.name = name;
    this.shopId = shopId;
    this.supplierId = supplierId;
  }
}
