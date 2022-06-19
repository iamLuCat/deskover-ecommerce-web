// Tạo interface danh mục sản phẩm
import {Product} from "@/entites/product";

export interface Brand {
  id: number;
  name: string;
  description: string;
  slug: string;
  createdAt: Date;
  modifiedAt: Date;
  deletedAt: Date;
  actived: boolean;
}
