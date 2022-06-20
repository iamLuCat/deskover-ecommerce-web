import {Category} from '@/entites/category';
import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {UrlUtils} from "@/utils/url-utils";
import {DatePipe} from "@angular/common";
import {DataTableDirective} from "angular-datatables";
import {Subject} from "rxjs";
import Swal from 'sweetalert2';
import {ToastrService} from "ngx-toastr";
import {environment} from "../../../environments/environment";
import {RestApiService} from "@services/rest-api.service";


@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit, AfterViewInit, OnDestroy {
  url = environment.globalUrl.categoryApi;

  categories: Category[];
  category: Category;
  closeResult: string;
  isEdit: boolean = false;

  dtOptions: any = {};
  dtTrigger: Subject<any> = new Subject();

  @ViewChild('categoryModal') categoryModal: any;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(
    private modalService: NgbModal,
    private restApiService: RestApiService,
    private toastr: ToastrService,
  ) {
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
        this.restApiService.post(this.url + '/datatables', dataTablesParameters).toPromise().then(resp => {
          self.categories = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
            data: self.categories.filter(category => category.actived)
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
        // {
        //   title: 'Trạng thái', data: 'actived', className: 'align-middle text-left text-md-center',
        //   render: (data, type, full, meta) => {
        //     return `<span class="badge badge-${data ? 'success' : 'danger'}">${data ? 'Đã kích hoạt' : 'Chưa kích hoạt'}</span>`;
        //   }
        // },
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
    this.restApiService.getOne(this.url, id).subscribe(data => {
      this.category = data;
      this.isEdit = true;
      this.openModal(this.categoryModal);
    });
  }

  saveCategory(category: Category) {
    if (this.isEdit) {
      this.restApiService.put(this.url, category).subscribe(data => {
        this.toastr.success('Cập nhật thành công');
        this.rerender();
      }, error => {
        this.toastr.error(error);
      });
    } else {
      this.restApiService.post(this.url, category).subscribe(data => {
        this.toastr.success('Thêm mới thành công');
        this.rerender();
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
        this.restApiService.delete(this.url, id).subscribe(data => {
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
