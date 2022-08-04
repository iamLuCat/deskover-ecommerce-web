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
import {Category} from "@/entites/category";
import {Subcategory} from "@/entites/subcategory";
import { CategoryService } from '@services/category.service';
import {Brand} from "@/entites/brand";
import {BrandService} from "@services/brand.service";

@Component({
  selector: 'app-promotion',
  templateUrl: './promotions.component.html',
  styleUrls: ['./promotions.component.scss']
})
export class PromotionsComponent implements OnInit, AfterViewInit {
  discounts: Discount[];
  discount: Discount = <Discount>{};

  products: Product[];
  discountProducts: Product[];
  product: Product = <Product>{};

  categories: Category[];
  categoryIdFilter: number = null;

  brands: Brand[];
  brandIdFilter: number = null;

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
    private categoryService: CategoryService,
    private brandService: BrandService
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

    this.getCategories();
    this.getBrands();
  }

  ngOnInit() {
    const self = this;

    self.dtOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      responsive: false,
      serverSide: true,
      processing: true,
      stateSave: true, // sau khi refresh sẽ giữ lại dữ liệu đã filter, sort và paginate
      ajax: (dataTablesParameters: any, callback) => {
        this.discountService.getByActiveForDatatable(dataTablesParameters, this.isActive).subscribe(resp => {
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
        {data: null,orderable: false,searchable: false},
      ],
      order: [[4, 'desc']],
    };
    self.dtAllProductOptions = {
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      serverSide: true,
      processing: true,
      columnDefs: [{
        "defaultContent": "",
        "targets": "_all",
      }],
      ajax: (dataTablesParameters: any, callback) => {
        const params = new HttpParams()
          .set("isDiscount", "false")
          .set("isActive", this.isActive ? this.isActive.toString() : "")
          .set("categoryId", this.categoryIdFilter ? this.categoryIdFilter.toString() : "")
          .set("brandId", this.brandIdFilter ? this.brandIdFilter.toString() : "");
        this.productService.getByActiveForDatatable(dataTablesParameters, params).subscribe(resp => {
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
        {data: 'price'},
        {data: null,orderable: false,searchable: false},
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
          .set("isDiscount", "true")
          .set("isActive", this.isActive ? this.isActive.toString() : "")
          .set("categoryId", this.categoryIdFilter ? this.categoryIdFilter.toString() : "")
          .set("brandId", this.brandIdFilter ? this.brandIdFilter.toString() : "");
        this.productService.getByActiveForDatatable(dataTablesParameters, params).subscribe(resp => {
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
        {data: 'price'},
        {data: 'priceSale'},
        {data: null,orderable: false,searchable: false},
      ]
    };
  }

  ngAfterViewInit() {
    this.productDiscountModal.onShown.subscribe(() => {
      $('.product-table').DataTable().ajax.reload(null, false);
    });

  }

  rerenderDiscountTable() {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload(null, false);
    });
  }

  applyFilterDiscount() {
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
    this.rerenderDiscountTable();
    this.discountModal.hide();
  }

  deleteDiscount(discount: Discount) {
    NotiflixUtils.showConfirm('Xác nhận xoá', 'Khuyến mãi đang áp dụng trên sản phẩm sẽ bị huỷ!', () => {
      this.discountService.changeActive(discount.id).subscribe(data => {
        NotiflixUtils.successNotify('Xoá khuyến mãi thành công');
        this.rerenderDiscountTable();
      });
    });
  }

  activeDiscount(discount: Discount) {
    // if (this.isExpired(discount.endDate)) {
    //   NotiflixUtils.failureNotify('Khuyến mãi đã hết hạn');
    //   return;
    // }
    this.discountService.changeActive(discount.id).subscribe(data => {
      NotiflixUtils.successNotify('Kích hoạt khuyến mãi thành công');
      this.rerenderDiscountTable();
    });
  }

  isExpired(endDate: number): boolean {
    return new Date() > new Date(endDate);
  }

  /* Product */
  rerenderProductTable() {
    $('.product-table').DataTable().ajax.reload(null, false);
  }

  getProduct(discountId: number) {
    this.getDiscount(discountId);
    this.productDiscountModal.show();
  }

  addProduct(productId: number) {
    this.discountService.update(this.discount, productId, null).subscribe(data => {
      NotiflixUtils.successNotify('Thêm sản phẩm thành công');
      this.rerenderProductTable();
    });
  }

  removeProduct(productId: number) {
    this.discountService.update(this.discount, null, productId).subscribe(data => {
      NotiflixUtils.successNotify('Xoá sản phẩm thành công');
      this.rerenderProductTable();
    });
  }

  getCategories() {
    this.categoryService.getByActive().subscribe(data => {
      this.categories = data;
    });
  }

  getBrands() {
    this.brandService.getByActive().subscribe(data => {
      this.brands = data;
    });
  }
}
