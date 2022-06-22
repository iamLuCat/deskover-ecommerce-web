import Swal from 'sweetalert2';
import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Subject} from "rxjs";
import {DataTableDirective} from "angular-datatables";
import {NgbModal, NgbModalConfig} from "@ng-bootstrap/ng-bootstrap";
import {ToastrService} from "ngx-toastr";
import {DatePipe} from "@angular/common";
import {UrlUtils} from "@/utils/url-utils";
import {Subcategory} from "@/entites/subcategory";
import {SubcategoryService} from "@services/subcategory.service";
import {CategoryService} from "@services/category.service";
import {Category} from "@/entites/category";

@Component({
  selector: 'app-subcategory',
  templateUrl: './subcategory.component.html',
  styleUrls: ['./subcategory.component.scss'],
  providers: [NgbModalConfig, NgbModal]
})
export class SubcategoryComponent implements OnInit, AfterViewInit, OnDestroy {
  subcategories: Subcategory[];
  subcategory: Subcategory;

  categories: Category[];

  isEdit: Boolean = false;
  isActive: Boolean = true;

  dtOptions: any = {};
  dtTrigger: Subject<any> = new Subject();

  @ViewChild('subcategoryModal') subcategoryModal: any;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(
    private modalConfig: NgbModalConfig,
    private modalService: NgbModal,
    private subcategoryService: SubcategoryService,
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
        this.subcategoryService.getAllForDatatable(dataTablesParameters).then(resp => {
          self.subcategories = resp.data.filter(category => category.actived === this.isActive);
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: self.subcategories.length,
            data: self.subcategories
          });
        });
      },
      columns: [
        {title: 'Tên', data: 'name', className: 'align-middle'},
        {title: 'Slug', data: 'slug', className: 'align-middle'},
        {title: 'Danh mục cha', data: 'category.name', className: 'align-middle'},
        {
          title: 'Ngày sửa', data: 'modifiedAt', className: 'align-middle text-left',
          render: (data, type, full, meta) => {
            return new DatePipe('en-US').transform(data, 'dd/MM/yyyy');
          }
        },
        {
          title: 'Công cụ',
          data: null,
          orderable: false,
          searchable: false,
          className: 'align-middle text-left text-md-center',
          render: (data, type, full, meta) => {
            if (self.isActive) {
              return `
                <a href="javascript:void(0)" class="btn btn-edit btn-sm bg-faded-info" data-id="${data.id}"
                    title="Sửa" data-toggle="tooltip">
                    <i class="fa fa-pen-square text-info"></i>
                </a>
                <a href="javascript:void(0)" class="btn btn-delete btn-sm bg-faded-danger" data-id="${data.id}"
                    title="Xoá" data-toggle="tooltip">
                    <i class="fa fa-trash text-danger"></i>
                </a>
            `;
            } else {
              return `
               <button type="button" class="btn btn-active btn-sm bg-success"
                (click)="activeCategory(item.id)"> Kích hoạt </button>`
            }
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
      self.getSubcategory(id);
    });
    body.on('click', '.btn-delete', function () {
      const id = $(this).data('id');
      self.deleteSubcategory(id);
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

  newSubcategory() {
    this.isEdit = false;
    this.subcategory = <Subcategory>{};
    this.openModal(this.subcategoryModal);
  }

  getSubcategory(id: number) {
    this.subcategoryService.getOne(id).subscribe(data => {
      this.subcategory = data;
      this.isEdit = true;
      this.openModal(this.subcategoryModal);
    });
  }

  saveSubcategory(category: Subcategory) {
    if (this.isEdit) {
      this.subcategoryService.create(category).subscribe(data => {
        this.toastr.success('Cập nhật thành công');
        this.rerender();
        this.closeModal();
      }, error => {
        this.toastr.error(error);
      });
    } else {
      this.subcategoryService.update(category).subscribe(data => {
        this.toastr.success('Thêm mới thành công');
        this.rerender();
        this.closeModal();
      }, error => {
        this.toastr.error(error);
      });
    }
  }

  deleteSubcategory(id: number) {
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
        this.subcategoryService.delete(id).subscribe(data => {
          this.toastr.success('Xoá danh mục thành công');
          this.rerender();
        });
      }
    });
  }

  // Slugify
  toSlug(text: string) {
    return UrlUtils.slugify(text);
  }

  // Modal bootstrap
  openModal(content) {
    this.modalService.open(content);
  }

  closeModal() {
    this.modalService.dismissAll();
  }

}
