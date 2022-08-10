import 'package:deskover_develop/src/apis/category/response/category_response.dart';
import 'package:deskover_develop/src/apis/product/response/product_response.dart';
import 'package:deskover_develop/src/usecases/category_usercase/category_usercase.dart';
import 'package:deskover_develop/src/usecases/category_usercase/product_usercase.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:flutter/foundation.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';


@lazySingleton
class SearchModel extends ViewModel {
  final ProductUserCase _productUserCase;
  final CategoryUserCase _categoryUserCase;
  RxList<Product> listProduct = RxList.empty();
  RxList<CategoryReponse> listCaterory = RxList.empty();
  RxList<bool> listCateroryFilter = RxList.empty();
  RxInt index = 0.obs;

  @factoryMethod
  SearchModel(this._productUserCase, this._categoryUserCase);

  @override
  initState() {
    loadCategory();
  }

  void getProductSearch(String search) async{
   await loading(() async{
      await _productUserCase.getSearch(search, 0, 100).then((value) async{
        listProduct.value = value.content ?? [];
        if(listProduct.isNotEmpty){
            index.value = 1;
        }
      });
    });
  }
  void loadCategory() async{
    listCaterory.value = (await _categoryUserCase.doGetAll()
        .then((value) => value)
        .catchError((onError){}))!;
    listCateroryFilter = RxList.filled(listCaterory.length, false);

  }




}
