import {Subcategory} from "@/entites/subcategory";
import {Brand} from "@/entites/brand";

export interface Product {
  id: number;
  name: string;
  slug: string;
  description: string;
  price: number;
  image: string;
  createdAt: Date;
  modifiedAt: Date;
  modifiedBy: string;
  actived: boolean;
  subcategory: Subcategory;
  brand: Brand;
}
