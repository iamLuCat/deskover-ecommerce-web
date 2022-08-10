import 'dart:math';

import 'package:carousel_slider/carousel_slider.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/global_modules/widget/global_html_widget.dart';
import 'package:deskover_develop/src/modules/global_modules/widget/global_image.dart';
import 'package:deskover_develop/src/modules/homepage/widgets/list_product_new.dart';
import 'package:deskover_develop/src/modules/product_widget/product_detail_model.dart';
import 'package:deskover_develop/src/modules/product_widget/product_selling/product_selling.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter_rating_bar/flutter_rating_bar.dart';
import 'package:get/get.dart';
import 'package:intl/intl.dart';

class ProductDetail extends StatefulWidget {
  ProductDetail({Key? key, required this.idProduct, required this.heroTag}) : super(key: key);
  int idProduct;
  String? heroTag;
  @override
  State<ProductDetail> createState() => _ProductDetailState(idProduct: idProduct);
}

class _ProductDetailState extends ViewWidget<ProductDetail, ProductDetailModel> {
  _ProductDetailState({required this.idProduct}) : super();
  int idProduct;
  String? _herotag;
  String get herotag {
    _herotag ??= '${widget.idProduct.toString()}${Random().nextDouble()}';
    return _herotag!;
  }

  @override
  void initState() {
    super.initState();
    viewModel.idProduct = widget.idProduct;
    viewModel.initStateAsync();
  }

