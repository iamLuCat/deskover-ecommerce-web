import 'package:deskover_develop/src/config/assets/image_asset.dart';
import 'package:deskover_develop/src/themes/space_values.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import '../../app/signin/app/signin_screen.dart';

class SucessScreen extends StatelessWidget {
  const SucessScreen({Key? key}) : super(key: key);

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
                Text('Yêu cầu đăng ký tài khoản của bạn',
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
                SizedBox(height: 120,),

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
