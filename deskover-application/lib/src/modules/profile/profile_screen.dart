import 'package:deskover_develop/src/config/assets/icon_assets.dart';
import 'package:deskover_develop/src/config/assets/image_asset.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/config/static_data.dart';
import 'package:deskover_develop/src/modules/address/addrest_screen.dart';
import 'package:deskover_develop/src/modules/global_modules/widget/global_image.dart';
import 'package:deskover_develop/src/modules/global_modules/widget/global_image_netword.dart';
import 'package:deskover_develop/src/modules/main_page.dart';
import 'package:deskover_develop/src/modules/order/all_order/list_order.dart';
import 'package:deskover_develop/src/modules/profile/product/purchased_product.dart';
import 'package:deskover_develop/src/modules/profile/profile_model.dart';
import 'package:deskover_develop/src/modules/profile/setting/setting_profiled.dart';
import 'package:deskover_develop/src/themes/space_values.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import 'package:url_launcher/url_launcher_string.dart';

import 'managerorder/managerorder.dart';

class ProfileScreen extends StatefulWidget {
  const ProfileScreen({Key? key}) : super(key: key);

  @override
  State<ProfileScreen> createState() => _ProfileScreenState();
}

class _ProfileScreenState extends ViewWidget<ProfileScreen, ProfileModel> {
  bool _customTileExpanded = false;

