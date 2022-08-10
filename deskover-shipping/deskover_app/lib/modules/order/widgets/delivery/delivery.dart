import 'package:deskover_app/config/base_api.dart';
import 'package:deskover_app/config/injection_config.dart';
import 'package:deskover_app/global/global_image.dart';
import 'package:deskover_app/modules/main_page/home_page.dart';
import 'package:deskover_app/themes/space_values.dart';
import 'package:deskover_app/themes/ui_colors.dart';
import 'package:deskover_app/utils/widgets/view_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';

import 'delivery_model.dart';

class Delivery extends StatefulWidget{

  const Delivery({Key? key, }) : super(key: key);

  @override
  State<StatefulWidget> createState() => _Delivery();

}
class _Delivery extends ViewWidget<Delivery, DeliveryModel>{

  @override
  void initState() {
    super.initState();
    viewModel.getAllOrderDelivery();
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Obx(()=>
          Visibility(
            visible:  viewModel.dataOrderDelivery.value!=null,
            child: RefreshIndicator(
              onRefresh: () => viewModel.getAllOrderDelivery(),
              child: Container(
                color: UIColors.white,
                child:  Obx(
                  ()=> ListView(
                    children: [
                      ListView.separated(
                        shrinkWrap: true,
                        physics: const NeverScrollableScrollPhysics(),
                        itemCount: viewModel.dataOrderDelivery.value.length,
                        itemBuilder: (context, int index) {
                          return Padding(
                            padding: const EdgeInsets.only(left: 32,right: 32),
                            child: InkWell(
                              onTap: (){
                                print('Next screen ${index}');
                                Get.bottomSheet(OrderDiaLog(
                                   orderCode: viewModel.dataOrderDelivery[index].orderCode,
                                ));
                              },
                              child: Padding(
                                padding: const EdgeInsets.fromLTRB(24, 16, 24, 16),
                                child: Column(
                                  crossAxisAlignment: CrossAxisAlignment.start,
                                  children:  [
                                    Text(
                                      viewModel.dataOrderDelivery[index].orderCode ?? '',
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
                                            viewModel.dataOrderDelivery[index].fullName ??'',
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
                                            viewModel.dataOrderDelivery[index].address ??'',
                                            style: const TextStyle(
                                                fontSize: 12
                                            ),
                                          ),
                                        )
                                      ],
                                    ),
                                    SizedBox(height: 4,),
                                    Row(
                                      children: [
                                        SvgPicture.asset('resources/icons/event_note.svg',height: 16,),
                                        const SizedBox(width: 6,),
                                        Text(
                                          viewModel.dataOrderDelivery[index].createdAt!,
                                          style: const TextStyle(
                                              fontSize: 12
                                          ),
                                        ),
                                        const Expanded(child: SizedBox()),
                                        Text(
                                          viewModel.dataOrderDelivery[index].totalPrice!+' đ',
                                          style: TextStyle(
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
              ),
            ),
          ),
      ),
    );
  }

  @override
  DeliveryModel createViewModel() => getIt<DeliveryModel>();

}


class OrderDiaLog extends StatefulWidget{
  final String? orderCode;

  const OrderDiaLog({Key? key, required this.orderCode})
      : super(key: key);

  @override
  State<StatefulWidget> createState() => _OrderDiaLog();
}

class _OrderDiaLog extends ViewWidget<OrderDiaLog,DeliveryModel>{

  @override
  void initState() {
    super.initState();
    viewModel.orderDelivery(widget.orderCode!, 'LH-TC');
  }

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: MediaQuery.of(context).size.height *0.45,
      child: Scaffold(
          bottomSheet:  Obx(
                ()=>     SizedBox(
                  child: Padding(
                    padding: const EdgeInsets.fromLTRB(20, 20, 20, 0),
                    child: Column(
                      children: [
                        Expanded(
                          child: ListView(
                            children: [
                              Row(
                                mainAxisAlignment:
                                MainAxisAlignment.start,
                                children: [
                                  const Text(
                                    'Mã vận đơn:',
                                    style: TextStyle(
                                        fontWeight: FontWeight.w600,
                                        color: UIColors.black,
                                        fontSize: 14),
                                  ),
                                  const SizedBox(width: 4,),
                                  Text(
                                    viewModel.orderReponese.value?.orderCode ?? '',
                                    style: const TextStyle(
                                        fontWeight: FontWeight.bold,
                                        color: UIColors.black,
                                        fontSize: 14),
                                  ),
                                ],
                              ),
                              SizedBox(height: 12,),
                              Row(
                                children: [
                                  Container(
                                    decoration: BoxDecoration(
                                        color: UIColors.yellow40,
                                        borderRadius:
                                        BorderRadius.circular(10)),
                                    child: const Padding(
                                      padding: EdgeInsets.all(8.0),
                                      child: Icon(
                                        Icons.location_on_outlined,
                                        color: UIColors.yellow,
                                      ),
                                    ),
                                  ),
                                  const SizedBox(
                                    width: SpaceValues.space16,
                                  ),
                                  Expanded(
                                      child: Column(
                                        crossAxisAlignment:
                                        CrossAxisAlignment.start,
                                        mainAxisAlignment: MainAxisAlignment.start,
                                        children:  [
                                          const Text(
                                            'Địa chỉ giao hàng:',
                                            style: TextStyle(
                                                color: UIColors.black70,
                                                fontSize: 12,
                                                fontWeight: FontWeight.w700),
                                          ),
                                          SizedBox(height: 4,),
                                          Text(
                                            viewModel.orderReponese.value?.address ?? '',
                                            style: const TextStyle(
                                                color: UIColors.black,
                                                fontSize: 14,
                                                fontWeight: FontWeight.w600),
                                          )
                                        ],
                                      ))
                                ],
                              ),
                              const SizedBox(
                                height: SpaceValues.space16,
                              ),
                              const SizedBox(
                                height: SpaceValues.space16,
                              ),
                              ListView.separated(
                                shrinkWrap: true,
                                physics: const NeverScrollableScrollPhysics(),
                                itemCount: viewModel.orderReponese.value?.orderItem?.length ?? 0,
                                itemBuilder: (BuildContext context, int index) {
                                  return Card(
                                    elevation: 0.0,
                                    margin: EdgeInsets.zero,
                                    child: Row(
                                      children: [
                                        SizedBox(
                                          width: MediaQuery.of(context).size.width*0.2,
                                          child: Padding(
                                            padding: EdgeInsets.all(10),
                                            child: GlobalImage(BaseApi.baseUrl_product+ (viewModel.orderReponese.value?.orderItem?[index].img ?? '')),
                                          ),
                                        ),
                                        Expanded(
                                          child: Padding(
                                            padding: const EdgeInsets.all(20),
                                            child: Column(
                                              mainAxisAlignment:
                                              MainAxisAlignment.end,
                                              crossAxisAlignment: CrossAxisAlignment.end,
                                              children: [
                                                Text(
                                                  viewModel.orderReponese.value?.orderItem?[index].name ?? '',
                                                  textAlign: TextAlign.end,
                                                  style: const TextStyle(
                                                    fontWeight: FontWeight.w400,
                                                    fontSize: 12,
                                                  ),
                                                ),
                                                Text(
                                                  'x ${viewModel.orderReponese.value?.orderItem?[index].quantity}',
                                                  textAlign: TextAlign.end,
                                                  style: const TextStyle(
                                                    fontWeight: FontWeight.w600,
                                                    fontSize: 12,
                                                  ),
                                                ),
                                                Text(
                                                  'Giá: ${viewModel.orderReponese.value?.orderItem?[index].price}',
                                                  textAlign: TextAlign.end,
                                                  style: const TextStyle(
                                                    fontWeight: FontWeight.w600,
                                                    fontSize: 12,
                                                  ),
                                                ),
                                              ],
                                            ),
                                          ),
                                        ),

                                      ],
                                    ),

                                  );
                                },
                                separatorBuilder: (context, index) => const SizedBox(height: 10,),

                              ),
                              const SizedBox(height: SpaceValues.space12,),

                              const Padding(
                                padding: EdgeInsets.only(top: 8),
                                child: Divider(color: UIColors.black,thickness: 0.5,endIndent: 20,indent: 20,height: 10),
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.end,
                                children:  [
                                  const Text(
                                    'Tổng tiền: ',
                                    style: TextStyle(
                                        fontWeight: FontWeight.bold,
                                        color: UIColors.black,
                                        fontSize: 14),
                                  ),
                                  const SizedBox(
                                    width: SpaceValues.space4,
                                  ),
                                  Text(
                                    viewModel.orderReponese.value?.totalPrice ?? '',
                                    style: const TextStyle(
                                        fontWeight: FontWeight.w700,
                                        color: UIColors.black,
                                        fontSize: 14),
                                  ),
                                  const Text(
                                    'đ',
                                    style: TextStyle(
                                        fontWeight: FontWeight.w500,
                                        color: UIColors.black,
                                        fontSize: 14),
                                  ),
                                  const SizedBox(
                                    width: SpaceValues.space24,
                                  ),

                                ],
                              ),

                            ],
                          ),
                        ),
                        Padding(
                          padding: const EdgeInsets.fromLTRB(16, 10, 16, 10),
                          child: Row(
                            children: [
                              Expanded(
                                child: ElevatedButton(
                                    style: ElevatedButton.styleFrom(
                                      shape: RoundedRectangleBorder(
                                        borderRadius:
                                        BorderRadius.circular(5),
                                      ),
                                      primary: UIColors.green, // background
                                      onPrimary: Colors.white,
                                      // foreground
                                    ),
                                    onPressed: () async {
                                      await viewModel.PickupOrder(viewModel.orderReponese.value?.orderCode ?? '','DG','','Cập nhập thành công');
                                      await viewModel.getAllOrderDelivery();
                                      Get.offAll(()=> const HomePage(indexTap: 2,));
                                      // await viewModel.getAllOrderDelivery();
                                      },
                                    child: const Text(
                                      'Giao hàng',
                                      style: TextStyle(
                                          color: UIColors.white
                                      ),
                                    )),
                              ),
                            ],
                          ),
                        )
                      ],
                    ),
                  ),
                ),
          )
      ),
    );
  }

  @override
  DeliveryModel createViewModel() => getIt<DeliveryModel>();

}