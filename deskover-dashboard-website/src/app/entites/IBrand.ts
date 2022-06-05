// Tạo interface danh mục sản phẩm
export interface IBrand {
    id: number,
    brand_name: string,
    brand_icon: string,
    brand_desc: string,
    brand_url: string,
    created_at: number,
    updated_at: number,
    deleted_at: number,
    created_by: string,
    updated_by: string,
    deleted_by: string,
    is_status: boolean
}
