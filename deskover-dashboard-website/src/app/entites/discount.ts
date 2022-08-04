export interface Discount {
  id: number
  name: string
  description: string
  percent: number
  startDate: Date
  endDate: Date
  modifiedAt: Date
  modifiedBy: string
  actived: boolean
}
