import {Subcategory} from "@/entites/subcategory";

export interface Category {
  id: number;
  name: string;
  description: string;
  slug: string;
  createdAt: Date;
  modifiedAt: Date;
  modifiedBy: string;
  actived: boolean;
  subcategory: Subcategory;
}
