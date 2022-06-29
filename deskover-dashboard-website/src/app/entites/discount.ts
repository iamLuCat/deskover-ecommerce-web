export interface Discount {
  id: number;
  name: string;
  description: string;
  percent: number;
  startDate: Date;
  endDate: Date;
  discountTime: Date[];
  createdDate: Date;
  modifiedDate: Date;
  modifiedUser: string;
  actived: boolean;
}
