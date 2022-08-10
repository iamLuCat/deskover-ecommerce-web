import 'package:deskover_develop/src/modules/main_page_model.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/usecases/cart_usercase/cart_usercase.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';

@injectable
class ProductCartModel extends ViewModel{

  final CartUserCase _cartUserCase;
  final MainPageModel _mainPageModel;

  ProductCartModel( this._cartUserCase, this._mainPageModel);

  @override
  void initState() {
    super.initState();
  }

  Future<void> btnAddToCart(int productId) async{
     loading(() async{
      await _cartUserCase.addToCart(productId, 1).then((value) async{
        Fluttertoast.showToast(msg: value.message.toString(), backgroundColor: UIColors.black70);
        await _mainPageModel.loadCart();
        Get.back();
      });
    }).then((value) async{

     });
  }

}