  @override
  Widget build(BuildContext context) {
    RxBool isLogin = viewModel.isLogin;
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
          padding: const EdgeInsets.only(top: 8),
          child: Column(
            children: [
              Obx(() =>
              !viewModel.isLogin.value
                  ?
              InkWell(
                onLongPress: () => {},
                child: FractionallySizedBox(
                  widthFactor: 1,
                  child: Container(
                    color: UIColors.white,
                    child: Padding(
                      padding: const EdgeInsets.all(8),
                      child: Row(
                        children: [
                          SvgPicture.asset(ImageAssets.icAvatarUser2),
                          Expanded(
                            flex: 1,
                            child: Container(
                              margin: const EdgeInsets.only(left: 8),
                              child: Column(
                                crossAxisAlignment:
                                CrossAxisAlignment.start,
                                mainAxisAlignment:
                                MainAxisAlignment.center,
                                children: [
                                  Text(
                                    "Xin chào ",
                                    overflow: TextOverflow.ellipsis,
                                    style: Theme.of(context).textTheme.headline5,
                                  ),
                                  SizedBox(
                                    height: 35,
                                    child: Row(
                                      mainAxisAlignment:
                                      MainAxisAlignment.start,
                                      children: [
                                        TextButton(
                                          style: TextButton.styleFrom(
                                              padding: EdgeInsets.zero,
                                              minimumSize: Size.zero),
                                          child: Text(
                                            "Đăng ký ",
                                            style: Theme.of(context)
                                                .textTheme
                                                .subtitle1,
                                          ),
                                          onPressed: () => {
                                            // Get.to(SigninSignupScreen())
                                          },
                                        ),
                                        const Text("|"),
                                        TextButton(
                                          child: Text(
                                            "Đăng nhập",
                                            style: Theme.of(context)
                                                .textTheme
                                                .subtitle1,
                                          ),
                                          onPressed: () async {
                                            // await Get.to(SigninSignupScreen(initScreen: 1))!
                                            //     .then((value) {
                                            //   viewModel.checkLogin();
                                            // });
                                          },
                                        ),

                                      ],
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                          // Container(
                          //   margin: const EdgeInsets.only(right: 8),
                          //   child: SvgPicture.asset(
                          //       IconAssets.navigationArrowRight),
                          // )
                        ],
                      ),
                    ),
                  ),
                ),
              )
                  : InkWell(
                onTap: () {
                  Get.to(const SettingProfile());
                },
                child: FractionallySizedBox(
                  widthFactor: 1,
                  child: Container(
                    decoration: BoxDecoration(
                        color: UIColors.white),
                    margin: EdgeInsets.only(bottom: 0),
                    child: Padding(
                      padding: const EdgeInsets.all(8),
                      child: Column(children: [
                        Row(
                          children: [
                            Obx(() => (
                                viewModel.myProfile.value?.avatar ?? '').isNotEmpty
                                  ? Container(
                                height: 64,
                                width: 64,
                                margin:
                                const EdgeInsets.only(left: 8),
                                child: Visibility(
                                  visible: (viewModel.myProfile.value?.avatar ?? '').isNotEmpty,
                                  child: CircleAvatar(
                                    backgroundColor: Colors.grey,
                                    radius: 60,
                                    child: ClipOval(

                                      child:
                                      Obx(()=>GlobalImageNetWord(
                                        BaseApi.baseUrlUser+(viewModel.myProfile.value?.avatar??''),
                                        fit: BoxFit.cover,
                                        width: 120,
                                        height: 120,
                                      ),),

                                    ),
                                  ),
                                ),
                              )
                                  : SvgPicture.asset(
                                ImageAssets.icAvatarUser2,
                              ),
                            ),
                            Expanded(
                              flex: 1,
                              child: Container(
                                margin: const EdgeInsets.only(left: 8),
                                child: Column(
                                  crossAxisAlignment:
                                  CrossAxisAlignment.start,
                                  children: [
                                    Text(
                                      viewModel.myProfile.value?.fullname ?? '...',
                                      overflow: TextOverflow.ellipsis,
                                      style: Theme.of(context)
                                          .textTheme
                                          .headline5,
                                    ),
                                  ],
                                ),
                              ),
                            ),
                            Container(
                              margin: const EdgeInsets.only(right: 8),
                              child: SvgPicture.asset(
                                  IconAssets.navigationArrowRight),
                            ),
                          ],
                        ),
                      ]),
                    ),
                  ),
                ),
              )),
              const SizedBox(
                height: 8,
              ),
              ////////////membership///////////////////////
              // Obx(
              //       () => Visibility(
              //     visible: isLogin.value,
              //     child: Container(
              //       margin:const EdgeInsets.only(bottom:8),
              //       decoration:const BoxDecoration(
              //           color: UIColors.white),
              //       child: Padding(
              //         padding: const EdgeInsets.only(left: 8),
              //         child: Column(
              //           children: [
              //             Obx(
              //                   () => Row(
              //                 children: [
              //                   for (int i = 0; i < (viewModel.dataMembershipResponese.value?.data?.length ?? 0); i++)
              //                     Expanded(
              //                       flex: i < ((viewModel.dataMembershipResponese.value?.data?.length ?? 0) - 1) ? 2 : 1,
              //                       child: Row(
              //                         children: [
              //                           SizedBox(
              //                               height:  60,
              //                               width: 25,
              //                               child: Column(
              //                                 children: [
              //                                   SizedBox(
              //                                     height:
              //                                     16,
              //                                   ),
              //                                   GlobalImage(viewModel
              //                                       .dataMembershipResponese
              //                                       .value
              //                                       ?.data?[i]
              //                                       .icon ??'')
              //                                   // Image.memory(Base64Codec().decode((viewModel
              //                                   //     .dataMembershipResponese
              //                                   //     .value
              //                                   //     ?.data?[i]
              //                                   //     .icon ??
              //                                   //     'data:image/png;base64,${viewModel.icondef}')
              //                                   //     .toString()
              //                                   //     .substring(
              //                                   //     22))),
              //                                 ],
              //                               )),
              //                           if (i <
              //                               ((viewModel
              //                                   .dataMembershipResponese
              //                                   .value
              //                                   ?.data
              //                                   ?.length ??
              //                                   0) -
              //                                   1))
              //                             ((viewModel.myProfile.value
              //                                 .earn_points_total ??
              //                                 0) >=
              //                                 viewModel
              //                                     .dataMembershipResponese
              //                                     .value
              //                                     ?.data?[i + 1]
              //                                     .point)
              //                                 ? const Expanded(
              //                               flex: 1,
              //                               child: Divider(
              //                                   height: 50,
              //                                   thickness: 3,
              //                                   color: UIColors
              //                                       .brandA),
              //                             )
              //                                 : ((viewModel.myProfile.value
              //                                 .earn_points_total ??
              //                                 0) <
              //                                 viewModel
              //                                     .dataMembershipResponese
              //                                     .value
              //                                     ?.data?[i +
              //                                     1]
              //                                     .point &&
              //                                 (viewModel
              //                                     .myProfile
              //                                     .value
              //                                     .earn_points_total ??
              //                                     0) >=
              //                                     viewModel
              //                                         .dataMembershipResponese
              //                                         .value
              //                                         ?.data?[i]
              //                                         .point
              //                                 ? ProgressBarCusstom(
              //                               viewModel:
              //                               viewModel,
              //                             )
              //                                 : const Expanded(
              //                               flex: 1,
              //                               child: Divider(
              //                                   height: 50,
              //                                   thickness:
              //                                   3,
              //                                   color: Colors
              //                                       .grey),
              //                             )),
              //                         ],
              //                       ),
              //                     )
              //                 ],
              //               ),
              //             ),
              //             const SizedBox(
              //               height: 16,
              //             ),
              //             Row(
              //               mainAxisAlignment: MainAxisAlignment.spaceBetween,
              //               children: [
              //                 Expanded(
              //                   flex: 1,
              //                   child: Column(
              //                     children: [
              //                       const Text(
              //                         "Điểm của bạn",
              //                         style: TextStyle(fontSize: 12),
              //                       ),
              //                       Text(
              //                         "${viewModel.myProfile.value.remaining_points_total ?? 0}",
              //                         overflow: TextOverflow.ellipsis,
              //                         maxLines: 1,
              //                         style: const TextStyle(
              //                             fontSize: 14,
              //                             fontWeight: FontWeight.bold),
              //                       ),
              //                     ],
              //                   ),
              //                 ),
              //                 Container(
              //                   width: 1,
              //                   height: 24,
              //                   color: UIColors.border10,
              //                 ),
              //                 Expanded(
              //                   flex: 1,
              //                   child: Column(
              //                     children: [
              //                       const Text(
              //                         "Điểm xếp hạng",
              //                         style: const TextStyle(fontSize: 12),
              //                       ),
              //                       Text(
              //                         "${viewModel.myProfile.value.ranking_user_point ?? 0}",
              //                         overflow: TextOverflow.ellipsis,
              //                         maxLines: 1,
              //                         style: const TextStyle(
              //                             fontSize: 14,
              //                             fontWeight: FontWeight.bold),
              //                       ),
              //                     ],
              //                   ),
              //                 ),
              //                 Container(
              //                   width: 1,
              //                   height: 24,
              //                   color: UIColors.border10,
              //                 ),
              //                 Expanded(
              //                   flex: 1,
              //                   child: Column(
              //                     children: [
              //                       Text(
              //                         "Điểm tích lũy",
              //                         style: const TextStyle(fontSize: 12),
              //                       ),
              //                       Text(
              //                         "${viewModel.myProfile.value.earn_points_total ?? 0}",
              //                         overflow: TextOverflow.ellipsis,
              //                         maxLines: 1,
              //                         style: const TextStyle(
              //                             fontSize: 14,
              //                             fontWeight: FontWeight.bold),
              //                       ),
              //                     ],
              //                   ),
              //                 )
              //               ],
              //             ),
              //             const SizedBox(
              //               height: 16,
              //             ),
              //             Obx(
              //                   () => Visibility(
              //                 visible: viewModel.dataMembershipResponese.value
              //                     ?.data !=
              //                     null &&
              //                     (viewModel.dataMembershipResponese.value?.data
              //                         ?.length ??
              //                         0) >
              //                         0,
              //                 child: Row(
              //                   mainAxisAlignment: MainAxisAlignment.center,
              //                   children: [
              //                     viewModel.myProfile.value.next_ranking_name !=
              //                         null
              //                         ? Column(
              //                       children: [
              //                         Text(
              //                           "Tích lũy thêm ${viewModel.myProfile.value.next_ranking_point_need} để đạt",
              //                           textAlign: TextAlign.center,
              //                           style: const TextStyle(
              //                             fontSize: 13,
              //                           ),
              //                         ),
              //                         Text(
              //                           "hạng ${viewModel.myProfile.value.next_ranking_name} trước ngày ${viewModel.myProfile.value.end_date_earn_points ?? 'kết thúc'}",
              //                           textAlign: TextAlign.center,
              //                           style: const TextStyle(
              //                             fontSize: 13,
              //                           ),
              //                         ),
              //                         Text(
              //                           "( Thứ hạng sẽ cập nhật sau ngày ${viewModel.myProfile.value.date_update_ranking ?? 'kết thúc'} )",
              //                           textAlign: TextAlign.center,
              //                           style: const TextStyle(
              //                             fontSize: 11,
              //                             color: Colors.grey,
              //                           ),
              //                         ),
              //                       ],
              //                     )
              //                         : (viewModel.myProfile.value
              //                         .earn_points_total ??
              //                         0) >=
              //                         ((viewModel.dataMembershipResponese
              //                             .value?.data?.length ??
              //                             0) >
              //                             0
              //                             ? (viewModel
              //                             .dataMembershipResponese
              //                             .value
              //                             ?.data?[((viewModel
              //                             .dataMembershipResponese
              //                             .value
              //                             ?.data
              //                             ?.length ??
              //                             0) -
              //                             1)]
              //                             .point ??
              //                             double.infinity)
              //                             : double.infinity)
              //                         ? const Center(
              //                       child: Text(
              //                         "Bạn đã đạt hạng cao nhất",
              //                       ),
              //                     )
              //                         : const Center(
              //                       child: Text(
              //                         "Bạn chưa có hạng",
              //                       ),
              //                     ),
              //                   ],
              //                 ),
              //               ),
              //             ),
              //             const SizedBox(height: SpaceValues.space8,),
              //           ],
              //         ),
              //       ),
              //     ),
              //   ),
              // ),
              ///////
              ////////////////////////////////////
              Container(
                margin: EdgeInsets.only(top: 8),
                decoration: BoxDecoration(
                    color: isLogin.value ? UIColors.white : Color(0xAaFFFFFF)),
                child: Padding(
                  padding: const EdgeInsets.only(top: 16.0),
                  child: GridView.count(
                    shrinkWrap: true,
                    physics: const NeverScrollableScrollPhysics(),
                    crossAxisCount: 4,
                    children: [
                      Obx(
                            () => InkWell(
                          onTap: () {
                            isLogin.value
                                ? Get.to(NotAddressScreen(cart: false,))
                                : print("pl login");
                          },
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              SvgPicture.asset(
                                IconAssets.actionHome,
                                width: 30,
                                color: isLogin.value
                                    ? UIColors.black
                                    : UIColors.border10,
                              ),
                              const SizedBox(
                                height: 8,
                              ),
                              SizedBox(
                                  child: Text(
                                    "Sổ địa chỉ",
                                    textAlign: TextAlign.center,
                                    style: TextStyle(
                                        color: isLogin.value
                                            ? UIColors.black
                                            : UIColors.border10,
                                        fontSize: 12,
                                        fontWeight: FontWeight.bold),
                                  ))
                            ],
                          ),
                        ),
                      ),
                      Obx(
                            () => InkWell(
                          onTap: () {
                            isLogin.value
                                ? Get.to(ListOrder())
                                : print("pl login");
                          },
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              SvgPicture.asset(
                                SvgImageAssets.imgcard,
                                width: 30,
                                color: isLogin.value
                                    ? UIColors.black
                                    : UIColors.border10,
                              ),
                              const SizedBox(
                                height: 8,
                              ),
                              SizedBox(
                                  child: Text(
                                    "Đơn hàng",
                                    textAlign: TextAlign.center,
                                    style: TextStyle(
                                        color: isLogin.value
                                            ? UIColors.black
                                            : UIColors.border10,
                                        fontSize: 12,
                                        fontWeight: FontWeight.bold),
                                  ))
                            ],
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              ///
              Obx(
                    () => Container(
                  margin: const EdgeInsets.only(top: 8),
                  padding: const EdgeInsets.only(left: 14),
                  decoration: BoxDecoration(
                      color: UIColors.white),
                  child: Column(
                    children: [
                      ListTile(
                        dense: true,
                        contentPadding:
                        const EdgeInsets.only(left: 0.0, right: 0.0),
                        title: Row(
                          children: [
                            SvgPicture.asset(SvgImageAssets.imgLiveChat),
                            const SizedBox(
                              width: 10,
                            ),
                            const Text(
                              "Trung tâm trợ giúp",
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
                          child: SvgPicture.asset(
                              IconAssets.navigationArrowRight),
                        ),
                        onTap: () {
                          // Get.to(() => SuportScreen());
                        },
                      ),
                      // const PolicyTermsScreen(),
                      ListTileTheme(
                        dense: true,
                        minLeadingWidth: 0,
                        contentPadding: EdgeInsets.zero,
                        horizontalTitleGap: 5,
                        child: ExpansionTile(
                          textColor: Colors.black,
                          iconColor: Colors.black,
                          collapsedTextColor: UIColors.black,
                          collapsedIconColor: UIColors.black,
                          leading: SvgPicture.asset(IconAssets.mapsMenuBook),
                          title: const Text(
                            'Chính sách và điều khoản',
                            style: TextStyle(
                              color: Colors.black,
                              fontSize: 12,
                              fontWeight: FontWeight.w700,
                            ),),
                          trailing: Container(
                            margin: EdgeInsets.only(right: 7),
                            child: Icon(
                              _customTileExpanded
                                  ? Icons.arrow_drop_down
                                  : Icons.arrow_right,
                            ),
                          ),
                          onExpansionChanged: (bool expanded) {
                            setState(() => _customTileExpanded = expanded);
                          },
                          tilePadding: const EdgeInsets.only(left: 4),
                          childrenPadding: EdgeInsets.zero,
                          children: [
                            Container(
                              height: 150,
                              padding: const EdgeInsets.only(top: SpaceValues.space16),
                              color: Colors.white,
                              child: GridView.count(
                                childAspectRatio: 3/2,
                                crossAxisCount: 3,
                                shrinkWrap: true,
                                physics: const NeverScrollableScrollPhysics(),
                                children: [
                                  InkWell(
                                    onTap: (){
                                      // viewModel.gotoLicense('CSGH');
                                    },
                                    child: Column(
                                      crossAxisAlignment: CrossAxisAlignment.center,
                                      children: [
                                        Icon(Icons.delivery_dining, color: Colors.black54,),
                                        const SizedBox(
                                          height: 8,
                                        ),
                                        SizedBox(
                                          child: Text("Giao Hàng",
                                            textAlign: TextAlign.center,
                                            style: TextStyle(
                                                fontSize: 12,
                                                fontWeight: FontWeight.bold),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  InkWell(
                                    onTap: (){
                                      // viewModel.gotoLicense('CSBM');
                                    },
                                    child: Column(
                                      crossAxisAlignment: CrossAxisAlignment.center,
                                      children: [
                                        Icon(Icons.security, color: Colors.black54,),
                                        const SizedBox(
                                          height: 8,
                                        ),
                                        SizedBox(
                                          child: Text("Bảo Mật",
                                            textAlign: TextAlign.center,
                                            style: TextStyle(
                                                fontSize: 12,
                                                fontWeight: FontWeight.bold),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  InkWell(
                                    onTap: (){
                                      // viewModel.gotoLicense('CSLD');
                                    },
                                    child: Column(
                                      crossAxisAlignment: CrossAxisAlignment.center,
                                      children: [
                                        Icon(Icons.local_shipping_rounded, color: Colors.black54,),
                                        const SizedBox(
                                          height: 8,
                                        ),
                                        SizedBox(
                                          child: Text("Lắp Đặt",
                                            textAlign: TextAlign.center,
                                            style: TextStyle(
                                                fontSize: 12,
                                                fontWeight: FontWeight.bold),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  InkWell(
                                    onTap: (){
                                      // viewModel.gotoLicense('CSBH');
                                    },
                                    child: Column(
                                      crossAxisAlignment: CrossAxisAlignment.center,
                                      children: [
                                        Icon(Icons.handyman_outlined, color: Colors.black54,),
                                        const SizedBox(
                                          height: 8,
                                        ),
                                        SizedBox(
                                          child: Text("Bảo Hành",
                                            textAlign: TextAlign.center,
                                            style: TextStyle(
                                                fontSize: 12,
                                                fontWeight: FontWeight.bold),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  InkWell(
                                    onTap: (){
                                      // viewModel.gotoLicense('CSDTH');
                                    },
                                    child: Column(
                                      crossAxisAlignment: CrossAxisAlignment.center,
                                      children: [
                                        Icon(Icons.cached_rounded, color: Colors.black54,),
                                        const SizedBox(
                                          height: 8,
                                        ),
                                        SizedBox(
                                          child: Text("Đổi Trả",
                                            textAlign: TextAlign.center,
                                            style: TextStyle(
                                                fontSize: 12,
                                                fontWeight: FontWeight.bold),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                ],
                              ),

                            )

                          ],
                        ),
                      ),
                      isLogin.value? ListTile(
                        dense: true,
                        contentPadding:
                        const EdgeInsets.only(left: 0.0, right: 0.0),
                        title: Row(
                          children: [
                            SvgPicture.asset(IconAssets.actionLogoutFill),
                            const SizedBox(
                              width: 10,
                            ),
                            const Text(
                              "Đăng xuất",
                              style: TextStyle(
                                fontWeight: FontWeight.w700,
                                fontSize: 12,
                                color: Colors.black,),
                            )
                          ],
                        ),
                        tileColor: Colors.transparent,
                        onTap: () {
                          viewModel.logout();
                        },
                      ):Text(''),
                    ],
                  ),
                ),
              ),
              const SizedBox(
                height: 8,
              ),
              ////////////////
              //////////////////////////////
              const SizedBox(
                height: 8,
              ),
              ///
              Container(
                color: UIColors.white,
                padding: const EdgeInsets.symmetric(horizontal: 16, vertical: 12),
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
                                Text("Hotline",
                                  textAlign: TextAlign.left,
                                  style: TextStyle(
                                    color: UIColors.brandA,
                                    fontWeight: FontWeight.w700,
                                  ),
                                ),
                              ],
                            ),
                          ),
                          const SizedBox(height: SpaceValues.space12),
                          Text(
                            '1900 919 119',
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
                            onPressed: () {
                              launchUrlString('1900919119');
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
                        SvgImageAssets.imgHotlline,
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
//
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
