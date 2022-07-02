import {Component, OnInit, ViewChild} from '@angular/core';
import {Product} from "@/entites/product";
import {BsDatepickerConfig} from "ngx-bootstrap/datepicker";
import {DataTableDirective} from "angular-datatables";
import {NgbModal, NgbModalConfig} from "@ng-bootstrap/ng-bootstrap";
import {ProductService} from "@services/product.service";
import {DatePipe} from "@angular/common";
import {AlertUtils} from "@/utils/alert-utils";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit {

  products: Product[];
  product: Product;

  isEdit: boolean = false;
  isActive: boolean = true;

  dtOptions: any = {};

  bsConfig?: Partial<BsDatepickerConfig>;

  @ViewChild('productModal') productModal: any;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(
    private modalConfig: NgbModalConfig,
    private modalService: NgbModal,
    private productService: ProductService,
  ) {
    modalConfig.backdrop = 'static';
    modalConfig.keyboard = false;
    modalConfig.centered = true;

    // Config datepicker ngx-bootstrap
    this.bsConfig = Object.assign({}, {
      containerClass: 'theme-dark-blue',
      withTimepicker: true,
      locale: 'vi',
      rangeInputFormat : 'DD/MM/YYYY HH:mm:ss',
      minDate: new Date()
    });
  }

  ngOnInit() {
    const self = this;

    self.dtOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      responsive: true,
      lengthMenu: [5, 10, 25, 50, 100],
      serverSide: true,
      processing: true,
      stateSave: true, // sau khi refresh sẽ giữ lại dữ liệu đã filter, sort và paginate
      ajax: (dataTablesParameters: any, callback) => {
        this.productService.getByActiveForDatatable(dataTablesParameters, this.isActive).then(resp => {
          self.products = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
            data: self.products
          });
        });
      },
      columns: [
        {
          title: 'Ảnh',
          data: 'image',
          orderable: false,
          searchable: false,
          className: 'align-middle',
          responsivePriority: 1,
          render: (data, type, row, meta) => {
            let srcImg = data ? data : 'assets/images/no-image.png';
            return '<img src="' + srcImg + '" width="60" height="60" class="img-thumbnail" alt="thumbnail">';
          }
        },
        {title: 'Tên', data: 'name', className: 'align-middle', responsivePriority: 2},
        {
          title: 'Giá',
          data: 'price',
          className: 'align-middle text-start text-md-center',
          render: (data, type, row, meta) => {
            return new Intl.NumberFormat('vi-VN', {style: 'currency', currency: 'VND'}).format(data);
          }
        },
        { title: 'Danh mục', data: 'subCategory.name', className: 'align-middle text-center' },
        { title: 'Thương hiệu', data: 'brand.name', className: 'align-middle text-start text-md-center' },
        {
          title: 'Công cụ',
          data: null,
          orderable: false,
          searchable: false,
          className: 'align-middle text-end',
          responsivePriority: 3,
          render: (data, type, full, meta) => {
            if (self.isActive) {
              return `
              <div class="d-flex justify-content-end align-items-center">
                <a href="javascript:void(0)" class="btn btn-edit btn-sm bg-faded-info me-1" data-id="${data.id}"
                    title="Sửa" data-toggle="tooltip">
                    <i class="fa fa-pen-square text-info"></i>
                </a>
                <a href="javascript:void(0)" class="btn btn-delete btn-sm bg-faded-danger" data-id="${data.id}"
                    title="Xoá" data-toggle="tooltip">
                    <i class="fa fa-trash text-danger"></i>
                </a>
              </div>
            `;
            } else {
              return `
               <button type="button" class="btn btn-active btn-sm bg-success" data-id="${data.id}">Kích hoạt</button>`
            }
          }
        },
      ]
    }
  }

    ngAfterViewInit() {
    const self = this;

    let body = $('body');
    body.on('click', '.btn-edit', function () {
      const id = $(this).data('id');
      self.getProduct(id);
    });
    body.on('click', '.btn-delete', function () {
      const id = $(this).data('id');
      self.deleteProduct(id);
    });
    body.on('click', '.btn-active', function () {
      const id = $(this).data('id');
      self.activeProduct(id);
    });
  }

  rerender() {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload(null, false);
    });
  }

  filter() {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.draw();
    });
  }

  newProduct() {
    this.isEdit = false;
    this.product = <Product>{};
    this.openModal(this.productModal);
  }

  getProduct(id: number) {
    this.productService.getById(id).subscribe(data => {
      this.product = data;
    });
    this.isEdit = true;
    this.openModal(this.productModal);
  }

  saveProduct(product: Product) {
    if (this.isEdit) {
      this.productService.update(product).subscribe(data => {
        AlertUtils.toastSuccess('Cập nhật thành công');
        this.rerender();
        this.closeModal();
      }, error => {
        AlertUtils.toastError(error);
      });
    } else {
      this.productService.create(product).subscribe(data => {
        AlertUtils.toastSuccess('Thêm mới thành công');
        this.rerender();
        this.closeModal();
      }, error => {
        AlertUtils.toastError(error);
      });
    }
  }

  deleteProduct(id: number) {
    AlertUtils.warning('Xác nhận', 'Các danh mục con liên quan cũng sẽ bị xoá').then((result) => {
      if (result.value) {
        this.productService.changeActive(id).subscribe(data => {
          AlertUtils.toastSuccess('Xoá danh mục thành công');
          this.rerender();
        });
      }
    });
  }

  activeProduct(id: number) {
    this.productService.changeActive(id).subscribe(data => {
      AlertUtils.toastSuccess('Kích hoạt danh mục thành công');
      this.rerender();
    });
  }

  // Modal
  openModal(content) {
    this.closeModal();
    this.modalService.open(content);
  }

  closeModal() {
    this.modalService.dismissAll();
  }
}
