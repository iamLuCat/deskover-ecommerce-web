import 'package:deskover_develop/src/apis/category/response/category_response.dart';
import 'package:deskover_develop/src/apis/product/response/product_response.dart';
import 'package:deskover_develop/src/modules/cart/cart_model.dart';
import 'package:deskover_develop/src/modules/homepage/homepage_screen.dart';
import 'package:deskover_develop/src/modules/main_page.dart';
import 'package:deskover_develop/src/usecases/cart_usercase/cart_usercase.dart';
import 'package:deskover_develop/src/usecases/category_usercase/category_usercase.dart';
import 'package:deskover_develop/src/usecases/category_usercase/product_usercase.dart';
import 'package:deskover_develop/src/utils/AppUtils.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';

@injectable
class HomePageModel extends ViewModel{
  final CategoryUserCase _categoryUserCase;
  final ProductUserCase _productUserCase;
  final CartUserCase _cartUserCase;

  RxList<CategoryReponse> DataCategory = RxList.empty();
  RxList<Product> dataProductNew = RxList.empty();
  RxList<Product> dataProductFlashSale = RxList.empty();

  RxInt size = 8.obs;

  RxBool flashSale = true.obs;

  HomePageModel(this._categoryUserCase, this._productUserCase, this._cartUserCase);

  @override
  void initState() {
    super.initState();
    resfresh();

  }

  Future<void> resfresh()async {
    await Future.wait([
      loadCategory(),
      loadProductNew(),
      loadProductSale()

    ]);
  }


  Future<void> loadCategory() async{
    loading(() async{
     await _categoryUserCase.doGetAll().then((value) async{
        DataCategory.value = value ?? [];
      }).catchError((error) async{
       DataCategory.value = [];
        AppUtils().showPopup(
            isSuccess: false,
            subtitle: error.replace('Connecting timed out', 'Kết nối quá hạng')
        );
      });
    }).then((value) async{

    }).catchError((error) async{

    });
  }
  Future<void> loadProductNew() async{
     loading(() async{
     await _productUserCase.doGetAllProductNew(0,size.value).then((value) async{
        dataProductNew.value = value.content ?? [Product()];
      });
    });
  }

  Future<void> loadProductSale() async{
    await _productUserCase.doGetAllProductSale(0,size.value).then((value) async{
      dataProductFlashSale.value = value.content ?? [Product()];
      if(value.content == null){
        Get.offAll( const MainPage(indexTab: 0,));
      }
    });

  }

}