import {Subcategory} from "@/entites/subcategory";
import {Brand} from "@/entites/brand";
import {Discount} from "@/entites/discount";

export interface Product {
  id: number;
  name: string;
  slug: string;
  description: string;
  price: number;
  image: string;
  modifiedAt: Date;
  modifiedBy: string;
  actived: boolean;
  subcategory: Subcategory;
  brand: Brand;
  discount: Discount;
}
