import {Component, OnInit, ViewChild} from '@angular/core';
import {UrlUtils} from "@/utils/url-utils";
import {DataTableDirective} from "angular-datatables";
import {NotiflixUtils} from '@/utils/notiflix-utils';
import {Brand} from "@/entites/brand";
import {BrandService} from "@services/brand.service";
import {ModalDirective} from "ngx-bootstrap/modal";
import {FormControlDirective} from "@angular/forms";

@Component({
  selector: 'app-brand',
  templateUrl: './brands.component.html',
  styleUrls: ['./brands.component.scss']
})
export class BrandsComponent implements OnInit {

  brands: Brand[];
  brand: Brand = <Brand>{};

  isEdit: boolean = false;
  isActive: boolean = true;

  dtOptions: any = {};

  @ViewChild('brandModal') brandModal: ModalDirective;
  @ViewChild('brandForm') brandForm: FormControlDirective;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(private brandService: BrandService) {
  }

  ngOnInit() {
    const self = this;

    this.dtOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      lengthMenu: [5, 10, 25, 50, 100],
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
        {data: 'name'},
        {data: 'slug'},
        {data: 'description'},
        {data: 'modifiedAt'},
        {data: null, orderable: false, searchable: false,},
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
    this.brandForm.control.reset();
    this.isEdit = false;
    this.brand = <Brand>{};
    this.openModal(this.brandModal);
  }

  getBrand(id: number) {
    this.isEdit = true;
    this.brandService.getById(id).subscribe(data => {
      this.brand = data;
      this.openModal(this.brandModal);
    });
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

  // Slugify
  toSlug(text: string) {
    return UrlUtils.slugify(text);
  }

  // Modal
  openModal(content) {
    this.brandModal.show();
  }

  closeModal() {
    this.brandModal.hide();
  }

}
