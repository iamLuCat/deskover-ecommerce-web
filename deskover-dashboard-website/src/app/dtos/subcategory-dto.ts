import {Subcategory} from "@/entites/subcategory";

export interface SubcategoryDto {
  id: number;
  name: string;
  description: string;
  slug: string;
  modifiedAt: Date;
  modifiedBy: string;
  actived: boolean;
  categoryId: number;
}
