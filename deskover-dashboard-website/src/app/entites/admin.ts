import {AdminPassword} from "@/entites/admin-password";
import {AdminAuthority} from "@/entites/admin-authority";

export interface Admin {
  id: number;
  username: string;
  fullname: string;
  lastLogin: Date;
  createdAt: Date;
  modifiedAt: Date;
  actived: boolean;
  avatar: string;
  password: AdminPassword;
  authorities: AdminAuthority[];
}
