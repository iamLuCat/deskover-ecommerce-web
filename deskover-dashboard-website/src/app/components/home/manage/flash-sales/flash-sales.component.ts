import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {FlashSale} from "@/entites/flash-sale";
import {Product} from "@/entites/product";
import {Category} from "@/entites/category";
import {Brand} from "@/entites/brand";
import {BsDatepickerConfig} from "ngx-bootstrap/datepicker";
import {ModalDirective} from "ngx-bootstrap/modal";
import {FormControlDirective} from "@angular/forms";
import {DataTableDirective} from "angular-datatables";
import {ProductService} from "@services/product.service";
import {CategoryService} from "@services/category.service";
import {BrandService} from "@services/brand.service";
import {AuthService} from "@services/auth.service";
import {FlashSaleService} from "@services/flash-sale.service";
import {PermissionContants} from "@/constants/permission-contants";
import {NotiflixUtils} from "@/utils/notiflix-utils";
import {HttpParams} from "@angular/common/http";
import {DatetimeUtils} from "@/utils/datetime-utils";

@Component({
  selector: 'app-flash-sales',
  templateUrl: './flash-sales.component.html',
  styleUrls: ['./flash-sales.component.scss']
})
export class FlashSalesComponent implements OnInit, AfterViewInit {
  public flashSales: FlashSale[] = [];
  public flashSale: FlashSale = <FlashSale>{};

  public products: Product[];
  public flashSaleProducts: Product[];
  public product: Product = <Product>{};

  public categories: Category[];
  public categoryIdFilter: number = null;

  public brands: Brand[];
  public brandIdFilter: number = null;

  public isActive: boolean = true;

  public dtFlashSaleOptions: any = {};
  public dtDiscountProductOptions: any = {};
  public dtFlashSaleProductOptions: any = {};

  public bsConfig?: Partial<BsDatepickerConfig>;
  public flashSaleDateRange: Date[] = [new Date(), new Date()];
  public bsInlineRangeValue: Date[] = [new Date(), new Date()];

  @ViewChild('flashSaleModal') flashSaleModal: ModalDirective;
  @ViewChild('flashSaleForm') flashSaleForm: FormControlDirective;
  @ViewChild('flashSaleProductModal') flashSaleProductModal: ModalDirective;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(
    private flashSaleService: FlashSaleService,
    private productService: ProductService,
    private categoryService: CategoryService,
    private brandService: BrandService,
    private authService: AuthService
  ) {
    this.bsConfig = Object.assign({}, {
      containerClass: 'theme-dark-blue',
      withTimepicker: true,
      adaptivePosition: true,
      isAnimated: true,
      locale: 'vi',
      rangeInputFormat: 'DD/MM/YYYY, hh:mm:ss A',
      dateInputFormat: 'DD/MM/YYYY, hh:mm:ss A',
      minDate: new Date(),
    });

    this.getCategories();
    this.getBrands();
  }

