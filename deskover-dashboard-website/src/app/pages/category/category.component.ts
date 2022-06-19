import {Category} from '@/entites/category';
import {Component, OnInit, ViewChild} from '@angular/core';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {UrlUtils} from "@/utils/url-utils";
import Swal from 'sweetalert2';
import {CategoryService} from '@services/category.service';

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

  @ViewChild('categoryModal') categoryModal: any;

  constructor(private modalService: NgbModal, private categoryService: CategoryService) {
    this.category = <Category>{};
    this.getCategories(0, 6, true);
  }

  ngOnInit() {
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
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', centered: true }).result.then((result) => {
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
