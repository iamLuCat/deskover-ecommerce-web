import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';

import '../../../../config/assets/image_asset.dart';
import '../../../../themes/space_values.dart';
import '../../../../themes/ui_colors.dart';
import '../../app/signin/app/signin_screen.dart';

class SuccessResetPass extends StatefulWidget {
  const SuccessResetPass({Key? key}) : super(key: key);

  @override
  State<SuccessResetPass> createState() => _SuccessResetPassState();
}

class _SuccessResetPassState extends State<SuccessResetPass> {
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
              crossAxisAlignment: CrossAxisAlignment.center,
              mainAxisAlignment: MainAxisAlignment.center,
              children: [
                Container(
                    margin: EdgeInsets.all(15),
                    child: Center(child: Image.asset(ImageAssets.success,height: 337,))),
                Text('Chúc Mừng Bạn!!!',
                  style: TextStyle(
                      fontSize: 24,
                      fontWeight: FontWeight.w500,
                      color: UIColors.black
                  ),),
                SizedBox(
                  height: SpaceValues.space24,
                ),
                Text('Yêu cầu thiết lập lại mật khẩu của bạn',
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w400,
                  ),
                ),
                Text('thành công',
                  style: TextStyle(
                    fontSize: 16,
                    fontWeight: FontWeight.w400,
                  ),
                ),
                SizedBox(height: SpaceValues.space64,),

                SizedBox(
                  width: double.infinity,
                  child: ElevatedButton(
                      onPressed: (){
                        Get.to(SigninPage());
                      },
                      child:const Padding(
                        padding:  EdgeInsets.only(top: SpaceValues.space8,bottom: SpaceValues.space8 ),
                        child: Text("Đăng nhập ngay"),
                      )),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }
}
