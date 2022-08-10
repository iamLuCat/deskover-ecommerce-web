import 'package:deskover_develop/src/apis/login/login_datasource.dart';
import 'package:deskover_develop/src/apis/message_response.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/main_page.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';
import 'package:shared_preferences/shared_preferences.dart';

@injectable
class SigninModel extends ViewModel {
  final SharedPreferences sharedPreferences;
  final LoginDataSource loginDataSource;


  TextEditingController inputPhone = TextEditingController();
  TextEditingController inputPassword = TextEditingController();
  TextEditingController passtxt = TextEditingController();

  GlobalKey<FormState> formKey = GlobalKey<FormState>();

  RxBool isShowPass = true.obs;
  RxBool checkPass = false.obs;
  RxString titleMessPass = ''.obs;
  RxnString userSeletected = RxnString();

  SigninModel(this.sharedPreferences, this.loginDataSource);

  void onShowPass() {
    isShowPass.value = !isShowPass.value;
  }

  void errorPass(String title, bool check) {
    titleMessPass.value = title;
    checkPass.value = check;
  }

  void onForgetPassword() {
    //Get.to(() => const ForgetPassWordScreen());
  }

  void openMainPage() {
    Get.offAll(() => const MainPage());
  }



  void onLogin() {
    // if (!(formKey.currentState?.validate() ?? false)) {
    //   // not validate or null
    //   return;
    // }
    loading(() async {
      MessageResponse response = await loginDataSource.doLogin(inputPhone.text, inputPassword.text);
      sharedPreferences.setString('uToken', response.message ??'');
      getIt<Dio>().options = BaseOptions(headers: {
        'Authorization': (response.message?.isNotEmpty ?? false)
        ? 'Bearer ${response.message}'
        : '',
      });
      // await _cartUsecase.getProducts();
    },reCatchString: true)
    .then((value) async {
      Get.offAll(const MainPage(indexTab: 0,));
    });
  }

  void onRegistration() {
   // Get.to(BusinessRegistration());
  }
  
}
