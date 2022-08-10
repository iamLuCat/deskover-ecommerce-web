import 'package:deskover_app/config/injection_config.dart';
import 'package:deskover_app/modules/order/widgets/delivering/delivering.dart';
import 'package:deskover_app/modules/order/widgets/delivery/delivery.dart';
import 'package:deskover_app/modules/order/widgets/delivery/delivery_model.dart';
import 'package:deskover_app/themes/space_values.dart';
import 'package:deskover_app/themes/ui_colors.dart';
import 'package:deskover_app/utils/widgets/view_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';


class HomeOrderScreen extends StatefulWidget{

  const HomeOrderScreen({Key? key,}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _HomeOrderScreen();
}
class _HomeOrderScreen extends  ViewWidget<HomeOrderScreen,DeliveryModel>{


  @override
  void initState()  {
    super.initState();
    print('widget.initScreen');

  }

  @override
  Widget build(BuildContext context) {
      return DefaultTabController(
        length: 2,
        initialIndex: 0,
        child: Scaffold(
          appBar: AppBar(
            centerTitle: true,
            leading: const SizedBox(
              width: 0,
              height: 0,
            ),
            backgroundColor: UIColors.white,
            leadingWidth: 0,
            title: const Text('Đơn hàng của bạn',
              style: TextStyle(
                fontSize: 14
            ),),
            // actions: [
            //   IconButton(
            //     onPressed: () {
            //
            //     },
            //     icon:  Icon(Icons.search, size: 26,color: UIColors.black ,),
            //   ),
            //   IconButton(
            //     onPressed: () {
            //     },
            //     icon:  Icon(Icons.notifications_none, size: 26,color: UIColors.black),
            //   ),
            // ],
          ),
          body: Column(
            crossAxisAlignment: CrossAxisAlignment.start,
            children: <Widget> [
              // Container(
              //   color: UIColors.white,
              //   margin: EdgeInsets.only(top: 1,bottom: 2),
              //   child: Padding(
              //     padding: const EdgeInsets.all(16),
              //     child: TextField(
              //       decoration: InputDecoration(
              //         contentPadding: const EdgeInsets.all(14),
              //         prefixIconConstraints: const BoxConstraints.expand(
              //             height: SpaceValues.space16,
              //             width: SpaceValues.space48),
              //         prefixIcon:  SvgPicture.asset('resources/icons/search.svg',color: UIColors.black),
              //         border: InputBorder.none,
              //         hintText: 'Tìm kiếm đơn hàng...',
              //         hintStyle: const TextStyle(
              //             color: UIColors.black
              //         ),
              //         enabledBorder: OutlineInputBorder(
              //           borderSide: const BorderSide(color: UIColors.black10),
              //           borderRadius: BorderRadius.circular(SpaceValues.space48),
              //         ),
              //         focusedBorder:OutlineInputBorder(
              //           borderSide: const BorderSide(color: UIColors.black10),
              //           borderRadius: BorderRadius.circular(SpaceValues.space48),
              //         ) ,
              //         suffixIconConstraints: const BoxConstraints(
              //             maxHeight: SpaceValues.space24, minWidth: 0),
              //
              //       ),
              //     ),
              //   ),
              // ),
              const SizedBox(height: 2,),
              Container(
                width: double.infinity,
                color: UIColors.white,
                child: Center(
                  child: TabBar(
                    indicatorColor: UIColors.black,
                    indicatorSize: TabBarIndicatorSize.label,
                    labelColor: UIColors.black,
                    labelStyle: const TextStyle(fontWeight: FontWeight.w700),
                    unselectedLabelColor: UIColors.navNonSelected,

                    unselectedLabelStyle: const TextStyle(
                      fontWeight: FontWeight.w700,
                    ),
                    indicatorWeight: 2,
                    labelPadding: EdgeInsets.zero,
                    indicatorPadding: const EdgeInsets.symmetric(horizontal: SpaceValues.space64, vertical: 0),
                    isScrollable: true,
                    tabs: [
                      Container(
                        width: MediaQuery.of(context).size.width * .5 - SpaceValues.space16,
                        decoration: const BoxDecoration(
                          border: Border(
                            right: BorderSide(color: UIColors.dividerDark, width: 1),
                          ),
                        ),
                        child: const Tab(
                          // text: 'Kích hoạt bảo hành',
                          child: Text('Đơn hàng tiếp nhận', style: TextStyle(
                            fontSize: 14,
                          ),),
                        ),
                      ),
                      Container(
                        width: MediaQuery.of(context).size.width * .5 - SpaceValues.space16,
                        decoration: const BoxDecoration(
                          border: Border(left: BorderSide(color: UIColors.dividerDark, width: 1),),
                        ),
                        child: const Tab(
                          // text: 'Kích hoạt bảo hành',
                          child: Text('Đơn hàng đang giao', style: TextStyle(
                            fontSize: 14,
                          ),),
                        ),
                      ),
                    ],
                  ),
                ),
              ),
              const SizedBox(height: 2,),
                 const Expanded(
                  child: TabBarView(
                    children: [
                      Delivery(),
                      Delivering(),
                    ],
                  )
              ),
            ],
          ),
        ),
      );
  }

  @override
  DeliveryModel createViewModel() => getIt<DeliveryModel>();

}