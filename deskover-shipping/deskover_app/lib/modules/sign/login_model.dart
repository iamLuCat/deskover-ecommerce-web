import 'dart:core';

import 'package:deskover_app/config/injection_config.dart';
import 'package:deskover_app/entity/sign/response/sign_response.dart';
import 'package:deskover_app/modules/dashboard/dashboard_screen.dart';
import 'package:deskover_app/modules/main_page/home_page.dart';
import 'package:deskover_app/usercases/sign_usecase.dart';
import 'package:deskover_app/utils/widgets/view_model.dart';
import 'package:dio/dio.dart';
import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';
import 'package:shared_preferences/shared_preferences.dart';

@injectable
class LoginModel extends ViewModel{

  final SigninUsecase _signinUsecase;
  final SharedPreferences sharedPreferences;

  TextEditingController inputUsername = TextEditingController();
  TextEditingController inputPassword = TextEditingController();

  GlobalKey<FormState> formKey = GlobalKey<FormState>();

  @factoryMethod
  LoginModel(this._signinUsecase, this.sharedPreferences);

  @override
  void initState()   {
    super.initState();
  }

  void onLogin() async {
    bool checkLogin = false;
    if (!(formKey.currentState?.validate() ?? false)) {
      // not validate or null
      return;
    }
    sharedPreferences.remove('uToken');
    loading(() async {
      SigninResponses value = await _signinUsecase.signin(inputUsername.text, inputPassword.text);
      await sharedPreferences.setString('uToken', value.token ?? '');
      await sharedPreferences.setString('username', value.fullname ?? '');
      await sharedPreferences.setString('avatar', value.avatar ?? '');
      await sharedPreferences.setString('password', inputPassword.text);
      if ((value.token ?? '').isEmpty) {
        throw 'Số điện thoại hoặc mật khẩu không đúng';
      }
      if(value.authority?.role?.roleId == 'ROLE_SHIPPER'){
        getIt<Dio>().options = BaseOptions(headers: {
          'Authorization': (value.token?.isNotEmpty ?? false)
              ? 'Bearer ${value.token}'
              : '',
        });
        sharedPreferences.remove('login-failed');
        checkLogin = true;
      }
      if(checkLogin == false){
        throw 'Vui lòng đăng nhập đúng vai trò';
      }
    }, reCatchString: true).then((value) async {
      Get.offAll(() => const HomePage());
    }).catchError((error) {
        print(error);
    });
  }
}