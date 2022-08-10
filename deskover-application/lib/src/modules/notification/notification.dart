import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/notification/get_time.dart';
import 'package:deskover_develop/src/modules/notification/notifi_model.dart';
import 'package:deskover_develop/src/modules/notification/notification_order.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';

class Notify extends StatefulWidget{
  const Notify({Key? key}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _Notify();

}
class _Notify extends ViewWidget<Notify,NotifyModel>{

  @override
  Widget build(BuildContext context) {
     return Scaffold(
       appBar: AppBar(
         backgroundColor: UIColors.white,
         title: const Text('Thông báo'),
         shadowColor: Colors.grey.withOpacity(0.5),
       ),
       body:
           Obx(()=>  RefreshIndicator(
             onRefresh: ()=> viewModel.loadNotify(),
             child: ListView.separated(
               itemCount: viewModel.listNotify.length,
               itemBuilder: (BuildContext context, int index) {
                 return InkWell(
                   onTap: (){
                      viewModel.changeNotify(viewModel.listNotify[index].id ?? 0);
                      Get.to( NotificationOrder(orderCode: viewModel.listNotify[index].orderCode ?? ''));
                   },
                   child: Container(
                     margin: const EdgeInsets.only(top: 6),
                     color: viewModel.listNotify[index].isWatched! ? UIColors.white : Colors.blue.shade50,
                     child: Padding(
                       padding: const EdgeInsets.only(right: 16,left: 16,top: 6,bottom: 6),
                       child: Row(
                         mainAxisAlignment: MainAxisAlignment.start,
                         children: [
                           Container(
                               width: MediaQuery.of(context).size.width * 0.17,
                               height: MediaQuery.of(context).size.width * 0.17,
                               decoration:  BoxDecoration(
                                 color: UIColors.black10,
                                 borderRadius: BorderRadius.circular(999),
                               ),
                               child: Center(
                                 child: SvgPicture.asset(
                                    'resources/icons/shopping_cart.svg',
                                    fit: BoxFit.none
                                 ),
                               ),
                           ),
                           SizedBox(width: 10),
                           Expanded(
                             child: Column(
                               crossAxisAlignment: CrossAxisAlignment.start,
                               children: [
                                 Text(
                                     viewModel.listNotify[index].title ?? '',
                                   style:  const TextStyle(
                                      fontSize: 14,
                                     fontWeight: FontWeight.w700
                                   ),
                                 ),
                                 const SizedBox(height: 6,),
                                 Text(
                                   GetTime.parse(DateTime.parse(viewModel.listNotify[index].createdAt ?? ''),).toString()
                                 )
                               ],
                             ),
                           ),
                         ],
                       ),
                     ),
                   ),
                 );
               },
               separatorBuilder: (BuildContext context, int index) => SizedBox(height: 0,),
             ),
           ),)

     );
  }

  @override
  NotifyModel createViewModel() => getIt<NotifyModel>();

}
