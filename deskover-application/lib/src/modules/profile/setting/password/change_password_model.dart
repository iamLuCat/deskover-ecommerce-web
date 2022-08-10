import 'package:deskover_develop/src/apis/message_response.dart';
import 'package:deskover_develop/src/modules/global_modules/app/global_configuration_screen.dart';
import 'package:deskover_develop/src/usecases/user_usercase.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:flutter/cupertino.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:injectable/injectable.dart';

@injectable
class ChangePasswordModel extends ViewModel{
  final UserUserCase _userUserCase;
  TextEditingController inputPassword = TextEditingController();
  TextEditingController inputPasswordNew = TextEditingController();
  TextEditingController inputPasswordConfirm = TextEditingController();

  GlobalKey<FormState> formKey =  GlobalKey<FormState>();

  ChangePasswordModel(this._userUserCase);

  void btnContinueOnPress() {
    if (!(formKey.currentState?.validate() ?? false)) {
      return;
    }
    loading(() async {
      return await _userUserCase.changePassword({
        'oldPassword': inputPassword.text,
        'newPassword': inputPasswordNew.text,
        'confirmPassword': inputPasswordConfirm.text
      });
    }).then((value) {
      if (value is MessageResponse) {
        successConfiguration();
      }
    });
  }

  void successConfiguration() {
    Get.off(GlobalConfigurationScreen(
      textTitle: 'Thay đổi mật khẩu',
      textContent: 'Chúc mừng bạn\nthiết lập mật khẩu thành công',
      textButton: 'Đăng nhập',
      pressButton: () => Get.back(), // offAll(const SigninSignupScreen(initScreen: 1,))
    ));
  }

}