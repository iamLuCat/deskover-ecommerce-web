import 'dart:ui';

import 'package:deskover_develop/src/apis/shipping_payment_status/response/shipping_payment_status.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/cart/address_cart.dart';
import 'package:deskover_develop/src/modules/cart/cart_model.dart';
import 'package:deskover_develop/src/modules/global_modules/widget/global_image.dart';
import 'package:deskover_develop/src/utils/AppUtils.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';
import 'package:intl/intl.dart';

import '../../config/assets/image_asset.dart';
import '../../themes/space_values.dart';
import '../../themes/ui_colors.dart';

class CreateChangePointCart extends StatefulWidget{
  @override
  State<StatefulWidget> createState() => _CreateChangePointCart();

}
class _CreateChangePointCart extends ViewWidget<CreateChangePointCart,CartModel>{

  final formatCurrency = NumberFormat.currency(locale:"vi_VN", symbol: "đ");

  bool isChecked = false;
  @override
  void initState() {
    super.initState();
    viewModel.loadAddress();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
       appBar: AppBar(
         title: Text('Giỏ hàng'),
         backgroundColor: UIColors.white  ,
       ),
      body: RefreshIndicator(
        onRefresh: ()=> viewModel.refresh(),
        child: Container(
          margin: const EdgeInsets.only(top: 1),
          color: UIColors.white,
          child: Column(
            children: [
              Obx(
                  ()=>Expanded(
                        child: Padding(
                          padding: const EdgeInsets.fromLTRB(16, 16, 16, 0),
                          child: ListView(
                            children: [
                              ListView.separated(
                                shrinkWrap: true,
                                physics: const NeverScrollableScrollPhysics(),
                                itemCount: viewModel.dataCartResponse.length,
                                itemBuilder: (context, int index) {
                                  return Card(
                                    elevation: 0.0,
                                    margin: EdgeInsets.zero,
                                    child: Row(
                                      crossAxisAlignment: CrossAxisAlignment.center,
                                      children: [
                                        SizedBox(
                                          width: MediaQuery.of(context).size.width*0.3,
                                          child: Padding(
                                            padding: EdgeInsets.all(10),
                                            child: GlobalImage(BaseApi.baseUrl_product+'${viewModel.dataCartResponse[index].product?.img}') ,
                                          ),
                                        ),
                                        Expanded(
                                          child: Padding(
                                            padding: const EdgeInsets.fromLTRB(10,10,10,10),
                                            child: Column(
                                              crossAxisAlignment: CrossAxisAlignment.start,
                                              children:  [
                                                Row(
                                                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                                  children: [
                                                     Expanded(
                                                       child: Text(
                                                        viewModel.dataCartResponse[index].product?.name ?? '',
                                                        style: const TextStyle(
                                                            fontSize: 12,
                                                            fontWeight: FontWeight.w700
                                                        ),
                                                    ),
                                                     ),
                                                    const SizedBox(width: 26,),
                                                    SizedBox(
                                                      width: 30,
                                                      height: 24,
                                                      child: ElevatedButton(
                                                        style: ElevatedButton.styleFrom(
                                                          onSurface: Colors.red,
                                                          minimumSize: Size.zero, // Set this
                                                          padding: EdgeInsets.zero, // and this
                                                          primary: UIColors.white,
                                                          elevation: 0.0,
                                                        ),
                                                        onPressed: () async => await viewModel.btnDelete( viewModel.dataCartResponse[index].product?.id ?? 0),
                                                        child: SvgPicture.asset(SvgImageAssets.trash,height: 20),
                                                      ),
                                                    ),
                                                  ],
                                                ),
                                                SizedBox(height: SpaceValues.space16,),
                                                Visibility(
                                                  visible: (viewModel.dataCartResponse[index].product?.discount?.percent ?? 0) > 0,
                                                  child: Column(
                                                    crossAxisAlignment: CrossAxisAlignment.start,
                                                    children: [
                                                      Text(
                                                        formatCurrency.format(viewModel.dataCartResponse[index].product?.price),
                                                        maxLines: 1,
                                                        overflow: TextOverflow.ellipsis,
                                                        style: const TextStyle(fontSize: 12, color: UIColors.title, decoration: TextDecoration.lineThrough),
                                                      ),
                                                      const SizedBox(height: 6,),
                                                      Row(
                                                        children: [
                                                          const Text(
                                                            'Giá:',
                                                            style: TextStyle(
                                                              fontSize: 12,
                                                              fontWeight: FontWeight.w400,

                                                            ),
                                                          ),
                                                          const SizedBox(width: 6,),
                                                          Text(
                                                            formatCurrency.format(
                                                                (viewModel.dataCartResponse[index].product?.price ?? 0)
                                                                    -((viewModel.dataCartResponse[index].product?.price ?? 0) * (viewModel.dataCartResponse[index].product?.discount?.percent ?? 0)/100)
                                                            ),
                                                            style: const TextStyle(
                                                              fontSize: 12,
                                                              fontWeight: FontWeight.w700,

                                                            ),
                                                          ),
                                                        ],
                                                      ),
                                                    ],
                                                  ),
                                                  replacement:   Row(
                                                    children: [
                                                      const Text(
                                                        'Giá:',
                                                        style: TextStyle(
                                                          fontSize: 12,
                                                          fontWeight: FontWeight.w400,

                                                        ),
                                                      ),
                                                      const SizedBox(width: 6,),
                                                      Text(
                                                        formatCurrency.format(
                                                            (viewModel.dataCartResponse[index].product?.price ?? 0)
                                                        ),
                                                        style: TextStyle(
                                                          fontSize: 12,
                                                          fontWeight: FontWeight.w700,

                                                        ),
                                                      ),
                                                    ],
                                                  ),
                                                ),
                                                //thêm loại thẻ
                                                const SizedBox(height: SpaceValues.space4,),
                                                //kun vận động
                                                Row(
                                                  children: [
                                                    const Text(
                                                      'Số lượng:',
                                                      style: TextStyle(
                                                        fontSize: 12,
                                                        fontWeight: FontWeight.w400,

                                                      ),
                                                    ),
                                                    const Expanded(child: SizedBox(),),

                                                    SizedBox(
                                                      height: 25 ,
                                                      width: 25,
                                                      child: ElevatedButton(
                                                        style: ElevatedButton.styleFrom(
                                                            primary: UIColors.red,
                                                            minimumSize: Size.zero, // Set this
                                                            padding: EdgeInsets.zero, // and this
                                                            elevation: 0.0,
                                                            shadowColor: Colors.transparent,
                                                            shape:   RoundedRectangleBorder(
                                                                borderRadius: BorderRadius.circular(20),
                                                                side: BorderSide(color: UIColors.black10,width: 1)
                                                            )
                                                        ),
                                                        onPressed: viewModel.dataCartResponse[index].quantity! > 1 ? () async {
                                                          await viewModel.btnMinusCart( viewModel.dataCartResponse[index].product?.id ?? 0);
                                                        }: null,
                                                        child: Center(child: SvgPicture.asset(SvgImageAssets.minus,color: UIColors.white,height: 16,)),
                                                      ),
                                                    ),
                                                    SizedBox(width: SpaceValues.space8,),
                                                    Obx(
                                                         ()=>SizedBox(
                                                              height: 25,
                                                              child:  Padding(
                                                                padding: const EdgeInsets.only(left: 15,right: 15),
                                                                child: Center(child: Text(viewModel.dataCartResponse[index].quantity.toString(),style: TextStyle(fontSize:12 ),)),
                                                              ),
                                                            ),),

                                                    const SizedBox(width: SpaceValues.space8,),
                                                    SizedBox(
                                                      height: 25 ,
                                                      width: 25,
                                                      child: ElevatedButton(
                                                        style: ElevatedButton.styleFrom(
                                                            minimumSize: Size.zero, // Set this
                                                            padding: EdgeInsets.zero, // and this
                                                            elevation: 0.0,
                                                            shadowColor: Colors.transparent,
                                                            shape:   RoundedRectangleBorder(
                                                                borderRadius: BorderRadius.circular(20),
                                                                side: BorderSide(color: UIColors.black10,width: 1)
                                                            )
                                                        ),
                                                        onPressed: viewModel.dataCartResponse[index].quantity! <10 ? () async {
                                                          await viewModel.btnAddToCart( viewModel.dataCartResponse[index].product?.id ?? 0);
                                                        }:null,
                                                        child: Center(child: SvgPicture.asset(SvgImageAssets.plus,color: UIColors.white,height: 16,)),
                                                      ),
                                                    ),
                                                  ],
                                                ),
                                              ],
                                            ),
                                          ),
                                        )
                                      ],
                                    ),
                                  );
                                },
                                separatorBuilder: (context, index) => const SizedBox(height: 10,),
                              ),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                children: [
                                  const Text(
                                    'Địa chỉ nhận hàng',
                                    style: TextStyle(
                                        fontWeight: FontWeight.w600,
                                        fontSize: 14
                                    ),
                                  ),
                                  TextButton(
                                      style: TextButton.styleFrom(
                                        onSurface: UIColors.brandA,
                                        primary: UIColors.brandA,
                                      ) ,
                                      onPressed: (){
                                          Get.to(AddressCart());
                                      }
                                      , child: Row(
                                    children: [
                                      SvgPicture.asset(SvgImageAssets.driveFileRename,color: UIColors.brandA,height: 20,),
                                      Text(' Thay đổi',style: TextStyle(color: UIColors.brandA,fontSize: 12,fontWeight: FontWeight.w400),)
                                    ],

                                  ))
                                ],
                              ),
                              Obx(
                               ()=>  Card(
                                        elevation: 0.0,
                                        margin: EdgeInsets.zero,
                                        child: Padding(
                                          padding: const EdgeInsets.all(20),
                                          child: Column(

                                            children: [
                                              Row(
                                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                                children: [
                                                  Text(
                                                    'Người nhận',
                                                    style: TextStyle(
                                                        fontWeight: FontWeight.w500,
                                                        fontSize: 12
                                                    ),
                                                  ),
                                                  Text(
                                                    viewModel.address.value?.fullname ?? '',
                                                    style: TextStyle(
                                                        fontWeight: FontWeight.w400,
                                                        fontSize: 12
                                                    ),
                                                  ),
                                                ],
                                              ),
                                              SizedBox(height: 20,),
                                              Row(
                                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                                children:  [
                                                  Text(
                                                    'Số điện thoại',
                                                    style: TextStyle(
                                                        fontWeight: FontWeight.w500,
                                                        fontSize: 12
                                                    ),
                                                  ),
                                                  Text(
                                                    viewModel.address.value?.tel ?? '',
                                                    style: TextStyle(
                                                        fontWeight: FontWeight.w400,
                                                        fontSize: 12
                                                    ),
                                                  ),
                                                ],
                                              ),
                                              SizedBox(height: 20,),
                                              Row(
                                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                                crossAxisAlignment: CrossAxisAlignment.start,
                                                children:  [
                                                  Text(
                                                    'Địa chỉ',
                                                    style: TextStyle(
                                                        fontWeight: FontWeight.w500,
                                                        fontSize: 12
                                                    ),
                                                  ),
                                                  SizedBox(width: MediaQuery.of(context).size.width*0.2),
                                                  Expanded(
                                                    child: Text(
                                                      viewModel.address.value?.address ?? '',
                                                      // maxLines: 3,
                                                      // overflow: TextOverflow.fade,
                                                      textAlign: TextAlign.end,
                                                      style: TextStyle(
                                                        fontWeight: FontWeight.w400,
                                                        fontSize: 12,
                                                      ),
                                                    ),
                                                  ),
                                                ],
                                              ),
                                            ],
                                          ),
                                        ),
                                      ),
                              ),
                              SizedBox(height: 16,),
                              const Align(
                                alignment: Alignment.topLeft,
                                child: Text(
                                  'Vui lòng chọn phương thức vận chuyển:',
                                  style: TextStyle(
                                    fontSize: 14,
                                    fontWeight: FontWeight.w700,
                                  ),
                                  textAlign: TextAlign.center,
                                ),
                              ),
                              const SizedBox(height: SpaceValues.space16,),
                              Obx(
                                    ()=>Visibility(
                                  visible: true,
                                  child: ConstrainedBox(
                                    constraints: BoxConstraints(maxHeight: MediaQuery.of(Get.context!).size.height * .5),
                                    child: SingleChildScrollView(
                                      child: Column(
                                        crossAxisAlignment: CrossAxisAlignment.start,
                                        children: [
                                          for (int i = 0;
                                          i < viewModel.dataShipping.length;
                                          i++)
                                            ListTile(
                                              visualDensity: const VisualDensity(
                                                  horizontal: 0, vertical: -4),
                                              contentPadding: EdgeInsets.zero,
                                              horizontalTitleGap: 0,
                                              tileColor: Colors.transparent,
                                              title: Text(
                                                viewModel.dataShipping[i].name_shipping ?? '' ,
                                                style: TextStyle(
                                                    fontWeight: FontWeight.w500,
                                                    fontSize: 12
                                                ),
                                              ),
                                              leading: Radio(
                                                value: viewModel.dataShipping[i],
                                                groupValue: viewModel.shipping.value,
                                                // activeColor: Color(0xFF6200EE),
                                                onChanged: i < 1 ? (value) {
                                                  viewModel.shipping.value = value as Shipping? ;
                                                  viewModel.checkShipping();
                                                  viewModel.feeValue.value = 0;

                                                }: (value) {
                                                    viewModel.shipping.value = value as Shipping? ;
                                                    viewModel.checkShipping();
                                                    viewModel.feeValue.value = 0;
                                                    viewModel.getFee();
                                                }
                                              ),
                                            ),
                                          viewModel.feeValue.value > 0 ?
                                          Row(
                                            children: [
                                              const Text('Phí vận chuyển của bạn là: '),
                                              Text(
                                                  formatCurrency.format(viewModel.feeValue.value),
                                                style: TextStyle(
                                                  fontSize: 14,
                                                  fontWeight: FontWeight.w600
                                                ),
                                              )
                                            ],
                                          )
                                           : SizedBox()
                                        ],
                                      ),
                                    ),
                                  ),
                                ),
                              ),

                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                children: [
                                  const Text(
                                    'Vui lòng chọn phương thức thanh toán',
                                    style: TextStyle(
                                        fontWeight: FontWeight.w600,
                                        fontSize: 14
                                    ),
                                  ),
                                  TextButton(
                                      style: TextButton.styleFrom(
                                        onSurface: UIColors.brandA,
                                        primary: UIColors.brandA,
                                      ) ,
                                      onPressed: (){
                                        viewModel.payment.value == null;
                                        Get.bottomSheet(PaymentMethod(viewModel: viewModel,));
                                      }
                                      , child: Row(
                                    children: [
                                      SvgPicture.asset(SvgImageAssets.driveFileRename,color: UIColors.brandA,height: 20,),
                                      Text('Chọn',style: TextStyle(color: UIColors.brandA,fontSize: 12,fontWeight: FontWeight.w400),)
                                    ],

                                  ))
                                ],
                              ),
                              // IconAssets.actionStore
                              const SizedBox(height: SpaceValues.space12,),
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                children: const [
                                  Text(
                                    'Ghi chú',
                                    style: TextStyle(
                                        fontWeight: FontWeight.bold,
                                        fontSize: 14
                                    ),
                                  ),

                                ],
                              ),
                              const SizedBox(height: SpaceValues.space12,),
                              TextField(
                                controller: viewModel.inputNote,
                                decoration: InputDecoration(
                                    hintText: 'Nhập ghi chú (nếu có)',
                                    hintStyle: TextStyle(
                                      fontStyle: FontStyle.italic,
                                    ),
                                    floatingLabelAlignment: FloatingLabelAlignment.center
                                ),
                                minLines: 3,
                                maxLines: 3,
                              ),
                              const SizedBox(height: SpaceValues.space48,),
                            ],
                          ),
                        ),
                      ),),
                Row(
                  children: [
                    Obx(()=>Expanded(
                      child: Container(
                        padding: const EdgeInsets.fromLTRB(16, 10, 16, 10),
                        decoration: const BoxDecoration(
                          color: UIColors.white,
                          boxShadow: [
                            BoxShadow(
                              color: Color.fromARGB(255, 218, 218, 218),
                              blurRadius: 15, // soften the shadow
                              spreadRadius: -10, //extend the shadow
                              offset: Offset(
                                0.0, // Move to right 10  horizontally
                                -16.0, // Move to bottom 10 Vertically
                              ),
                            ),
                          ],
                        ),
                        child: Column(
                          children: [
                            const SizedBox(height: 6,),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                const Text(
                                  'Tổng tiền:',
                                  style: TextStyle(
                                      fontSize: 14
                                  ),
                                ),
                                Text(
                                  formatCurrency.format(viewModel.totalPriceOrigin.value),
                                  style: const TextStyle(fontWeight: FontWeight.w700,
                                      fontSize: 14
                                  ),
                                ),
                              ],
                            ),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                const Text(
                                  'Tổng tiền giảm:',
                                  style: TextStyle(
                                      fontSize: 14
                                  ),
                                ),
                                Text(
                                  formatCurrency.format(viewModel.totalPercent.value),
                                  style: const TextStyle(
                                      fontWeight: FontWeight.w700,
                                      fontSize: 14
                                  ),
                                ),
                              ],
                            ),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                const Text(
                                  'Phí vận chuyển:',
                                  style: TextStyle(
                                      fontSize: 14
                                  ),
                                ),
                                Text(
                                  formatCurrency.format(viewModel.feeValue.value),
                                  style: const TextStyle(
                                      fontWeight: FontWeight.w700,
                                      fontSize: 14
                                  ),
                                ),
                              ],
                            ),
                            const Divider(color: UIColors.black),
                            Row(
                              mainAxisAlignment: MainAxisAlignment.spaceBetween,
                              children: [
                                const Text(
                                  'Tổng tiền thanh toán:',
                                  style: TextStyle(

                                      fontSize: 14
                                  ),
                                ),
                                Text(
                                  formatCurrency.format(viewModel.totalPriceOrigin.value-viewModel.totalPercent.value-viewModel.feeValue.value),
                                  style: const TextStyle(
                                      fontWeight: FontWeight.w700,
                                      fontSize: 14
                                  ),
                                ),
                              ],
                            ),
                            const SizedBox(height: 8,),
                            SizedBox(
                              width: double.infinity,
                              child: ElevatedButton(
                                  style: ElevatedButton.styleFrom(

                                    // elevation: 0.0,
                                      shape:  RoundedRectangleBorder(
                                        borderRadius: BorderRadius.circular(5),

                                      )
                                  ) ,
                                  onPressed: () async {
                                    await viewModel.btnConfirmOrder();
                                  },
                                  child: const Padding(
                                    padding: EdgeInsets.all(6.0),
                                    child: Text(
                                      'Xác nhận mua hàng',
                                      style: TextStyle(

                                      ),
                                    ),
                                  )),
                            ),
                          ],
                        ),
                      ),
                    ))
                  ],
                )

              //button add product

            ],
          ),
        ),
      ),
    );

  }

  @override
  CartModel createViewModel() => getIt<CartModel>();

}

