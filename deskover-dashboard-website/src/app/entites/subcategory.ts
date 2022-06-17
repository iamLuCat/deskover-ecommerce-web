import { Product } from "./product";
import {Category} from "@/entites/category";

export interface Subcategory {
  id: number;
  name: string;
  description: string;
  slug: string;
  createdAt: Date;
  modifiedAt: Date;
  deletedAt: Date;
  actived: boolean;
  category: Category;
}
