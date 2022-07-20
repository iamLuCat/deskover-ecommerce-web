import {Subcategory} from "@/entites/subcategory";
import {Brand} from "@/entites/brand";
import {Discount} from "@/entites/discount";
import {ProductThumbnail} from "@/entites/product-thumbnail";

export interface Product {
  id: number;
  name: string;
  slug: string;
  description: string;
  price: number;
  image: string;
  imageUrl: string;
  quantity: number;
  modifiedAt: Date;
  modifiedBy: string;
  actived: boolean;
  spec: string;
  video: string;
  subCategory: Subcategory;
  brand: Brand;
  discount: Discount;
  productThumbnails: ProductThumbnail[];
}
