import 'package:deskover_app/config/base_api.dart';
import 'package:deskover_app/config/injection_config.dart';
import 'package:deskover_app/modules/main_page/home_page.dart';
import 'package:deskover_app/themes/ui_colors.dart';
import 'package:deskover_app/utils/widgets/view_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter_svg/svg.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';
import 'package:line_icons/line_icons.dart';
import 'package:url_launcher/url_launcher_string.dart';

import '../../../global/global_image.dart';
import '../../../themes/space_values.dart';
import '../widgets/delivery/delivery_model.dart';

class OrderDelivering extends StatefulWidget{

  final String? OrderCode;

  const OrderDelivering({Key? key,required this.OrderCode }) : super(key: key);

  @override
  State<StatefulWidget> createState() => _OrderDelivering();

}
class _OrderDelivering extends ViewWidget<OrderDelivering, DeliveryModel>{


  @override
  void initState()  {
    super.initState();
      viewModel.orderDelivery(widget.OrderCode!,'DG');
  }
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: UIColors.white,
        title: Obx(()=>Text('Đơn hàng của: ${viewModel.orderReponese.value?.fullName!}'
          ,style: TextStyle(
              fontSize: 14
          ),
        ),)
      ),
      body:
      Obx(
              ()=>     Visibility(
                visible: viewModel.orderReponese.value != null,
                child: Card(
                  elevation: 0.0,
                  margin: EdgeInsets.zero,
                  shape: const RoundedRectangleBorder(
                    side: BorderSide(
                        style: BorderStyle.solid,
                        color: UIColors.black10,
                        width: 1
                    ),
                    borderRadius: BorderRadius.zero,
                  ),
                  child: SizedBox(
                    child: Column(
                      children: [
                        Expanded(
                          child: Padding(
                            padding: const EdgeInsets.all(16),
                            child: ListView(
                              children: [
                                Row(
                                  mainAxisAlignment:
                                  MainAxisAlignment.start,
                                  children: [
                                    const Text(
                                      'Mã vận đơn:',
                                      style: TextStyle(
                                          fontWeight: FontWeight.bold,
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
                                Row(
                                  children: [
                                    SizedBox(
                                      child: TextButton(
                                          onPressed: (){
                                            Clipboard.setData(ClipboardData(text: viewModel.orderReponese.value?.tel));
                                          },
                                          style: ButtonStyle(
                                            alignment: Alignment.centerLeft, // <-- had to set alignment
                                            padding: MaterialStateProperty.all<EdgeInsetsGeometry>(
                                              EdgeInsets.zero, // <-- had to set padding to zero
                                            ),
                                          ),
                                          child: Row(
                                            children: [
                                              Text('${viewModel.orderReponese.value?.tel}',
                                                      style: const TextStyle(
                                                          color: UIColors.black
                                                      ),),
                                              Icon(Icons.copy,color: UIColors.black70,size: 20,),
                                            ],
                                          )
                                      ),
                                    ),

                                  ],
                                ),
                                const SizedBox(
                                  height: 20,
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
                                          mainAxisAlignment: MainAxisAlignment.center,
                                          children:  [
                                            const Text(
                                              'Địa chỉ giao hàng:',
                                              style: TextStyle(
                                                  color: UIColors.black70,
                                                  fontSize: 12,
                                                  fontWeight: FontWeight.w700),
                                            ),
                                            SizedBox(
                                              child: TextButton(
                                                  onPressed: (){
                                                    Clipboard.setData(ClipboardData(text:  viewModel.orderReponese.value?.address ?? ''));
                                                  },

                                                  style: TextButton.styleFrom(
                                                      onSurface: UIColors.white,
                                                      backgroundColor: UIColors.white,
                                                      padding: EdgeInsets.zero,
                                                      minimumSize: Size(50, 30),
                                                      tapTargetSize: MaterialTapTargetSize.shrinkWrap,
                                                      alignment: Alignment.centerLeft),
                                                  child: Row(
                                                    children: [
                                                      Expanded(
                                                        child: Text('${viewModel.orderReponese.value?.address}',
                                                          style: const TextStyle(
                                                              color: UIColors.black
                                                          ),),
                                                      ),
                                                      SizedBox(width: 6,),
                                                      Icon(Icons.copy,color: UIColors.black70,size: 20,),
                                                    ],
                                                  )
                                              ),
                                            ),
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
                                  enabled: false,
                                  decoration: InputDecoration(
                                      hintText: viewModel.orderReponese.value?.note ?? 'Ghi chú',
                                      hintStyle: const TextStyle(
                                        fontStyle: FontStyle.italic,
                                        color: UIColors.black70
                                      ),
                                      floatingLabelAlignment: FloatingLabelAlignment.center
                                  ),
                                  minLines: 3,
                                  maxLines: 3,
                                ),
                                const Padding(
                                  padding: EdgeInsets.only(top: 8),
                                  child: Divider(color: UIColors.black,thickness: 0.5,endIndent: 20,indent: 20,height: 10),
                                ),
                                Padding(
                                  padding: const EdgeInsets.only(left: 8,top: 8,bottom: 32),
                                  child: Row(
                                    mainAxisAlignment: MainAxisAlignment.start,
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
                                            fontWeight: FontWeight.w700,
                                            color: UIColors.black,
                                            fontSize: 14),
                                      ),
                                      const SizedBox(
                                        width: SpaceValues.space24,
                                      ),

                                    ],
                                  ),
                                ),

                              ],
                            ),
                          ),
                        ),
                        Container(
                          padding: const EdgeInsets.only(left: 16,right: 16,top: 8,bottom: 8),
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
                          child: Row(
                            children: [
                              Expanded(
                                child: ElevatedButton(
                                    style: ElevatedButton.styleFrom(
                                        primary: UIColors.white,
                                        // elevation: 0.0,
                                        shape:  RoundedRectangleBorder(
                                            borderRadius: BorderRadius.circular(5),
                                            side: const BorderSide(color: UIColors.red,width: 1)
                                        )
                                    ) ,
                                    onPressed: (){
                                        Get.dialog(DiaLogOrderDelivering(viewModel: viewModel,));
                                    },
                                    child: const Text(
                                      'Cập nhập đơn hàng',
                                      style: TextStyle(
                                          color: UIColors.red
                                      ),
                                    )),
                              ),
                              const SizedBox(width: 8,),
                              Expanded(
                                child: ElevatedButton(
                                    style: ElevatedButton.styleFrom(
                                        primary: UIColors.appBar,
                                        shape:  RoundedRectangleBorder(
                                          borderRadius: BorderRadius.circular(5),
                                        )
                                    ) ,
                                    onPressed: () async{
                                        await viewModel.PickupOrder(viewModel.orderReponese.value?.orderCode ??'', 'GH-TC',
                                            'Giao hàng thành công','Cập nhập thành công');
                                        Get.off(()=> const HomePage(initScreen: 2,));
                                    },
                                    child: Text(
                                      'Thành công',
                                      style: TextStyle(

                                      ),
                                    )),
                              )
                            ],
                          ),
                        )
                      ],
                    ),
                  ),
                ),
                replacement: Container(
                  margin: const EdgeInsets.only(top: 1),
                  color: UIColors.white,
                  width: double.infinity,
                  child: Column(
                    mainAxisAlignment: MainAxisAlignment.center,
                    children: [
                      SvgPicture.asset('resources/images/search.svg'),
                    ],
                  ),
                ),
              ),
      ),
      floatingActionButton: Padding(
        padding: const EdgeInsets.only(bottom: 75),
        child: SizedBox(
          width: 60,
          height: 60,
          child: ElevatedButton(
                style: ElevatedButton.styleFrom(
                  primary: UIColors.green,
                  shape: RoundedRectangleBorder(
                      borderRadius: BorderRadius.circular(100)
                  )
                ),
                onPressed: () async {
                  launchUrlString('tel:${viewModel.orderReponese.value?.tel}');
                },
                child: Transform.scale(
                  scaleX: -1,
                  child: Icon(LineIcons.phone),
                ),

          ),
        ),
      ),
    );
  }

  @override
  DeliveryModel createViewModel() => getIt<DeliveryModel>();
}

