import {Product} from "@/entites/product";
import {Order} from "@/entites/order";

export interface OrderItem {
  id: number;
  quantity: number;
  price: number;
  order: Order;
  product: Product;
}
