import {User} from "@/entites/user";
import {OrderItem} from "@/entites/order-item";
import {Payment} from "@/entites/payment";
import {Shipping} from "@/entites/shipping";
import {OrderStatus} from "@/entites/order-status";

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
  email: any
  createdAt: Date
  modifiedBy: string
  orderItems: OrderItem[]
}
