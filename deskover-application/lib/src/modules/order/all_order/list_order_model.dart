import 'package:deskover_develop/src/apis/order/order_datasource.dart';
import 'package:deskover_develop/src/apis/order/request/order/order_response.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';

@injectable
class ListOrderModel extends ViewModel{
  final OrderDataSource _orderDataSource;
  RxList<OrderReponse> orders = RxList.empty();
  RxString statusCode = ''.obs;

  ListOrderModel(this._orderDataSource);

  Future<void> loadOrderByStatus() async {
    loading(() async{
     await _orderDataSource.getAllOrderByStatusCode(statusCode.value)
         .then((value) async{
            orders.value = value ?? [];
      });
    });

  }

}