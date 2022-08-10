import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/order/order_manager_model.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';

import '../../utils/widgets/view_widget.dart';
import 'get_time.dart';

class NotificationOrder extends StatefulWidget{
 final String orderCode;

  const NotificationOrder({Key? key, required this.orderCode}) : super(key: key);

  @override
  State<NotificationOrder> createState() => _NotificationOrder();

}
class _NotificationOrder extends ViewWidget<NotificationOrder,OrderManagerModel>{
  @override
  void initState() {
    super.initState();
    viewModel.orderCode.value = widget.orderCode;
    viewModel.loadNotifyByOrderCode();
  }
  @override
  Widget build(BuildContext context) {
    return  Scaffold(
      appBar: AppBar(
        backgroundColor: UIColors.white,
        // shadowColor: UIColors.black10,
        title: Text('Trạng thái đơn hàng: ${widget.orderCode}'),
      ),
      body: Container(
        margin: EdgeInsets.only(top: 6),
          color: UIColors.white,
          width: double.infinity,
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            crossAxisAlignment: CrossAxisAlignment.start,
            children:  [
              SizedBox(height: 10,),
              Obx(()=> Expanded(
                child: ListView.separated(
                    padding: const EdgeInsets.all(8),
                    separatorBuilder: (BuildContext context, int index) => const Divider(),
                    itemCount: viewModel.notifyResponse.length,
                    itemBuilder: (BuildContext context, int index) {
                      return Column(
                        children: [
                          Row(
                            children: [
                              SizedBox(
                                width: MediaQuery.of(context).size.width * 0.1,
                                child: CircleAvatar(
                                  backgroundColor: UIColors.white,
                                  child: Icon(Icons.circle_rounded,color: (viewModel.notifyResponse.length -1) == index ? UIColors.brandA : UIColors.black10 ,),
                                ),
                              ),
                              Expanded(child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children: [
                                  SizedBox(
                                    child: Text(viewModel.notifyResponse[index].title ?? ''),
                                  ),
                                  SizedBox(height: 6,),
                                  Text(GetTime.parse(
                                      DateTime.parse(viewModel.notifyResponse[index].createdAt ?? '')
                                  )),
                                ],
                              ),)
                            ],
                          )

                        ],
                      );
                    }
                ),
              ))



            ],
          )),
    );
  }

  @override
  OrderManagerModel createViewModel() => getIt<OrderManagerModel>();

}