import {AdminAuthority} from "@/entites/admin-authority";

export interface Admin {
  id: number;
  username: string;
  fullname: string;
  lastLogin: Date;
  modifiedAt: Date;
  actived: boolean;
  avatar: string;
  password: string;
  authorities: AdminAuthority[];
}
