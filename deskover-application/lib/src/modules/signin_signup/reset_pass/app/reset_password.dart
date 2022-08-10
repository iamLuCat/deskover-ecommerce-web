import 'package:deskover_develop/src/modules/signin_signup/reset_pass/app/success_pass.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import '../../../../config/assets/image_asset.dart';
import '../../../../themes/space_values.dart';
import '../../../../themes/ui_colors.dart';
import '../../../global_modules/widget/global_input_form_widget.dart';

class ResetPassword extends StatefulWidget {
  const ResetPassword({Key? key}) : super(key: key);

  @override
  State<ResetPassword> createState() => _ResetPasswordState();
}

class _ResetPasswordState extends State<ResetPassword> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: UIColors.white,
      body: Container(
        height: MediaQuery.of(context).size.height,
        color: UIColors.login,
        child: Padding(
          padding: const EdgeInsets.all(20.0),
          child: Column(
              children: [
                Column(
                  crossAxisAlignment: CrossAxisAlignment.start,
                  children: [
                    Container(
                      padding: EdgeInsets.only(top: 50),
                      margin: EdgeInsets.all(20),
                      child: Center(
                        child:  Image.asset( ImageAssets.secure_login, height: 223),
                      ),
                    ),
                    SizedBox(height: 120,),
                    Text('Thiết lập lại mật khẩu?',
                      style: TextStyle(
                        fontWeight: FontWeight.w600,
                        fontSize: 24,
                      ),
                    ),
                    SizedBox(
                      height: SpaceValues.space12,
                    ),
                    GlobalInputFormWidget(
                      textInputType: TextInputType.visiblePassword,
                      hint: 'Mật khẩu',
                      security: true,
                      requireInput: '',
                      prefixIcon: Padding(
                        padding: const EdgeInsets.all(7.0),
                        child: SvgPicture.asset(SvgImageAssets.iconblock,height: 15,),
                      ),
                    ),

                    SizedBox(
                      height: SpaceValues.space12,
                    ),
                    GlobalInputFormWidget(
                      textInputType: TextInputType.visiblePassword,
                      // title: 'Số điện thoại',
                      hint: 'Xác nhận lại mật khẩu',
                      security: true,
                      requireInput: '',
                      prefixIcon: Padding(
                        padding: const EdgeInsets.all(7.0),
                        child: SvgPicture.asset(SvgImageAssets.iconblock,height: 15,),
                      ),
                    ),
                  ],
                ),
                SizedBox(height: SpaceValues.space12,),
                SizedBox(
                  width: double.infinity,
                  child: ElevatedButton(
                      onPressed: (){
                        Get.to(SuccessResetPass());
                      },
                      child:const Padding(
                        padding:  EdgeInsets.only(top: SpaceValues.space8,bottom: SpaceValues.space8 ),
                        child: Text("Xác nhận"),
                      )),
                ),
                TextButton(
                  onPressed: (){
                    Get.back();
                  } ,
                  child:const Padding(
                    padding:  EdgeInsets.only(top: SpaceValues.space8,bottom: SpaceValues.space8 ),
                    child: Text("Trở về",style: TextStyle(fontSize: 13,color: Colors.grey),),
                  ),

                )
              ]),
        ),
      ),
    );
  }
}
