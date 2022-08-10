import 'package:deskover_app/modules/dashboard/reponse/order7ago.dart';
import 'package:deskover_app/modules/dashboard/service/dashboard_api.dart';
import 'package:injectable/injectable.dart';

abstract class DashBoardDataSource {
  Future<String> getPricePerMonth();
  Future<String> getCountOrderPerMonth();
  Future<DataTotalPrice7Ago> doGetTotalPrice7DaysAgo();
}

@LazySingleton(as:DashBoardDataSource)
class DashBoardDataSourceImpl extends DashBoardDataSource {

  final DashboardAPI _dashboardAPI;
  DashBoardDataSourceImpl(this._dashboardAPI);

  @override
  Future<String> getPricePerMonth()
  => _dashboardAPI.getTotalPerMonth();

  @override
  Future<String> getCountOrderPerMonth()
  => _dashboardAPI.getCountOrderPerMonth();

  @override
  Future<DataTotalPrice7Ago> doGetTotalPrice7DaysAgo()
  => _dashboardAPI.doGetTotalPrice7DaysAgo();
}
