import 'package:badges/badges.dart';
import 'package:deskover_develop/src/config/assets/icon_assets.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/profile/managerorder/manager_model.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';

class ManagerOrderSreen extends StatefulWidget {
  @override
  State<ManagerOrderSreen> createState() => _ManagerOrderSreenState();
}

class _ManagerOrderSreenState
    extends ViewWidget<ManagerOrderSreen, ManagerOrderModel> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text(
          'Quản lý đơn hàng',
          style: TextStyle(fontWeight: FontWeight.w700, color: UIColors.title,fontSize: 14),
        ),
        centerTitle: true,
        backgroundColor: UIColors.white,
        shape: Border(bottom: BorderSide(width: 1, color: UIColors.border10)),
      ),
      body: SingleChildScrollView(
        child: Padding(
          padding: const EdgeInsets.all(0),
          child: Text('123')
          // Obx(
          //       () => Column(
          //     children: [
          //       for (int i = 0; i < viewModel.listOrder.length; i++)
          //         InkWell(
          //           onTap: () => Get.to(OrderDetailScreen(id: viewModel.listOrder[i].id!,
          //           )),
          //           child: Container(
          //             margin: const EdgeInsets.only(top: 6),
          //             decoration: BoxDecoration(
          //               color: UIColors.white,
          //               border: Border.all(color: UIColors.border10),),
          //             child: Padding(
          //               padding: const EdgeInsets.all(16),
          //               child: Column(
          //                 children: [
          //                   Row(
          //                     mainAxisAlignment:
          //                     MainAxisAlignment.spaceBetween,
          //                     children: [
          //                       Text(
          //                         "${viewModel.listOrder[i].code}",
          //                         style: const TextStyle(
          //                             color: UIColors.brandA,
          //                             fontSize: 12,
          //                             fontWeight: FontWeight.bold),
          //                       ),
          //                       Container(
          //                         decoration: BoxDecoration(
          //                             color: viewModel.listOrder[i].status ==
          //                                 'NEW'
          //                                 ? Color(0x50B41D8D) //UIColors.brandA
          //                                 : viewModel.listOrder[i].status ==
          //                                 'APPROVED'
          //                                 ?Colors.yellow[200]
          //                                 : viewModel.listOrder[i]
          //                                 .status ==
          //                                 'SHIPPING'
          //                                 ? Colors.blue[200]
          //                                 : viewModel.listOrder[i]
          //                                 .status ==
          //                                 'CANCELED'
          //                                 ? Colors.red[200]
          //                                 : Colors.green[200],
          //                             borderRadius: BorderRadius.circular(12)
          //                         ),
          //                         child: Padding(
          //                           padding: const EdgeInsets.all(4),
          //                           child: Text(
          //                             "${viewModel.listOrder[i].status_name}",
          //                             style: TextStyle(
          //                                 color: viewModel.listOrder[i].status ==
          //                                     'NEW'
          //                                     ? UIColors.brandA
          //                                     : viewModel.listOrder[i].status ==
          //                                     'APPROVED'
          //                                     ? Colors.yellow
          //                                     : viewModel.listOrder[i]
          //                                     .status ==
          //                                     'SHIPPING'
          //                                     ? Colors.blue
          //                                     : viewModel.listOrder[i]
          //                                     .status ==
          //                                     'CANCELED'
          //                                     ? Colors.red
          //                                     : Colors.green,
          //                                 fontSize: 12,
          //                                 fontWeight: FontWeight.bold),
          //                           ),
          //                         ),
          //                       ),
          //
          //                     ],
          //                   ),
          //                   SizedBox(
          //                     height: 8,
          //                   ),
          //                   Row(
          //                     mainAxisAlignment: MainAxisAlignment.start,
          //                     children: [
          //                       SvgPicture.asset(
          //                         IconAssets.socialPersonFill,
          //                         width: 18,
          //                       ),
          //                       const SizedBox(
          //                         width: 12,
          //                       ),
          //                       Text(
          //                         "${viewModel.listOrder[i].customer_name}",
          //                         style: const TextStyle(fontSize: 12),
          //                       ),
          //                     ],
          //                   ),
          //                   SizedBox(
          //                     height: 8,
          //                   ),
          //                   Row(
          //                     mainAxisAlignment: MainAxisAlignment.start,
          //                     children: [
          //                       SvgPicture.asset(
          //                         IconAssets.mapsLocationPinFill,
          //                         width: 18,
          //                       ),
          //                       const SizedBox(
          //                         width: 12,
          //                       ),
          //                       Expanded(
          //                         child: Text(
          //                             "${viewModel.listOrder[i].shipping_address}",
          //                             maxLines: 1,
          //                             overflow: TextOverflow.ellipsis,
          //                             style: TextStyle(
          //                               fontSize: 12,
          //                             )),
          //                       ),
          //                     ],
          //                   ),
          //                   SizedBox(
          //                     height: 8,
          //                   ),
          //                   Row(
          //                     mainAxisAlignment: MainAxisAlignment.start,
          //                     children: [
          //                       SvgPicture.asset(
          //                         IconAssets.notificationEventNoteFill,
          //                         width: 18,
          //                       ),
          //                       const SizedBox(
          //                         width: 12,
          //                       ),
          //                       Expanded(
          //                         child: Text(
          //                             "${viewModel.listOrder[i].created_at}",
          //                             maxLines: 1,
          //                             overflow: TextOverflow.ellipsis,
          //                             style: const TextStyle(
          //                               fontSize: 12,
          //                             )),
          //                       ),
          //                       Text(
          //                         "${viewModel.listOrder[i].total_price_formatted}",
          //                         style: const TextStyle(
          //                             color: UIColors.brandA,
          //                             fontSize: 12,
          //                             fontWeight: FontWeight.bold),
          //                       ),
          //                     ],
          //                   ),
          //                   SizedBox(
          //                     height: 8,
          //                   ),
          //                   Row(
          //                     mainAxisAlignment: MainAxisAlignment.start,
          //                     children: [
          //                       // SvgPicture.asset(
          //                       //   IconAssets.actionAssignmentTurnedInFill,
          //                       //   width: 18,
          //                       // ),
          //                       // const SizedBox(
          //                       //   width: 12,
          //                       // ),
          //                       // const Expanded(
          //                       //   child: Text("Trạng thái",
          //                       //       maxLines: 1,
          //                       //       overflow: TextOverflow.ellipsis,
          //                       //       style: TextStyle(
          //                       //         fontSize: 12,
          //                       //       )),
          //                       // ),
          //                       // Text(
          //                       //   "${viewModel.listOrder[i].status_name}",
          //                       //   style: TextStyle(
          //                       //       color: viewModel.listOrder[i].status ==
          //                       //           'NEW'
          //                       //           ? UIColors.brandA
          //                       //           : viewModel.listOrder[i].status ==
          //                       //           'APPROVED'
          //                       //           ? Colors.yellow
          //                       //           : viewModel.listOrder[i]
          //                       //           .status ==
          //                       //           'SHIPPING'
          //                       //           ? Colors.blue
          //                       //           : viewModel.listOrder[i]
          //                       //           .status ==
          //                       //           'CANCELED'
          //                       //           ? Colors.red
          //                       //           : Colors.green,
          //                       //       fontSize: 12,
          //                       //       fontWeight: FontWeight.bold),
          //                       // ),
          //                     ],
          //                   )
          //                 ],
          //               ),
          //             ),
          //           ),
          //         ),
          //       /////////////////
          //       const SizedBox(
          //         height: 8,
          //       ),
          //     ],
          //   ),
          // ),
        ),
      ),
    );
  }

  @override
  ManagerOrderModel createViewModel() => getIt<ManagerOrderModel>();
}
