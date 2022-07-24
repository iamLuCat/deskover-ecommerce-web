import {Component, OnInit, ViewChild} from '@angular/core';
import {DataTableDirective} from "angular-datatables";
import {UrlUtils} from "@/utils/url-utils";
import {Subcategory} from "@/entites/subcategory";
import {SubcategoryService} from "@services/subcategory.service";
import {CategoryService} from "@services/category.service";
import {NotiflixUtils} from '@/utils/notiflix-utils';
import {Category} from "@/entites/category";
import {ModalDirective} from "ngx-bootstrap/modal";
import {FormControlDirective} from "@angular/forms";
import {UploadService} from "@services/upload.service";

@Component({
  selector: 'app-subcategory',
  templateUrl: './subcategories.component.html',
  styleUrls: ['./subcategories.component.scss'],
})
export class SubcategoriesComponent implements OnInit {
  subcategories: Subcategory[];
  subcategory: Subcategory = <Subcategory>{};

  categories: Category[];
  categoryId: number = null;

  isEdit: boolean = false;
  isActive: boolean = true;

  dtOptions: any = {};

  @ViewChild('subcategoryModal') subcategoryModal: ModalDirective;
  @ViewChild('subcategoryForm') subcategoryForm: FormControlDirective;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(
    private subcategoryService: SubcategoryService,
    private categoryService: CategoryService,
    private uploadService: UploadService,
  ) {
    this.getCategories();
  }

  ngOnInit() {
    const self = this;

    this.dtOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      lengthMenu: [5, 10, 25, 50, 100],
      responsive: true,
      serverSide: true,
      processing: true,
      stateSave: true,
      ajax: (dataTablesParameters: any, callback) => {
        this.subcategoryService.getAllForDatatable(dataTablesParameters, this.isActive, this.categoryId).subscribe(resp => {
          self.subcategories = resp.data;
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
        {data: 'category.name'},
        {data: 'modifiedAt'},
        {data: null, orderable: false, searchable: false}
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

  getCategories() {
    this.categoryService.getByActive().subscribe(data => {
      this.categories = data;
    });
  }

  newSubcategory() {
    this.subcategoryForm.control.reset();
    this.isEdit = false;
    this.openModal(this.subcategoryModal);
  }

  getSubcategory(id: number) {
    this.subcategoryService.getOne(id).subscribe(data => {
      this.subcategory = data;
      this.isEdit = true;
      this.openModal(this.subcategoryModal);
    });
  }

  saveSubcategory(subcategory: Subcategory) {
    if (!this.isEdit) {
      this.subcategoryService.create(subcategory).subscribe(data => {
        NotiflixUtils.successNotify('Thêm mới thành công');
        this.rerender();
        this.closeModal(this.subcategoryModal);
      });
    } else {
      this.subcategoryService.update(subcategory).subscribe(data => {
        NotiflixUtils.successNotify('Cập nhật thành công');
        this.rerender();
        this.closeModal(this.subcategoryModal);
      });
    }
  }

  deleteSubcategory(subcategory: Subcategory) {
    NotiflixUtils.showConfirm('Xác nhận', 'Xoá "' + subcategory.name + '"?', () => {
      this.subcategoryService.changeActive(subcategory.id).subscribe(data => {
        NotiflixUtils.successNotify('Xoá danh mục thành công');
        this.rerender();
      });
    });
  }

  activeSubcategory(id: number) {
    this.subcategory = this.subcategories.find(item => item.id === id);
    if (this.subcategory) {
      if (!this.subcategory.category.actived) {
        NotiflixUtils.showConfirm('Danh mục cha đã bị khoá', 'Kích hoạt lại danh mục cha?', () => {
          this.categoryService.changeActive(this.subcategory.category.id).subscribe(data => {
            this.changeActive(id);
          });
        });
      } else {
        this.changeActive(id);
      }
    }
  }

  private changeActive(id: number) {
    this.subcategoryService.changeActive(id).subscribe(data => {
      NotiflixUtils.successNotify('Kích hoạt danh mục thành công');
      this.rerender();
    });
  }

  compareFn(c1: any, c2: any): boolean {
    return c1 && c2 ? c1.id === c2.id : c1 === c2;
  }

  // Slugify
  toSlug(text: string) {
    return UrlUtils.slugify(text);
  }

  // Modal bootstrap
  openModal(content: ModalDirective) {
    if(!content.isShown) {
      content?.show();
    }
  }

  closeModal(content: ModalDirective) {
    content?.hide();
  }

  selectedImageChanged($event: Event) {
    const file = $event.target['files'][0];
    this.uploadService.uploadImage(file).subscribe(data => {
      this.subcategory.imgUrl = data.url;
      this.subcategory.img = data.filename;
    });
  }

}
