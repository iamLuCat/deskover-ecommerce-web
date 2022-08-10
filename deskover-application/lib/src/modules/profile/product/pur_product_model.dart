import 'package:deskover_develop/src/apis/product/response/product_response.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:get/get_rx/src/rx_types/rx_types.dart';
import 'package:injectable/injectable.dart';

@injectable
class PurchasedProductModel extends ViewModel{

  RxList<Product>? listProduct = RxList.empty();

}