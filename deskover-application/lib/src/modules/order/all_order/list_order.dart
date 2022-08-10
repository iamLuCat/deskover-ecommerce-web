import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/order/all_order/list_order_model.dart';
import 'package:deskover_develop/src/modules/order/all_order/widget.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/gestures.dart';
import 'package:flutter/material.dart';

class ListOrder extends StatefulWidget {
  final int index;

  const ListOrder({Key? key, this.index = 0}) : super(key: key);
  @override
  State<StatefulWidget> createState()=> _ListOrder();

}

class _ListOrder extends ViewWidget<ListOrder,ListOrderModel> {

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      initialIndex: widget.index,
      length: 6,
      child: Scaffold(
        appBar: AppBar(
          backgroundColor: UIColors.white,
          title: const Text('Quản lý đơn hàng'),
        ),
        body:  Column(
          children:  [
            Container(
              decoration: BoxDecoration(
                color: UIColors.white,
                boxShadow: [
                  BoxShadow(
                    color: Colors.grey.withOpacity(0.5),
                    spreadRadius: 5,
                    blurRadius: 7,
                    offset: const Offset(0, 3), // changes position of shadow
                  ),
                ],
              ),
              child: const TabBar(
                isScrollable: true,
                labelColor: UIColors.brandA,
                indicatorColor: UIColors.brandA,
                unselectedLabelColor: Colors.black38,
                labelStyle: TextStyle(fontWeight: FontWeight.w700),
                unselectedLabelStyle: TextStyle(
                    fontSize: 13
                ),
                tabs: <Widget>[
                  Tab(
                    child: Text('Tất cả đơn hàng', style: TextStyle(
                      fontSize: 14,
                    ),
                    ),
                  ),
                  Tab(
                    child: Text('Chờ xác nhận', style: TextStyle(
                      fontSize: 14,
                    ),
                    ),
                  ),
                  Tab(
                    child: Text('Đang giao', style: TextStyle(
                      fontSize: 14,
                    ),
                    ),
                  ),
                  Tab(
                    child: Text('Đã giao', style: TextStyle(
                      fontSize: 14,
                    ),
                    ),
                  ),
                  Tab(
                    child: Text('Chờ huỷ', style: TextStyle(
                      fontSize: 14,
                    ),
                    ),
                  ),
                  Tab(
                    child: Text('Đã huỷ', style: TextStyle(
                      fontSize: 14,
                    ),
                    ),
                  ),
                ],
              ),
            ),
            const Expanded(
              child: TabBarView(
                physics: BouncingScrollPhysics(),
                dragStartBehavior: DragStartBehavior.down,
                children: <Widget>[
                  OrderTab(orderStatus: '',),
                  OrderTab(orderStatus: 'C-XN',),
                  OrderTab(orderStatus: 'DG',),
                  OrderTab(orderStatus: 'GH-TC',),
                  OrderTab(orderStatus: 'C-HUY',),
                  OrderTab(orderStatus: 'HUY',),
                ],
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
  ListOrderModel createViewModel()=> getIt<ListOrderModel>();
}
