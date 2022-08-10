import 'dart:ui';

import 'package:deskover_develop/src/config/assets/image_asset.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/global_modules/widget/global_input_form_widget.dart';
import 'package:deskover_develop/src/modules/signin_signup/app/signin/app/signin_model.dart';
import 'package:deskover_develop/src/themes/space_values.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';

import '../../../reset_pass/app/forget_pass_phonenumber.dart';
import '../../singup/app/signup_screen.dart';
// import 'package:package_info_plus/package_info_plus.dart';

class SigninPage extends StatefulWidget {
  const SigninPage({Key? key}) : super(key: key);

  @override
  State<SigninPage> createState() => _SigninPage();
}

class _SigninPage extends ViewWidget<SigninPage, SigninModel> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: UIColors.white,
      body: Container(
        height: MediaQuery.of(context).size.height,
        color: UIColors.white,
        child: Padding(
          padding: const EdgeInsets.all(20.0),
          child: SingleChildScrollView(
            child: Column(
              children: [
                Container(
                  margin: EdgeInsets.all(50),
                  child: Center(
                    child:  Image.asset( ImageAssets.imgLogobanner, height: 120),
                  ),
                ),
                  SizedBox(
                height: SpaceValues.space32,
                  ),
                  GlobalInputFormWidget(
                    controller: viewModel.inputPhone,
                textInputType:TextInputType.text,
                validator:(valueDy)=> Validator.phone(valueDy),
                requireInput: '',
                hint: 'Số điện thoại',
                prefixIcon: Padding(
                  padding: const EdgeInsets.all(7.0),
                  child: SvgPicture.asset(SvgImageAssets.iconphone,height: 15,),
                ),
                  ),
                  SizedBox(
                height: SpaceValues.space12,
                  ),
                  GlobalInputFormWidget(
                    controller: viewModel.inputPassword,
                textInputType: TextInputType.visiblePassword,
                // title: 'Số điện thoại',
                hint: '*******',
                security: true,
                requireInput: '',
                prefixIcon: Padding(
                  padding: const EdgeInsets.all(7.0),
                  child: SvgPicture.asset(SvgImageAssets.iconblock,height: 15,),
                ),
                  ),
             SizedBox(height: 32,),
             Row(
               children: [
                 Expanded(
                 child: ElevatedButton(
                     onPressed: (){viewModel.onLogin();},
                     child:const Padding(
                       padding:  EdgeInsets.only(top: SpaceValues.space8,bottom: SpaceValues.space8 ),
                       child: Text("Đăng nhập"),
                     )),
               ),
             ]),
             TextButton(
                 onPressed: () {
                   Get.to(InputPhoneNumberScreen());
                 },
                 child: Text('Quên mật khẩu',style: TextStyle(
                   fontSize: 13,
                   color: UIColors.black,
                   fontWeight: FontWeight.w400,
                 ),)),
             SizedBox(height: 10,),
             Row(
               mainAxisAlignment: MainAxisAlignment.center,
               children: [
                 Text("Dành cho khách hàng chưa có tài khoản?", style: TextStyle(fontSize: 11),),

                 TextButton(
                     onPressed: (){
                       Get.to(SignUpScreen());
                     } ,
                     child: Row(
                       children: [
                         Text("Đăng ký ngay" ,style: TextStyle(fontSize: 11,color: UIColors.brandA),),
                       ],
                     ),
                 ),
               ],
             )
            ]),
          ),
        ),
      ),
    );
  }

  @override
  SigninModel createViewModel() => getIt<SigninModel>();
}
