import {User} from "@/entites/user";
import {OrderItem} from "@/entites/order-item";
import {Payment} from "@/entites/payment";
import {Shipping} from "@/entites/shipping";
import {OrderStatus} from "@/entites/order-status";
import {OrderDetail} from "@/entites/order-detail";

export interface Order {
  id: number
  orderCode: string
  qrCode: string
  user: User
  payment: Payment
  shipping: Shipping
  orderStatus: OrderStatus
  note: string
  shipping_note: string
  fullName: string
  email: string
  createdAt: string
  modifiedBy: string
  unitPrice: number
  orderQuantity: number
  orderDetail: OrderDetail
  orderItems: OrderItem[]
}
