import 'package:badges/badges.dart';
import 'package:deskover_develop/src/config/assets/icon_assets.dart';
import 'package:deskover_develop/src/config/assets/image_asset.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/action/search/search_screen.dart';
import 'package:deskover_develop/src/modules/notification/notification.dart';
import 'package:deskover_develop/src/modules/profile/profile_screen.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';

import 'cart/creat_cart.dart';
import 'homepage/homepage_screen.dart';
import 'main_page_model.dart';



class MainPage extends StatefulWidget {
  const MainPage({Key? key, this.indexTab}) : super(key: key);
  final int? indexTab;

  @override
  State<MainPage> createState() => _MainPageState();
}



class _MainPageState extends ViewWidget<MainPage, MainPageModel> {
  @override
  void initState() {
    super.initState();
    viewModel.loadCart();
    viewModel.loadNotify();
    WidgetsBinding.instance
        ?.addPostFrameCallback((_) => setState(() {
      viewModel.index.value = widget.indexTab ?? viewModel.index.value;
    }));
  }

  static final List<Widget> _widgetOptions = <Widget>[
    const HomePage(),
    const Notify(),
    const ProfileScreen(),

    // const GarnitureScreen(),
    // const NewsCreen(),
    // const FlashSalePage(),

  ];
  @override
  Widget build(BuildContext context) {
    return Obx(
      () => Scaffold(
          appBar: [1,2].contains(viewModel.index.value)
              ? null
              : AppBar(
                  backgroundColor: UIColors.white,
                  centerTitle: false,
                  title:   Image.asset( ImageAssets.imgLogobanner, height: 40),
            actions: [
              IconButton(
                onPressed: () {
                  Get.to(const SearchPage());
                },
                icon:  SvgPicture.asset('resources/icons/change_point/search.svg',color: UIColors.black.withOpacity(1.0), ),
              ),
              Obx(()=>Padding(
                padding: const EdgeInsets.fromLTRB(0, 8, 16, 8.0),
                child: Badge(
                  badgeColor: UIColors.brandA,
                  badgeContent:
                  Text(
                    viewModel.itemCart.value.toString(),
                    style: const TextStyle(
                        color: UIColors.white
                    ),
                  ),
                  animationType: BadgeAnimationType.scale,
                  child: IconButton(
                    onPressed: () {
                      Get.to(CreateChangePointCart());
                    },
                    icon: SvgPicture.asset(SvgImageAssets.imgcard),
                  ),
                ),
              ),),

            ],
            shape: const Border(bottom: BorderSide(color: UIColors.black10)),
                ),
          body: Obx(() => _widgetOptions[viewModel.index.value]),
          bottomNavigationBar: Container(
            decoration: const BoxDecoration(
              boxShadow: <BoxShadow>[
                BoxShadow(
                  color: Colors.black26,
                  blurRadius: 5,
                ),
              ],
            ),
            child: BottomNavigationBar(
              elevation: 5,
              backgroundColor: Colors.white,
              type: BottomNavigationBarType.fixed,
              selectedItemColor: UIColors.brandA,
              unselectedItemColor: Colors.black54,
              selectedLabelStyle: const TextStyle(
                fontSize: 11,
                fontWeight: FontWeight.w600,
              ),
              unselectedFontSize: 10,
              items: [
                BottomNavigationBarItem(
                  icon: SizedBox(
                    width: 24,
                    height: 24,
                    child: viewModel.index == 0
                        ? SvgPicture.asset(
                            SvgImageAssets.icHome,
                            color: UIColors.brandA,
                          )
                        : SvgPicture.asset(
                            SvgImageAssets.icHome,
                            color: UIColors.black40,
                          ),
                  ),
                  // icon: new Image.asset("resources/images/ic_dashboard_dashboard.png"),
                  label: "Trang chủ",
                ),
                BottomNavigationBarItem(
                    icon: SizedBox(
                      width: 24,
                      height: 24,
                      child: viewModel.index == 1
                          ? Badge(
                          showBadge: viewModel.notification.value,
                          position: BadgePosition.topEnd(top: 0, end: 1),
                          child: const Icon(Icons.notifications_none, size: 26))
                          : Badge(
                          showBadge: viewModel.notification.value,
                          position: BadgePosition.topEnd(top: 0, end: 1),
                          child: const Icon(Icons.notifications_none, size: 24)),
                    ),
                    label: "Thông báo"),
                BottomNavigationBarItem(
                    icon: SizedBox(
                      width: 24,
                      height: 24,
                      child: viewModel.index == 2
                          ? SvgPicture.asset(
                        SvgImageAssets.icAccount,
                        color: UIColors.brandA,
                      )
                          : SvgPicture.asset(
                        SvgImageAssets.icAccount,
                        color: UIColors.black40,
                      ),
                    ),
                    label: "Tài khoản")
              ],
              currentIndex: viewModel.index.value,
              onTap: (index) {
                viewModel.index.value = index;
                viewModel.loadCart();
                viewModel.loadNotify();
              },
            ),
          )),
    );
  }

  @override
  MainPageModel createViewModel() => getIt<MainPageModel>();
}
