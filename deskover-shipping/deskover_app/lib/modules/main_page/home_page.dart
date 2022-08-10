import 'package:deskover_app/config/injection_config.dart';
import 'package:deskover_app/modules/main_page/home_page_model.dart';
import 'package:deskover_app/themes/ui_colors.dart';
import 'package:deskover_app/utils/widgets/view_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';
import 'package:google_nav_bar/google_nav_bar.dart';
import 'package:line_icons/line_icons.dart';

import '../dashboard/dashboard_screen.dart';
import '../order/order_delivery.dart';
import '../profile/profile.dart';
import '../receive_orders/find_order.dart';

class HomePage extends StatefulWidget {
  final int? indexTap;
  final int? initScreen;
 const HomePage({Key? key, this.indexTap, this.initScreen}) : super(key: key);


  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends ViewWidget<HomePage,HomePageModel> {

  @override
  void initState()  {
    super.initState();

    WidgetsBinding.instance
        ?.addPostFrameCallback((_) => setState(() {
      viewModel.index.value = widget.indexTap ?? viewModel.index.value;
    }));

  }
  static final List<Widget> _widgetOptions = <Widget>[
          const DashboardScreen(),
          const ReceiveOrders(),
          HomeOrderScreen(),
          ProfileScreen(),
  ];
  @override
  Widget build(BuildContext context) {
    return Obx(
        ()=>Scaffold(
            body: Center(
            child: Obx(() => _widgetOptions[viewModel.index.value]),
          ),
          bottomNavigationBar:
          Container(
            decoration: BoxDecoration(
              color: Colors.white,
              boxShadow: [
                BoxShadow(
                  blurRadius: 20,
                  color: Colors.black.withOpacity(.1),
                )
              ],
            ),
            child: SafeArea(
              child: GNav(
                rippleColor: Colors.grey[300]!,
                hoverColor: Colors.grey[100]!,
                gap: 8,
                activeColor: UIColors.appBar,
                iconSize: 24,
                padding: EdgeInsets.symmetric(horizontal: 20, vertical: 12),
                duration: Duration(milliseconds: 400),
                tabBackgroundColor: Colors.grey[100]!,
                color: Colors.black,
                // backgroundColor: UIColors.white,
                tabs:   const [
                  GButton(
                    icon:  LineIcons.home ,
                    text: 'Trang chủ',

                  ),
                  GButton(
                    icon:  LineIcons.search,
                    text: 'Tìm kiếm',

                  ),
                  GButton(
                    icon: LineIcons.dropbox,
                    text: 'Đơn hàng',
                  ),
                  GButton(
                    icon:LineIcons.user,
                    text: 'Profile',
                  ),
                ],
                selectedIndex: viewModel.index.value,
                onTabChange: (index) {
                  viewModel.index.value = index;
                },
              ),
            ),
          ),


        )
    );
  }

  @override
  HomePageModel createViewModel() => getIt<HomePageModel>();

}
