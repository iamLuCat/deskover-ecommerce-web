import 'package:deskover_develop/src/apis/product/response/product_response.dart';
import 'package:deskover_develop/src/usecases/category_usercase/product_usercase.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';

@injectable
class ProductDetailModel extends ViewModel{
  final ProductUserCase _productUserCase;

  Rxn<Product> productDetail = Rxn<Product>();
  RxInt indexSlider = 0.obs;
  int idProduct = 0;

  RxInt start1 = 0.obs;
  RxInt start2 = 0.obs;
  RxInt start3 = 0.obs;
  RxInt start4 = 0.obs;
  RxInt start5 = 0.obs;


  ProductDetailModel(this._productUserCase);

  Future<void> initStateAsync() async {
    await refresh();
  }

  Future<void> refresh() async {
    await Future.wait([
      getProductById(),
    ]);
  }
  Future<void> getProductById()async {
    start1.value = 0;
    start2.value = 0;
    start3.value = 0;
    start4.value = 0;
    start5.value = 0;
    await loading(() async{
     await _productUserCase.getById(idProduct).then((value) async{
          productDetail.value = value;
          productDetail.value?.ratings?.forEach((element) {
              if(element.point == 1){
                start1.value += 1;
              }else if(element.point == 2){
                start2.value += 1;
              }
              else if(element.point == 3){
                start3.value += 1;
              }
              else if(element.point == 4){
                start4.value += 1;
              }
              else if(element.point == 5){
                start5.value += 1;
              }

          });

      });
    });

  }



}