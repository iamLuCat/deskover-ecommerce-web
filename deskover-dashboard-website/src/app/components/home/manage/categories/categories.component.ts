import {Category} from '@/entites/category';
import {Component, OnInit, ViewChild} from '@angular/core';
import {UrlUtils} from "@/utils/url-utils";
import {DataTableDirective} from "angular-datatables";
import {CategoryService} from "@services/category.service";
import {NotiflixUtils} from '@/utils/notiflix-utils';
import {ModalDirective} from "ngx-bootstrap/modal";
import {FormControlDirective} from "@angular/forms";
import {UploadService} from "@services/upload.service";
import {environment} from "../../../../../environments/environment";
import {PermissionContants} from "@/constants/permission-contants";
import {AuthService} from "@services/auth.service";

@Component({
  selector: 'app-category',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.scss'],
})
export class CategoriesComponent implements OnInit {

  categories: Category[];
  category: Category = <Category>{};
  categoryImgPreview: string;

  isEdit: boolean = false;
  isActive: boolean = true;

  dtOptions: any = {};

  @ViewChild('categoryModal') categoryModal: ModalDirective;
  @ViewChild('categoryForm') categoryForm: FormControlDirective;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(
    private categoryService: CategoryService,
    private uploadService: UploadService,
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
        this.categoryService.getByActiveForDatatable(dataTablesParameters, this.isActive).subscribe(resp => {
          self.categories = resp.data;
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
        {data: 'description'},
        {data: 'modifiedAt'},
        {data: 'modifiedBy'},
        {
          data: null, orderable: false, searchable: false,
          // visible: self.hasAdminRole()
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
      dtInstance.ajax.reload();
    });
  }

  newCategory() {
    this.categoryForm.control.reset();
    this.category = <Category>{};
    this.categoryImgPreview = 'assets/images/no-image.png';
    this.isEdit = false;
    this.openModal(this.categoryModal);
  }

  editCategory(category: Category) {
    this.category = Object.assign({}, category);
    this.categoryImgPreview = this.getSrc(category.img);
    this.isEdit = true;
    this.openModal(this.categoryModal);
  }

  saveCategory(category: Category) {
    if (this.isEdit) {
      this.categoryService.update(category).subscribe(data => {
        NotiflixUtils.successNotify('Cập nhật thành công');
        this.rerender();
        this.closeModal();
      });
    } else {
      this.categoryService.create(category).subscribe(data => {
        NotiflixUtils.successNotify('Thêm mới thành công');
        this.rerender();
        this.closeModal();
      });
    }
  }

  deleteCategory(category: Category) {
    NotiflixUtils.showConfirm('Xác nhận xoá', 'Các danh mục con thuộc "' + category.name + '" sẽ bị xoá!', () => {
      this.categoryService.changeActive(category.id).subscribe(data => {
        NotiflixUtils.successNotify('Xoá danh mục thành công');
        this.rerender();
      });
    });
  }

  activeCategory(id: number) {
    this.categoryService.changeActive(id).subscribe(data => {
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
    this.categoryModal.show();
  }

  closeModal() {
    this.categoryModal.hide();
  }

  selectedImageChanged($event: Event) {
    const file = $event.target['files'][0];
    this.uploadService.uploadImage(file).subscribe(data => {
      this.category.img = data.filename;
      this.categoryImgPreview = `${environment.globalUrl.tempFolder}/${data.filename}`;
      $event.target['value'] = '';
    });
  }

  getSrc(image: string) {
    return image ? `${environment.globalUrl.categoryImg}/${image}` : 'assets/images/no-image.png';
  }

  hasAdminRole() {
    return this.authService.hasPermissions([
      PermissionContants.ADMIN
    ]);
  }
}
