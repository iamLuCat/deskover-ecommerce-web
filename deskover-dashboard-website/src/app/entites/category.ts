import {Subcategory} from "@/entites/subcategory";

export interface Category {
  id: number
  name: string
  img: string
  imgUrl: string
  description: any
  slug: string
  modifiedAt: string
  actived: boolean
  modifiedBy: string
  subcategory: Subcategory
}
