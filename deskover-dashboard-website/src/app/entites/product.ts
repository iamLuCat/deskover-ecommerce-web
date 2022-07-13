import {Subcategory} from "@/entites/subcategory";
import {Brand} from "@/entites/brand";
import {Discount} from "@/entites/discount";
import {ProductThumbnail} from "@/entites/product-thumbnail";
import {Rating} from "@/entites/rating";
import {Inventory} from "@/entites/inventory";
import {OrderItem} from "@/entites/order-item";

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
  spec: string;
  video: string;
  subCategory: Subcategory;
  brand: Brand;
  discount: Discount;
  productThumbnails: ProductThumbnail[];
  ratings: Rating[];
  inventories: Inventory[];
  orderItems: OrderItem[];
}
