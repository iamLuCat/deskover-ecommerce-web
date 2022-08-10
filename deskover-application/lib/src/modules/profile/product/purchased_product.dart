// import 'dart:math';
//
// import 'package:deskover_develop/src/config/injection_config.dart';
// import 'package:deskover_develop/src/modules/order/all_order/list_order_model.dart';
// import 'package:deskover_develop/src/modules/product_widget/product_widget.dart';
// import 'package:deskover_develop/src/modules/profile/product/pur_product_model.dart';
// import 'package:deskover_develop/src/themes/dialogs/loading_dialog.dart';
// import 'package:deskover_develop/src/themes/ui_colors.dart';
// import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
// import 'package:flutter/material.dart';
// import 'package:flutter_svg/flutter_svg.dart';
// import 'package:get/get.dart';
// import 'package:intl/intl.dart';
//
//
// class PurchasedProductScreen extends StatefulWidget {
//   const PurchasedProductScreen({Key? key}) : super(key: key);
//
//   @override
//   State<PurchasedProductScreen> createState() => _PurchasedProductScreenState();
// }
//
// class _PurchasedProductScreenState
//     extends ViewWidget<PurchasedProductScreen, ListOrderModel> {
//   @override
//   void initState() {
//     super.initState();
//     viewModel.statusCode.value = 'DG';
//     viewModel.loadOrderByStatus();
//   }
//   final formatCurrency = NumberFormat.currency(locale:"vi_VN", symbol: "đ");
//   @override
//   Widget build(BuildContext context) {
//     return Scaffold(
//       backgroundColor: UIColors.white,
//       appBar: AppBar(
//         title: const Text(
//           'Sản phẩm đã mua',
//           style: TextStyle(
//               fontSize: 14, fontWeight: FontWeight.w700, color: UIColors.title),
//         ),
//         centerTitle: true,
//         backgroundColor: UIColors.white,
//         shape: const Border(
//             bottom: BorderSide(color: Color(0xAFAFB4C7), width: 1)),
//       ),
//       body: Obx(() {
//         return SingleChildScrollView(
//           physics: const AlwaysScrollableScrollPhysics(),
//           child: Container(
//             constraints: BoxConstraints(minHeight: MediaQuery.of(context).size.height),
//             child: Visibility(
//               visible: viewModel.orders.isNotEmpty,
//               child: Column(
//                 children: <Widget>[
//                   Padding(
//                     padding: const EdgeInsets.all(16.0),
//                     child: Column(
//                       children: [
//                         Visibility(
//                           visible: (viewModel.orders?.length ?? 0) > 0,
//                           child: GridView.builder(
//                             shrinkWrap: true,
//                             itemCount: viewModel.orders?.length ?? 0,
//                             physics: const NeverScrollableScrollPhysics(),
//                             gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
//                               mainAxisSpacing: 6,
//                               crossAxisSpacing: 2,
//                               crossAxisCount: 2,
//                               mainAxisExtent: 270,
//                               // childAspectRatio: .5,
//                             ),
//                             itemBuilder: (context, index) => ProductWidget(
//                               productId: viewModel.orders[index].products.,
//                               title: viewModel.orders?[index].name ?? '',
//                             ),
//                           ),
//                           replacement: Column(
//                             children:const [
//                               Text(
//                                   'Không tìm thấy sản phẩm nào trong trang đã mua, vui lòng trở lại sau!',
//                                   textAlign: TextAlign.center),
//                             ],
//                           ),
//                         ),
//                       ],
//                     ),
//                   ),
//                 ],
//               ),
//               replacement: Column(
//                 children: [
//                   SizedBox(
//                     height:
//                     (MediaQuery.of(context).size.height) / 2 - 64 - 96,
//                   ),
//                   const LoadingDialog(
//                     elevation: 0,
//                     backgroundColor: Colors.transparent,
//                     // backgroundColor: Colors.transparent,
//                   ),
//                 ],
//               ),
//             ),
//           ),
//         );
//       }),
//     );
//   }
//
//   @override
//   ListOrderModel createViewModel() => getIt<ListOrderModel>();
// }
