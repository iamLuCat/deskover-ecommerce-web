import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Discount} from "@/entites/discount";
import {DataTableDirective} from "angular-datatables";
import {DiscountService} from "@services/discount.service";
import {NotiflixUtils} from "@/utils/notiflix-utils";
import {BsDatepickerConfig} from "ngx-bootstrap/datepicker";
import {ProductService} from "@services/product.service";
import {Product} from "@/entites/product";
import {ModalDirective} from "ngx-bootstrap/modal";
import {FormControlDirective} from "@angular/forms";
import {HttpParams} from "@angular/common/http";

@Component({
  selector: 'app-promotion',
  templateUrl: './promotion.component.html',
  styleUrls: ['./promotion.component.scss']
})
export class PromotionComponent implements OnInit, AfterViewInit {
  discounts: Discount[];
  discount: Discount = <Discount>{};
  products: Product[];
  discountProducts: Product[];
  product: Product = <Product>{};

  isEdit: boolean = false;
  isActive: boolean = true;

  dtOptions: any = {};
  dtAllProductOptions: any = {};
  dtDiscountProductOptions: any = {};

  bsConfig?: Partial<BsDatepickerConfig>;
  discountDateRange: Date[] = [new Date(), new Date()];
  discountStartTime: Date = new Date();
  discountEndTime: Date = new Date();

  @ViewChild('discountModal') discountModal: ModalDirective;
  @ViewChild('productDiscountModal') productDiscountModal: ModalDirective;
  @ViewChild('discountForm') discountForm: FormControlDirective;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(
    private discountService: DiscountService,
    private productService: ProductService,
  ) {
    // Config datepicker ngx-bootstrap
    this.bsConfig = Object.assign({}, {
      containerClass: 'theme-dark-blue',
      withTimepicker: false,
      locale: 'vi',
      rangeInputFormat: 'DD/MM/YYYY',
      dateInputFormat: 'DD/MM/YYYY',
      adaptivePosition: true,
      minDate: new Date(),
    });
  }

  ngOnInit() {
    const self = this;

    self.dtOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      responsive: false,
      lengthMenu: [5, 10, 25, 50, 100],
      serverSide: true,
      processing: true,
      stateSave: true, // sau khi refresh sẽ giữ lại dữ liệu đã filter, sort và paginate
      ajax: (dataTablesParameters: any, callback) => {
        this.discountService.getByActiveForDatatable(dataTablesParameters, this.isActive).then(resp => {
          self.discounts = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
            data: []
          });
        });
      },
      columns: [
        {data: 'name'},
        {data: 'description'},
        {data: 'percent'},
        {data: 'startDate'},
        {data: 'endDate'},
        {data: null,
          orderable: false,
          searchable: false,
          render: (data, type, full, meta) => {
            if (self.isActive) {
              return `
              <div class="d-flex justify-content-end align-items-center">
              <a href="javascript:void(0)" class="btn btn-product btn-sm bg-faded-warning me-2" data-id="${data.id}"
                    title="Sản phẩm" data-toggle="tooltip">
                    <i class="fa-solid fa-box text-warning"></i>
                </a>
                <a href="javascript:void(0)" class="btn btn-edit btn-sm bg-faded-info me-2" data-id="${data.id}"
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
    };
    self.dtAllProductOptions = {
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      lengthMenu: [10, 25, 50, 100],
      serverSide: true,
      processing: true,
      columnDefs: [{
        "defaultContent": "",
        "targets": "_all",
      }],
      ajax: (dataTablesParameters: any, callback) => {
        const params = new HttpParams()
          .set("isActive", this.isActive.toString())
          .set("isDiscount", "false");
        this.productService.getByActiveForDatatable(dataTablesParameters, params).then(resp => {
          self.products = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
            data: []
          });
        });
      },
      columns: [
        {data: 'name'},
        {title: 'Công cụ',data: null,orderable: false,searchable: false},
      ]
    };
    self.dtDiscountProductOptions = {
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      lengthMenu: [10, 25, 50, 100],
      serverSide: true,
      processing: true,
      columnDefs: [{
        "defaultContent": "",
        "targets": "_all",
      }],
      ajax: (dataTablesParameters: any, callback) => {
        const params = new HttpParams()
          .set("isActive", this.isActive.toString())
          .set("isDiscount", "true");
        this.productService.getByActiveForDatatable(dataTablesParameters, params).then(resp => {
          self.discountProducts = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
            data: []
          });
        });
      },
      columns: [
        {data: 'name'},
        {title: 'Công cụ',data: null,orderable: false,searchable: false},
      ]
    };
  }

  ngAfterViewInit() {
    const self = this;
    const body = $('body');

    this.productDiscountModal.onShown.subscribe(() => {
      $('.product-table').DataTable().ajax.reload(null, false);
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

  newDiscount() {
    this.discountForm.control.reset();

    this.isEdit = false;
    this.discount = <Discount>{};

    this.discountDateRange = [new Date(), new Date()];
    this.discountStartTime = new Date();
    this.discountEndTime = new Date();

    this.discountModal.show();
  }

  getDiscount(discountId: number) {
    this.discountService.getById(discountId).subscribe(data => {
      this.discount = data;
      this.discountDateRange = [new Date(this.discount.startDate), new Date(this.discount.endDate)];
      this.discountStartTime = new Date(this.discount.startDate);
      this.discountEndTime = new Date(this.discount.endDate);
    });
  }

  editDiscount(discountId: number) {
    this.isEdit = true;
    this.getDiscount(discountId);
    this.discountModal.show();
  }

  saveDiscount(discount: Discount) {
    this.discount.startDate = this.discountDateRange[0].setTime(this.discountStartTime.getTime());
    this.discount.endDate = this.discountDateRange[1].setTime(this.discountEndTime.getTime());
    if (this.isEdit) {
      this.discountService.update(discount).subscribe(data => {
        NotiflixUtils.successNotify('Cập nhật thành công');
      }, error => {
        NotiflixUtils.failureNotify(error);
      });
    } else {
      this.discountService.create(discount).subscribe(data => {
        NotiflixUtils.successNotify('Thêm mới thành công');
      }, error => {
        NotiflixUtils.failureNotify(error);
      });
    }
    this.rerender();
    this.discountModal.hide();
  }

  deleteDiscount(discountId: number) {
    NotiflixUtils.showConfirm('Xác nhận xoá', 'Khuyến mãi đang áp dụng trên sản phẩm sẽ bị huỷ', () => {
      this.discountService.changeActive(discountId).subscribe(data => {
        NotiflixUtils.successNotify('Xoá danh mục thành công');
        this.rerender();
      });
    });
  }

  activeDiscount(discountId: number) {
    this.discountService.changeActive(discountId).subscribe(data => {
      NotiflixUtils.successNotify('Kích hoạt danh mục thành công');
      this.rerender();
    });
  }

  getProduct(discountId: number) {
    this.getDiscount(discountId);
    this.productDiscountModal.show();
  }

  addProduct(productId: number) {
    this.discountService.update(this.discount, productId, null).subscribe(data => {
      NotiflixUtils.successNotify('Thêm sản phẩm thành công');
      $('.product-table').DataTable().ajax.reload(null, false);
    });
  }

  removeProduct(productId: number) {
    this.discountService.update(this.discount, null, productId).subscribe(data => {
      NotiflixUtils.successNotify('Xoá sản phẩm thành công');
      $('.product-table').DataTable().ajax.reload(null, false);
    });
  }
}
