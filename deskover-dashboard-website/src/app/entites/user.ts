export interface User {
  id: number
  username: string
  fullname: string
  lastLogin: Date
  modifiedAt: Date
  modifiedBy: string
  actived: boolean
  avatar: string
  password: string
  authority: UserAuthority
}

export interface UserAuthority {
  id: number
  role: UserRole
  modifiedAt: Date
  modifiedBy: string
}

export interface UserRole {
  id: number
  name: string
  roleId:string
  description: string
  modifiedAt: Date
  modifiedBy: string
}


