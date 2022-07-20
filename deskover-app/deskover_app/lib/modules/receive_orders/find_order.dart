import 'dart:ui';
import 'package:deskover_app/config/base_api.dart';
import 'package:deskover_app/config/injection_config.dart';
import 'package:deskover_app/global/global_image.dart';
import 'package:deskover_app/themes/space_values.dart';
import 'package:deskover_app/utils/widgets/view_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import '../../global/global_qr_code.dart';
import '../../themes/ui_colors.dart';
import 'order_model.dart';

class ReceiveOrders extends StatefulWidget {
  const ReceiveOrders({Key? key}) : super(key: key);

  @override
  State<ReceiveOrders> createState() => _ReceiveOrders();
}

class _ReceiveOrders extends ViewWidget<ReceiveOrders, OrderModel> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        automaticallyImplyLeading: false,
        title: Text('Tìm kiếm đơn hàng'),
        backgroundColor: UIColors.white,
      ),
      body: Column(
        children: [
          Container(
            margin: EdgeInsets.only(top: 2),
            padding: EdgeInsets.only(top: 8, bottom: 8),
            color: UIColors.white,
            child: Padding(
              padding: const EdgeInsets.symmetric(
                  horizontal: SpaceValues.space16,
                  vertical: SpaceValues.space8),
              child: Row(
                crossAxisAlignment: CrossAxisAlignment.start,
                children: [
                  Expanded(
                    child: Obx(() {
                      return Form(
                          key: viewModel.formKey,
                          child: TextFormField(
                            controller: viewModel.inputOrderCode,
                            keyboardType: TextInputType.text,
                            decoration: InputDecoration(
                              prefixIconConstraints: const BoxConstraints(
                                  minHeight: SpaceValues.space24,
                                  maxHeight: SpaceValues.space24,
                                  minWidth: 0),
                              // prefixIcon: Padding(
                              //   padding: const EdgeInsets.symmetric(
                              //       horizontal: SpaceValues.space8),
                              //   child: SvgPicture.asset(
                              //     'resources/icons/loading.png',
                              //     color: UIColors.black70,
                              //   ),
                              // ),
                              hintText: 'Nhập mã vận đơn',
                              errorText: viewModel.validBarcode.value,
                            ),
                          ));
                    }),
                  ),
                  const SizedBox(
                    width: SpaceValues.space12,
                  ),
                  ElevatedButton(
                    style: ElevatedButton.styleFrom(
                        shape: RoundedRectangleBorder(
                          borderRadius: BorderRadius.circular(20),
                            side: BorderSide(color: Colors.black)
                        ),
                        primary: Colors.white,
                        tapTargetSize: MaterialTapTargetSize.shrinkWrap,
                        // padding: const EdgeInsets.symmetric(vertical: 9.5),
                        minimumSize: Size.zero,
                        fixedSize: const Size(56, 42)),
                    onPressed: () => {viewModel.onSearch(viewModel.inputOrderCode.text,'C-LH')},
                    // viewModel.btnQRScaner,
                    child: const Center(
                        child: Icon(Icons.search,
                            color: UIColors.black, size: 25)),
                  ),
                  const SizedBox(
                    width: SpaceValues.space8,
                  ),
                  ElevatedButton(
                    style: ElevatedButton.styleFrom(
                        shape: RoundedRectangleBorder(
                            borderRadius: BorderRadius.circular(20),
                        ),
                        primary: UIColors.black50,
                        tapTargetSize: MaterialTapTargetSize.shrinkWrap,
                        // padding: const EdgeInsets.symmetric(vertical: 9.5),
                        minimumSize: Size.zero,
                        fixedSize: const Size(56, 42)),
                    onPressed: () => {btnQRScaner()},
                    // viewModel.btnQRScaner,
                    child: Center(
                        child: Icon(Icons.qr_code,
                            color: UIColors.white, size: 25)),
                  ),
                ],
              ),
            ),
          ),
          Expanded(
            child: Container(
              margin: EdgeInsets.only(top: 4),
              color: UIColors.white,
              width: double.infinity,
              height: MediaQuery.of(context).size.height,
              child: Obx(()=> Visibility(
                  visible: viewModel.orderReponese.value != null,
                  child: Padding(
                    padding: const EdgeInsets.all(16),
                    child: Column(
                      children: [
                         const Text(
                            'Tìm kiếm thành công',
                          style: TextStyle(
                            fontStyle: FontStyle.italic,
                            fontWeight: FontWeight.w400,
                            fontSize: 14
                          ),
                        ),
                        const SizedBox(height: 12,),
                        Expanded(
                          child: ListView(
                            children: [
                              Card(
                                elevation: 0.0,
                                margin:
                                const EdgeInsets.only(bottom: SpaceValues.space16),
                                shape: RoundedRectangleBorder(
                                    side: const BorderSide(
                                      style: BorderStyle.solid,
                                      color: UIColors.black10,
                                      width: 1
                                    ),
                                    borderRadius: BorderRadius.circular(10),
                                ),
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
                                        Row(
                                          children: [
                                            Expanded(
                                                child: Column(
                                                  crossAxisAlignment: CrossAxisAlignment.start,
                                                  children: [
                                                    const SizedBox(
                                                      height: SpaceValues.space12,
                                                    ),
                                                    Row(
                                                      mainAxisAlignment: MainAxisAlignment.start,
                                                      children: [
                                                        Container(
                                                          decoration: BoxDecoration(
                                                              color: UIColors.yellow40,
                                                              borderRadius:
                                                              BorderRadius.circular(10)),
                                                          child: const Padding(
                                                            padding: EdgeInsets.all(8.0),
                                                            child: Icon(
                                                              Icons.real_estate_agent_outlined,
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
                                                              children: const [
                                                                Text(
                                                                  'Địa chỉ kho hàng:',
                                                                  style: TextStyle(
                                                                      color: UIColors.black70,
                                                                      fontSize: 12,
                                                                      fontWeight: FontWeight.bold),
                                                                ),
                                                                SizedBox(height: 4,),
                                                                Text(
                                                                  'Quận 9, TP Hồ Chí Minh',
                                                                  style: TextStyle(
                                                                      color: UIColors.black,
                                                                      fontSize: 14,
                                                                      fontWeight: FontWeight.w600),
                                                                )
                                                              ],
                                                            ))
                                                      ],
                                                    ),
                                                    Container(
                                                      margin: const EdgeInsets.only(left: 12),
                                                      child: Column(
                                                        crossAxisAlignment: CrossAxisAlignment.start,
                                                        children: const [
                                                          Icon(
                                                            Icons.keyboard_double_arrow_down,
                                                            size: 12,
                                                            color: UIColors.black70,
                                                          ),
                                                          Icon(
                                                            Icons.keyboard_double_arrow_down,
                                                            size: 12,
                                                            color: UIColors.black70,
                                                          ),
                                                          Icon(
                                                            Icons.keyboard_double_arrow_down,
                                                            size: 12,
                                                            color: UIColors.black70,
                                                          )
                                                        ],
                                                      ),
                                                    ),
                                                  ],
                                                )),
                                            SizedBox(
                                                width: MediaQuery.of(context).size.height *0.10,
                                                child: GlobalImage(BaseApi.baseUrl+'/app/images/qr_code_scanner.png'))
                                          ],
                                        ),
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
                                              viewModel.orderReponese.value?.totalPrice ?? '',
                                              style: const TextStyle(
                                                  fontWeight: FontWeight.w700,
                                                  color: UIColors.black,
                                                  fontSize: 14),
                                            ),
                                            const Text(
                                              'đ',
                                              style: TextStyle(
                                                  fontWeight: FontWeight.bold,
                                                  color: UIColors.black,
                                                  fontSize: 14),
                                            ),
                                            const SizedBox(
                                              width: SpaceValues.space24,
                                            ),
                                            const Icon(
                                              Icons.access_time,
                                              color: UIColors.appBar,
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
                                                      child: GlobalImage(viewModel.orderReponese.value?.orderItem?[index].img ?? ''),
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
                                        Center(
                                          child: Padding(
                                            padding: const EdgeInsets.only(
                                                top: SpaceValues.space32,
                                                left: SpaceValues.space32,
                                                right: SpaceValues.space32),
                                            child: SizedBox(
                                              // width: width *0.4,
                                              width: double.infinity,
                                              child: ElevatedButton(
                                                style: ElevatedButton.styleFrom(
                                                  shape: RoundedRectangleBorder(
                                                    borderRadius:
                                                    BorderRadius.circular(10),
                                                  ),
                                                  primary: UIColors.green, // background
                                                  onPrimary: Colors.white,
                                                  // foreground
                                                ),
                                                onPressed: () async{
                                                  await viewModel.PickupOrder(viewModel.orderReponese.value?.orderCode ?? '','LH-TC');
                                                },
                                                child: Text('TIẾP NHẬN',
                                                    style: TextStyle(

                                                        color: UIColors.white,
                                                        fontWeight: FontWeight.w600)),
                                              ),
                                            ),
                                          ),
                                        ),

                                      ],
                                    ),
                                  ),
                                ),
                              ),
                            ],
                          ),
                        ),
                      ],
                    ),
                  ),
                replacement: Container(
                  color: UIColors.white,
                  child: SvgPicture.asset('resources/images/search.svg'),
                ),
                //   replacement: const LoadingDialog(
                //   backgroundColor: Colors.transparent,
                //   message: 'Đang tìm kiếm sản phẩm',
                //   elevation: 0,
                // ),
              )),
            ),
          ),
        ],
      ),
    );
  }

  void btnQRScaner() {
    Get.to(GlobalQRScannerScreen(
      onDetect: (barcode, args) {
        String rowcode = barcode.rawValue ?? '';
        print(rowcode);
        // String? result;

        // if (result?.isEmpty ?? true) {
        //   loading(() async => throw 'Vui lòng quét Barcode hoặc QRcode sản phẩm của Mutosi');
        // }
        // inputBarcode.text = result ?? '';
        Get.back();
      },
    ));
  }

  @override
  OrderModel createViewModel() => getIt<OrderModel>();
}
