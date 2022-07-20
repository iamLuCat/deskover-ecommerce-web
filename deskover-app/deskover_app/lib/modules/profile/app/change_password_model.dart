import 'package:deskover_app/utils/widgets/view_model.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:injectable/injectable.dart';

import '../../../usercases/sign_usecase.dart';


@injectable
class ChangepasswordModel extends ViewModel{
  final SigninUsecase signinUsecase;
  TextEditingController inputPassword = TextEditingController();
  TextEditingController inputPasswordnew = TextEditingController();
  TextEditingController inputPasswordconfirm = TextEditingController();
  GlobalKey<FormState> formKey = GlobalKey<FormState>();

  @factoryMethod
  ChangepasswordModel(this.signinUsecase);

  changePassword() {
    if (!(formKey.currentState?.validate() ?? false)) {
      loading(() async => throw 'Vui lòng kiểm tra lại dữ liệu');
      return;
    }
    // if (inputPassword.text!=sharedPreferences.getString("password")) {
    //   loading(() async => throw 'Mật khẩu không đúng');
    //   return;
    // }
    if (inputPassword.text == inputPasswordconfirm.text) {
      loading(() async => throw 'Mật khẩu trùng với mật khẩu cũ');
      return;
    }
    loading(() async{
      signinUsecase.changePassword({
        "oldPassword" : inputPassword.text,
        "newPassword" : inputPasswordnew.text,
        "confirmPassword": inputPasswordconfirm.text
      });
    });
  }
}