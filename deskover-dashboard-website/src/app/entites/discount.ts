export interface Discount {
  id: number
  name: string
  description: string
  percent: number
  startDate: number
  endDate: number
  modifiedAt: Date
  modifiedBy: string
  actived: boolean
}
