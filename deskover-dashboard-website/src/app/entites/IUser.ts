export interface IUser {
    token: any;
    id: number,
    username: string,
    password: string,
    fullname: string,
    email: string,
    address: string,
    phoneNumber: string,
    photo: string,
    createDate: Date,
    enabled: boolean,
}
