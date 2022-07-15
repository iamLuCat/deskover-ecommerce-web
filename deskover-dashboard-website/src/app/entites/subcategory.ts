import { Product } from "./product";
import {Category} from "@/entites/category";

export interface Subcategory {
  id: number;
  name: string;
  description: string;
  slug: string;
  modifiedAt: Date;
  modifiedBy: string;
  actived: boolean;
  category: Category;
}
