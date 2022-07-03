export interface ProductDto {
  id: number;
  name: string;
  slug: string;
  description: string;
  price: number;
  image: string;
  createdAt: Date;
  modifiedAt: Date;
  modifiedBy: string;
  actived: boolean;
  subcategoryId: number;
  brandId: number;
  discountId: number;
}
