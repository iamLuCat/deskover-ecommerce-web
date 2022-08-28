import {environment} from "../../../../../environments/environment";
import {Component, OnInit, ViewChild} from '@angular/core';
import {UrlUtils} from "@/utils/url-utils";
import {DataTableDirective} from "angular-datatables";
import {NotiflixUtils} from '@/utils/notiflix-utils';
import {Brand} from "@/entites/brand";
import {BrandService} from "@services/brand.service";
import {ModalDirective} from "ngx-bootstrap/modal";
import {FormControlDirective} from "@angular/forms";
import {UploadService} from "@services/upload.service";
import {PermissionContants} from "@/constants/permission-contants";
import {AuthService} from '@services/auth.service';

@Component({
  selector: 'app-brand',
  templateUrl: './brands.component.html',
  styleUrls: ['./brands.component.scss']
})
export class BrandsComponent implements OnInit {
  brands: Brand[];
  brand: Brand = <Brand>{};
  brandImgPreview: string;

  isEdit: boolean = false;
  isActive: boolean = true;

  dtOptions: any = {};

  @ViewChild('brandModal') brandModal: ModalDirective;
  @ViewChild('brandForm') brandForm: FormControlDirective;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(
    private brandService: BrandService,
    private uploadServive: UploadService,
    private authService: AuthService
  ) {}

  ngOnInit() {
    const self = this;

    this.dtOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      serverSide: true,
      processing: true,
      stateSave: true,
      ajax: (dataTablesParameters: any, callback) => {
        this.brandService.getByActiveForDatatable(dataTablesParameters, this.isActive).subscribe(resp => {
          self.brands = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
            data: []
          });
        });
      },
      columns: [
        {data: 'imgUrl', orderable: false, searchable: false},
        {data: 'name'},
        {data: 'slug'},
        /*{data: 'description'},*/
        {data: 'modifiedAt'},
        {data: 'modifiedBy'},
        {
          data: null, orderable: false, searchable: false,
          // visible: self.hasAdminRole(),
        },
      ]
    }
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

  newBrand() {
    this.isEdit = false;

    this.brandForm.control.reset();
    this.brand = <Brand>{};
    this.brandImgPreview = 'assets/images/no-image.png';

    this.openModal(this.brandModal);
  }

  editBrand(brand: Brand) {
    this.brand = Object.assign({}, brand);
    this.brandImgPreview = this.getSrc(this.brand.img);

    this.isEdit = true;
    this.openModal(this.brandModal);
  }

  saveBrand(brand: Brand) {
    if (this.isEdit) {
      this.brandService.update(brand).subscribe(data => {
        NotiflixUtils.successNotify('Cập nhật thành công');
        this.rerender();
        this.closeModal();
      });
    } else {
      this.brandService.create(brand).subscribe(data => {
        NotiflixUtils.successNotify('Thêm mới thành công');
        this.rerender();
        this.closeModal();
      });
    }
  }

  deleteBrand(brand: Brand) {
    NotiflixUtils.showConfirm('Xác nhận', 'Xoá "' + brand.name + '"?', () => {
      this.brandService.changeActive(brand.id).subscribe(data => {
        NotiflixUtils.successNotify('Xoá thương hiệu thành công');
        this.rerender();
      });
    });
  }

  activeBrand(id: number) {
    this.brandService.changeActive(id).subscribe(data => {
      NotiflixUtils.successNotify('Kích hoạt danh mục thành công');
      this.rerender();
    });
  }

  /* Utils */
  toSlug(text: string) {
    return UrlUtils.slugify(text);
  }

  openModal(content) {
    this.brandModal.show();
  }

  closeModal() {
    this.rerender();
    this.brandModal.hide();
  }

  selectedImageChanged($event: Event) {
    const file = $event.target['files'][0];
    this.uploadServive.uploadImage(file).subscribe(data => {
      this.brand.img = data.filename;
      this.brandImgPreview = `${environment.globalUrl.tempFolder}/${data.filename}`;
      $event.target['value'] = '';
    });

  }

  getSrc(image: string) {
    return image ? `${environment.globalUrl.brandImg}/${image}` : 'assets/images/no-image.png';
  }

  hasAdminRole() {
    return this.authService.hasPermissions([
      PermissionContants.ADMIN
    ]);
  }
}
