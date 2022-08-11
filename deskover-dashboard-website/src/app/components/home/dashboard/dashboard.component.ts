import {Component, QueryList, ViewChild, ViewChildren} from '@angular/core';
import {DashboardService} from "@services/dashboard.service";
import {OrderContants} from "@/constants/order-contants";
import {GeneralReport, OrderReport, ProductReport} from "@/entites/statistical";
import {ChartConfiguration} from "chart.js";
import {BaseChartDirective} from "ng2-charts";

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

  public barChartLegend;
  public barChartPlugins;
  public barChartOptions: ChartConfiguration<'bar'>['options'];
  public horizontalBarChartOptions: ChartConfiguration<'bar'>['options'];
  public productSoldByCategoryChartData: ChartConfiguration<'bar'>['data'];
  public topProductSoldChartData: ChartConfiguration<'bar'>['data'];
  public salesByOrderChartData: ChartConfiguration<'bar'>['data'];

  @ViewChildren(BaseChartDirective) charts: QueryList<BaseChartDirective>;

  constructor(private dashboardService: DashboardService) {
    this.years = Array.from(Array((new Date().getFullYear() + 1) - 2020).keys()).map(i => i + 2020);
    this.year = new Date().getFullYear();
    this.month = new Date().getMonth() + 1;

    this.barChartLegend = true;
    this.barChartPlugins = [];
    this.barChartOptions = {
      responsive: true,
    }
    this.horizontalBarChartOptions = {
      responsive: true,
      indexAxis: 'y',
    }
    this.productSoldByCategoryChartData = {
      labels: [],
      datasets: [{data: [], label: 'Số lượng sản phẩm'},]
    }
    this.topProductSoldChartData = {
      labels: [],
      datasets: [{data: [], label: 'Số lượng sản phẩm'},]
    }
    this.salesByOrderChartData = {
      labels: [],
      datasets: [{data: [], label: 'Doanh thu'},]
    }

    this.getNumberOfOrders();
    this.getTopProductSold();
    this.getTotalQuantityProduct();
    this.getSalesByOrder();
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

  changeYear() {
    if (this.year === new Date().getFullYear()) {
      this.month = new Date().getMonth() + 1;
    } else {
      this.month = 12;
    }
    console.log(this.month);
    this.getSalesByOrder();
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

  updateChart() {
    this.charts.forEach((child) => {
      child.chart?.update()
    });
  }
}
