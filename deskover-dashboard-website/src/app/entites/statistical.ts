import {Subcategory} from "@/entites/subcategory";
import {Product} from "@/entites/product";

export interface GeneralReport {
  totalOrders: number;
  totalProducts: number;
  totalCustomers: number;
  totalPosts: number;
  totalRevenue: number;
}

export interface OrderReport {
  subcategory: Subcategory;
  quantity: number;
}

export interface ProductReport {
  product: Product;
  quantity: number;
}
