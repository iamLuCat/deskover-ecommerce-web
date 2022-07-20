import 'package:deskover_app/modules/receive_orders/service/service/order_service.dart';
import 'package:deskover_app/usercases/dashboard_usercase.dart';
import 'package:deskover_app/usercases/order_usercase.dart';
import 'package:deskover_app/utils/widgets/view_model.dart';
import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:get/get_rx/src/rx_types/rx_types.dart';
import 'package:injectable/injectable.dart';

import '../../entity/order/order_responses.dart';
import '../../utils/AppUtils.dart';

@Injectable()
class OrderModel extends ViewModel{

    final OrderUsercase _orderUsercase;
    final TextEditingController inputOrderCode = TextEditingController();
    final Rx<String?> validBarcode = Rx<String?>(null);
    GlobalKey<FormState> formKey = GlobalKey<FormState>();

    final RxInt index = 0.obs;

    final Rxn<OrderReponses> orderReponese = Rxn();

    @factoryMethod
    OrderModel(this._orderUsercase);

    @override
    void initState() async  {
      super.initState();
    }

    bool validAll() {
      bool result = formKey.currentState?.validate() ?? false;

      if (inputOrderCode.text.isEmpty) {
        validBarcode.value = 'Nhập mã vận đơn hoặc quét QR';
        result = false;
      } else {
        validBarcode.value = null;
      }
      return result;
    }

    Future<void> onSearch(String input,String status) async{
      orderReponese.value = null;
      validBarcode.value = null;
      if (!validAll() || !(formKey.currentState?.validate() ?? false)) {
        await loading(() async {
          throw 'Vui lòng kiểm tra lại thông tin';
        });
        return;
      }
      await loading(() async{
        await _orderUsercase.findByOrderCode(input, status).then((value) async{
          orderReponese.value = value;
        });
      }).then((value) async{
      });
    }

    Future<void> orderDetails(String input,String status) async{
      orderReponese.value = null;
      await loading(() async{
        await _orderUsercase.findByOrderCode(input, status).then((value) async{
          orderReponese.value = value;
        });
      }).then((value) async{
      });
    }

    Future<void> PickupOrder(String orderCode,String status) async{
      loading(() async{
        await _orderUsercase.doPostPickupOrder(orderCode, status,'');
      }).then((value) async {
        orderReponese.value = null;
        await AppUtils().showPopup(
          title: 'Thành công',
          subtitle: 'Cập nhập thành công',
          isSuccess: true
        );
      });
    }


}