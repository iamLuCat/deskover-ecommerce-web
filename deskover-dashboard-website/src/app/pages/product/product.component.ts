import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {Product} from "@/entites/product";
import {DataTableDirective} from "angular-datatables";
import {ProductService} from "@services/product.service";
import {AlertUtils} from "@/utils/alert-utils";
import {Category} from "@/entites/category";
import {CategoryService} from '@services/category.service';
import {DatePipe} from "@angular/common";
import {Subcategory} from "@/entites/subcategory";
import {SubcategoryService} from "@services/subcategory.service";
import {UrlUtils} from "@/utils/url-utils";
import {ModalDirective} from "ngx-bootstrap/modal";

@Component({
  selector: 'app-product',
  templateUrl: './product.component.html',
  styleUrls: ['./product.component.scss']
})
export class ProductComponent implements OnInit, AfterViewInit {

  products: Product[];
  product: Product = <Product>{};
  categories: Category[];
  categoryId: number = null;
  subcategories: Subcategory[];
  subcategoryId: number = null;

  isEdit: boolean = false;
  isActive: boolean = true;

  dtOptions: any = {};

  @ViewChild('productModal') productModal: ModalDirective;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private subcategoryService: SubcategoryService
  ) {
    this.getCategories();
    this.getSubcategories();
  }

  ngOnInit() {
    const self = this;


    self.dtOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      lengthMenu: [5, 10, 25, 50, 100],
      responsive: false,
      serverSide: true,
      processing: true,
      stateSave: true,
      columnDefs: [{
        "defaultContent": "",
        "targets": "_all",
      }],
      ajax: (dataTablesParameters: any, callback) => {
        this.productService.getByActiveForDatatable(dataTablesParameters, this.isActive, this.categoryId).then(resp => {
          self.products = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
            data: []
          });
        });
      },
      columns: [
        {data: 'image', orderable: false, searchable: false},
        {data: 'name'},
        {data: 'brand.name'},
        {data: 'subCategory.name'},
        {data: 'price'},
        {data: 'modifiedAt'},
        {data: 'modifiedBy'},
        {data: null, orderable: false, searchable: false,},
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

  /* Category & Subcategory */
  getCategories() {
    this.categoryService.getByActive().subscribe(data => {
      this.categories = data;
    });
  }

  getSubcategories() {
    this.subcategoryService.getByActive(true, this.categoryId).subscribe(data => {
      this.subcategories = data;
      console.log(this.subcategories);
    });
  }

  /* Product */
  newProduct() {
    this.isEdit = false;
    this.product = <Product>{};
    this.openModal(this.productModal);
  }

  getProduct(id: number) {
    this.productService.getById(id).subscribe(data => {
      this.product = data;
    });
    this.openModal(this.productModal);
    this.isEdit = true;
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

  /* Slugify */
  toSlug(text: string) {
    return UrlUtils.slugify(text);
  }

  /* Modal */
  openModal(content) {
    this.productModal.show();
  }

  closeModal() {
    this.productModal.hide();
  }
}
