import {Subcategory} from "@/entites/subcategory";
import {Brand} from "@/entites/brand";
import {Discount} from "@/entites/discount";
import {ProductThumbnail} from "@/entites/product-thumbnail";

export interface Product {
  id: number
  name: string
  slug: string
  description: string
  price: number
  img: string
  imgUrl: string
  quantity: number
  modifiedAt: Date
  modifiedBy: string
  actived: boolean
  spec: string
  utility: string
  design: string
  other: string
  video: string
  subCategory: Subcategory
  brand: Brand
  discount: Discount
  productThumbnails: ProductThumbnail[]
}
