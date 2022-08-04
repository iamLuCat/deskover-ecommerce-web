import {Component, OnInit, ViewChild} from '@angular/core';
import {Product, ProductThumbnail} from "@/entites/product";
import {DataTableDirective} from "angular-datatables";
import {ProductService} from "@services/product.service";
import {NotiflixUtils} from "@/utils/notiflix-utils";
import {Category} from "@/entites/category";
import {CategoryService} from '@services/category.service';
import {Subcategory} from "@/entites/subcategory";
import {SubcategoryService} from "@services/subcategory.service";
import {UrlUtils} from "@/utils/url-utils";
import {ModalDirective} from "ngx-bootstrap/modal";
import {Brand} from "@/entites/brand";
import {BrandService} from "@services/brand.service";
import {FormControlDirective} from "@angular/forms";
import {HttpParams} from "@angular/common/http";
import {UploadService} from "@services/upload.service";
import {DomSanitizer} from "@angular/platform-browser";
import {environment} from "../../../../../environments/environment";

@Component({
  selector: 'app-product',
  templateUrl: './products.component.html',
  styleUrls: ['./products.component.scss']
})
export class ProductsComponent implements OnInit {
  products: Product[];
  product: Product;
  productPreviewImg: string;
  productPreviewThumbnails: string[];

  categories: Category[];
  category: Category;
  subcategories: Subcategory[];
  subcategory: Subcategory;
  brands: Brand[];
  brand: Brand;

  categoryIdFilter: number = null;
  brandIdFilter: number = null;

  isActive: boolean = true;
  isCopy: boolean = false;

  dtOptions: any = {};

  ckeditorUrl: string;
  ckeditorConfig: any;

