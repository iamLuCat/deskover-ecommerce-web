import {Category} from '@/entites/category';
import {Component, OnInit, ViewChild} from '@angular/core';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {UrlUtils} from "@/utils/url-utils";
import {CategoryService} from '@services/category.service';
import {ADTSettings} from 'angular-datatables/src/models/settings';
import Swal from 'sweetalert2';
import {DatePipe} from "@angular/common";

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit {
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
    this.category = <Category>{};
  }

  ngOnInit() {
    this.dtOptions = {
      pagingType: 'full_numbers',
      paging: true,
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      responsive: false,
      serverSide: true,
      processing: true,
      ajax: (dataTablesParameters: any, callback) => {
        this.categoryService.getAll(dataTablesParameters.start / dataTablesParameters.length, dataTablesParameters.length, true).subscribe(data => {
          callback({
            recordsTotal: data.totalElements,
            recordsFiltered: data.totalElements,
            data: data.content
          });
        });
      },
      columns: [
        {title: 'Tên', data: 'name'},
        {title: 'Slug', data: 'slug'},
        {
          title: 'Ngày tạo',
          data: 'createdAt',
          className: 'text-center',
          render: (data, type, full, meta) => {
            return new DatePipe('en-US').transform(data, 'dd/MM/yyyy');
          }
        },
        {
          title: 'Ngày sửa',
          data: 'modifiedAt',
          className: 'text-center',
          render: (data, type, full, meta) => {
            return new DatePipe('en-US').transform(data, 'dd/MM/yyyy');
          }
        },
        {
          title: 'Trạng thái',
          data: 'actived',
          className: 'text-center',
          render: (data, type, full, meta) => {
            return data ? '<span class="badge badge-success">Hoạt động</span>' : '<span class="badge badge-danger">Không hoạt động</span>';
          }
        },
        {
          title: 'Tác vụ',
          data: null,
          orderable: false,
          searchable: false,
          className: 'text-center',
          render: (data, type, full, meta) => {
            return `
                <a href="javascript:void(0)" class="btn btn-sm btn-warning"
                   data-toggle="tooltip" data-placement="top" title="Sửa"><i
                  class="fa fa-edit"></i></a>
                <a href="javascript:void(0)" class="btn btn-sm btn-danger"
                   data-toggle="tooltip" data-placement="top" title="Xoá"><i
                  class="fa fa-trash"></i></a>
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
    // return Object.values(this.categories).find(category => category.id == id);
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
