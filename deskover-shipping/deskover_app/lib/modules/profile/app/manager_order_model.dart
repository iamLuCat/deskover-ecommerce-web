import 'package:deskover_app/entity/order/order_responses.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';

import '../../../usercases/order_usercase.dart';
import '../../../utils/widgets/view_model.dart';

@injectable
class ManagerOrderModel extends ViewModel{
  final OrderUsercase _orderUsercase;

  @factoryMethod
  ManagerOrderModel(this._orderUsercase);

  RxList <OrderReponses> listOrder = RxList.empty();
  @override
  initState() {
    getListOrder();
  }

  getListOrder(){
    loading(() async {
      await _orderUsercase.getListMyOrders().then((value) => {
        listOrder.value= value.data!
      });
    });
  }
}