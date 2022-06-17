// Tạo interface danh mục sản phẩm
export interface Category {
  id: number,
  name: string,
  description: string,
  slug: string,
  createdAt: Date,
  modifiedAt: Date,
  deletedAt: Date,
  actived: boolean,
}
