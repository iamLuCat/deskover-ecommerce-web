import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';

import '../../../config/injection_config.dart';
import '../../../themes/ui_colors.dart';
import '../../../utils/widgets/view_widget.dart';
import 'manager_order_model.dart';
import 'order_detail.dart';

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
      body: RefreshIndicator(
        onRefresh: () async {
          viewModel.getListOrder();
        },
        child: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(0),
            child: Obx(
                  () => Column(
                children: [
                  for (int i = 0; i < viewModel.listOrder.length; i++)
                    InkWell(
                      onTap: () => Get.to(OrderDetail(
                        code: viewModel.listOrder[i].code!, orderCode: viewModel.listOrder[i].orderCode!,
                      )),
                      child: Container(
                        margin: const EdgeInsets.only(top: 6),
                        decoration: BoxDecoration(
                          color: UIColors.white,
                          border: Border.all(color: UIColors.border10),),
                        child: Padding(
                          padding: const EdgeInsets.all(16),
                          child: Column(
                            children: [
                              Row(
                                mainAxisAlignment:
                                MainAxisAlignment.spaceBetween,
                                children: [
                                  Text(
                                    "#${viewModel.listOrder[i].orderCode}",
                                    style: const TextStyle(
                                        color: UIColors.black,
                                        fontSize: 12,
                                        fontWeight: FontWeight.bold),
                                  ),
                                  Container(
                                    decoration: BoxDecoration(
                                        color: viewModel.listOrder[i].code ==
                                            'LH-TC'
                                            ? Color(0x50B41D8D) //UIColors.brandA
                                            : viewModel.listOrder[i].code ==
                                            'DG'
                                            ?Colors.yellow[200]
                                            : viewModel.listOrder[i].code == 'GH-TC'
                                            ?Colors.black26
                                            : viewModel.listOrder[i].code == 'GH-TB'
                                            ?Colors.blueAccent[200]
                                            : viewModel.listOrder[i].code == 'LH-TB'
                                            ? Colors.blue[200]
                                            : viewModel.listOrder[i]
                                            .code ==
                                            'HUY'
                                            ? Colors.red[200]
                                            : Colors.green[200],
                                        borderRadius: BorderRadius.circular(12)
                                    ),
                                    child: Padding(
                                      padding: const EdgeInsets.fromLTRB(8, 4, 8, 4),
                                      child: Text(
                                        "${viewModel.listOrder[i].status}",
                                        style: TextStyle(
                                            color: viewModel.listOrder[i].code ==
                                                'LH-TC'
                                                ? UIColors.brandA
                                                : viewModel.listOrder[i].code ==
                                                'DG'
                                                ? Colors.yellow
                                                : viewModel.listOrder[i]
                                                .code ==
                                                'GH-TC'
                                                ? Colors.white
                                                : viewModel.listOrder[i].code == 'HUY'
                                                ? Colors.red
                                                : viewModel.listOrder[i].code == 'GH-TB'
                                                ? Colors.red
                                                : viewModel.listOrder[i].code == 'LH-TB'
                                                ? Colors.blueAccent
                                                : Colors.green,
                                            fontSize: 12,
                                            fontWeight: FontWeight.bold),
                                      ),
                                    ),
                                  ),
                                ],
                              ),
                              SizedBox(
                                height: 8,
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.start,
                                children: [
                                  SvgPicture.asset('resources/icons/person.svg',height: 16,),
                                  const SizedBox(
                                    width: 12,
                                  ),
                                  Text(
                                    "${viewModel.listOrder[i].fullName}",
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
                                  SvgPicture.asset('resources/icons/location_pin.svg',height: 16,),
                                  const SizedBox(
                                    width: 12,
                                  ),
                                  Expanded(
                                    child: Text(
                                        "${viewModel.listOrder[i].address}",
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
                                  SvgPicture.asset('resources/icons/event_note.svg',height: 16,),
                                  const SizedBox(
                                    width: 12,
                                  ),
                                  Expanded(
                                    child: Text(
                                        "${viewModel.listOrder[i].createdAt}",
                                        maxLines: 1,
                                        overflow: TextOverflow.ellipsis,
                                        style: const TextStyle(
                                          fontSize: 12,
                                        )),
                                  ),
                                  Text(
                                    "${viewModel.listOrder[i].totalPrice}",
                                    style: const TextStyle(
                                        color: UIColors.brandA,
                                        fontSize: 12,
                                        fontWeight: FontWeight.bold),
                                  ),
                                ],
                              ),
                              SizedBox(
                                height: 8,
                              ),
                            ],
                          ),
                        ),
                      ),
                    ),
                  /////////////////
                  const SizedBox(
                    height: 8,
                  ),
                ],
              ),
            ),
          ),
        ),
      ),
    );
  }

  @override
  ManagerOrderModel createViewModel() => getIt<ManagerOrderModel>();
}
