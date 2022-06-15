export interface IAdmin {
  id: number;
  username: string;
  fullname: string;
  avatar: string;
  lastLogin: Date;
  createdAt: Date;
  modifiedAt: Date;
  actived: boolean;
}
