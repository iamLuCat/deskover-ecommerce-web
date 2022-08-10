import 'package:deskover_develop/src/config/assets/icon_assets.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/order/all_order/list_order_model.dart';
import 'package:deskover_develop/src/modules/order/order_screen.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';
import 'package:intl/intl.dart';
class OrderTab extends StatefulWidget{
  final String orderStatus;
  const OrderTab({Key? key, required this.orderStatus}) : super(key: key);

  @override
  State<OrderTab> createState() => _OrderTab();
}
class _OrderTab extends ViewWidget<OrderTab,ListOrderModel>{
  @override
  void initState() {
    super.initState();
    viewModel.statusCode.value = widget.orderStatus;
    viewModel.loadOrderByStatus();
  }
  final formatCurrency = NumberFormat.currency(locale:"vi_VN", symbol: "đ");

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: Padding(
        padding: const EdgeInsets.all(0),
        child: Obx(
              () => Visibility(
                  visible:viewModel.orders.isNotEmpty,
                  child: Column(
                    children: [
                      for (int i = 0; i < viewModel.orders.length; i++)
                        InkWell(
                          onTap: (){
                            Get.to(OrderManager(orderCode: viewModel.orders[i].orderCode.toString(),));
                          },
                          child: Container(
                            margin: const EdgeInsets.only(top: 6),
                            decoration: BoxDecoration(
                              color: UIColors.white,
                              border: Border.all(color: UIColors.border10),),
                            child: Padding(
                              padding: const EdgeInsets.all(16),
                              child: Column(
                                children: [
                                  const SizedBox(
                                    height: 8,
                                  ),
                                  Row(
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    children: [
                                      SvgPicture.asset(
                                        IconAssets.socialPersonFill,
                                        width: 18,
                                      ),
                                      const SizedBox(
                                        width: 12,
                                      ),
                                      Text(
                                        "${viewModel.orders[i].fullName}",
                                        style: const TextStyle(fontSize: 12),
                                      ),
                                    ],
                                  ),
                                  SizedBox(
                                    height: 8,
                                  ),
                                  Row(
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    children: [
                                      SvgPicture.asset(
                                        IconAssets.mapsLocationPinFill,
                                        width: 18,
                                      ),
                                      const SizedBox(
                                        width: 12,
                                      ),
                                      Expanded(
                                        child: Text(
                                            "${viewModel.orders[i].orderDetail?.address}",
                                            maxLines: 1,
                                            overflow: TextOverflow.ellipsis,
                                            style: const TextStyle(
                                              fontSize: 12,
                                            )),
                                      ),
                                    ],
                                  ),
                                  const SizedBox(
                                    height: 8,
                                  ),
                                  Row(
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    children: [
                                      SvgPicture.asset(
                                        IconAssets.notificationEventNoteFill,
                                        width: 18,
                                      ),
                                      const SizedBox(
                                        width: 12,
                                      ),
                                      Expanded(
                                        child: Text(
                                            DateFormat('dd-MM-yyyy HH:mm').format(DateTime.parse(viewModel.orders[i].createdAt.toString()))
                                            ,
                                            maxLines: 1,
                                            overflow: TextOverflow.ellipsis,
                                            style: const TextStyle(
                                              fontSize: 12,
                                            )),
                                      ),
                                      Text(
                                        formatCurrency.format(viewModel.orders[i].unitPrice),
                                        style: const TextStyle(
                                            color: UIColors.brandA,
                                            fontSize: 12,
                                            fontWeight: FontWeight.bold),
                                      ),
                                    ],
                                  ),
                                  const SizedBox(
                                    height: 8,
                                  ),
                                ],
                              ),
                            ),
                          ),
                        ),
                    ],
                  ),
                replacement: Column(
                  children: [
                    const SizedBox(height: 16,),
                    SizedBox(
                      width: MediaQuery.of(context).size.width * 0.3,
                      child: ClipRRect(
                          borderRadius: BorderRadius.circular(999),
                          child: Image.asset(
                              'resources/images/listplan.jpg',
                          )),
                    ),
                    const SizedBox(height: 6,),
                    const Text(
                      'Bạn chưa có đơn hàng'
                    )
                  ],
                ),
              ),
        ),
      ),
    );
  }

  @override
  ListOrderModel createViewModel() => getIt<ListOrderModel>();
  
}