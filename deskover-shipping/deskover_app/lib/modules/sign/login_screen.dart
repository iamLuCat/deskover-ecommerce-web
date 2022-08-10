import 'package:deskover_app/component/widget/global_input_form_widget.dart';
import 'package:deskover_app/config/injection_config.dart';
import 'package:deskover_app/constants/icon_assets.dart';
import 'package:deskover_app/modules/sign/login_model.dart';
import 'package:deskover_app/themes/ui_colors.dart';
import 'package:deskover_app/utils/widgets/view_widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';

class LoginScreen extends StatefulWidget {
  const LoginScreen({Key? key}) : super(key: key);

  @override
  _LoginScreen createState() => _LoginScreen();
}

class _LoginScreen extends ViewWidget<LoginScreen,LoginModel> {

  @override
  void initState() {
    super.initState();
  }

  @override
  Widget build(BuildContext context) {
    var heightOfScreen = MediaQuery.of(context).size.height;
    var widthOfScreen = MediaQuery.of(context).size.width;
    return Scaffold(
      // resizeToAvoidBottomInset: false,
      body: GestureDetector(
        child: Stack(
          children:  <Widget>[
            Container(
              width: double.infinity,
              height: heightOfScreen*0.7,
              decoration: const BoxDecoration(
                image: DecorationImage(
                  image: AssetImage(IconAssets.brackgroundLogin),
                  fit: BoxFit.fill,
                ),
              ),
              child: SingleChildScrollView(
                child: Column(
                  crossAxisAlignment: CrossAxisAlignment.center,
                  children: [
                    SizedBox(height: heightOfScreen*0.05,),
                    SizedBox(
                      height: heightOfScreen*0.35,
                      width: widthOfScreen,
                      child: Center(child: Image.asset('resources/icons/delivery.png',height: 400)),
                    ),
                    Image.asset('resources/icons/logo.png',width: 330,),
                  ],
                ),
              ),
            ),
            ListView(
              padding: EdgeInsets.all(0),
              children: <Widget>[
                SizedBox(
                  height: heightOfScreen * 0.6,
                ),
                Container(
                  height: heightOfScreen * 0.4,
                  decoration: const BoxDecoration(
                      color: UIColors.white,// Set border width
                      borderRadius: BorderRadius.only( topLeft: Radius.circular(40),topRight: Radius.circular(40)
                      ), // Set rounded corner radius
                      boxShadow: [BoxShadow(blurRadius: 10,color: UIColors.black10,offset: Offset(1,2))] // Make rounded corner of border
                  ),
                  child: Padding(
                    padding: const EdgeInsets.all(42),
                    child: _form(),
                  ),
                ),

              ],
            ),

          ],
        )
      ),
    );
  }
  Widget _form(){
    return SingleChildScrollView(
      child: Form(
        key: viewModel.formKey,
        child: Column(
          children: [
            GlobalInputFormWidget(
              controller: viewModel.inputUsername,
              validator: Validator.username,
              textInputType: TextInputType.text,
              title: 'Vui lòng nhập tên đăng nhập',
              hint: 'Vui lòng nhập số điện thoại',
            ),
            const SizedBox(
              height: 20,
            ),
            GlobalInputFormWidget(
              controller: viewModel.inputPassword,
              validator: Validator.password,
              textInputType: TextInputType.visiblePassword,
              title: 'Mật khẩu',
              hint: 'Vui lòng nhập mật khẩu',
              security: true,
            ),
            const SizedBox(height: 16.0),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: 20),
              child: SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  style: ElevatedButton.styleFrom(
                    shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(54),
                    ),
                    primary: UIColors.loginbuuton, // background
                    onPrimary: Colors.white,
                    // foreground
                  ),
                  onPressed: viewModel.onLogin,
                  child: const Padding(
                    padding: EdgeInsets.all(5),
                    child: Text('Đăng nhập',style: TextStyle(
                        color: UIColors.white,
                        fontSize: 16,
                        fontWeight: FontWeight.w600
                    ),),
                  ),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
  LoginModel createViewModel() => getIt<LoginModel>();
}
