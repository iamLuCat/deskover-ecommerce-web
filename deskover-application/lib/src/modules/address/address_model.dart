import 'package:deskover_develop/src/apis/user_addrees/response/user_address.dart';
import 'package:deskover_develop/src/modules/cart/cart_model.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/usecases/cart_usercase/cart_usercase.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';

@injectable
class NotAddressModel extends ViewModel{
  final CartUserCase _cartUserCase;
  final CartModel _cartModel;
  RxList<UserAddress> dataAddress = RxList.empty();

  NotAddressModel(this._cartUserCase, this._cartModel);

  Future<void> loadAddress() async {
    await _cartUserCase.doGetAddress().then((value) async{
      dataAddress.value = value ?? [];
    });
  }

  Future<void> loadAddressCart() async {
    await _cartUserCase.doGetAddress().then((value) async{
      _cartModel.dataAddress.value = value ?? [];
      _cartModel.dataAddress.value.forEach((item) {
        if(item.choose == true){
          _cartModel.address.value = item;
        }
      });
    });
  }

  void removeAddressAndPayment() async{
    _cartModel.shipping.value = null;
    _cartModel.payment.value = null;

  }

  Future<void> btnChooseAddress(int id)async {
    await _cartUserCase.changeChoose(id).then((value) async{
      await loadAddress();
    });
  }


  Future<void> btnActiveAddress(int id)async {
    await _cartUserCase.changeActive(id).then((value) async{
      Fluttertoast.showToast(msg: value.message.toString(), backgroundColor: UIColors.black70);
      await loadAddress();
    });
  }

}