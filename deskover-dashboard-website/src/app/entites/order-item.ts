import {Product} from "@/entites/product";

export interface OrderItem {
  id: number;
  quantity: number;
  price: number;
  // order: Order;
  product: Product;
}
