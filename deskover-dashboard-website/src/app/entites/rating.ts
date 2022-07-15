import {Product} from "@/entites/product";

export interface Rating {
  id: number;
  fullname: string;
  phone: string;
  point: number;
  content: string;
  actived: boolean;
  modifiedAt: Date;
  product: Product;
}
