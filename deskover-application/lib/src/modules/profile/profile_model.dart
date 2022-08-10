import 'package:deskover_develop/src/apis/user/response/user_response.dart';
import 'package:deskover_develop/src/modules/cart/cart_model.dart';
import 'package:deskover_develop/src/modules/signin_signup/app/signin/app/signin_screen.dart';
import 'package:deskover_develop/src/usecases/cart_usercase/cart_usercase.dart';
import 'package:deskover_develop/src/usecases/user_usercase.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:flutter/foundation.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_rx/src/rx_types/rx_types.dart';
import 'package:injectable/injectable.dart';
import 'package:shared_preferences/shared_preferences.dart';

@injectable
class ProfileModel extends ViewModel{
  final SharedPreferences _sharedPreferences;
  final UserUserCase _userUserCase;
  RxBool isLogin = false.obs;

  ProfileModel(this._sharedPreferences,  this._userUserCase);
  Rxn<User> myProfile = Rxn();
  RxString avatar = ''.obs;

  @override
   initState() {
    loading(() async {
      await Future.wait([
        checkLogin(),
      ]);

    });

  }
  Future<void> checkLogin() async {
    if ((_sharedPreferences.getString("uToken") ?? '').isNotEmpty) {
      if (kDebugMode) {
        print(_sharedPreferences.getString("uToken"));
      }
      await getProfile();
      isLogin.value = true;
    } else {
      isLogin.value = false;
    }
  }
  getProfile() async{
    await _userUserCase.doGetProfiled().then((value){
      myProfile.value = value;
      avatar.value = myProfile.value?.avatar ?? '';
    });
  }
  void logout() async {
    _sharedPreferences.remove("uToken");
    if ((_sharedPreferences.getString("uToken") ?? '').isEmpty) {
      isLogin.value = false;
      Get.offAll(()=>const SigninPage());
    } else {
      isLogin.value = true;
    }

  }


}