  @ViewChild('productModal') productModal: ModalDirective;
  @ViewChild('productForm') productForm: FormControlDirective;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(
    private productService: ProductService,
    private categoryService: CategoryService,
    private subcategoryService: SubcategoryService,
    private brandService: BrandService,
    private uploadService: UploadService,
    private sanitizer: DomSanitizer
  ) {
    this.ckeditorUrl = environment.globalUrl.ckeditor;
    this.ckeditorConfig = {
      language: 'vi',
      allowedContent: true,
      removePlugins: "save"
    };

    this.newData();
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
      serverSide: true,
      processing: true,
      stateSave: true,
      columnDefs: [{
        "defaultContent": "",
        "targets": "_all",
      }],
      ajax: (dataTablesParameters: any, callback) => {
        const params = new HttpParams()
          .set("isActive", this.isActive.toString())
          .set("categoryId", this.categoryIdFilter ? this.categoryIdFilter.toString() : '')
          .set("brandId", this.brandIdFilter ? this.brandIdFilter.toString() : '');
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
        {data: 'image', orderable: false, searchable: false},
        {data: 'name'},
        {data: 'brand.name'},
        {data: 'subCategory.name'},
        {data: 'price'},
        {data: 'modifiedAt'},
        {data: 'modifiedBy'},
        {data: null, orderable: false, searchable: false,},
      ],
      order: [5, 'desc']
    }
  }

  /* Category & Subcategory */
  getCategories() {
    this.categoryService.getByActive().subscribe(data => {
      this.categories = data;
    });
  }

  getSubcategoriesByCategory() {
    if (this.category) {
      this.subcategoryService.getByActive(true, this.category.id).subscribe(data => {
        this.subcategories = data;
      });
    } else {
      this.subcategories = [];
    }
  }

  getBrands() {
    this.brandService.getByActive().subscribe(data => {
      this.brands = data;
    });
  }

  newData() {
    this.product = <Product>{
      id: null,
      name: '',
      slug: '',
      description: '',
      spec: `
        <ul class="list-unstyled fs-sm pb-2">
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">CPU: </span><span>Apple M2</span></li>
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">RAM: </span><span>8GB</span></li>
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">Card đồ họa: </span><span>8 nhân GPU, 16 nhân Neural Engine</span></li>
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">Ổ cứng</span><span>SSD - 256GB</span></li>
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">Màn hình</span><span>2560 x 1664 Liquid Retina Display - IPS</span></li>
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">Pin</span><span>52,6 Wh</span></li>
        </ul>`,
      design: `
        <ul class="list-unstyled fs-sm pb-2">
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">Kích thước: </span><span>30,41 cm - 21,5 cm - 1,13 cm</span></li>
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">Trọng lượng: </span><span>1.27 kg</span></li>
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">Chất liệu: </span><span>Vỏ kim loại</span></li>
        </ul>`,
      utility: `
        <ul class="list-unstyled fs-sm pb-2">
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">Cổng giao tiếp: </span><span>Cổng HDMI và đầu đọc thẻ SD, USB Type-C</span></li>
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">Wifi: </span><span>802.11ax Wi-Fi 6</span></li>
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">Webcam: </span><span>1080p FaceTime HD camera</span></li>
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">Hệ điều hành: </span><span>MacOS</span></li>
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">Âm thanh: </span><span>Yes</span></li>
          <li class="d-flex justify-content-between pb-2 border-bottom"><span class="text-muted">Bluetooth: </span><span>5.0</span></li>
        </ul>`,
      other: ``,
      price: null,
      img: '',
      modifiedAt: null,
      modifiedBy: '',
      actived: true,
      video: '',
      brand: <Brand>{
        id: null,
      },
      subCategory: <Subcategory>{
        category: null
      },
      productThumbnails: [
        <ProductThumbnail>{thumbnail: ''},
        <ProductThumbnail>{thumbnail: ''},
        <ProductThumbnail>{thumbnail: ''},
        <ProductThumbnail>{thumbnail: ''},
      ],
    };
    this.productPreviewImg = 'assets/images/no-image.png';
    this.productPreviewThumbnails = new Array(this.product.productThumbnails.length)
      .fill('assets/images/no-image.png');

    this.category = <Category>{
      id: null,
      name: '',
      description: '',
      slug: '',
      modifiedAt: null,
      modifiedBy: '',
      actived: true,
    };
    this.subcategory = <Subcategory>{
      id: null,
      name: '',
      description: '',
      slug: '',
      modifiedAt: null,
      modifiedBy: '',
      actived: true
    };
    this.brand = <Brand>{
      id: null,
      name: '',
      description: '',
      slug: '',
      modifiedAt: null,
      modifiedBy: '',
      actived: true
    };
  }

  /* Product */
  newProduct() {
    this.isCopy = false;
    this.productForm.control.reset();
    setTimeout(() => {
      this.newData();
      this.product.brand = null;
      this.category = null;
    });
    this.openModal(this.productModal);
  }

  editProduct(product: Product) {
    this.product = product;
    this.category = product.subCategory.category;

    if(this.isCopy) {
      this.product.id = null;
      this.product.name = `${this.product.name} - Copy`;
      this.product.slug = `${this.product.slug}-copy`;
    }

    if (this.product.productThumbnails.length < 4) {
      for (let i = this.product.productThumbnails.length; i < 4; i++) {
        this.product.productThumbnails.push(<ProductThumbnail>{thumbnail: ''});
      }
    }
    this.product.productThumbnails.sort((a, b) => a.id - b.id);
    this.productPreviewImg = this.getSrc(this.product.img);
    this.productPreviewThumbnails = this.product.productThumbnails.map(item => this.getSrc(item.thumbnail));

    this.getSubcategoriesByCategory();
    this.openModal(this.productModal);
  }

  updateProduct(product: Product) {
    this.isCopy = false;
    this.editProduct(product);
  }

  copyProduct(product: Product) {
    this.isCopy = true;
    this.editProduct(product);
  }

  saveProduct(product: Product) {
    let params = new HttpParams().set('isCopy', this.isCopy.toString() || "");
    this.product.weight = this.getWeightFromHtml(this.product.design);
    if (product.id) {
      this.productService.update(product).subscribe(data => {
        NotiflixUtils.successNotify('Cập nhật thành công');

        this.closeModal();
        this.rerender();
      });
    } else {
      this.productService.create(product, params).subscribe(data => {
        NotiflixUtils.successNotify('Thêm mới thành công');

        this.closeModal();
        this.rerender();
      });
    }
  }

  deleteProduct(product: Product) {
    NotiflixUtils.showConfirm('Xác nhận', 'Xoá "' + product.name + '"?', () => {
      this.productService.changeActive(product.id).subscribe(data => {
        NotiflixUtils.successNotify('Xoá sản phẩm thành công');
        this.rerender();
      });
    });
  }

  activeProduct(id: number) {
    this.productService.changeActive(id).subscribe(data => {
      NotiflixUtils.successNotify('Kích hoạt sản phẩm thành công');
      this.rerender();
    });
  }

  getWeightFromHtml(html: string): number {
    html = html
      .replaceAll(/&nbsp;/g, '').toLowerCase();

    const weight = html.match(/<span class="text-muted">trọng lượng:(\s*)<\/span>([0-9.]+)(\s*)(kg|g|)/)
      ?? html.match(/<span class="text-muted">trọng lượng:(\s*)<\/span><span>([0-9.]+)(\s*)(kg|g)<\/span>/);
    if (weight[4] === 'g') {
      return Number(weight[2]) / 1000;
    } else {
      return Number(weight[2]);
    }
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

  /* Other */
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

  compareFn(c1: any, c2: any): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  selectedImageChanged($event: Event) {
    const file = $event.target['files'][0];
    this.uploadService.uploadImage(file).subscribe(data => {
      this.product.img = data.filename;
      this.productPreviewImg = `${environment.globalUrl.tempFolder}/${data.filename}`;
      $event.target['value'] = '';
    });
  }

  selectedThumbnailChange($event: Event, index: number) {
    const file = $event.target['files'][0];
    this.uploadService.uploadImage(file).subscribe(data => {
      this.product.productThumbnails[index].thumbnail = data.filename;
      this.productPreviewThumbnails[index] = `${environment.globalUrl.tempFolder}/${data.filename}`;
      $event.target['value'] = '';
    });
  }

  getSrc(image: string) {
    return image ? `${environment.globalUrl.productImg}/${image}` : 'assets/images/no-image.png';
  }

  getUrlYoutubeEmbed(url: string) {
    return this.sanitizer.bypassSecurityTrustResourceUrl(`https://www.youtube.com/embed/${UrlUtils.getYoutubeId(url)}?rel=0`);
  }
}
