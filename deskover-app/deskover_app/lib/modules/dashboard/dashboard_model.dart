import 'package:deskover_app/modules/dashboard/reponse/order7ago.dart';
import 'package:deskover_app/modules/main_page/home_page.dart';
import 'package:deskover_app/usercases/dashboard_usercase.dart';
import 'package:deskover_app/utils/widgets/view_model.dart';
import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';

@injectable
class DashBoardModel extends ViewModel{

  final DashBoardUserCase _boardUserCase;

  final RxList<TotalPrice7DaysAgo> totalPrice7DaysAgo = RxList.empty();

  final RxString totalPricePerMonth = ''.obs;
  final RxString countOrderPerMonth = ''.obs;
  final RxString month = ''.obs;
  @factoryMethod
  DashBoardModel(this._boardUserCase);

  @override
  void initState() async  {
    super.initState();
    print('111111111111111111');
    print(totalPrice7DaysAgo.value);
    refresh();
  }

  Future<void> refresh() => Future.wait([
        getPriceByMonth(),
        getCountOrderPerMonth(),
       doGetToTalPrice7DaysAgo()
  ]);

 Future<void> getPriceByMonth() async{
    month.value = '';
    var now = DateTime.now();
    month.value = now.month.toString();

    totalPricePerMonth.value = '';
   _boardUserCase.getPricePerMonth().then((value) async{
        totalPricePerMonth.value = value;
   });
  }
  Future<void> getCountOrderPerMonth() async{
    _boardUserCase.getCountOrderPerMonth().then((value) async {
      countOrderPerMonth.value = value;
    });
  }

  Future<void> doGetToTalPrice7DaysAgo() async{
    loading(()async{
      await _boardUserCase.doGetTotalPrice7DaysAgo().then((value) async{
        totalPrice7DaysAgo.value = value.data ?? [TotalPrice7DaysAgo()];
      });
    }).then((value) async{

    });
  }



}