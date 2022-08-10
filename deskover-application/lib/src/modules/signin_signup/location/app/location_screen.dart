import 'package:deskover_develop/src/modules/signin_signup/location/app/success_screen.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import '../../../../config/assets/image_asset.dart';
import '../../../../themes/space_values.dart';
import '../../../../themes/ui_colors.dart';

class LocationScreen extends StatefulWidget {
  const LocationScreen({Key? key}) : super(key: key);

  @override
  State<LocationScreen> createState() => _LocationScreenState();
}

class _LocationScreenState extends State<LocationScreen> {
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
                    margin: EdgeInsets.only(top: 80,left: 50,right: 50,bottom: 50),
                    child: Center(
                      child:  Image.asset( ImageAssets.imgLogobanner, height: 120),
                    ),
                  ),
                  SizedBox(
                    height: SpaceValues.space32,
                  ),
                     TextFormField(
                   decoration: InputDecoration(
                     border:OutlineInputBorder(),
                     hintText: 'Thành Phố Hồ Chí Minh',
                     prefixIcon: Padding(
                       padding: const EdgeInsets.only(top: 6,right: 8,left: 6,bottom: 6),
                       child: SvgPicture.asset(SvgImageAssets.phone,height: 15,),
                     ),
                     suffixIcon: Padding(
                       padding: const EdgeInsets.only(top: 6,right: 8,left: 6,bottom: 6),
                       child: Icon(Icons.arrow_drop_down),
                     )
                   ),
                     ),
                  SizedBox(
                    height: SpaceValues.space12,
                  ),
                  TextFormField(
                    decoration: InputDecoration(
                        border:OutlineInputBorder(),
                        hintText: 'Quận 1',
                        prefixIcon: Padding(
                          padding: const EdgeInsets.only(top: 6,right: 8,left: 6,bottom: 6),
                          child: SvgPicture.asset(SvgImageAssets.phone,height: 15,),
                        ),
                        suffixIcon: Padding(
                          padding: const EdgeInsets.only(top: 6,right: 8,left: 6,bottom: 6),
                          child: Icon(Icons.arrow_drop_down),
                        )
                    ),
                  ),
                  SizedBox(height: 200,),

                  SizedBox(
                    width: double.infinity,
                    child: ElevatedButton(
                        onPressed: (){
                          Get.to(SucessScreen());
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

                  ),

                ]),
          ),
        ),
      ),
    );
  }
}
