import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {NgbModal, NgbModalConfig} from '@ng-bootstrap/ng-bootstrap';
import {UrlUtils} from "@/utils/url-utils";
import {DataTableDirective} from "angular-datatables";
import {Subject} from "rxjs";
import {AlertUtils} from '@/utils/alert-utils';
import {Brand} from "@/entites/brand";
import {BrandService} from "@services/brand.service";

@Component({
  selector: 'app-brand',
  templateUrl: './brand.component.html',
  styleUrls: ['./brand.component.scss']
})
export class BrandComponent implements OnInit, OnDestroy, AfterViewInit {

  brands: Brand[];
  brand: Brand;

  isEdit: boolean = false;
  isActive: boolean = true;

  dtOptions: any = {};
  dtTrigger: Subject<any> = new Subject();

  @ViewChild('brandModal') brandModal: any;
  @ViewChild(DataTableDirective, {static: false}) dtElement: DataTableDirective;

  constructor(
    private modalConfig: NgbModalConfig,
    private modalService: NgbModal,
    private brandService: BrandService
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
        this.brandService.getByActiveForDatatable(dataTablesParameters, this.isActive).then(resp => {
          self.brands = resp.data;
          callback({
            recordsTotal: resp.recordsTotal,
            recordsFiltered: resp.recordsFiltered,
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

  newBrand() {
    this.isEdit = false;
    this.brand = <Brand>{};
    this.openModal(this.brandModal);
  }

  getBrand(id: number) {
    this.brandService.getById(id).subscribe(data => {
      this.brand = data;
      this.isEdit = true;
      this.openModal(this.brandModal);
    });
  }

  saveBrand(brand: Brand) {
    if (this.isEdit) {
      this.brandService.update(brand).subscribe(data => {
        AlertUtils.toastSuccess('Cập nhật thành công');
        this.rerender();
        this.closeModal();
      }, error => {
        AlertUtils.toastError(error);
      });
    } else {
      this.brandService.create(brand).subscribe(data => {
        AlertUtils.toastSuccess('Thêm mới thành công');
        this.rerender();
        this.closeModal();
      }, error => {
        AlertUtils.toastError(error);
      });
    }
  }

  deleteBrand(id: number) {
    AlertUtils.warning('Xác nhận', 'Các danh mục con liên quan cũng sẽ bị xoá').then((result) => {
      if (result.value) {
        this.brandService.changeActive(id).subscribe(data => {
          AlertUtils.toastSuccess('Xoá danh mục thành công');
          this.rerender();
        });
      }
    });
  }

  activeBrand(id: number) {
    this.brandService.changeActive(id).subscribe(data => {
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
