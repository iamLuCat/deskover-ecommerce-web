import 'package:deskover_develop/src/config/assets/icon_assets.dart';
import 'package:deskover_develop/src/config/assets/image_asset.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/global_modules/widget/global_image.dart';
import 'package:deskover_develop/src/modules/notification/get_time.dart';
import 'package:deskover_develop/src/themes/space_values.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:get/get.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';
import 'package:intl/intl.dart';

import 'order_manager_model.dart';

class OrderManager extends StatefulWidget{
  final String orderCode;
  const OrderManager({Key? key, required this.orderCode}) : super(key: key);

  @override
  State<OrderManager> createState() => _OrderManager();

}
class _OrderManager extends ViewWidget<OrderManager,OrderManagerModel>{
  @override
  void initState() {
    super.initState();
    viewModel.orderCode.value = widget.orderCode;
    viewModel.loadOrder();
    viewModel.loadNotifyByOrderCode();
  }
  final formatCurrency = NumberFormat.currency(locale:"vi_VN", symbol: "đ");
  @override
  Widget build(BuildContext context) {

      return Scaffold(
        appBar: AppBar(
          title: Text('Quản lý đơn hàng'),
          backgroundColor: UIColors.white,
          centerTitle: true,
        ),
        body: Obx(()=>
           Column(
          children: [
            Container(
              margin: const EdgeInsets.only(top: 6),
              color: UIColors.white,
              child: Padding(
                padding: const EdgeInsets.fromLTRB(SpaceValues.space32, SpaceValues.space16, SpaceValues.space32, SpaceValues.space16),
                child: Column(
                  children: [
                    Visibility(
                      visible: viewModel.cancel.value,
                      child:  Visibility(
                        visible: viewModel.indexAction.value == 4,
                        child:SizedBox(
                          width: double.infinity,
                          child: Column(
                            children: [
                              AnimatedContainer(
                                duration: const Duration(seconds: 1),
                                decoration: BoxDecoration(
                                  shape: BoxShape.circle,
                                  border: Border.all(
                                    color: Colors.white,
                                    width: 2,
                                  ),
                                ),
                                child: CircleAvatar(
                                  backgroundColor: UIColors.black10,
                                  child: SvgPicture.asset(
                                    SvgImageAssets.icCacel,
                                    color: UIColors.black70,
                                  ),
                                ),
                              ),
                              Text('Chờ huỷ')
                            ],
                          ),
                        ),
                        replacement:  Obx(() => SizedBox(
                          width: double.infinity,
                          child: Column(
                            children: [
                              AnimatedContainer(
                                duration: const Duration(seconds: 1),
                                decoration: BoxDecoration(
                                  shape: BoxShape.circle,
                                  border: Border.all(
                                    color: UIColors.white,
                                    width: 2,
                                  ),
                                ),
                                child: CircleAvatar(
                                  backgroundColor: viewModel.indexAction.value >=5 ? UIColors.black10 : UIColors.white,
                                  child: SvgPicture.asset(
                                    SvgImageAssets.icInfomation00,
                                    color: UIColors.green,
                                  ),
                                ),
                              ),
                              Text('Đã huỷ')
                            ],
                          ),
                        )),
                      ),
                      replacement: Column(
                        children: [
                          Row(
                            mainAxisAlignment: MainAxisAlignment.center,
                            children: [
                              const SizedBox(width: SpaceValues.space24,),
                              Container(
                                decoration: BoxDecoration(
                                  shape: BoxShape.circle,
                                  border: Border.all(
                                    color: UIColors.brandA,
                                    width: 2,
                                  ),
                                ),
                                child: CircleAvatar(
                                  backgroundColor: UIColors.brandA,
                                  child: Image.asset(
                                    SvgImageAssets.icInfomation0,
                                    color: UIColors.white,
                                    width: 30,
                                  ),
                                ),
                              ),
                              Expanded(
                                child: viewModel.indexAction.value >=1 ? Divider(height: 50, thickness: 3, color: UIColors.brandA) : SizedBox(),
                              ),
                              Obx(() => AnimatedContainer(
                                duration: const Duration(seconds: 1),
                                decoration: BoxDecoration(
                                  shape: BoxShape.circle,
                                  border: Border.all(
                                    color: UIColors.brandA,
                                    width: 2,
                                  ),
                                ),
                                child: CircleAvatar(
                                  backgroundColor: viewModel.indexAction.value >= 1 ? UIColors.brandA : UIColors.white,
                                  child: SvgPicture.asset(
                                    SvgImageAssets.icInfomation00,
                                    color: viewModel.indexAction.value >= 1 ? UIColors.white : UIColors.black70,
                                  ),
                                ),
                              )),
                              Expanded(
                                child:viewModel.indexAction.value >=2 ? Divider(height: 50, thickness: 3, color: UIColors.brandA) : const SizedBox(),
                              ),
                              Obx(() => AnimatedContainer(
                                duration: const Duration(seconds: 1),
                                decoration: BoxDecoration(
                                  shape: BoxShape.circle,
                                  border: Border.all(
                                    color: UIColors.brandA,
                                    width: 2,
                                  ),
                                ),
                                child: CircleAvatar(
                                  backgroundColor: viewModel.indexAction.value >= 2 ? UIColors.brandA : UIColors.white,
                                  child: SvgPicture.asset(
                                    SvgImageAssets.icInfomation1,
                                    color: viewModel.indexAction.value >= 2 ? UIColors.white : UIColors.black70,
                                  ),
                                ),
                              )),
                              Expanded(
                                child:viewModel.indexAction.value >=3 ? Divider(height: 50, thickness: 3, color: UIColors.brandA) : const SizedBox(),
                              ),
                              Obx(() => AnimatedContainer(
                                duration: const Duration(seconds: 1),
                                decoration: BoxDecoration(
                                  shape: BoxShape.circle,
                                  border: Border.all(
                                    color: UIColors.brandA,
                                    width: 1,
                                  ),
                                ),
                                child: CircleAvatar(
                                  backgroundColor: viewModel.indexAction.value >= 3 ? UIColors.brandA : UIColors.white,
                                  child: SvgPicture.asset(
                                    SvgImageAssets.icInfomation2,
                                    width: 30,
                                    color: viewModel.indexAction.value >= 3 ? UIColors.white : UIColors.black,
                                  ),
                                ),
                              )),
                              const SizedBox(width: SpaceValues.space24,),
                            ],
                          ),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.start,
                            children: const [
                              SizedBox(width: 10,),
                              Text(
                                "Chờ xác nhận",
                                style: TextStyle(fontSize: 12),
                              ),
                              SizedBox(width: 15,),
                              Text(
                                "Đã xác nhận",
                                style: TextStyle(fontSize: 12),
                              ),
                              SizedBox(width: 20,),
                              Text(
                                "Đang giao",
                                style: TextStyle(fontSize: 12),
                              ),
                              SizedBox(width: 30,),
                              Text(
                                "Đã giao",
                                style: TextStyle(fontSize: 12),
                              ),
                            ],
                          ),
                        ],
                      ),

                    ),

                  ],
                ),
              ),
            ),
            viewModel.orderReponese.value != null ?
            Expanded(
              flex: 2,
              child:
              Obx(()=>   Visibility(
                visible: viewModel.orderReponese.value != null,
                child: SizedBox(
                  width: double.infinity,
                  height: MediaQuery.of(context).size.height,
                  child:Column(
                    children: [
                      Expanded(
                        child: ListView(
                          children: [
                            Container(
                              margin: EdgeInsets.only(top: 6),
                              color: UIColors.white,
                              child: SizedBox(
                                child: Padding(
                                  padding: const EdgeInsets.all(20),
                                  child: Column(
                                    children: [
                                      Row(
                                        mainAxisAlignment:
                                        MainAxisAlignment.spaceBetween,
                                        children: [
                                          const Text(
                                            'Mã vận đơn:',
                                            style: TextStyle(
                                                fontWeight: FontWeight.w600,
                                                color: UIColors.black,
                                                fontSize: 14),
                                          ),
                                          Text(
                                            viewModel.orderReponese.value?.orderCode ?? '',
                                            style: const TextStyle(
                                                fontWeight: FontWeight.bold,
                                                color: UIColors.black,
                                                fontSize: 14),
                                          ),
                                        ],
                                      ),
                                      const SizedBox(height: 24,),
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
                                                  const SizedBox(height: 4,),
                                                  Text(
                                                    viewModel.orderReponese.value?.orderDetail?.address ?? '',
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
                                      Row(
                                        mainAxisAlignment: MainAxisAlignment.center,
                                        children:  [
                                          const Icon(
                                            Icons.local_offer_outlined,
                                            color: UIColors.error80,
                                          ),
                                          const SizedBox(
                                            width: SpaceValues.space4,
                                          ),
                                          Text(
                                            formatCurrency.format(viewModel.orderReponese.value?.unitPrice),
                                            style: const TextStyle(
                                                fontWeight: FontWeight.w700,
                                                color: UIColors.black,
                                                fontSize: 14),
                                          ),
                                          const SizedBox(
                                            width: SpaceValues.space24,
                                          ),
                                          const Icon(
                                            Icons.access_time,
                                            color: UIColors.brandA,
                                          ),
                                          const SizedBox(
                                            width: SpaceValues.space4,
                                          ),
                                          Text(
                                            viewModel.orderReponese.value?.createdAt?.substring(0,10) ?? '',
                                            style: const TextStyle(
                                                fontWeight: FontWeight.bold,
                                                color: UIColors.black,
                                                fontSize: 14),
                                          ),
                                        ],
                                      ),
                                      const SizedBox(
                                        height: SpaceValues.space16,
                                      ),
                                      ListView.separated(
                                        shrinkWrap: true,
                                        physics: const NeverScrollableScrollPhysics(),
                                        itemCount: viewModel.orderReponese.value?.products?.length ?? 0,
                                        itemBuilder: (BuildContext context, int index) {
                                          return Card(
                                            elevation: 0.0,
                                            margin: EdgeInsets.zero,
                                            child: Row(
                                              children: [
                                                SizedBox(
                                                  width: MediaQuery.of(context).size.width*0.2,
                                                  height: MediaQuery.of(context).size.width*0.2,
                                                  child: Padding(
                                                    padding: const EdgeInsets.all(10),
                                                    child: GlobalImage(BaseApi.baseUrl_product+ (viewModel.orderReponese.value?.products?[index].product?.img ?? '')),
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
                                                          viewModel.orderReponese.value?.products?[index].product?.name ?? '',
                                                          textAlign: TextAlign.end,
                                                          style: const TextStyle(
                                                            fontWeight: FontWeight.w400,
                                                            fontSize: 12,
                                                          ),
                                                        ),
                                                        Text(
                                                          'x ${viewModel.orderReponese.value?.products?[index].quantity}',
                                                          textAlign: TextAlign.end,
                                                          style: const TextStyle(
                                                            fontWeight: FontWeight.w600,
                                                            fontSize: 12,
                                                          ),
                                                        ),
                                                        Text(
                                                          'Giá: ${formatCurrency.format(viewModel.orderReponese.value?.products?[index].price)}',
                                                          textAlign: TextAlign.end,
                                                          style: const TextStyle(
                                                            fontWeight: FontWeight.w600,
                                                            fontSize: 12,
                                                          ),
                                                        ),
                                                        (viewModel.orderReponese.value?.orderStatus?.code == 'HUY'
                                                            || viewModel.orderReponese.value?.orderStatus?.code == 'GH-TC' ) ?
                                                        ElevatedButton(
                                                            onPressed: (){
                                                              viewModel.addProductToCart(viewModel.orderReponese.value?.id ?? 1);
                                                            },
                                                            style: ElevatedButton.styleFrom(
                                                            ),
                                                            child: const Padding(
                                                              padding: EdgeInsets.only(left: 10,right: 10),
                                                              child: Text('Mua lại'),
                                                            )) : SizedBox.shrink()
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
                                    ],
                                  ),
                                ),
                              ),
                            ),
                            Container(
                              margin: const EdgeInsets.only(top: 6),
                              padding: const EdgeInsets.only(left: 16,right: 16,top: 16,bottom: 16),
                              color: UIColors.white,
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                children:   [
                                  const Text(
                                    'Phương thức vận chuyển:',
                                  style: TextStyle(
                                    fontSize: 14,
                                    fontWeight: FontWeight.w500
                                  ),),
                                  Text(viewModel.orderReponese.value?.shipping?.name_shipping ?? '')
                                ],
                              ),
                            ),
                            Container(
                              margin: const EdgeInsets.only(top: 6),
                              padding: const EdgeInsets.only(left: 16,right: 16,top: 16,bottom: 16),
                              color: UIColors.white,
                              child: Row(
                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                children:   [
                                  const Text(
                                    'Phương thức thanh toán:',
                                    style: TextStyle(
                                        fontSize: 14,
                                        fontWeight: FontWeight.w500
                                    ),),
                                  Text(viewModel.orderReponese.value?.payment?.name_payment ?? '')
                                ],
                              ),
                            ),
                            Container(
                              color: UIColors.white,
                              margin: const EdgeInsets.only(top: 6),
                              child: ListView.separated(
                                  padding: const EdgeInsets.all(8),
                                  shrinkWrap: true,
                                  physics: const NeverScrollableScrollPhysics(),
                                  separatorBuilder: (BuildContext context, int index) => const Divider(),
                                  itemCount: viewModel.notifyResponse.length,
                                  itemBuilder: (BuildContext context, int index) {
                                    return Column(
                                      children: [
                                        Row(
                                          children: [
                                            CircleAvatar(
                                              backgroundColor: UIColors.white,
                                              child: Icon(Icons.circle_rounded,color: (viewModel.notifyResponse.length -1) == index ? UIColors.brandA : UIColors.black10 ,),
                                            ),
                                            Expanded(child: Column(
                                              crossAxisAlignment: CrossAxisAlignment.start,
                                              children: [
                                                SizedBox(
                                                  child: Text(viewModel.notifyResponse[index].title ?? ''),
                                                ),
                                                Text(GetTime.parse(DateTime.parse(viewModel.notifyResponse[index].createdAt ?? '')
                                                )),
                                              ],
                                            ),)
                                          ],
                                        )

                                      ],
                                    );
                                  }
                              ),
                            ),
                          ],
                        ),
                      ),
                      viewModel.orderReponese.value?.orderStatus?.code == 'C-XN'
                      ? Container(
                        color: UIColors.white,
                        width: double.infinity,
                        padding: const EdgeInsets.only(top: 16,bottom: 16),
                        child: SizedBox(
                          child: Padding(
                            padding: const EdgeInsets.only(left: 16,right: 16),
                            child: ElevatedButton(
                              onPressed: (){
                                viewModel.cancelOrder('C-HUY');
                              },
                              child: const Padding(
                                padding: EdgeInsets.all(8.0),
                                child: Text('Huỷ đơn hàng'),
                              ),
                            ),
                          ),
                        ),
                      ) : viewModel.orderReponese.value?.orderStatus?.code == 'C-HUY'
                          ? Container(
                        color: UIColors.white,
                        width: double.infinity,
                        padding: const EdgeInsets.only(top: 16,bottom: 16),
                        child: SizedBox(
                          child: Padding(
                            padding: const EdgeInsets.only(left: 16,right: 16),
                            child: ElevatedButton(
                              onPressed: (){
                                viewModel.cancelOrder('CANCEL-C-HUY');
                              },
                              child: const Padding(
                                padding: EdgeInsets.all(8.0),
                                child: Text('Hoàn tác đơn hàng'),
                              ),
                            ),
                          ),
                        ),
                      ) : SizedBox.shrink()
                    ],
                  ),
                ),
                replacement: Text('...'),
              ),),

            )
            : Text('...'),

          ],
        ),)

      );
  }

  @override
  OrderManagerModel createViewModel() => getIt<OrderManagerModel>();
}