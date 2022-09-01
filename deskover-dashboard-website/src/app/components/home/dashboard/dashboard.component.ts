import {Component, QueryList, ViewChild, ViewChildren} from '@angular/core';
import {DashboardService} from "@services/dashboard.service";
import {OrderContants} from "@/constants/order-contants";
import {GeneralReport, OrderReport, ProductReport} from "@/entites/statistical";
import {ChartConfiguration} from "chart.js";
import {BaseChartDirective} from "ng2-charts";
import ChartDataLabels from 'chartjs-plugin-datalabels';
import {PermissionContants} from "@/constants/permission-contants";
import {AuthService} from "@services/auth.service";

@Component({
  selector: 'app-dashboard',
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.scss']
})
export class DashboardComponent {
  totalStatistic: GeneralReport;
  orderPendingConfirm = OrderContants.PENDING_CONFIRM
  orderDelivered = OrderContants.DELIVERED

  orderReport: OrderReport[];
  productReport: ProductReport[];

  years: number[];
  year: number;
  month: number;

  public barChartPlugins = [ChartDataLabels];
  public barChartOptions: ChartConfiguration<'bar'>['options'];
  public horizontalBarChartOptions: ChartConfiguration<'bar'>['options'];
  public productSoldByCategoryChartData: ChartConfiguration<'bar'>['data'];
  public topProductSoldChartData: ChartConfiguration<'bar'>['data'];
  public salesByOrderChartData: ChartConfiguration<'bar'>['data'];
  public totalAccountsByRoleChartData: ChartConfiguration<'bar'>['data'];

  @ViewChildren(BaseChartDirective) charts: QueryList<BaseChartDirective>;

  constructor(private dashboardService: DashboardService, private authService: AuthService) {
    this.years = Array.from(Array((new Date().getFullYear() + 1) - 2020).keys()).map(i => i + 2020);
    this.year = new Date().getFullYear();
    this.month = new Date().getMonth() + 1;

    this.barChartOptions = {
      responsive: true,
      plugins: {
        legend: {
          display: true,
        },
        datalabels: {
          anchor: 'end',
          align: 'end'
        }
      }
    }
    this.horizontalBarChartOptions = {
      responsive: true,
      indexAxis: 'y',
      plugins: {
        legend: {
          display: true,
        },
        datalabels: {
          anchor: 'end',
          align: 'end'
        }
      }
    }
    this.productSoldByCategoryChartData = {
      labels: [],
      datasets: [{data: [], label: 'Số lượng'},]
    }
    this.topProductSoldChartData = {
      labels: [],
      datasets: [{data: [], label: 'Số lượng'},]
    }
    this.salesByOrderChartData = {
      labels: [],
      datasets: [{data: [], label: 'Doanh thu'},]
    }
    this.totalAccountsByRoleChartData = {
      labels: [],
      datasets: [{data: [], label: 'Số lượng'},]
    }

    this.getNumberOfOrders();
    this.getTopProductSold();
    this.getTotalQuantityProduct();
    this.getSalesByOrder();
    this.getTotalAccountsByRole();
  }

  getNumberOfOrders() {
    this.dashboardService.getNumberOrOrders().subscribe(
      data => {
        this.totalStatistic = data;
      }
    );
  }

  getTotalQuantityProduct() {
    this.dashboardService.getTotalQuantityProduct().subscribe(
      data => {
        this.orderReport = data;

        this.productSoldByCategoryChartData.datasets[0].data = this.orderReport.map(item => item.quantity);
        this.productSoldByCategoryChartData.labels = this.orderReport.map(item => item.subcategory?.category.name + '/' + item.subcategory?.name);

        this.updateChart();
      }
    );
  }

  getTopProductSold() {
    this.dashboardService.topProductSold(5).subscribe(
      data => {
        this.productReport = data;

        this.topProductSoldChartData.datasets[0].data = this.productReport.map(item => item.quantity);
        this.topProductSoldChartData.labels = this.productReport.map(item => item.product?.name);

        this.updateChart();
      }
    );
  }

  getSalesByOrder() {
    this.dashboardService.getSalesByOrder(this.month, this.year).subscribe(
      data => {
        this.salesByOrderChartData.datasets[0].data = data[1].map(item => item);
        this.salesByOrderChartData.labels = data[0].map(item => item);

        this.updateChart();
      }
    );
  }

  getTotalAccountsByRole() {
    this.dashboardService.totalAccountsByRole().subscribe(
      data => {
        data.forEach(item => {
          this.totalAccountsByRoleChartData.datasets[0].data.push(item[1]);
          this.totalAccountsByRoleChartData.labels.push(item[0]);
        })

        this.updateChart();
      }
    );
  }

  changeYear() {
    if (this.year === new Date().getFullYear()) {
      this.month = new Date().getMonth() + 1;
    } else {
      this.month = 12;
    }
    console.log(this.month);
    this.getSalesByOrder();
  }

  updateChart() {
    this.charts.forEach((child) => {
      child.chart?.update()
    });
  }

  hasRoleAdmin() {
    return this.authService.hasPermissions([
      PermissionContants.ADMIN,
    ]);
  }
}
