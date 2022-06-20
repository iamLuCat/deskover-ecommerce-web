import {Category} from '@/entites/category';
import {AfterViewInit, Component, OnInit, ViewChild} from '@angular/core';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {UrlUtils} from "@/utils/url-utils";
import {CategoryService} from '@services/category.service';
import Swal from 'sweetalert2';
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit, AfterViewInit {
  categories: Category[];
  category: Category;
  closeResult: string;
  isEdit: boolean = false;

  dtOptions: any = {};

  @ViewChild('categoryModal') categoryModal: any;

  constructor(
    private modalService: NgbModal,
    private categoryService: CategoryService,
  ) {
  }



  ngAfterViewInit() {
    const self = this;

    // delete category
    $('body').on('click', '.btn-delete', function () {
      const id = $(this).data('id');
      self.deleteCategory(id);
    });
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
        this.categoryService.getAllForDatatables(dataTablesParameters).then(resp => {
          self.categories = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
            data: self.categories
          });
        });
      },
      columns: [
        {title: 'Tên danh mục', data: 'name', className: 'align-middle'},
        {title: 'Slug danh mục', data: 'slug', className: 'align-middle'},
        {
          title: 'Ngày tạo', data: 'createdAt', className: 'align-middle text-left text-md-center',
          render: (data, type, full, meta) => {
            return new DatePipe('en-US').transform(data, 'dd/MM/yyyy');
          }
        },
        {
          title: 'Ngày cập nhật', data: 'modifiedAt', className: 'align-middle text-left text-md-center',
          render: (data, type, full, meta) => {
            return new DatePipe('en-US').transform(data, 'dd/MM/yyyy');
          }
        },
        /*{
          title: 'Trạng thái', data: 'actived', className: 'align-middle text-left text-md-center',
          render: (data, type, full, meta) => {
            return `<span class="badge badge-${data ? 'success' : 'danger'}">${data ? 'Đã kích hoạt' : 'Chưa kích hoạt'}</span>`;
          }
        },*/
        {
          title: 'Tác vụ', data: null, orderable: false, searchable: false, className: 'align-middle text-left text-md-center',
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

  newCategory() {
    this.isEdit = false;
    this.category = <Category>{};
    this.openModal(this.categoryModal);
  }

  getCategories(page: number, size: number, isActive: Boolean) {
    this.categoryService.getAll(page, size, isActive).subscribe(data => {
      this.categories = data;
    });
  }

  getCategory(id: number) {
  }

  editCategory(id: number) {
  }

  saveCategory(category: Category) {
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
        // this.categoryService.delete(id).subscribe(data => {
        //   Swal.fire(
        //     'Xoá thành công!',
        //     'Danh mục đã được xoá.',
        //     'success'
        //   );
        //   this.getCategories(1, 10, true);
        // });
      }
    })
  }

  // Slugify
  toSlug(text: string) {
    return UrlUtils.slugify(text);
  }

  // Modal bootstrap
  openModal(content) {
    this.modalService.open(content, {ariaLabelledBy: 'modal-basic-title', centered: true}).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${CategoryComponent.getDismissReason(reason)}`;
    });
  }

  private static getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

}
