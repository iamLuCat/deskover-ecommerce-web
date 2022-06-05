import { ApiService } from './../../services/api.service';
import { ICategory } from '@/entites/ICategory';
import { Component, ViewChild, OnInit, OnDestroy, AfterViewInit } from '@angular/core';
import { NgbModal, ModalDismissReasons } from '@ng-bootstrap/ng-bootstrap';
import { environment } from 'environments/environment';
import { AppService } from '@services/app.service';
import { Subject } from 'rxjs';
import { DataTableDirective } from 'angular-datatables';
import Swal from 'sweetalert2';

@Component({
  selector: 'app-category',
  templateUrl: './category.component.html',
  styleUrls: ['./category.component.scss']
})
export class CategoryComponent implements OnInit, OnDestroy, AfterViewInit {
  categories: ICategory[];
  category: ICategory;
  closeResult: string;
  isEdit: boolean = false;

  url = environment.apiURL + "/categories";

  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject<any>();

  @ViewChild('categoryModal') categoryModal: any;
  @ViewChild(DataTableDirective) dtElement: DataTableDirective;

  constructor(private modalService: NgbModal, private apiService: ApiService, public appService: AppService) {
    this.category = <ICategory>{};
    this.getCategories();
  }

  ngOnInit() {
    this.dtOptions = {
      pagingType: 'full_numbers',
      paging: true,
      language: {
        url: "//cdn.datatables.net/plug-ins/1.12.0/i18n/vi.json"
      },
      responsive: true,
    };
  }

  ngOnDestroy() {
    this.dtTrigger.unsubscribe();
  }

  ngAfterViewInit() {
    this.dtTrigger.next();
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
    this.category = <ICategory>{};
    this.openModal(this.categoryModal);
  }

  getCategories() {
    this.apiService.getAll(this.url).subscribe(data => {
      this.categories = data;
      this.rerender();
    });
  }

  getCategory(id: number) {
    return Object.values(this.categories).find(category => category.id == id);
  }

  editCategory(id: number) {
    this.isEdit = true;
    this.apiService.getOne(this.url, id).subscribe(data => {
      this.category = data;
    });
    if (this.category) {
      this.openModal(this.categoryModal);
    }
  }

  saveCategory(category: ICategory) {
    // if (this.key) {
    //   this.apiService.put(this.url, this.key, category).subscribe(data => {
    //     this.getCategories();
    //   });
    // } else {
    //   const categoriesArray = Object.values(this.categories);
    //   category.id = categoriesArray.length > 0 ? categoriesArray[categoriesArray.length - 1].id + 1 : 0;

    //   this.apiService.post(this.url, category).subscribe(data => {
    //     this.getCategories();
    //   });
    // }
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
      // if (result.value) {
      //   this.apiService.delete(this.url, key).subscribe(data => {
      //     this.getCategories();
      //   });
      // }
    })
  }

  // Slugify
  slugify(text: string) {
    if (text) {
      return text.toString().toLowerCase().trim()
        .replace(/[àáạảãâầấậẩẫăằắặẳẵ]/g, "a")
        .replace(/[èéẹẻẽêềếệểễ]/g, "e")
        .replace(/[ìíịỉĩ]/g, "i")
        .replace(/[òóọỏõôồốộổỗơờớợởỡ]/g, "o")
        .replace(/[ùúụủũưừứựửữ]/g, "u")
        .replace(/[ỳýỵỷỹ]/g, "y")
        .replace(/[đ]/g, "d")
        .replace(/\s+/g, '-')
        .replace(/[^\w\-]+/g, '')
        .replace(/\-\-+/g, '-')
        .replace(/^-+/, '')
        .replace(/-+$/, '');
    }
  }

  // Xác thực URL
  validateURL(url: string) {
    var pattern = new RegExp('^(https?:\\/\\/)?' + // protocol
      '((([a-z\\d]([a-z\\d-]*[a-z\\d])*)\\.)+[a-z]{2,}|' + // domain name
      '((\\d{1,3}\\.){3}\\d{1,3}))' + // OR ip (v4) address
      '(\\:\\d+)?(\\/[-a-z\\d%_.~+]*)*' + // port and path
      '(\\?[;&a-z\\d%_.~+=-]*)?' + // query string
      '(\\#[-a-z\\d_]*)?$', 'i'); // fragment locator
    return !!pattern.test(url);
  }

  // Modal bootstrap
  openModal(content) {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', centered: true }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${this.getDismissReason(reason)}`;
    });
  }
  private getDismissReason(reason: any): string {
    if (reason === ModalDismissReasons.ESC) {
      return 'by pressing ESC';
    } else if (reason === ModalDismissReasons.BACKDROP_CLICK) {
      return 'by clicking on a backdrop';
    } else {
      return `with: ${reason}`;
    }
  }

}
