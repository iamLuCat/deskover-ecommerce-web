import {Product} from "@/entites/product";

export interface Inventory {
  id: number;
  quantity: number;
  modifiedAt: Date;
  modifiedBy: string;
  product: Product;
}
