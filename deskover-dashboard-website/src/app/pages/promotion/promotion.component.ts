import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {Discount} from "@/entites/discount";
import {Subject} from "rxjs";
import {DataTableDirective} from "angular-datatables";
import {NgbModal, NgbModalConfig} from "@ng-bootstrap/ng-bootstrap";
import {DiscountService} from "@services/discount.service";
import {DatePipe} from "@angular/common";
import {AlertUtils} from "@/utils/alert-utils";
import {BsDatepickerConfig} from "ngx-bootstrap/datepicker";

@Component({
  selector: 'app-promotion',
  templateUrl: './promotion.component.html',
  styleUrls: ['./promotion.component.scss']
})
export class PromotionComponent implements OnInit, AfterViewInit {
  discounts: Discount[];
  discount: Discount;

  isEdit: boolean = false;
  isActive: boolean = true;

  dtOptions: any = {};

  bsConfig?: Partial<BsDatepickerConfig>;
  discountDateRange: Date[] = [new Date(), new Date()];
  discountStartTime: Date = new Date();
  discountEndTime: Date = new Date();

  @ViewChild('discountModal') discountModal: any;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(
    private modalConfig: NgbModalConfig,
    private modalService: NgbModal,
    private discountService: DiscountService,
  ) {
    modalConfig.size = 'lg';
    modalConfig.backdrop = 'static';
    modalConfig.keyboard = false;
    modalConfig.centered = true;

    // Config datepicker ngx-bootstrap
    this.bsConfig = Object.assign({}, {
      containerClass: 'theme-dark-blue',
      withTimepicker: false,
      locale: 'vi',
      rangeInputFormat: 'DD/MM/YYYY',
      dateInputFormat: 'DD/MM/YYYY',
      adaptivePosition: true,
      minDate: new Date(),
    });
  }

  ngOnInit() {
    const self = this;

    self.dtOptions = {
      pagingType: 'full_numbers',
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      responsive: true,
      lengthMenu: [5, 10, 25, 50, 100],
      serverSide: true,
      processing: true,
      stateSave: true, // sau khi refresh sẽ giữ lại dữ liệu đã filter, sort và paginate
      ajax: (dataTablesParameters: any, callback) => {
        this.discountService.getByActiveForDatatable(dataTablesParameters, this.isActive).then(resp => {
          self.discounts = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
            data: self.discounts
          });
        });
      },
      columns: [
        {title: 'Tên', data: 'name', className: 'align-middle', responsivePriority: 1},
        {title: 'Mô tả', data: 'description', className: 'align-middle'},
        {
          title: 'Mức giảm giá (%)', data: 'percent', className: 'align-middle text-start text-md-center',
          responsivePriority: 3,
          render(data, type, row, meta) {
            return `<span class="badge bg-danger">${data}</span>`;
          }
        },
        {
          title: 'Thời gian bắt đầu', data: 'startDate', className: 'align-middle text-start text-md-center',
          render: function (data, type, row) {
            return new DatePipe('en-US').transform(data, 'dd/MM/yyyy HH:mm:ss');
          }
        },
        {
          title: 'Thời gian kết thúc', data: 'endDate', className: 'align-middle text-start text-md-center',
          render: function (data, type, row) {
            return new DatePipe('en-US').transform(data, 'dd/MM/yyyy HH:mm:ss');
          }
        },
        {
          title: 'Công cụ',
          data: null,
          orderable: false,
          searchable: false,
          className: 'align-middle text-end',
          responsivePriority: 2,
          render: (data, type, full, meta) => {
            if (self.isActive) {
              return `
              <div class="d-flex justify-content-end align-items-center">
                <a href="javascript:void(0)" class="btn btn-edit btn-sm bg-faded-info me-2" data-id="${data.id}"
                    title="Sửa" data-toggle="tooltip">
                    <i class="fa fa-pen-square text-info"></i>
                </a>
                <a href="javascript:void(0)" class="btn btn-delete btn-sm bg-faded-danger" data-id="${data.id}"
                    title="Xoá" data-toggle="tooltip">
                    <i class="fa fa-trash text-danger"></i>
                </a>
              </div>
            `;
            } else {
              return `
               <button type="button" class="btn btn-active btn-sm bg-success" data-id="${data.id}">Kích hoạt</button>`
            }
          }
        },
      ]
    }
  }

  ngAfterViewInit() {
    const self = this;

    let body = $('body');
    body.on('click', '.btn-edit', function () {
      const id = $(this).data('id');
      self.getDiscount(id);
    });
    body.on('click', '.btn-delete', function () {
      const id = $(this).data('id');
      self.deleteDiscount(id);
    });
    body.on('click', '.btn-active', function () {
      const id = $(this).data('id');
      self.activeDiscount(id);
    });
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

  newDiscount() {
    this.isEdit = false;
    this.discount = <Discount>{};

    this.discountDateRange = [new Date(), new Date()];
    this.discountStartTime = new Date();
    this.discountEndTime = new Date();

    this.openModal(this.discountModal);
  }

  getDiscount(id: number) {
    this.discountService.getById(id).subscribe(data => {
      this.discount = data;
      this.discountDateRange = [new Date(this.discount.startDate), new Date(this.discount.endDate)];
      this.discountStartTime = new Date(this.discount.startDate);
      this.discountEndTime = new Date(this.discount.endDate);
    });
    this.isEdit = true;
    this.openModal(this.discountModal);
  }

  saveDiscount(discount: Discount) {
    this.discount.startDate = this.discountDateRange[0].setTime(this.discountStartTime.getTime());
    this.discount.endDate = this.discountDateRange[1].setTime(this.discountEndTime.getTime());
    if (this.isEdit) {
      this.discountService.update(discount).subscribe(data => {
        AlertUtils.toastSuccess('Cập nhật thành công');
      }, error => {
        AlertUtils.toastError(error);
      });
    } else {
      this.discountService.create(discount).subscribe(data => {
        AlertUtils.toastSuccess('Thêm mới thành công');
      }, error => {
        AlertUtils.toastError(error);
      });
    }
    this.rerender();
    this.closeModal();
  }

  deleteDiscount(id: number) {
    AlertUtils.warning('Xác nhận', 'Các danh mục con liên quan cũng sẽ bị xoá').then((result) => {
      if (result.value) {
        this.discountService.changeActive(id).subscribe(data => {
          AlertUtils.toastSuccess('Xoá danh mục thành công');
          this.rerender();
        });
      }
    });
  }

  activeDiscount(id: number) {
    this.discountService.changeActive(id).subscribe(data => {
      AlertUtils.toastSuccess('Kích hoạt danh mục thành công');
      this.rerender();
    });
  }

  // Modal
  openModal(content) {
    this.closeModal();
    this.modalService.open(content);
  }

  closeModal() {
    this.modalService.dismissAll();
  }
}