import 'package:deskover_develop/src/apis/product/response/product_response.dart';
import 'package:deskover_develop/src/usecases/category_usercase/product_usercase.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';

@injectable
class ProductSellingModel extends ViewModel{
  final ProductUserCase _productUserCase;
  RxList<Product> dataProduct = RxList.empty();

  RxInt size = 8.obs;
  RxInt categoryId = 0.obs;

  ProductSellingModel(this._productUserCase);


  Future<void> doGetProductByCate() async{
      loading(() async {
        await _productUserCase.doGetProductByCategoryId(categoryId.value, 0, size.value, 'DESC')
            .then((value) => dataProduct.value = value.content ?? []);
      });
  }

}