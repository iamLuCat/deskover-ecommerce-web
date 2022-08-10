import 'package:deskover_develop/src/config/base_api.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/homepage/widgets/list_product_new.dart';
import 'package:deskover_develop/src/modules/product_widget/product_widget.dart';
import 'package:deskover_develop/src/modules/subcategory/subcategory_screen.dart';
import 'package:deskover_develop/src/themes/dialogs/loading_dialog.dart';
import 'package:deskover_develop/src/themes/space_values.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_countdown_timer/flutter_countdown_timer.dart';
import 'package:flutter_image_slideshow/flutter_image_slideshow.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import 'package:intl/intl.dart';

import '../../themes/ui_colors.dart';
import '../global_modules/widget/global_image.dart';
import 'homepage_model.dart';

class HomePage extends StatefulWidget {
  const HomePage({Key? key}) : super(key: key);

  @override
  State<HomePage> createState() => _HomePageState();
}

class _HomePageState extends ViewWidget<HomePage,HomePageModel> {

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: RefreshIndicator(
        onRefresh: ()=> viewModel.resfresh(),
        child: SingleChildScrollView(
          child: Column(
            children: [
              AspectRatio(
                aspectRatio: 16 / 7,
                child: ImageSlideshow(
                    width: double.infinity,
                    initialPage: 0,
                    indicatorColor: UIColors.brandA,
                    indicatorBackgroundColor: Colors.grey,
                    onPageChanged: (value) {
                      //debugPrint('Page changed: $value');
                    },
                    autoPlayInterval: 3000,
                    isLoop: true,
                    children: const [
                      GlobalImage(
                        'https://cdn2.cellphones.com.vn/690x300/https://dashboard.cellphones.com.vn/storage/690-300%20poco.png',
                        fit: BoxFit.fill,
                      ),
                      GlobalImage(
                        'https://cdn2.cellphones.com.vn/690x300/https://dashboard.cellphones.com.vn/storage/banner-huawei-tbvp.png',
                        fit: BoxFit.fill,
                      ),
                      GlobalImage(
                        'https://cdn2.cellphones.com.vn/690x300/https://dashboard.cellphones.com.vn/storage/CPS_690x300_19July22.jpg',
                        fit: BoxFit.fill,
                      )
                    ]),
              ),
              Container(
                color: UIColors.white,
                child: Column(
                  children: [
                    const SizedBox(
                      height: SpaceValues.space16,
                    ),
                    const Center(
                      child: Text(
                        "DANH MỤC NỔI BẬT",
                        textAlign: TextAlign.center,
                        style:
                            TextStyle(fontWeight: FontWeight.w700, fontSize: 14),
                      ),
                    ),
                    Obx(
                       ()=> Visibility(
                         visible: viewModel.DataCategory.value.isNotEmpty,
                         child: SizedBox(
                           height: (viewModel.DataCategory.value.length) > 8
                               ? 230
                               : 230 / 2,
                           child: Center(
                             child: Obx(
                                   ()=> GridView.builder(
                                 // primary: false,
                                 shrinkWrap: true,
                                 scrollDirection: Axis.horizontal,
                                 // physics: const NeverScrollableScrollPhysics(),
                                 padding: const EdgeInsets.all(0),
                                 itemCount: viewModel.DataCategory.length,
                                 gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                                   crossAxisCount: (viewModel.DataCategory.length) > 8
                                       ? 2
                                       : 1,
                                   mainAxisExtent: 80,
                                   childAspectRatio: 2,
                                   mainAxisSpacing: 12,
                                   crossAxisSpacing: 8,
                                 ),
                                 itemBuilder: (context, i) {
                                   return TextButton(
                                     style: TextButton.styleFrom(
                                         padding: EdgeInsets.zero,
                                         tapTargetSize: MaterialTapTargetSize.shrinkWrap,
                                         minimumSize: Size.zero),
                                     onPressed: () {
                                       Get.to(()=> SubCategoryScreen(categoryId: viewModel.DataCategory[i].id!, title:  viewModel.DataCategory[i].name!,));
                                     },
                                     child: Column(
                                       crossAxisAlignment: CrossAxisAlignment.center,
                                       mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                       children: [
                                         SizedBox(
                                           width: 60,
                                           height: 60,
                                           child: ClipRRect(
                                             borderRadius: BorderRadius.circular(999),
                                             child: GlobalImage(
                                               BaseApi.baseUrl+'/img/shop/categories/${viewModel.DataCategory[i].img}',
                                               fit: BoxFit.cover,
                                             ),
                                           ),
                                         ),
                                         SizedBox(
                                           height: 31,
                                           child: Padding(
                                             padding: const EdgeInsets.symmetric(horizontal: 3),
                                             child: Text(
                                               viewModel.DataCategory[i].name ?? '',
                                               textAlign: TextAlign.center,
                                               maxLines: 2,
                                               overflow: TextOverflow.ellipsis,
                                               style: const TextStyle(
                                                 fontSize: 10,
                                                 fontWeight: FontWeight.w400,
                                                 color: UIColors.black,
                                               ),
                                             ),
                                           ),
                                         ),
                                       ],
                                     ),
                                   );
                                 },
                               ),
                             ),

                           ),
                         ),
                         replacement: const Center(
                           child: LoadingDialog(
                             backgroundColor: Colors.transparent,
                             elevation: 0,
                             message: 'Đang tìm các danh mục...',
                           ),
                         ),
                       ),)

                  ],
                ),
              ),
              Container(
                margin: const EdgeInsets.only(top: SpaceValues.space6),
                color: UIColors.white,
                child: Column(
                  children: [
                    Obx(
                            () {
                          return Visibility(
                            visible: viewModel.dataProductFlashSale.length > 0,
                            child: Visibility(
                              visible: viewModel.flashSale.value,
                              child: Container(
                                color: UIColors.brandA,
                                child: Column(
                                  children: [
                                    Padding(
                                      padding: const EdgeInsets.fromLTRB(16,12,16,0),
                                      child: Row(
                                        children: [
                                          SvgPicture.asset(
                                            "resources/icons/flash_on.svg",
                                            height: 20,
                                            width: 20,
                                            color: Colors.yellow,
                                          ),
                                          const Expanded(
                                            child: Text(
                                              ' Deal sắp diễn ra',
                                              style: TextStyle(
                                                color: UIColors.white,
                                                fontWeight: FontWeight.w700,
                                              ),
                                            ),
                                          ),
                                        ],
                                      ),
                                    ),
                                    Padding(
                                      padding: const EdgeInsets.fromLTRB(16,8,16,8),
                                      child: Row(
                                        children: [
                                          const Expanded(
                                            child: Text(
                                              'Thời gian bắt đầu sau',
                                              style: TextStyle(
                                                color: UIColors.white,
                                              ),
                                            ),
                                          ),
                                          CountdownTimer(
                                            endTime: DateTime.parse(
                                                (viewModel.dataProductFlashSale).isNotEmpty ?
                                                viewModel.dataProductFlashSale.value.first.flashSale?.startDate ?? DateFormat('yyyy-MM-dd HH:mm:ss').format(DateTime.now().add(const Duration(minutes: 1)))
                                                    : DateFormat('yyyy-MM-dd HH:mm:ss').format(DateTime.now().add(const Duration(minutes: 1)))).millisecondsSinceEpoch,
                                            // endTime: DateFormat('yyyy-MM-dd HH:mm:ss').parse(
                                            //     (viewModel.dataProductFlashSale.value).isNotEmpty ?
                                            //     ('${viewModel.dataProductFlashSale.value.first.flashSale?.endDate?.substring(0,10)} ${viewModel.dataProductFlashSale.value.first.flashSale?.endDate?.substring(11,18)}')
                                            //         : DateFormat('yyyy-MM-dd HH:mm:ss').format(DateTime.now().add(const Duration(minutes: 1)))).millisecondsSinceEpoch,
                                            // endTime: DateFormat('yyyy-MM-dd HH:mm:ss').parse(viewModel.dataProductFlashSale.value.first.flashSale?.endDate.toString() ?? '2022-07-29 23:12:00').millisecondsSinceEpoch,
                                            onEnd: () async {
                                              await viewModel.loadProductSale();
                                              viewModel.flashSale.value = false;
                                            },

                                            widgetBuilder: (context, time) {
                                              return Row(
                                                children: [
                                                  Container(
                                                    padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
                                                    decoration: BoxDecoration(
                                                      color: UIColors.white,
                                                      borderRadius: BorderRadius.circular(4),
                                                    ),
                                                    child: Text(
                                                      (time?.days ?? 0).toString().length < 2 ? '0${time?.days ?? 0}' : '${time?.days ?? 0}',
                                                      style: const TextStyle(
                                                        color: UIColors.brandA,
                                                      ),
                                                    ),
                                                  ),
                                                  const Text(
                                                    ' : ',
                                                    style: TextStyle(
                                                      color: UIColors.white,
                                                    ),
                                                  ),
                                                  Container(
                                                    padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
                                                    decoration: BoxDecoration(
                                                      color: UIColors.white,
                                                      borderRadius: BorderRadius.circular(4),
                                                    ),
                                                    child: Text(
                                                      (time?.hours ?? 0).toString().length < 2 ? '0${time?.hours ?? 0}' : '${time?.hours ?? 0}',
                                                      style: const TextStyle(
                                                        color: UIColors.brandA,
                                                      ),
                                                    ),
                                                  ),
                                                  const Text(
                                                    ' : ',
                                                    style: TextStyle(
                                                      color: UIColors.white,
                                                    ),
                                                  ),
                                                  Container(
                                                    padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
                                                    decoration: BoxDecoration(
                                                      color: UIColors.white,
                                                      borderRadius: BorderRadius.circular(4),
                                                    ),
                                                    child: Text(
                                                      (time?.min ?? 0).toString().length < 2 ? '0${time?.min ?? 0}' : '${time?.min ?? 0}',
                                                      style: const TextStyle(
                                                        color: UIColors.brandA,
                                                      ),
                                                    ),
                                                  ),
                                                  const Text(
                                                    ' : ',
                                                    style: TextStyle(
                                                      color: UIColors.white,
                                                    ),
                                                  ),
                                                  Container(
                                                    padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
                                                    decoration: BoxDecoration(
                                                      color: UIColors.white,
                                                      borderRadius: BorderRadius.circular(4),
                                                    ),
                                                    child: Text(
                                                      (time?.sec ?? 0).toString().length < 2 ? '0${time?.sec ?? 0}' : '${time?.sec ?? 0}',
                                                      style: const TextStyle(
                                                        color: UIColors.brandA,
                                                      ),
                                                    ),
                                                  ),
                                                ],
                                              );
                                            },
                                          )
                                        ],
                                      ),
                                    ),
                                    const SizedBox(height: 12,),
                                    ConstrainedBox(
                                      constraints: const BoxConstraints(maxHeight: 256),
                                      child: ListView.separated(
                                        padding: const EdgeInsets.symmetric(horizontal: 12),
                                        scrollDirection: Axis.horizontal,
                                        itemCount: viewModel.dataProductFlashSale.length,
                                        separatorBuilder: (context, index) => const SizedBox(width: 8),
                                        itemBuilder: (context, index) {
                                          return ProductWidget(
                                            productId: viewModel.dataProductFlashSale.value[index].id ?? 0,
                                            avatar: BaseApi.baseUrl_product+ '${viewModel.dataProductFlashSale.value[index].img}',
                                            title: viewModel.dataProductFlashSale.value[index].name ?? '',
                                            isCanBuy: false,
                                            flashSale: false,
                                            quantity: viewModel.dataProductFlashSale.value[index].quantity ?? 0,
                                            promotion: '${viewModel.dataProductFlashSale.value[index].discount?.percent}%',
                                            discount: viewModel.dataProductFlashSale.value[index].discount?.percent ?? 0,
                                            price: ((viewModel.dataProductFlashSale.value[index].discount?.percent) ?? 0) !=0
                                                ? viewModel.dataProductFlashSale.value[index].price! - (viewModel.dataProductFlashSale.value[index].price!
                                                * (viewModel.dataProductFlashSale.value[index].discount?.percent ?? 0)/100)
                                                :  viewModel.dataProductFlashSale.value[index].price ?? 0,
                                            priceOrigin:viewModel.dataProductFlashSale.value[index].price ?? 0,
                                          );
                                        },
                                      ),
                                    ),
                                    const SizedBox(height: 16,),
                                  ],
                                ),
                              ),
                              replacement:
                              Container(
                              color: UIColors.brandA,
                              child: Column(
                                children: [
                                  Padding(
                                    padding: const EdgeInsets.fromLTRB(16,12,16,0),
                                    child: Row(
                                      children: [
                                        SvgPicture.asset(
                                          "resources/icons/flash_on.svg",
                                          height: 20,
                                          width: 20,
                                          color: Colors.yellow,
                                        ),
                                        const Expanded(
                                          child: Text(
                                            ' Deal đang diễn ra',
                                            style: TextStyle(
                                              color: UIColors.white,
                                              fontWeight: FontWeight.w700,
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                  ),
                                  Padding(
                                    padding: const EdgeInsets.fromLTRB(16,8,16,8),
                                    child: Row(
                                      children: [
                                        const Expanded(
                                          child: Text(
                                            'Thời gian kết thúc',
                                            style: TextStyle(
                                              color: UIColors.white,
                                            ),
                                          ),
                                        ),
                                        CountdownTimer(
                                          endTime: DateTime.parse(
                                              (viewModel.dataProductFlashSale).isNotEmpty ?
                                              viewModel.dataProductFlashSale.value.first.flashSale?.endDate ?? DateFormat('yyyy-MM-dd HH:mm:ss').format(DateTime.now().add(const Duration(minutes: 1)))
                                                  : DateFormat('yyyy-MM-dd HH:mm:ss').format(DateTime.now().add(const Duration(minutes: 1)))).millisecondsSinceEpoch,
                                          onEnd: () async {
                                           await viewModel.loadProductSale();
                                            viewModel.dataProductFlashSale.value = [];
                                          },

                                          widgetBuilder: (context, time) {
                                            return Row(
                                              children: [
                                                Container(
                                                  padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
                                                  decoration: BoxDecoration(
                                                    color: UIColors.white,
                                                    borderRadius: BorderRadius.circular(4),
                                                  ),
                                                  child: Text(
                                                    (time?.days ?? 0).toString().length < 2 ? '0${time?.days ?? 0}' : '${time?.days ?? 0}',
                                                    style: const TextStyle(
                                                      color: UIColors.brandA,
                                                    ),
                                                  ),
                                                ),
                                                const Text(
                                                  ' : ',
                                                  style: TextStyle(
                                                    color: UIColors.white,
                                                  ),
                                                ),
                                                Container(
                                                  padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
                                                  decoration: BoxDecoration(
                                                    color: UIColors.white,
                                                    borderRadius: BorderRadius.circular(4),
                                                  ),
                                                  child: Text(
                                                    (time?.hours ?? 0).toString().length < 2 ? '0${time?.hours ?? 0}' : '${time?.hours ?? 0}',
                                                    style: const TextStyle(
                                                      color: UIColors.brandA,
                                                    ),
                                                  ),
                                                ),
                                                const Text(
                                                  ' : ',
                                                  style: TextStyle(
                                                    color: UIColors.white,
                                                  ),
                                                ),
                                                Container(
                                                  padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
                                                  decoration: BoxDecoration(
                                                    color: UIColors.white,
                                                    borderRadius: BorderRadius.circular(4),
                                                  ),
                                                  child: Text(
                                                    (time?.min ?? 0).toString().length < 2 ? '0${time?.min ?? 0}' : '${time?.min ?? 0}',
                                                    style: const TextStyle(
                                                      color: UIColors.brandA,
                                                    ),
                                                  ),
                                                ),
                                                const Text(
                                                  ' : ',
                                                  style: TextStyle(
                                                    color: UIColors.white,
                                                  ),
                                                ),
                                                Container(
                                                  padding: const EdgeInsets.symmetric(horizontal: 4, vertical: 2),
                                                  decoration: BoxDecoration(
                                                    color: UIColors.white,
                                                    borderRadius: BorderRadius.circular(4),
                                                  ),
                                                  child: Text(
                                                    (time?.sec ?? 0).toString().length < 2 ? '0${time?.sec ?? 0}' : '${time?.sec ?? 0}',
                                                    style: const TextStyle(
                                                      color: UIColors.brandA,
                                                    ),
                                                  ),
                                                ),
                                              ],
                                            );
                                          },
                                        )
                                      ],
                                    ),
                                  ),
                                  const SizedBox(height: 12,),
                                  ConstrainedBox(
                                    constraints: const BoxConstraints(maxHeight: 256),
                                    child: ListView.separated(
                                      padding: const EdgeInsets.symmetric(horizontal: 12),
                                      scrollDirection: Axis.horizontal,
                                      itemCount: viewModel.dataProductFlashSale.length,
                                      separatorBuilder: (context, index) => const SizedBox(width: 8),
                                      itemBuilder: (context, index) {
                                        return ProductWidget(
                                          productId: viewModel.dataProductFlashSale.value[index].id ?? 0,
                                          avatar: BaseApi.baseUrl_product+ '${viewModel.dataProductFlashSale.value[index].img}',
                                          title: viewModel.dataProductFlashSale.value[index].name ?? '',
                                          isCanBuy: (viewModel.dataProductFlashSale.value[index].quantity ?? 0) > 0,
                                          quantity: viewModel.dataProductFlashSale.value[index].quantity ?? 0,
                                          promotion: '${viewModel.dataProductFlashSale.value[index].discount?.percent}%',
                                          discount: viewModel.dataProductFlashSale.value[index].discount?.percent ?? 0,
                                          price: ((viewModel.dataProductFlashSale.value[index].discount?.percent) ?? 0) !=0
                                              ? viewModel.dataProductFlashSale.value[index].price! - (viewModel.dataProductFlashSale.value[index].price!
                                              * (viewModel.dataProductFlashSale.value[index].discount?.percent ?? 0)/100)
                                              :  viewModel.dataProductFlashSale.value[index].price ?? 0,
                                          priceOrigin:viewModel.dataProductFlashSale.value[index].price ?? 0,
                                        );
                                      },
                                    ),
                                  ),
                                  const SizedBox(height: 16,),
                                  ListTile(
                                    dense: true,
                                    title: const Text(
                                      "KHUYẾN MÃI CHO BẠN",
                                      style: TextStyle(
                                          color: UIColors.white,
                                          fontWeight: FontWeight.w700,
                                          fontSize: 14),
                                    ),
                                    tileColor: Colors.transparent,
                                    trailing: const Text(
                                      "Xem thêm >",
                                      style: TextStyle(

                                          fontSize: 12,
                                          fontWeight: FontWeight.w400,
                                          color: UIColors.white),
                                    ),
                                    onTap: () {
                                      // Get.to(const FlashSalePage());
                                    },
                                  ),
                                ],
                              ),
                            ),
                            ),
                            replacement: SizedBox(child: Image.asset('resources/images/he-poco-720-220-720x220.png',fit: BoxFit.fill,))
                          );
                        }
                    ),
                  ],
                ),
              ),
              Container(
                margin: const EdgeInsets.only(top: SpaceValues.space6),
                color: UIColors.white,
                child: Column(
                  children: [
                    ListTile(
                      title: const Text(
                        "SẢN PHẨM MỚI",
                        style: TextStyle(fontWeight: FontWeight.w700),
                      ),
                      tileColor: Colors.transparent,
                      trailing: const Text(
                        "Xem thêm >",
                        style: TextStyle(
                            fontSize: 12,
                            fontWeight: FontWeight.w400,
                            color: UIColors.brandA),
                      ),
                      onTap: () {
                        Get.to(ListProductNew(categoryId:viewModel.dataProductNew.first.subCategory?.category?.id ?? 1 ,title: "Sản phẩm mới",));
                      },
                    ),
                    Obx(
                       ()=>    ConstrainedBox(
                         constraints: const BoxConstraints(maxHeight: 256),
                         child: ListView.separated(
                           padding: const EdgeInsets.symmetric(horizontal: 12),
                           scrollDirection: Axis.horizontal,
                           itemCount: viewModel.dataProductNew.value.length,
                           separatorBuilder: (context, index) =>
                           const SizedBox(width: 8),
                           itemBuilder: (context, index) {
                             return ProductWidget(
                               productId:  viewModel.dataProductNew[index].id!,
                               title: viewModel.dataProductNew[index].name ?? '',
                               discount: viewModel.dataProductNew[index].discount?.percent ?? 0,
                               avatar: BaseApi.baseUrl+'/img/shop/products/${viewModel.dataProductNew[index].img}',
                               price: ((viewModel.dataProductNew.value[index].discount?.percent) ?? 0) !=0
                                   ? viewModel.dataProductNew.value[index].price! - (viewModel.dataProductNew.value[index].price! * (viewModel.dataProductNew.value[index].discount?.percent ?? 0)/100)
                                   :  viewModel.dataProductNew.value[index].price ?? 0,
                               quantity: viewModel.dataProductNew[index].quantity!,
                             );
                           },
                         ),
                       ),),
                    const SizedBox(
                      height: 16,
                    ),
                  ],
                ),
              ),
              Container(
                margin: const EdgeInsets.only(top: SpaceValues.space6),
                color: UIColors.white,
                child: Column(
                  children: [
                    ListTile(
                      title: const Text(
                        "KHUYẾN MÃI ƯU ĐÃI",
                        style: TextStyle(fontWeight: FontWeight.w700),
                      ),
                      tileColor: Colors.transparent,
                      trailing: const Text(
                        "Xem thêm >",
                        style: TextStyle(
                            fontSize: 12,
                            fontWeight: FontWeight.w400,
                            color: UIColors.brandA),
                      ),
                      onTap: () {
                        Get.to(ListProductNew(categoryId:viewModel.dataProductNew.first.subCategory?.category?.id ?? 1 ,title: "Sản phẩm mới",));
                      },
                    ),
                    Obx(
                          ()=>    ConstrainedBox(
                        constraints: const BoxConstraints(maxHeight: 256),
                        child: ListView.separated(
                          padding: const EdgeInsets.symmetric(horizontal: 12),
                          scrollDirection: Axis.horizontal,
                          itemCount: viewModel.dataProductNew.value.length,
                          separatorBuilder: (context, index) =>
                          const SizedBox(width: 8),
                          itemBuilder: (context, index) {
                            return ProductWidget(
                              productId:  viewModel.dataProductNew[index].id!,
                              title: viewModel.dataProductNew[index].name ?? '',
                              discount: viewModel.dataProductNew[index].discount?.percent ?? 0,
                              avatar: BaseApi.baseUrl+'/img/shop/products/${viewModel.dataProductNew[index].img}',
                              price: ((viewModel.dataProductNew.value[index].discount?.percent) ?? 0) !=0
                                  ? viewModel.dataProductNew.value[index].price! - (viewModel.dataProductNew.value[index].price! * (viewModel.dataProductNew.value[index].discount?.percent ?? 0)/100)
                                  :  viewModel.dataProductNew.value[index].price ?? 0,
                              quantity: viewModel.dataProductNew[index].quantity!,
                            );
                          },
                        ),
                      ),),
                    const SizedBox(
                      height: 16,
                    ),
                  ],
                ),
              ),
              SizedBox(
                height: MediaQuery.of(context).size.height * .25,
              ),
            ],
          ),
        ),
      ),
    );
  }

  @override
  HomePageModel createViewModel() => getIt<HomePageModel>();
}
