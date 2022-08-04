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
  roles: string
}

export interface UserAuthority {
  id: number
  role: UserRole
}

export interface UserRole {
  id: number
  name: string
  description: string
  modifiedAt: Date
  modifiedBy: string
}


