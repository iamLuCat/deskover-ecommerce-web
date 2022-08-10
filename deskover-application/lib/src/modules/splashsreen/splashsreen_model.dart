import 'dart:async';

import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:dio/dio.dart';
import 'package:injectable/injectable.dart';
// import 'package:package_info_plus/package_info_plus.dart';
import 'package:shared_preferences/shared_preferences.dart';

@lazySingleton
class SplashsreenModel extends ViewModel {

  SplashsreenModel(this._sharedPreferences, this._dio);
  final SharedPreferences _sharedPreferences;

  final Dio _dio;

  loadModel() async {
    // if ((_sharedPreferences.getString('uToken') ?? '').isNotEmpty) {
    //  try{
    //    await _userUsecase.getProfile();
    //  }catch(_){
    //    print(_);
    //  }
    // }
    await delay(5);
    // await loadCaterory();
    // await loadProduct();
    // await removeUToken();
    // await Future.wait([loadCaterory(), loadProduct(), getMembershipRanks()]);
  }

  Future<void> removeUToken() async {
    await _sharedPreferences.remove('uToken');
    _dio.options = BaseOptions(headers: {
      'Authorization': '',
    });
  }

  Future<bool> checkVersion() async{
   await Timer(Duration(seconds: 3), () {
      print("Yeah, this line is printed after 3 seconds");
    });
     return true;
  }

  Future delay(int s) async{
   await Future.delayed(const Duration(milliseconds: 5000), () {
    });
  }

}
