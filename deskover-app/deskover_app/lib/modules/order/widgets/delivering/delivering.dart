import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';

import '../../../../config/injection_config.dart';
import '../../../../themes/ui_colors.dart';
import '../../../../utils/widgets/view_widget.dart';
import '../../../receive_orders/order_model.dart';
import '../../app/order_delivering.dart';
import '../delivery/delivery_model.dart';

class Delivering extends StatefulWidget{

  const Delivering({Key? key,}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _Delivering();

}
class _Delivering extends ViewWidget<Delivering, DeliveryModel>{
  @override
  void initState() {
    super.initState();
    viewModel.getAllOrderDelivering();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body:Obx(()=>
          RefreshIndicator(
            onRefresh: () => viewModel.getAllOrderDelivering(),
            child: Visibility(
              visible: viewModel.dataOrderDelivering.value.length != 0,
              child: Container(
                color: UIColors.white,

                child:  ListView(
                  children: [
                    ListView.separated(
                      shrinkWrap: true,
                      physics: const NeverScrollableScrollPhysics(),
                      itemCount: viewModel.dataOrderDelivering.value.length,
                      itemBuilder: (context, int index) {
                        return Padding(
                          padding: const EdgeInsets.only(left: 32,right: 32),
                          child: InkWell(
                            onTap: (){
                              Get.to(()=> OrderDelivering(OrderCode: viewModel.dataOrderDelivering[index].orderCode,));
                            },
                            child: Padding(
                              padding: const EdgeInsets.fromLTRB(24, 16, 24, 16),
                              child: Column(
                                crossAxisAlignment: CrossAxisAlignment.start,
                                children:  [
                                  Text(
                                    viewModel.dataOrderDelivering[index].orderCode ?? '',
                                    style: const TextStyle(
                                        fontSize: 13,
                                        fontWeight: FontWeight.w700,
                                        color: UIColors.appBar
                                    ),
                                  ),
                                  const SizedBox(height: 6,),
                                  Row(
                                    children: [
                                      SvgPicture.asset('resources/icons/person.svg',height: 16,),
                                      const SizedBox(width: 6,),
                                      Expanded(
                                        child: Text(
                                          viewModel.dataOrderDelivering[index].fullName ??'',
                                          style: const TextStyle(
                                              fontSize: 12
                                          ),
                                          textAlign: TextAlign.start,
                                        ),
                                      )
                                    ],
                                  ),
                                  const SizedBox(height: 4,),
                                  Row(
                                    children: [
                                      SvgPicture.asset('resources/icons/location_pin.svg',height: 16,),
                                      const SizedBox(width: 6,),
                                      Expanded(
                                        child: Text(
                                          viewModel.dataOrderDelivering[index].address ??'',
                                          style: const TextStyle(
                                              fontSize: 12
                                          ),
                                        ),
                                      )
                                    ],
                                  ),
                                  const SizedBox(height: 4,),
                                  Row(
                                    children: [
                                      SvgPicture.asset('resources/icons/event_note.svg',height: 16,),
                                      const SizedBox(width: 6,),
                                      Text(
                                        viewModel.dataOrderDelivering[index].createdAt!,
                                        style: const TextStyle(
                                            fontSize: 12
                                        ),
                                      ),
                                      const Expanded(child: SizedBox()),
                                      Text(
                                        viewModel.dataOrderDelivering[index].totalPrice!+' đ',
                                        style: const TextStyle(
                                            fontSize: 14,
                                            fontWeight: FontWeight.w700,
                                            color: UIColors.brandA
                                        ),
                                      ),
                                    ],
                                  ),
                                ],

                              ),
                            ),
                          ),
                        );
                      },
                      separatorBuilder: (context, index) =>  Padding(
                        padding: const EdgeInsets.only(left: 32,right: 32),
                        child: Container(height: 1, color: UIColors.black10,),
                      ),
                    ),
                  ],
                ),
              ),
              replacement: Container(
                color: UIColors.white,
                width: double.infinity,
                child: Column(
                  children: [
                    SvgPicture.asset('resources/images/search.svg'),
                  ],
                ),
              ),
            ),
          ),


      ),

    );
  }

  @override
  DeliveryModel createViewModel() => getIt<DeliveryModel>();

}