class DiaLogOrderDelivering extends StatelessWidget{

  final DeliveryModel viewModel;

  final List<String> status = <String>['Giao hàng không thành công', 'Huỷ đơn'];
  final List<String> code_status = <String>[ 'GH-TB', 'HUY'];


  DiaLogOrderDelivering({Key? key, required this.viewModel}) : super(key: key);



  @override
  Widget build(BuildContext context) {
      return  AlertDialog(
        titlePadding: EdgeInsets.zero,
        contentPadding: const EdgeInsets.symmetric(horizontal: SpaceValues.space16, vertical: 16),
        content: Column(
          mainAxisSize: MainAxisSize.min,
          children: [
            const Align(
              alignment: Alignment.topLeft,
              child: Text(
                'Vui lòng chọn trạng thái đơn hàng:',
                style: TextStyle(
                  fontSize: 14,
                  fontWeight: FontWeight.w700,
                ),
                textAlign: TextAlign.center,
              ),
            ),
            const SizedBox(height: SpaceValues.space8,),
           Obx(
             ()=>Visibility(
               visible: true,
               child: ConstrainedBox(
                 constraints: BoxConstraints(maxHeight: MediaQuery.of(Get.context!).size.height * .5),
                 child: SingleChildScrollView(
                   child: Column(
                     children: [
                       for (int i = 0;
                       i < status.length;
                       i++)
                         ListTile(
                           visualDensity: const VisualDensity(
                               horizontal: 0, vertical: -4),
                           contentPadding: EdgeInsets.zero,
                           horizontalTitleGap: 0,
                           tileColor: Colors.transparent,
                           title: Text(
                             status[i],
                             style: TextStyle(fontSize: 12),
                           ),
                           leading: Radio(
                             value: code_status[i],
                             groupValue:  viewModel.value_status.value,
                             // activeColor: Color(0xFF6200EE),
                             onChanged: (value) {
                               print(value);
                               viewModel.value_status.value = value.toString();
                             },
                           ),
                         ),
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
                       SizedBox(width: MediaQuery.of(context).size.width *1,height: SpaceValues.space12,),
                       TextField(
                         controller: viewModel.note,
                         decoration: const InputDecoration(
                             hintText: 'Nhập ghi chú (nếu có)',
                             hintStyle: TextStyle(
                                 fontStyle: FontStyle.italic,
                                 color: UIColors.black70
                             ),
                             floatingLabelAlignment: FloatingLabelAlignment.center
                         ),
                         minLines: 3,
                         maxLines: 3,
                       ),
                     ],
                   ),
                 ),
               ),
             ),),

            const SizedBox(
              height: SpaceValues.space12,
            ),
            SizedBox(
              width: MediaQuery.of(Get.context!).size.width * .5,
              child: ElevatedButton(
                style: ElevatedButton.styleFrom(
                    primary: Colors.blueAccent
                ),
                onPressed: () async{
                  print(viewModel.note.text);
                    await viewModel.PickupOrder(viewModel.orderReponese.value?.orderCode ??'',viewModel.value_status.value, viewModel.note.text,'Giao hàng không thành công').then((value) async => Get.back());
                    Get.off(()=> HomePage(initScreen: 2,));
                },
                child: const Text('Xác nhận'),
              ),
            ),

          ],
        ),
      );
  }

}

