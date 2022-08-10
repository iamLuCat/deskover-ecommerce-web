import 'package:deskover_app/utils/widgets/view_model.dart';
import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_rx/src/rx_types/rx_types.dart';
import 'package:injectable/injectable.dart';

import '../../../../entity/order/order_responses.dart';
import '../../../../usercases/order_usercase.dart';
import '../../../../utils/AppUtils.dart';

@injectable
class DeliveryModel extends ViewModel{
  final OrderUsercase _orderUsercase;
  final RxList<OrderReponses>  dataOrderDelivery = RxList.empty();
  final RxList<OrderReponses>  dataOrderDelivering = RxList.empty();
  final RxString value_status = 'GH-TB'.obs;
  final Rxn<OrderReponses> orderReponese = Rxn();
  final TextEditingController note = TextEditingController();
  @factoryMethod
  DeliveryModel(this._orderUsercase);
  @override
  void initState() async  {
    super.initState();
  }
  Future<void> refresh() => Future.wait([
    getAllOrderDelivery(),
    getAllOrderDelivering()
  ]);

  Future<void> getAllOrderDelivery() async{
    dataOrderDelivery.value == null;
    await _orderUsercase.doGetAllOrderDelivery('LH-TC').then((value) async{
      dataOrderDelivery.value = value.data ?? [OrderReponses()];
    });
  }

  Future<void> getAllOrderDelivering() async{
    dataOrderDelivering.value == null;
    await _orderUsercase.doGetAllOrderDelivery('DG').then((value) async{
      dataOrderDelivering.value = value.data ?? [OrderReponses()];
    });
  }
  Future<void> orderDelivery(String input,String status) async{
    orderReponese.value = null;
    await loading(() async{
      await _orderUsercase.findByOrderCode(input, status).then((value) async{
        orderReponese.value = value;
      });
    }).then((value) async{
    });
  }
  Future<void> PickupOrder(String orderCode,String status,String note, String title) async{
    loading(() async{
      await _orderUsercase.doPostPickupOrder(orderCode, status,note);
    }).then((value) async {
      AppUtils().showPopup(
          title: 'Thành công',
          subtitle: 'Cập nhập thành công',
          isSuccess: true
      );
    });
  }
}