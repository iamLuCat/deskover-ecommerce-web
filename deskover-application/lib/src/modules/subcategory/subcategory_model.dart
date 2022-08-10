import 'package:deskover_develop/src/apis/category/response/category_response.dart';
import 'package:deskover_develop/src/apis/product/response/product_response.dart';
import 'package:deskover_develop/src/usecases/category_usercase/product_usercase.dart';
import 'package:deskover_develop/src/usecases/subcategory_usercase/subcategory_usercase.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:get/get_rx/src/rx_types/rx_types.dart';
import 'package:injectable/injectable.dart';

@injectable
class SubCategoryModel extends ViewModel{
  final ProductUserCase _productUserCase;
  final SubCateUserCase _subCateUserCase;
  RxList<SubCategory> dataSubCategory = RxList.empty();
  RxList<Product> dataProductByCategory = RxList.empty();
  RxInt categoryId = 0.obs;
  RxInt subcategoryId = 0.obs;
  RxString keySort = 'DESC'.obs;
  RxInt size = 8.obs;

  SubCategoryModel(this._productUserCase, this._subCateUserCase);

  @override
  void initState() {
    super.initState();
  }

  Future<void> loadByCategory() async{
    subcategoryId.value = 0;
    await _productUserCase.doGetProductByCategoryId(categoryId.value, 0, size.value,keySort.value).then((value) async{
      dataProductByCategory.value = value.content ?? [Product()];
    });
  }

  Future<void> loadProductBySubId() async{
    await _productUserCase.doGetProductBySubId(subcategoryId.value, 0, size.value,keySort.value).then((value) async{
      dataProductByCategory.value = value.content ?? [Product()];
    });
  }

  Future<void> loadSubCategory() async{
    await _subCateUserCase.doGetSubByCategoryId(categoryId.value).then((value) async{
      dataSubCategory.value = value ?? [];
    });
  }


}