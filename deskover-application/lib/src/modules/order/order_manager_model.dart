import 'package:deskover_develop/src/apis/notify/notify_response.dart';
import 'package:deskover_develop/src/apis/notify/service/notify_api.dart';
import 'package:deskover_develop/src/apis/order/order_datasource.dart';
import 'package:deskover_develop/src/apis/order/request/order/order_response.dart';
import 'package:deskover_develop/src/modules/cart/cart_model.dart';
import 'package:deskover_develop/src/modules/cart/creat_cart.dart';
import 'package:deskover_develop/src/modules/main_page.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';

import 'all_order/list_order.dart';

@injectable
class OrderManagerModel extends ViewModel{
  final OrderDataSource _orderDataSource;
  final CartModel _cartModel;
  final NotifyApi _notifyApi;
  RxString orderCode = ''.obs;
  RxInt indexAction = 0.obs;
  RxBool cancel = false.obs;

  Rxn<OrderReponse> orderReponese  = Rxn();
  RxList<NotifyResponse> notifyResponse  = RxList.empty();

  OrderManagerModel(this._orderDataSource, this._notifyApi, this._cartModel);

  @override
  void initState() {
    super.initState();
  }
  Future<void> addProductToCart(int productId)async {
    await _cartModel.btnAddToCart(productId);
    Get.to(CreateChangePointCart());
  }

  Future<void> loadOrder()async {
    await _orderDataSource.getOrderByUser(orderCode.value).then((value){
      orderReponese.value = value;
      print('>>>>>>>>>>>>>>>>>');
      print(orderReponese.value?.unitPrice);
      if(orderReponese.value?.orderStatus?.code == 'C-XN'){
        indexAction.value = 0;
      }else if(orderReponese.value?.orderStatus?.code == 'C-LH' || orderReponese.value?.orderStatus?.code == 'LH-TC'){
        indexAction.value = 1;
      }else if(orderReponese.value?.orderStatus?.code == 'DG'){
        indexAction.value = 2;
      }else if(orderReponese.value?.orderStatus?.code == 'GH-TC'){
        indexAction.value = 3;
      }else if(orderReponese.value?.orderStatus?.code == 'HUY' || orderReponese.value?.orderStatus?.code == 'C-HUY'){
        if(orderReponese.value?.orderStatus?.code == 'C-HUY'){
          indexAction.value = 4;
        }else if(orderReponese.value?.orderStatus?.code == 'HUY'){
          indexAction.value = 5;
        }
        cancel.value = true;
      }
    });
  }

  Future<void> loadNotifyByOrderCode()async {
    await _orderDataSource.doGetAllNotify(orderCode.value).then((value){
        notifyResponse.value = value ?? [];
    }
    );
  }
  Future<void> cancelOrder(String statusOrder)async {
    await _orderDataSource.canCelOrder(orderCode.value,statusOrder).then((value) async {
      Fluttertoast.showToast(msg: value.message.toString(), backgroundColor: UIColors.black70);
      Get.offAll(const MainPage(indexTab: 2,));
      Get.to(const ListOrder(index: 1,));
    });
  }


}