class PaymentMethod extends StatelessWidget{
  final CartModel viewModel;

  const PaymentMethod({Key? key, required this.viewModel})
      : super(key: key);

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      height: MediaQuery.of(context).size.height *0.25,
      child: Scaffold(
        bottomSheet: Padding(
          padding: const EdgeInsets.all(16),
          child: Column(
            mainAxisAlignment: MainAxisAlignment.start,
            children: [
              const Align(
                alignment: Alignment.topLeft,
                child: Text(
                  'Chọn phương thức thanh toán:',
                  style: TextStyle(
                    fontSize: 14,
                    fontWeight: FontWeight.w700,
                  ),
                  textAlign: TextAlign.center,
                ),
              ),
              Obx(
                    ()=>Visibility(
                  visible: true,
                  child: ConstrainedBox(
                    constraints: BoxConstraints(maxHeight: MediaQuery.of(Get.context!).size.height * .5),
                    child: SingleChildScrollView(
                      child: Column(
                        children: [
                          for (int i = 0;
                          i < viewModel.dataPayment.length;
                          i++)
                            ListTile(
                              visualDensity: const VisualDensity(
                                  horizontal: 0, vertical: -4),
                              contentPadding: EdgeInsets.zero,
                              horizontalTitleGap: 0,
                              tileColor: Colors.transparent,
                              title: Text(
                                viewModel.dataPayment[i].name_payment ?? '' ,
                                style: TextStyle(fontSize: 12),
                              ),
                              leading: Radio(
                                value: viewModel.dataPayment[i],
                                groupValue: viewModel.payment.value,
                                // activeColor: Color(0xFF6200EE),
                                onChanged: i <1 ? (value) {
                                  viewModel.payment.value = value as Payment? ;
                                  print(viewModel.payment.value?.name_payment);
                                  }:null,
                              ),
                            ),
                        ],
                      ),
                    ),
                  ),
                ),),
              SizedBox(
                width: double.infinity,
                child: ElevatedButton(
                  onPressed: (){
                      if(viewModel.payment.value == null){
                        AppUtils().showPopup(
                          isSuccess: false,
                          title: 'Vui lòng chọn phương thức thanh toán',
                        );
                      }
                      print( viewModel.payment.value?.id);

                      if(viewModel.payment.value != null){
                        Get.back();
                      }

                  },
                  child: Text('Chọn'),
                ),
              )
            ],
          ),
        ) ,
      ),
    );
  }
}