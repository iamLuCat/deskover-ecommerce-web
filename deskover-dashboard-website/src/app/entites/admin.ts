export interface Admin {
  id: number
  username: string
  fullname: string
  lastLogin: Date
  modifiedAt: Date
  actived: boolean
  avatar: string
  password: string
  authorities: AdminAuthority[]
}

export interface AdminAuthority {
  id: number
  role: AdminRole
}

export interface AdminRole {
  id: number
  name: string
  description: string
  modifiedAt: Date
  modifiedBy: string
}


