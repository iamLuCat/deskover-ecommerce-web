import 'package:deskover_develop/src/config/base_api.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/homepage/homepage_model.dart';
import 'package:deskover_develop/src/modules/homepage/homepage_screen.dart';
import 'package:deskover_develop/src/modules/product_widget/product_widget.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';

import 'list_product_model.dart';

class ListProductNew extends StatefulWidget {
  final int categoryId;
  final String? title;

  const ListProductNew({Key? key, required this.categoryId, this.title})
      : super(key: key);
  @override
  State<StatefulWidget> createState() => _ListProductNew();
}

class _ListProductNew extends ViewWidget<ListProductNew, ListProductModel> {
  RangeValues _currentRangeValues = const RangeValues(40, 80);
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    viewModel.categoryId.value = widget.categoryId;
    viewModel.doGetProductByCate();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: Text(widget.title ?? ''),
        backgroundColor: UIColors.white,
      ),
      body: Column(
        children: [
          Expanded(
            child: Obx(
              () => ConstrainedBox(
                constraints: const BoxConstraints(maxHeight: 256),
                child: GridView.builder(
                  shrinkWrap: true,
                  padding: const EdgeInsets.symmetric(horizontal: 12),
                  itemCount: viewModel.dataProduct.value.length,
                  itemBuilder: (context, index) => InkWell(
                    onTap: () => Get.to(() => HomePage()),
                    child: ProductWidget(
                      productId: viewModel.dataProduct[index].id!,
                      title: viewModel.dataProduct[index].name ?? '',
                      avatar: BaseApi.baseUrl +
                          '/img/shop/products/${viewModel.dataProduct[index].img}',
                      price: viewModel.dataProduct[index].price!,
                      quantity: viewModel.dataProduct[index].quantity!,
                    ),
                  ),
                  gridDelegate: const SliverGridDelegateWithFixedCrossAxisCount(
                    mainAxisSpacing: 6,
                    crossAxisSpacing: 2,
                    crossAxisCount: 2,
                    mainAxisExtent: 270,
                    // childAspectRatio: 0.4,
                  ),
                ),
              ),
            ),
          ),
          Container(
            width: double.infinity,
            color: UIColors.white,
            child: TextButton(
              onPressed: () {
                viewModel.size.value = viewModel.size.value + 8;
                viewModel.loadProductNew();
              },
              child: Text(
                "Xem thêm >",
                style: TextStyle(
                    fontSize: 12,
                    fontWeight: FontWeight.w400,
                    color: UIColors.brandA),
              ),
            ),
          )
        ],
      ),
    );
  }

  @override
  ListProductModel createViewModel() => getIt<ListProductModel>();
}
