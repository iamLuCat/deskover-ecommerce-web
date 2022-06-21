import {Category} from '@/entites/category';
import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NgbModal, NgbModalConfig} from '@ng-bootstrap/ng-bootstrap';
import {UrlUtils} from "@/utils/url-utils";
import {DatePipe} from "@angular/common";
import {DataTableDirective} from "angular-datatables";
import {Subject} from "rxjs";
import {ToastrService} from "ngx-toastr";
import Swal from 'sweetalert2';
import {CategoryService} from "@services/category.service";

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
    private categoryService: CategoryService,
    private toastr: ToastrService,
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
            recordsFiltered: resp.recordsFiltered,
            data: []
          });
        });
      },
      columns: [
        {title: 'Tên', data: 'name', className: 'align-middle'},
        {title: 'Slug', data: 'slug', className: 'align-middle'},
        {
          title: 'Ngày sửa', data: 'modifiedAt', className: 'align-middle text-left text-md-center',
          render: (data, type, full, meta) => {
            return new DatePipe('en-US').transform(data, 'dd/MM/yyyy');
          }
        },
        {
          title: 'Tác vụ',
          data: null,
          orderable: false,
          searchable: false,
          className: 'align-middle text-left text-md-center',
          render: (data, type, full, meta) => {
            return `
                <a href="javascript:void(0)" class="btn btn-edit btn-sm bg-faded-info"
                   data-id="${data.id}"><i
                  class="fa fa-pen-square text-info"></i></a>
                <a href="javascript:void(0)" class="btn btn-delete btn-sm bg-faded-danger"
                   data-id="${data.id}"><i
                  class="fa fa-trash text-danger"></i></a>
            `;
          }
        },
      ]
    }
  }

  ngAfterViewInit() {
    const self = this;

    this.dtTrigger.next();

    let body = $('body');
    body.on('click', '.btn-edit', function () {
      const id = $(this).data('id');
      self.getCategory(id);
    });
    body.on('click', '.btn-delete', function () {
      const id = $(this).data('id');
      self.deleteCategory(id);
    });
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
        this.toastr.success('Cập nhật thành công');
        this.rerender();
        this.closeModal();
      }, error => {
        this.toastr.error(error);
      });
    } else {
      this.categoryService.create(category).subscribe(data => {
        this.toastr.success('Thêm mới thành công');
        this.rerender();
        this.closeModal();
      }, error => {
        this.toastr.error(error);
      });
    }
  }

  deleteCategory(id: number) {
    Swal.fire({
      title: 'Xác nhận',
      text: "Bạn có chắc chắn muốn xoá danh mục này không?",
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor: '#d33',
      cancelButtonText: 'Không',
      confirmButtonColor: '#3085d6',
      confirmButtonText: 'Có',
    }).then((result) => {
      if (result.value) {
        this.categoryService.changeActive(id).subscribe(data => {
          this.toastr.success('Xoá danh mục thành công');
          this.rerender();
        });
      }
    });
  }

  activeCategory(id: number) {
    this.categoryService.changeActive(id).subscribe(data => {
      this.toastr.success('Kích hoạt danh mục thành công');
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
