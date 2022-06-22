import {Category} from '@/entites/category';
import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NgbModal, NgbModalConfig} from '@ng-bootstrap/ng-bootstrap';
import {UrlUtils} from "@/utils/url-utils";
import {DataTableDirective} from "angular-datatables";
import {Subject} from "rxjs";
import {CategoryService} from "@services/category.service";
import {AlertUtils} from "@/utils/alert-utils";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss'],
  providers: [NgbModalConfig, NgbModal]
})
export class CategoryComponent implements OnInit, AfterViewInit, OnDestroy {

  categories: Category[];
  category: Category;

  isEdit: Boolean = false;
  isActive: Boolean = true;

  dtOptions: any = {};
  dtTrigger: Subject<any> = new Subject();

  @ViewChild('categoryModal') categoryModal: any;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(
    private modalConfig: NgbModalConfig,
    private modalService: NgbModal,
    private categoryService: CategoryService
  ) {
    modalConfig.backdrop = 'static';
    modalConfig.keyboard = false;
    modalConfig.centered = true;
  }

  ngOnInit() {
    const self = this;

    this.dtOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      responsive: true,
      serverSide: true,
      processing: true,
      ajax: (dataTablesParameters: any, callback) => {
        this.categoryService.getAllForDatatable(dataTablesParameters).then(resp => {
          self.categories = resp.data.filter(category => category.actived === this.isActive);
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: self.categories.length,
            data: []
          });
        });
      },
      columns: [
        { data: 'name' },
        { data: 'slug' },
        { data: 'modifiedAt' },
        // { data: 'actived' },
        { data: null, orderable: false, searchable: false },
      ]
    }
  }

  ngAfterViewInit() {
    const self = this;
    this.dtTrigger.next();
  }

  ngOnDestroy() {
    this.dtTrigger.unsubscribe();
  }

  rerender(): void {
    this.dtElement.dtInstance.then((dtInstance: DataTables.Api) => {
      // Destroy the table first
      dtInstance.destroy();
      // Call the dtTrigger to rerender again
      this.dtTrigger.next();
    });
  }

  newCategory() {
    this.isEdit = false;
    this.category = <Category>{};
    this.openModal(this.categoryModal);
  }

  getCategory(id: number) {
    this.categoryService.getOne(id).subscribe(data => {
      this.category = data;
      this.isEdit = true;
      this.openModal(this.categoryModal);
    });
  }

  saveCategory(category: Category) {
    if (this.isEdit) {
      this.categoryService.update(category).subscribe(data => {
        AlertUtils.toastSuccess('Cập nhật thành công');
        this.rerender();
        this.closeModal();
      }, error => {
        AlertUtils.toastError(error);
      });
    } else {
      this.categoryService.create(category).subscribe(data => {
        AlertUtils.toastSuccess('Thêm mới thành công');
        this.rerender();
        this.closeModal();
      }, error => {
        AlertUtils.toastError(error);
      });
    }
  }

  deleteCategory(id: number) {
    AlertUtils.warning('Xác nhận', 'Các danh mục con liên quan cũng sẽ bị xoá').then((result) => {
      if (result.value) {
        this.categoryService.changeActive(id).subscribe(data => {
          AlertUtils.toastSuccess('Xoá danh mục thành công');
          this.rerender();
        });
      }
    });
  }

  activeCategory(id: number) {
    this.categoryService.changeActive(id).subscribe(data => {
      AlertUtils.toastSuccess('Kích hoạt danh mục thành công');
      this.rerender();
    });
  }

  // Slugify
  toSlug(text: string) {
    return UrlUtils.slugify(text);
  }

  // Modal
  openModal(content) {
    this.modalService.open(content);
  }

  closeModal() {
    this.modalService.dismissAll();
  }

}
