import {Brand} from '../../entites/brand';
import {AfterViewInit, Component, OnDestroy, OnInit, ViewChild} from '@angular/core';
import {DataTableDirective} from 'angular-datatables';
import {environment} from 'environments/environment';
import {Subject} from 'rxjs';
import {ModalDismissReasons, NgbModal} from '@ng-bootstrap/ng-bootstrap';
import {AuthService} from "@services/auth.service";

@Component({
  selector: 'app-brand',
  templateUrl: './brand.component.html',
  styleUrls: ['./brand.component.scss']
})
export class BrandComponent implements OnInit, OnDestroy, AfterViewInit {
  brands: Brand[];
  brand: Brand;
  closeResult: string;
  key!: string;

  url = environment.globalUrl + "/brands";

  dtOptions: DataTables.Settings = {};
  dtTrigger: Subject<any> = new Subject<any>();

  @ViewChild('brandModal') brandModal: any;
  @ViewChild(DataTableDirective) dtElement: DataTableDirective;

  constructor(private modalService: NgbModal, public authService: AuthService) {
    this.brand = <Brand>{};
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

  // Modal bootstrap
  openModal(content) {
    this.modalService.open(content, { ariaLabelledBy: 'modal-basic-title', centered: true }).result.then((result) => {
      this.closeResult = `Closed with: ${result}`;
    }, (reason) => {
      this.closeResult = `Dismissed ${BrandComponent.getDismissReason(reason)}`;
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
