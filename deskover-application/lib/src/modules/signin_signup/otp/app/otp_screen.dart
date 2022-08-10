import 'dart:async';

import 'package:deskover_develop/src/modules/signin_signup/location/app/location_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:pin_code_fields/pin_code_fields.dart';

import '../../../../config/assets/image_asset.dart';
import '../../../../themes/space_values.dart';
import '../../../../themes/ui_colors.dart';

class OTPScreen extends StatefulWidget {
  const OTPScreen({Key? key}) : super(key: key);

  @override
  State<OTPScreen> createState() => _OTPScreenState();
}
class _OTPScreenState extends State<OTPScreen> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: UIColors.white,
      body: Container(
        height: MediaQuery.of(context).size.height,
        color: UIColors.login,
        child: Padding(
          padding: const EdgeInsets.all(20.0),
          child: SingleChildScrollView(
            child: Column(
                children: [
                  Container(
                    margin: EdgeInsets.all(50),
                    child: Center(
                      child:  Image.asset(ImageAssets.otpimg,height: 290,),
                    ),
                  ),
                  SizedBox(
                    height: SpaceValues.space12,
                  ),
                  Text(
                    "Nhập mật khẩu OTP",
                    style: TextStyle(
                      fontWeight: FontWeight.w600,
                      fontSize: 24,
                      color: UIColors.black,
                    ),
                  ),
                  SizedBox(height: 8,),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text("Nhập mật khẩu otp gửi đến"),
                      SizedBox(width: 3),
                      Text('0981865482', style: TextStyle(color:Colors.black,fontWeight: FontWeight.bold),),
                    ],
                  ),
                  SizedBox(height: SpaceValues.space24,),
                  Padding(
                    padding: const EdgeInsets.all(SpaceValues.space16),
                    child: PinCodeTextField(
                      autoFocus: true,
                      enablePinAutofill: true,
                      // controller: _textEditingController,
                      appContext: context,
                      length: 6,
                      // onCompleted: onCompleted,
                      onChanged: (String value) {},
                      animationType: AnimationType.scale,
                      keyboardType: TextInputType.number,
                      pinTheme: PinTheme(
                        shape: PinCodeFieldShape.box,
                        selectedColor: UIColors.black30,
                        inactiveColor: Colors.grey,
                        activeColor: UIColors.black30,
                        borderRadius: BorderRadius.circular(SpaceValues.space8),
                        borderWidth: 1,
                        fieldHeight: 58,
                        fieldWidth: 45,
                      ),
                    ),
                  ),
                  Text('00:120 giây',
                    style: TextStyle(
                        fontWeight: FontWeight.bold,
                        color: Colors.black54,
                        fontSize: 14),),
                  Row(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      Text("Nếu không nhập được mã?"),
                      TextButton(onPressed: (){}, child:
                          Text('Gửi lại', style: TextStyle(color:Colors.blue),)
                      ),
                    ],
                  ),
                  SizedBox(height: SpaceValues.space16,),
                  SizedBox(
                    width: double.infinity,
                    child: ElevatedButton(
                      onPressed: (){
                        Get.to(LocationScreen());
                      },
                      child:const Padding(
                        padding:  EdgeInsets.only(top: SpaceValues.space8,bottom: SpaceValues.space8 ),
                        child: Text("Đăng Nhập"),
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
      ),
    );
  }
}
