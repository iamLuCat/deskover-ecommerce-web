export interface Discount {
  id: number;
  name: string;
  description: string;
  percent: number;
  startDate: number;
  endDate: number;
  createdAt: Date;
  modifiedAt: Date;
  modifiedBy: string;
  actived: boolean;
}
