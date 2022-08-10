//import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:deskover_develop/src/apis/cart/response/cart_response.dart';
import 'package:deskover_develop/src/apis/notify/notify_response.dart';
import 'package:deskover_develop/src/apis/notify/service/notify_api.dart';
import 'package:deskover_develop/src/modules/signin_signup/app/signin/app/signin_screen.dart';
import 'package:deskover_develop/src/usecases/cart_usercase/cart_usercase.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';

import 'cart/cart_model.dart';
import 'notification/datasource/notify_datasource.dart';

@lazySingleton
class MainPageModel extends ViewModel {
  final NotifyApi _notifyApi;
  final NotifyDatasoure _notifyDatasoure;
  final CartUserCase _cartUserCase;
  RxInt index = 0.obs;
  RxInt itemCart = 0.obs;
  RxInt notify = 0.obs;
  RxList<Cart> dataCartResponse = RxList.empty();
  RxList<NotifyResponse> listNotify = RxList.empty();
  RxBool notification = false.obs;

  MainPageModel(this._cartUserCase, this._notifyApi, this._notifyDatasoure);


  @override
  void initState() {
    super.initState();
  }

  void openLogin() {
    Get.to(const SigninPage());
  }

  void openFlashSale() {
    //Get.to(const FlashSalePage());
  }

  Future<void> loadCart() async {
    await _cartUserCase.doGetAllCartOrder().then((value) async{
      dataCartResponse.value =  value ?? [];
      itemCart.value = dataCartResponse.value.length;

    });
  }

  Future<void> loadNotify() async{
    notification.value = false;
   await _notifyApi.doGetNotifyByUser().then((value)  async{
      listNotify.value = value ?? [];
      for (var element in listNotify) {
        if(element.isWatched == false){
          notification.value = true;
          break;
        };
      }


    });
  }
  void changeNotify(int notifyId) async{
    loading(() async{
      await  _notifyDatasoure.changeNotify(notifyId).then((value) async{
        loadNotify();
      });
    });
  }

}
