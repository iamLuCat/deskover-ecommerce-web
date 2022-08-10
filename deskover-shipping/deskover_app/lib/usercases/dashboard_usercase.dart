import 'package:deskover_app/modules/dashboard/dashboard_datasource.dart';
import 'package:injectable/injectable.dart';

import '../modules/dashboard/reponse/order7ago.dart';

@LazySingleton()
class DashBoardUserCase {
  final DashBoardDataSource _dashBoardDataSource;

  @factoryMethod
  DashBoardUserCase(this._dashBoardDataSource);

  Future<String> getPricePerMonth() =>
      _dashBoardDataSource.getPricePerMonth();

  Future<String> getCountOrderPerMonth() =>
      _dashBoardDataSource.getCountOrderPerMonth();

  Future<DataTotalPrice7Ago> doGetTotalPrice7DaysAgo()
  => _dashBoardDataSource.doGetTotalPrice7DaysAgo();
}
