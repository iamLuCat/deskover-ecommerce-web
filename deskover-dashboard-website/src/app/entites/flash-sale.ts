import {Product} from "@/entites/product";

export interface FlashSale {
  id: number
  name: string
  startDate: Date
  endDate: Date
  actived: boolean
  modifiedBy: string
  products: Product[]
}