  ngOnInit(): void {
    const self = this;

    self.dtFlashSaleOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      responsive: false,
      serverSide: true,
      processing: true,
      stateSave: true, // sau khi refresh sẽ giữ lại dữ liệu đã filter, sort và paginate
      ajax: (dataTablesParameters: any, callback) => {
        self.flashSaleService.getByActiveForDatatable(dataTablesParameters).subscribe(resp => {
          self.flashSales = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
            data: []
          });
        });
      },
      columns: [
        {data: 'name'},
        {data: 'startDate'},
        {data: 'endDate'},
        {data: 'modifiedBy'},
        {data: 'actived'},
        {data: null, orderable: false,searchable: false},
      ],
      order: [[1, 'asc']],
    };
    self.dtDiscountProductOptions = {
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
          .set("isDiscount", "true")
          .set("isFlashSale", "false")
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
        {data: null,orderable: false,searchable: false, visible: self.hasRole()},
      ]
    };
    self.dtFlashSaleProductOptions = {
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
          .set("isFlashSale", "true")
          .set("isActive", this.isActive ? this.isActive.toString() : "")
          .set("categoryId", this.categoryIdFilter ? this.categoryIdFilter.toString() : "")
          .set("brandId", this.brandIdFilter ? this.brandIdFilter.toString() : "");
        this.productService.getByActiveForDatatable(dataTablesParameters, params).subscribe(resp => {
          self.flashSaleProducts = resp.data;
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
        {data: null, orderable: false,searchable: false, visible: self.hasRole()},
      ]
    };
  }

  ngAfterViewInit() {
    this.flashSaleProductModal?.onShown.subscribe(() => {
      $('.product-table').DataTable().ajax.reload(null, false);
    });
  }

  private getCategories() {
    this.categoryService.getByActive().subscribe(data => {
      this.categories = data;
    });
  }

  private getBrands() {
    this.brandService.getByActive().subscribe(data => {
      this.brands = data;
    });
  }

  public hasRole() {
    return this.authService.hasPermissions([
      PermissionContants.ADMIN,
      PermissionContants.MANAGER,
    ]);
  }

  // Flash Sale
  public rerenderFlashSaleTable() {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.ajax.reload(null, false);
    });
  }

  public applyFilterFlashSale() {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      dtInstance.draw();
    });
  }

  public newFlashSale() {
    this.flashSaleForm.control.reset();
    this.flashSale = <FlashSale>{};
    this.flashSaleDateRange = [DatetimeUtils.addHours(new Date(), 1), DatetimeUtils.addDays(new Date(), 1)];

    this.flashSaleModal.show();
  }

  public getFlashSale(flashSale: FlashSale) {
    this.flashSale = Object.assign({}, flashSale);
    this.flashSaleDateRange = [new Date(this.flashSale.startDate), new Date(this.flashSale.endDate)];
  }

  public editFlashSale(flashSale: FlashSale) {
    this.getFlashSale(flashSale);
    this.flashSaleModal.show();
  }

  public saveFlashSale(flashSale: FlashSale) {
    flashSale.startDate = this.flashSaleDateRange[0];
    flashSale.endDate = this.flashSaleDateRange[1];

    if (flashSale.id) {
      this.flashSaleService.update(flashSale).subscribe(data => {
        NotiflixUtils.successNotify('Cập nhật thành công');

        this.flashSaleModal.hide();
        this.rerenderFlashSaleTable();
      });
    } else {
      this.flashSaleService.create(flashSale).subscribe(data => {
        NotiflixUtils.successNotify('Thêm mới thành công');

        this.flashSaleModal.hide();
        this.rerenderFlashSaleTable();
      });
    }
  }

  public deleteFlashSale(flashSale: FlashSale) {
    NotiflixUtils.showConfirm('Xác nhận xoá', 'Bạn có muốn xóa Flash Sale này không?', () => {
      this.flashSaleService.delete(flashSale.id).subscribe(data => {
        NotiflixUtils.successNotify('Xoá Flash Sale thành công');
        this.rerenderFlashSaleTable();
      });
    });
  }

  public flashSaleActiveToggle(flashSale: FlashSale) {
    let message = !flashSale.actived ? 'Flash Sale sẽ được kích hoạt' : 'Flash Sale sẽ bị vô hiệu hoá';

    NotiflixUtils.showConfirm('Xác nhận', message, () => {
      this.flashSaleService.statusToggle(flashSale.id).subscribe(data => {
        NotiflixUtils.successNotify('Đổi trạng thái thành công');
        this.rerenderFlashSaleTable();
      });
    });
  }

  public isExpired(endDate: Date): boolean {
    return DatetimeUtils.isExpired(endDate);
  }

  public getClassRowFlashSale(flashSale: FlashSale): string {
    if (flashSale.actived) {
      return 'table-success';
    } else if (!flashSale.actived && this.isExpired(flashSale.endDate)) {
      return 'table-danger';
    }
  }

  // Product
  public rerenderProductTable() {
    $('.product-table').DataTable().ajax.reload(null, false);
  }

  public editProduct(flashSale: FlashSale) {
    this.getFlashSale(flashSale);
    this.flashSaleProductModal.show();
  }

  public addProduct(productId: number) {
    this.flashSaleService.update(this.flashSale, productId, null).subscribe(data => {
      NotiflixUtils.successNotify('Thêm sản phẩm thành công');
      this.rerenderProductTable();
    });
  }

  public removeProduct(productId: number) {
    this.flashSaleService.update(this.flashSale, null, productId).subscribe(data => {
      NotiflixUtils.successNotify('Xoá sản phẩm thành công');
      this.rerenderProductTable();
    });
  }
}
