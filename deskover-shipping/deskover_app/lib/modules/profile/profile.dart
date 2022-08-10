import 'package:deskover_app/global/global_image.dart';
import 'package:deskover_app/modules/profile/profile_model.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import 'package:line_icons/line_icon.dart';
import 'package:url_launcher/url_launcher_string.dart';

import '../../config/injection_config.dart';
import '../../themes/space_values.dart';
import '../../themes/ui_colors.dart';
import '../../utils/widgets/view_widget.dart';
import 'app/change_password.dart';
import 'app/manager_order.dart';

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({Key? key}) : super(key: key);

  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends ViewWidget<ProfileScreen, ProfileModel> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: UIColors.white,
        automaticallyImplyLeading: false,
        leadingWidth: 500,
        title: const Text("Tài khoản"),
        shape: const Border(bottom: BorderSide(color: UIColors.border10)),
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.only(top: 6),
          child: Column(
            children: [
              InkWell(
                onTap: () {
                  Get.to( ChangePasswordScreen());
                },
                child: FractionallySizedBox(
                  widthFactor: 1,
                  child: Container(
                    decoration: const BoxDecoration(color: UIColors.white),
                    margin: const EdgeInsets.only(bottom: 0),
                    child: Padding(
                      padding: const EdgeInsets.all(8),
                      child: Column(children: [
                        Row(
                          mainAxisAlignment: MainAxisAlignment.start,
                          children: [
                            SizedBox(
                              width: 75,
                              height: 75,
                              child: ClipRRect(
                                  borderRadius: BorderRadius.circular(100),
                                  child: GlobalImage(viewModel.avatar.value,fit: BoxFit.none,))),
                            SizedBox(width: 10,),
                            Text(
                                viewModel.username.value,
                              style: TextStyle(
                                fontSize: 14,
                              ),
                            ),
                            Expanded(child: const SizedBox()),
                            Container(
                              margin: const EdgeInsets.only(right: 8),
                              child: SvgPicture.asset(
                                  'resources/icons/keyboard_arrow_right.svg'),
                            ),
                          ],
                        ),
                      ]),
                    ),
                  ),
                ),
              ),
              Container(
                margin: const EdgeInsets.only(top: 8),
                padding: const EdgeInsets.only(left: 14),
                decoration: BoxDecoration(color: UIColors.white),
                child: Column(
                  children: [
                    ListTile(
                      dense: true,
                      contentPadding:
                          const EdgeInsets.only(left: 0.0, right: 0.0),
                      title: Row(
                        children: const [
                          Icon(Icons.shopping_cart_outlined,color: UIColors.black70,),
                          SizedBox(
                            width: 10,
                          ),
                          Text(
                            "Thống kê đơn hàng",
                            style: TextStyle(
                              fontWeight: FontWeight.w700,
                              fontSize: 12,
                              color: Colors.black,
                            ),
                          )
                        ],
                      ),
                      tileColor: Colors.transparent,
                      trailing: Container(
                        margin: const EdgeInsets.only(right: 8),
                        child:
                            SvgPicture.asset('resources/icons/keyboard_arrow_right.svg'),
                      ),
                      onTap: () {
                        Get.to(() => ManagerOrderSreen());
                      },
                    ),
                    // const PolicyTermsScreen(),
                    ListTile(
                      dense: true,
                      contentPadding:
                          const EdgeInsets.only(left: 0.0, right: 0.0),
                      title: Row(
                        children: [
                          SvgPicture.asset('resources/icons/logout.svg'),
                          const SizedBox(
                            width: 10,
                          ),
                          const Text(
                            "Đăng xuất",
                            style: TextStyle(
                              fontWeight: FontWeight.w700,
                              fontSize: 12,
                              color: Colors.black,
                            ),
                          )
                        ],
                      ),
                      tileColor: Colors.transparent,
                      onTap: () {
                        // viewModel.logout();
                      },
                    ),
                  ],
                ),
              ),
              const SizedBox(
                height: 8,
              ),
              Container(
                color: UIColors.white,
                padding:
                    const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
                child: Row(
                  children: [
                    Expanded(
                      flex: 1,
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          SizedBox(
                            child: Row(
                              children: const [
                                Text(
                                  "Hotline",
                                  textAlign: TextAlign.left,
                                  style: TextStyle(
                                    color: UIColors.black,
                                    fontWeight: FontWeight.w700,
                                  ),
                                ),
                              ],
                            ),
                          ),
                          const SizedBox(height: SpaceValues.space12),
                          Text(
                            '1900 919 19',
                            overflow: TextOverflow.ellipsis,
                            style: Theme.of(context).textTheme.headline5,
                          ),
                          Container(
                            margin: const EdgeInsets.only(top: 4),
                            child: Text(
                              "Liên hệ trực tiếp tổng đài để giải quyết các vấn đề khẩn cấp",
                              style: Theme.of(context).textTheme.subtitle1,
                            ),
                          ),
                          ElevatedButton(
                            style: ElevatedButton.styleFrom(
                              primary: UIColors.appBar
                            ),
                            onPressed: () {
                              launchUrlString('tel:190091919');
                            },
                            child: const Text(
                              'Gọi ngay',
                              style: TextStyle(
                                fontSize: 12,
                                fontWeight: FontWeight.w400,
                              ),
                            ),
                          )
                        ],
                      ),
                    ),
                    Container(
                      margin: const EdgeInsets.only(right: 8),
                      child: SvgPicture.asset(
                        'SvgImageAssets.imgHotlline',
                        height: 95,
                      ),
                    )
                  ],
                ),
              ),
              ////////////////////
              const SizedBox(
                height: SpaceValues.space12,
              ),
            ],
          ),
        ),
      ),
    );
  }

  @override
  ProfileModel createViewModel() => getIt<ProfileModel>();
}

// class ProgressBarCusstom extends StatefulWidget {
//   ProgressBarCusstom({Key? key, required this.viewModel}) : super(key: key);
//
//   ProfileModel viewModel;
//   @override
//   State<ProgressBarCusstom> createState() => _ProgressBarCusstomState();
// }

// class _ProgressBarCusstomState extends State<ProgressBarCusstom> {
//   @override
//   Widget build(BuildContext context) {
//     return Expanded(
//       flex: 1,
//       child: Row(
//         children: [
//           Expanded(
//             flex: widget.viewModel.next_point_percentage.value.toString()!="NaN"?widget.viewModel.next_point_percentage.value.toInt():0,
//             child: const Divider(
//                 height: 50, thickness: 3, color: UIColors.brandA),
//           ),
//           Expanded(
//             flex:widget.viewModel.next_point_percentage.value.toString()!="NaN"?( 100 - widget.viewModel.next_point_percentage.value.toInt()):1,
//             child:
//             const Divider(height: 50, thickness: 3, color: Colors.grey),
//           )
//         ],
//       ),
//     );
//   }
// }