  @override
  Widget build(BuildContext context) {
    final formatCurrency = NumberFormat.currency(locale:"vi_VN", symbol: "đ");
    CarouselController _carouselController = CarouselController();
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        iconTheme: const IconThemeData(
          color: Colors.black,
        ),
        centerTitle: false,
        title: const Text(
          'Chi tiết sản phẩm',
          style: TextStyle(fontSize: 18, fontWeight: FontWeight.bold),
        ),
        actions: [
        ],
      ),
      backgroundColor: UIColors.white,
      body: Column(
        children: [
          Expanded(
            child: SingleChildScrollView(
              child: Column(
                children: [
                  Stack(children: [
                    Obx(() => (viewModel.productDetail.value?.productThumbnails?.length ?? 0) != 0
                        ? Container(
                      color: UIColors.white,
                      child: CarouselSlider(
                        items: (viewModel.productDetail.value?.productThumbnails ?? []).map((item) => Center(
                          child: GlobalImage(BaseApi.baseUrl_product+'${item.thumbnail}',),
                        )).toList(),
                        carouselController: _carouselController,
                        options: CarouselOptions(
                          autoPlay: true,
                          enlargeCenterPage: true,
                          onPageChanged: (index, reason) {
                            viewModel.indexSlider.value = index;
                          },
                        ),
                      ),
                    )
                        : CarouselSlider(
                      options: CarouselOptions(
                        autoPlay: true,
                      ),
                      items: [GlobalImage(viewModel.productDetail.value?.productThumbnails?.toString(), width: MediaQuery.of(context).size.width,)],
                    ),
                    ),
                    Positioned(
                      bottom: 16,
                      right: 16,
                      child: Container(
                        decoration: BoxDecoration(
                          color: UIColors.black10,
                          borderRadius: BorderRadius.circular(4),
                        ),
                        padding: const EdgeInsets.symmetric(vertical: 2, horizontal: 4),
                        child: Obx(() {
                          return Text('${viewModel.indexSlider.value + 1}/${viewModel.productDetail.value?.productThumbnails?.length ?? 0}');
                        }
                        ),
                      ),
                    )
                  ]),
                  Obx(() => Container(
                    color: UIColors.white,
                    height: 64,
                    width: double.infinity,
                    child: Visibility(
                      visible: (viewModel.productDetail.value?.productThumbnails?.length ?? 0) != 0,
                      child: ListView.builder(
                        shrinkWrap: true,
                        scrollDirection: Axis.horizontal,
                        itemCount: viewModel.productDetail.value?.productThumbnails?.length ?? 0,
                        itemBuilder: (context, i) {
                          return InkWell(
                            onTap: () {
                              if (_carouselController.ready) {
                                viewModel.indexSlider.value = i;
                                _carouselController.animateToPage(i);
                              }
                            },
                            child: Obx(()
                            => Container(
                              margin: const EdgeInsets.all(5),
                              decoration: BoxDecoration(
                                  color: UIColors.white,
                                  borderRadius: BorderRadius.circular(8),
                                  border: Border.all(color: viewModel.indexSlider.value == i ? UIColors.brandA : UIColors.border10)),
                              width: MediaQuery.of(context).size.width * 0.15,
                              child: GlobalImage(
                                BaseApi.baseUrl_product+'${viewModel.productDetail.value?.productThumbnails?[i].thumbnail}',
                                fit: BoxFit.contain,
                                height: 56,
                              ),
                            ),
                            ),
                          );
                        },
                      ),
                      replacement: Row(
                        children: [
                          Container(
                            margin: const EdgeInsets.all(5),
                            decoration: BoxDecoration(
                              color: UIColors.white,
                              borderRadius: BorderRadius.circular(8),
                              border: Border.all(color:  UIColors.brandA),
                            ),
                            child: const GlobalImage(null, height: 56, width: 56,),
                          ),
                        ],
                      ),
                    ),
                  ),
                  ),
                  Obx(() =>
                      Padding(
                      padding: const EdgeInsets.all(16.0),
                      child: Column(
                        crossAxisAlignment: CrossAxisAlignment.start,
                        children: [
                          Text(viewModel.productDetail.value?.name ?? '...',
                              style: const TextStyle(
                                  fontSize: 14, fontWeight: FontWeight.w700)),
                          const SizedBox(height: 12),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceBetween,
                            children: [
                              Row(
                                mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                children: [
                                  Visibility(
                                      visible: (viewModel.productDetail.value?.discount?.percent ?? 0) !=0 ,
                                      child: Row(
                                        children: [
                                          Text(
                                              formatCurrency.format((viewModel.productDetail.value?.price ?? 0)
                                                  - ((viewModel.productDetail.value?.price ?? 0) * (viewModel.productDetail.value?.discount?.percent ?? 0)/100)
                                              ),
                                              style: const TextStyle(
                                                  fontSize: 18,
                                                  fontWeight: FontWeight.w700,
                                                  color: UIColors.brandA)
                                          ),
                                          Container(
                                            padding:
                                            const EdgeInsets.only(left: 7, top: 7),
                                            child: Text(
                                              formatCurrency.format(viewModel.productDetail.value?.price?.toDouble() ?? 0),
                                              style: const TextStyle(
                                                fontSize: 10.0,
                                                fontWeight: FontWeight.w400,
                                                color: Colors.black54,
                                                decoration: TextDecoration.lineThrough,
                                              ),
                                            ),
                                          ),
                                        ],
                                      ),
                                    replacement: Text(
                                        formatCurrency.format(viewModel.productDetail.value?.price?.toDouble() ?? 0),
                                        style: const TextStyle(
                                            fontSize: 18,
                                            fontWeight: FontWeight.w700,
                                            color: UIColors.brandA)
                                    ),
                                  ),


                                  Visibility(
                                    visible: (viewModel.productDetail.value?.discount?.percent ?? 0) != 0,
                                    child: Container(
                                      margin: const EdgeInsets.only(left: 10),
                                      decoration: BoxDecoration(
                                        borderRadius: BorderRadius.circular(5),
                                        color: UIColors.brandA,
                                      ),
                                      child: Padding(
                                        padding: const EdgeInsets.all(5),
                                        child: Text(
                                            (viewModel.productDetail.value?.discount?.percent != 0 ? '${viewModel.productDetail.value?.discount?.percent.toString()}%':''),
                                            style: const TextStyle(
                                                color: Colors.white, fontSize: 10)),
                                      ),
                                    ),
                                  ),
                                ],
                              ),

                            ],
                          ),
                          ListTile(
                            dense: true,
                            contentPadding:
                            const EdgeInsets.only(left: 0.0, right: 0.0),
                            title: const Text("SẢN PHẨM TƯƠNG TỰ",
                                style: TextStyle(
                                    fontWeight: FontWeight.w700, fontSize: 14)),
                            tileColor: Colors.transparent,
                            trailing: GestureDetector(
                              onTap:(){
                                Get.back();
                                Get.to(ListProductNew(categoryId: viewModel.productDetail.value?.subCategory?.category?.id ?? 1,title: viewModel.productDetail.value?.subCategory?.category?.name,));
                              },
                              child: const Text("Xem thêm >",
                                  style: TextStyle(
                                      fontSize: 12,
                                      fontWeight: FontWeight.w400,
                                      color: UIColors.brandA)),
                            ),
                            onTap: () {},
                          ),
                           ProductSellingScreen(categoryId: viewModel.productDetail.value?.subCategory?.category?.id ?? 1),
                          const SizedBox(
                            height: 24,
                          ),
                        ],
                      ),
                    )
                  ),
                  Container(
                    padding: const EdgeInsets.all(15),
                    width: MediaQuery.of(context).size.width * 0.98,
                    decoration: BoxDecoration(
                      borderRadius: BorderRadius.circular(20),
                      color: Colors.white,
                    ),
                    child: Column(
                      children: [
                        Container(
                          alignment: Alignment.topLeft,
                          child: const Text(
                            "Mô tả sản phẩm",
                            style: TextStyle(
                              fontSize: 14,
                              fontWeight: FontWeight.bold,
                            ),
                          ),
                        ),
                        Obx(
                             ()=> Visibility(
                                visible: viewModel.productDetail.value != null,
                                 child: SizedBox(
                                   width: double.infinity,
                                   child: Stack(
                                     children: [
                                       GlobalHtmlWidget(
                                         data: viewModel.productDetail.value?.utility ?? '',
                                       ),
                                     ],
                                   ),
                                 ),
                               replacement: Text('123'),

                             ),

                        ),

                        // Obx(() {
                        //   Widget result = viewModel.productDetail.value?.spec != null
                        //       ?
                        //   SizedBox(
                        //     width: double.infinity,
                        //     child: Stack(
                        //       children: [
                        //         GlobalHtmlWidget(
                        //           data: viewModel.productDetail.value?.spec ?? '',
                        //         ),
                        //       ],
                        //     ),
                        //   ): const Text('...');
                        //   return ClipRect(
                        //     child: SizedBox(
                        //       height: viewModel.productDetail.value?.spec != null ? null : 320,
                        //       child: result,
                        //     ),
                        //   );
                        // }),
                        // TextButton(
                        //   style: TextButton.styleFrom(
                        //     padding: const EdgeInsets.symmetric(horizontal: 16,),
                        //   ),
                        //   onPressed: () {
                        //     viewModel.showMoreDetail.value = !viewModel.showMoreDetail.value;
                        //   },
                        //   child: Row(
                        //     mainAxisAlignment: MainAxisAlignment.center,
                        //     children: [
                        //       Obx(() {
                        //         return Text(
                        //           viewModel.showMoreDetail.value
                        //               ? 'Thu gọn'
                        //               : "Xem tất cả",
                        //           style: const TextStyle(fontSize: 11),
                        //         );
                        //       }),
                        //       Obx(
                        //               () {
                        //             return Padding(
                        //               padding: const EdgeInsets.only(left: 5),
                        //               child: RotatedBox(
                        //                 quarterTurns: viewModel.showMoreDetail.value ? 2 : 0,
                        //                 child: SvgPicture.asset(
                        //                   IconAssets.chervon1,
                        //                   width: 10,
                        //                   color: UIColors.brandA,
                        //                 ),
                        //               ),
                        //             );
                        //           }
                        //       ),
                        //     ],
                        //   ),
                        // ),
                      ],
                    ),
                  ),
                  const SizedBox(
                    height: 20,
                  ),
                  Container(
                    padding: const EdgeInsets.all(16),
                    width: MediaQuery.of(context).size.width,
                    decoration: const BoxDecoration(color: UIColors.white),
                    child: Column(
                      children: [
                        Container(
                          alignment: Alignment.topLeft,
                          child: const Text("Nhận xét từ khách hàng",
                            style: TextStyle(
                              fontSize: 14, fontWeight: FontWeight.w700,
                            ),
                          ),
                        ),
                        Obx(
                                () {
                              return Container(
                                padding: const EdgeInsets.only(left: 16, right: 16),
                                child: Row(
                                  mainAxisAlignment: MainAxisAlignment.spaceBetween,
                                  children: [
                                    Text(viewModel.productDetail.value.toString(),
                                      style: const TextStyle(
                                        fontSize: 18,
                                        color: UIColors.brandA,
                                        fontWeight: FontWeight.w700,
                                      ),
                                    ),
                                    Column(
                                      children: [
                                        RatingBar.builder(
                                          initialRating: viewModel.productDetail.value?.averageRating?.toDouble() ?? 0,
                                          minRating: 1,
                                          itemSize: 12,
                                          direction: Axis.horizontal,
                                          allowHalfRating: true,
                                          itemCount: 5,
                                          ignoreGestures: true,
                                          itemPadding: const EdgeInsets.symmetric(horizontal: 1),
                                          itemBuilder: (context, _) => const Icon(
                                            Icons.star,
                                            color: Color(0xAaFBA510),
                                          ),
                                          onRatingUpdate: (rating) {},
                                        ),
                                        Text("(${viewModel.productDetail.value?.totalRating ?? 0} đánh giá)",
                                            style: const TextStyle(
                                                fontSize: 12,
                                                fontWeight: FontWeight.w400)),
                                      ],
                                    )
                                  ],
                                ),
                              );
                            }
                        ),
                        const SizedBox(height: 12),
                        Container(
                          padding: const EdgeInsets.fromLTRB(12, 0, 12, 0),
                          width: MediaQuery.of(context).size.width - (16 * 2),
                          height: 100.0,
                          child: Column(
                            crossAxisAlignment: CrossAxisAlignment.start,
                            mainAxisAlignment:
                            MainAxisAlignment.spaceBetween,
                            children: [
                              Row(
                                mainAxisAlignment:
                                MainAxisAlignment.spaceBetween,
                                children: [
                                  const Text("5",
                                    style: TextStyle(
                                      fontSize: 14.0,
                                      fontWeight: FontWeight.w400,
                                    ),
                                  ),
                                  const Icon(
                                    Icons.star,
                                    color: Color(0xAaFBA510),
                                    size: 14,
                                  ),
                                  Expanded(
                                    child: Padding(
                                      padding: const EdgeInsets.symmetric(horizontal: 6),
                                      child: ClipRRect(
                                        borderRadius: BorderRadius.circular(6),
                                        child: ProgressIndicatorTheme(
                                          data: const ProgressIndicatorThemeData(
                                            linearMinHeight: 6,
                                          ),
                                          child: LinearProgressIndicator(
                                            value: viewModel.start5.toDouble() / (viewModel.productDetail.value?.averageRating?.toDouble() ?? 0),
                                            color: Colors.amber,
                                          ),
                                        ),
                                      ),
                                    ),
                                    // child: Container(
                                    //   margin: const EdgeInsets.symmetric(horizontal: 6),
                                    //   height: 6,
                                    //   decoration: BoxDecoration(
                                    //     color: Colors.grey.withOpacity(0.4),
                                    //     borderRadius: BorderRadius.circular(50.0),
                                    //   ),
                                    // ),
                                  ),
                                  Text(viewModel.start5.toString(),
                                    style: const TextStyle(
                                      fontSize: 14.0,
                                      fontWeight: FontWeight.w400,
                                    ),
                                  ),
                                ],
                              ),
                              Row(
                                mainAxisAlignment:
                                MainAxisAlignment.spaceBetween,
                                children: [
                                  const Text("4",
                                    style: TextStyle(
                                      fontSize: 14.0,
                                      fontWeight: FontWeight.w400,
                                    ),
                                  ),
                                  const Icon(
                                    Icons.star,
                                    color: Color(0xAaFBA510),
                                    size: 14,
                                  ),
                                  // Expanded(
                                  //   child: Padding(
                                  //     padding: const EdgeInsets.symmetric(horizontal: 6),
                                  //     child: ClipRRect(
                                  //       borderRadius: BorderRadius.circular(6),
                                  //       child: ProgressIndicatorTheme(
                                  //         data: const ProgressIndicatorThemeData(
                                  //           linearMinHeight: 6,
                                  //         ),
                                  //         child: LinearProgressIndicator(
                                  //           value: viewModel.starCountList[3].toDouble() / (viewModel.comment.value?.data?.length ?? 1).toDouble(),
                                  //           color: Colors.amber,
                                  //         ),
                                  //       ),
                                  //     ),
                                  //   ),
                                  //   // child: Container(
                                  //   //   margin: const EdgeInsets.symmetric(horizontal: 6),
                                  //   //   height: 6,
                                  //   //   decoration: BoxDecoration(
                                  //   //     color: Colors.grey.withOpacity(0.4),
                                  //   //     borderRadius: BorderRadius.circular(50.0),
                                  //   //   ),
                                  //   // ),
                                  // ),
                                  // Text(viewModel.starCountList[3].toString(),
                                  //   style: const TextStyle(
                                  //     fontSize: 14.0,
                                  //     fontWeight: FontWeight.w400,
                                  //   ),
                                  // ),
                                ],
                              ),
                              Row(
                                mainAxisAlignment:
                                MainAxisAlignment.spaceBetween,
                                children: [
                                  const Text("3",
                                    style: TextStyle(
                                      fontSize: 14.0,
                                      fontWeight: FontWeight.w400,
                                    ),
                                  ),
                                  const Icon(
                                    Icons.star,
                                    color: Color(0xAaFBA510),
                                    size: 14,
                                  ),
                                  // Expanded(
                                  //   child: Padding(
                                  //     padding: const EdgeInsets.symmetric(horizontal: 6),
                                  //     child: ClipRRect(
                                  //       borderRadius: BorderRadius.circular(6),
                                  //       child: ProgressIndicatorTheme(
                                  //         data: const ProgressIndicatorThemeData(
                                  //           linearMinHeight: 6,
                                  //         ),
                                  //         child: LinearProgressIndicator(
                                  //           value: viewModel.starCountList[2].toDouble() / (viewModel.comment.value?.data?.length ?? 1).toDouble(),
                                  //           color: Colors.amber,
                                  //         ),
                                  //       ),
                                  //     ),
                                  //   ),
                                  //   // child: Container(
                                  //   //   margin: const EdgeInsets.symmetric(horizontal: 6),
                                  //   //   height: 6,
                                  //   //   decoration: BoxDecoration(
                                  //   //     color: Colors.grey.withOpacity(0.4),
                                  //   //     borderRadius: BorderRadius.circular(50.0),
                                  //   //   ),
                                  //   // ),
                                  // ),
                                  // Text(viewModel.starCountList[2].toString(),
                                  //   style: const TextStyle(
                                  //     fontSize: 14.0,
                                  //     fontWeight: FontWeight.w400,
                                  //   ),
                                  // ),
                                ],
                              ),
                              Row(
                                mainAxisAlignment:
                                MainAxisAlignment.spaceBetween,
                                children: [
                                  const Text("2",
                                    style: TextStyle(
                                      fontSize: 14.0,
                                      fontWeight: FontWeight.w400,
                                    ),
                                  ),
                                  const Icon(
                                    Icons.star,
                                    color: Color(0xAaFBA510),
                                    size: 14,
                                  ),
                                  // Expanded(
                                  //   child: Padding(
                                  //     padding: const EdgeInsets.symmetric(horizontal: 6),
                                  //     child: ClipRRect(
                                  //       borderRadius: BorderRadius.circular(6),
                                  //       child: ProgressIndicatorTheme(
                                  //         data: const ProgressIndicatorThemeData(
                                  //           linearMinHeight: 6,
                                  //         ),
                                  //         child: LinearProgressIndicator(
                                  //           value: viewModel.starCountList[1].toDouble() / (viewModel.comment.value?.data?.length ?? 1).toDouble(),
                                  //           color: Colors.amber,
                                  //         ),
                                  //       ),
                                  //     ),
                                  //   ),
                                  //   // child: Container(
                                  //   //   margin: const EdgeInsets.symmetric(horizontal: 6),
                                  //   //   height: 6,
                                  //   //   decoration: BoxDecoration(
                                  //   //     color: Colors.grey.withOpacity(0.4),
                                  //   //     borderRadius: BorderRadius.circular(50.0),
                                  //   //   ),
                                  //   // ),
                                  // ),
                                  // Text(viewModel.starCountList[1].toString(),
                                  //   style: const TextStyle(
                                  //     fontSize: 14.0,
                                  //     fontWeight: FontWeight.w400,
                                  //   ),
                                  // ),
                                ],
                              ),
                              Row(
                                mainAxisAlignment:
                                MainAxisAlignment.spaceBetween,
                                children: [
                                  const Text("1",
                                    style: TextStyle(
                                      fontSize: 14.0,
                                      fontWeight: FontWeight.w400,
                                    ),
                                  ),
                                  const Icon(
                                    Icons.star,
                                    color: Color(0xAaFBA510),
                                    size: 14,
                                  ),
                                  // Expanded(
                                  //   child: Padding(
                                  //     padding: const EdgeInsets.symmetric(horizontal: 6),
                                  //     child: ClipRRect(
                                  //       borderRadius: BorderRadius.circular(6),
                                  //       child: ProgressIndicatorTheme(
                                  //         data: const ProgressIndicatorThemeData(
                                  //           linearMinHeight: 6,
                                  //         ),
                                  //         child: LinearProgressIndicator(
                                  //           value: viewModel.starCountList[0].toDouble() / (viewModel.comment.value?.data?.length ?? 1).toDouble(),
                                  //           color: Colors.amber,
                                  //         ),
                                  //       ),
                                  //     ),
                                  //   ),
                                  //   // child: Container(
                                  //   //   margin: const EdgeInsets.symmetric(horizontal: 6),
                                  //   //   height: 6,
                                  //   //   decoration: BoxDecoration(
                                  //   //     color: Colors.grey.withOpacity(0.4),
                                  //   //     borderRadius: BorderRadius.circular(50.0),
                                  //   //   ),
                                  //   // ),
                                  // ),
                                  // Text(viewModel.starCountList[0].toString(),
                                  //   style: const TextStyle(
                                  //     fontSize: 14.0,
                                  //     fontWeight: FontWeight.w400,
                                  //   ),
                                  // ),
                                ],
                              ),
                            ],
                          ),
                        ),
                        const SizedBox(
                          height: 16,
                        ),
                        // Container(
                        //   alignment: Alignment.topLeft,
                        //   child: Obx(
                        //           () {
                        //         return Text("${viewModel.comment.value?.data?.length ?? 0} nhận xét",
                        //           style: const TextStyle(
                        //             fontWeight: FontWeight.bold, fontSize: 12,
                        //           ),
                        //         );
                        //       }
                        //   ),
                        // ),
                        const SizedBox(
                          height: 8,
                        ),
                        // Obx(
                        //         () {
                        //       return ListView.builder(
                        //         physics: const NeverScrollableScrollPhysics(),
                        //         shrinkWrap: true,
                        //         itemCount: viewModel.commentCount.value > (viewModel.comment.value?.data?.length ?? 0) ? (viewModel.comment.value?.data?.length ?? 0) : viewModel.commentCount.value,
                        //         itemBuilder: (context, index) =>
                        //             Row(
                        //               crossAxisAlignment: CrossAxisAlignment.start,
                        //               mainAxisAlignment: MainAxisAlignment.spaceBetween,
                        //               children: [
                        //                 ClipRRect(
                        //                   borderRadius: BorderRadius.circular(9999),
                        //                   child: GlobalImage(
                        //                     viewModel.comment.value?.data?[index].avatar,
                        //                     width: 56,
                        //                     height: 56,
                        //                   ),
                        //                 ),
                        //                 const SizedBox(width: 4),
                        //                 Column(
                        //                   crossAxisAlignment: CrossAxisAlignment.start,
                        //                   children: [
                        //                     Text(viewModel.comment.value?.data?[index].userName ?? '<Khuyết danh>',
                        //                         style: const TextStyle(
                        //                             fontSize: 12,
                        //                             fontWeight: FontWeight.w700)),
                        //                     const SizedBox(
                        //                       height: 4,
                        //                     ),
                        //                     SizedBox(
                        //                       width: MediaQuery.of(context).size.width - 60 - 16 - 16,
                        //                       child: Text("Phân loại: ${viewModel.comment.value?.data?[index].productName}",
                        //                           style: const TextStyle(
                        //                               fontSize: 12,
                        //                               fontWeight: FontWeight.w400)),
                        //                     ),
                        //                     const SizedBox(
                        //                       height: 4,
                        //                     ),
                        //                     Row(
                        //                       children: [
                        //                         Container(
                        //                           alignment: Alignment.topLeft,
                        //                           child: RatingBar.builder(
                        //                             initialRating: viewModel.comment.value?.data?[index].rate?.toDouble() ?? 1,
                        //                             minRating: 1,
                        //                             itemSize: 12,
                        //                             direction: Axis.horizontal,
                        //                             allowHalfRating: false,
                        //                             itemCount: 5,
                        //                             ignoreGestures: true,
                        //                             itemPadding: const EdgeInsets.symmetric(
                        //                                 horizontal: 1.0),
                        //                             itemBuilder: (context, _) => const Icon(
                        //                               Icons.star,
                        //                               color: Color(0xAaFBA510),
                        //                             ),
                        //                             onRatingUpdate: (rating) {
                        //                             },
                        //                           ),
                        //                         ),
                        //                         Text(" ${viewModel.comment.value?.data?[index].createdAt ?? '...'}",
                        //                           style: const TextStyle(
                        //                             fontSize: 12.0,
                        //                             fontWeight: FontWeight.w400,
                        //                           ),
                        //                         ),
                        //                       ],
                        //                     ),
                        //                     const SizedBox(height: 8),
                        //                     SizedBox(
                        //                         width: MediaQuery.of(context).size.width - 60 - 16 - 16,
                        //                         child: Text(
                        //                             viewModel.comment.value?.data?[index].content ?? '<Không có nội dung>',
                        //                             style: const TextStyle(fontSize: 12))),
                        //                     const SizedBox(height: 8),
                        //                     // TODO: choichip add comment
                        //                     // Row(
                        //                     //   children: [
                        //                     //     Container(
                        //                     //       height: 30,
                        //                     //       width: 130,
                        //                     //       decoration: BoxDecoration(
                        //                     //         color: UIColors.brandA,
                        //                     //         borderRadius: BorderRadius.circular(20),
                        //                     //       ),
                        //                     //       child: const Align(
                        //                     //         alignment: Alignment.center,
                        //                     //         child: Text("Sản phẩm chất lượng",
                        //                     //             style: TextStyle(
                        //                     //                 fontSize: 10.0,
                        //                     //                 fontWeight: FontWeight.w400,
                        //                     //                 color: Colors.white)),
                        //                     //       ),
                        //                     //     ),
                        //                     //     Padding(
                        //                     //       padding: const EdgeInsets.only(left: 8.0),
                        //                     //       child: Container(
                        //                     //         height: 30,
                        //                     //         width: 100,
                        //                     //         decoration: BoxDecoration(
                        //                     //           color: UIColors.brandA,
                        //                     //           borderRadius:
                        //                     //           BorderRadius.circular(20),
                        //                     //         ),
                        //                     //         child: const Align(
                        //                     //           alignment: Alignment.center,
                        //                     //           child: Text("Giao hàng nhanh",
                        //                     //               style: TextStyle(
                        //                     //                   fontSize: 10.0,
                        //                     //                   fontWeight: FontWeight.w400,
                        //                     //                   color: Colors.white)),
                        //                     //         ),
                        //                     //       ),
                        //                     //     ),
                        //                     //   ],
                        //                     // ),
                        //                     SizedBox(
                        //                       width: MediaQuery.of(context).size.width - 60 - 16 - 16,
                        //                       child: const Divider(
                        //                           height: 25,
                        //                           thickness: 1,
                        //                           color: Colors.grey),
                        //                     ),
                        //                   ],
                        //                 ),
                        //               ],
                        //             ),
                        //       );
                        //     }
                        // ),
                        // Obx(
                        //         () {
                        //       return Visibility(
                        //         visible: viewModel.commentCount.value < (viewModel.comment.value?.data?.length ?? 0),
                        //         child: TextButton(
                        //             onPressed: () {
                        //               viewModel.commentCount.value = viewModel.commentCount.value + 6;
                        //             },
                        //             child: Row(
                        //               mainAxisAlignment: MainAxisAlignment.center,
                        //               children: [
                        //                 const Text("Xem thêm",
                        //                     style: TextStyle(
                        //                         fontSize: 12,
                        //                         fontWeight: FontWeight.w400)),
                        //                 Container(
                        //                   padding: const EdgeInsets.only(left: 5),
                        //                   child: SvgPicture.asset(IconAssets.chervon1,
                        //                       width: 10, color: UIColors.brandA),
                        //                 ),
                        //               ],
                        //             )),
                        //       );
                        //     }
                        // ),
                      ],
                    ),
                  ),
                  Container(
                    padding: const EdgeInsets.fromLTRB(16,0,16,16),
                    width: MediaQuery.of(context).size.width * 1,
                    // height: MediaQuery.of(context).size.height * 1,
                    decoration: const BoxDecoration(color: Colors.white),
                    child: Column(
                      children: [
                        Container(
                          alignment: Alignment.topLeft,
                          child: const Text("Đánh giá",
                              style: TextStyle(
                                  fontSize: 12, fontWeight: FontWeight.w700)),
                        ),
                        const SizedBox(height: 8),
                        // Obx(
                        //         () {
                        //       return Column(
                        //         children: [
                        //           Row(
                        //             children: [
                        //               SizedBox(
                        //                 child: ClipRRect(
                        //                   borderRadius: BorderRadius.circular(9999),
                        //                   child: GlobalImage(
                        //                     viewModel.dataUser.value?.data?.link_avatar?.toString(),
                        //                     width: 54,
                        //                     height: 54,
                        //                   ),
                        //                 ),
                        //               ),
                        //               const SizedBox(width: 8,),
                        //               Text(viewModel.dataUser.value?.data?.full_name ?? '<Khuyết danh>',
                        //                 style: const TextStyle(
                        //                   fontSize: 12,
                        //                   fontWeight: FontWeight.w700,
                        //                 ),
                        //               ),
                        //             ],
                        //           ),
                        //           const SizedBox(height: 8,),
                        //           // Obx(
                        //           //         () {
                        //           //       return Container(
                        //           //         alignment: Alignment.topLeft,
                        //           //         child: RatingBar.builder(
                        //           //           initialRating: viewModel.starVote.value.toDouble(),
                        //           //           minRating: 1,
                        //           //           itemSize: 18,
                        //           //           direction: Axis.horizontal,
                        //           //           allowHalfRating: false,
                        //           //           itemCount: 5,
                        //           //           itemPadding: const EdgeInsets.symmetric(horizontal: 8),
                        //           //           itemBuilder: (context, index) => const Icon(
                        //           //             Icons.star,
                        //           //             color: Color(0xAaFBA510),
                        //           //           ),
                        //           //           onRatingUpdate: (rating) {
                        //           //             viewModel.starVote.value = rating.round();
                        //           //           },
                        //           //         ),
                        //           //       );
                        //           //     }
                        //           // ),
                        //           const SizedBox(height: 8,),
                        //           // TODO: add choise chip
                        //           // Row(
                        //           //   children: [
                        //           //     Container(
                        //           //       height: 30,
                        //           //       width: 130,
                        //           //       decoration: BoxDecoration(
                        //           //         color: UIColors.brandA,
                        //           //         borderRadius: BorderRadius.circular(20),
                        //           //       ),
                        //           //       child: const Align(
                        //           //         alignment: Alignment.center,
                        //           //         child: Text("Sản phẩm chất lượng",
                        //           //             style: TextStyle(
                        //           //                 fontSize: 10.0,
                        //           //                 fontWeight: FontWeight.w400,
                        //           //                 color: Colors.white)),
                        //           //       ),
                        //           //     ),
                        //           //     Padding(
                        //           //       padding: const EdgeInsets.only(left: 8.0),
                        //           //       child: Container(
                        //           //         height: 30,
                        //           //         width: 100,
                        //           //         decoration: BoxDecoration(
                        //           //           color: UIColors.brandA,
                        //           //           borderRadius: BorderRadius.circular(20),
                        //           //         ),
                        //           //         child: const Align(
                        //           //           alignment: Alignment.center,
                        //           //           child: Text("Giao hàng nhanh",
                        //           //               style: TextStyle(
                        //           //                   fontSize: 10.0,
                        //           //                   fontWeight: FontWeight.w400,
                        //           //                   color: Colors.white)),
                        //           //         ),
                        //           //       ),
                        //           //     ),
                        //           //   ],
                        //           // ),
                        //           // const SizedBox(height: 8),
                        //           // GlobalInputFormWidget(
                        //           //   controller: viewModel.commentInput,
                        //           //   title: 'Bình luận',
                        //           //   requireInput: '',
                        //           //   hint: 'Viết nội dung...',
                        //           //   minLines: 3,
                        //           //   maxLines: 3,
                        //           // ),
                        //           const SizedBox(height: 6),
                        //           // FractionallySizedBox(
                        //           //   widthFactor: 1,
                        //           //   child: ElevatedButton(
                        //           //     onPressed: viewModel.sendComment,
                        //           //     child: Text(viewModel.dataUser.value?.data?.id != null ? 'Bình luận' : 'Đăng nhập để bình luận'),
                        //           //   ),
                        //           // ),
                        //         ],
                        //       );
                        //     }
                        // ),
                      ],
                    ),
                  ),
                  // Padding(
                  //   padding: const EdgeInsets.all(16.0),
                  //   child: Column(
                  //     crossAxisAlignment: CrossAxisAlignment.start,
                  //     children: const [
                  //       Text("Khám phá thêm",
                  //           style: TextStyle(
                  //               fontWeight: FontWeight.w700, fontSize: 14)),
                  //       SizedBox(height: 20),
                  //       ProductSellingScreen(), //  add link products to load
                  //       // TextButton(
                  //       //     style: TextButton.styleFrom(
                  //       //       padding:
                  //       //       const EdgeInsets.symmetric(horizontal: 16),
                  //       //     ),
                  //       //     onPressed: () {},
                  //       // child: Row(
                  //       //   mainAxisAlignment: MainAxisAlignment.center,
                  //       //   children: [
                  //       //     const Text(
                  //       //       "Xem thêm",
                  //       //       style: TextStyle(fontSize: 12),
                  //       //     ),
                  //       //     Container(
                  //       //       padding: const EdgeInsets.only(left: 5),
                  //       //       child: SvgPicture.asset(IconAssets.chervon1,
                  //       //           width: 10, color: UIColors.brandA),
                  //       //     ),
                  //       //   ],
                  //       // )),
                  //     ],
                  //   ),
                  // )
                ],
              ),
            ),
          ),
          Container(
            color: UIColors.white,
            width: double.infinity,
            padding: const EdgeInsets.all(12.0),
            child: Row(
              mainAxisAlignment: MainAxisAlignment.spaceAround,
              children: [
                InkWell(
                  onTap: (){

                  },
                  child: Container(
                    height: 36,
                    alignment: Alignment.center,
                    width: MediaQuery.of(context).size.width * 0.43,
                    decoration: BoxDecoration(
                        borderRadius:
                        const BorderRadius.all(Radius.circular(4)),
                        border: Border.all(
                          color: UIColors.brandA,
                          width: 1.0,
                        )),
                    child: const Text('Thêm vào giỏ',
                        style: TextStyle(
                            fontWeight: FontWeight.bold,
                            color: UIColors.brandA)),
                  ),
                ),
                SizedBox(
                  width: MediaQuery.of(context).size.width * 0.43,
                  child: ElevatedButton(
                    onPressed: (){

                    },
                    child: const Text('Mua ngay'),
                  ),
                ),
              ],
            ),
          ),
        ],
      ),
    );
  }

  @override
  ProductDetailModel createViewModel() => getIt<ProductDetailModel>();
}
