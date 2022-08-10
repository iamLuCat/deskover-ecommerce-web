import 'package:deskover_develop/src/config/base_api.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/product_widget/product_selling/product_selling_model.dart';
import 'package:deskover_develop/src/modules/product_widget/product_widget.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/widgets.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';

class ProductSellingScreen extends StatefulWidget{
  final int categoryId;
  const ProductSellingScreen({Key? key, required this.categoryId}) : super(key: key);

  @override
  State<StatefulWidget> createState() => _ProductSellingScreen();

}
class _ProductSellingScreen extends ViewWidget<ProductSellingScreen, ProductSellingModel>{
  @override
  void initState() {
    // TODO: implement initState
    super.initState();
    viewModel.categoryId.value = widget.categoryId;
    viewModel.doGetProductByCate();
  }
  @override
  Widget build(BuildContext context) {
    return Obx(
            () =>
            ConstrainedBox(
              constraints: const BoxConstraints(maxHeight: 256),
              child: ListView.separated(
                padding: const EdgeInsets.symmetric(horizontal: 12),
                scrollDirection: Axis.horizontal,
                itemCount: viewModel.dataProduct.length,
                separatorBuilder: (context, index) =>
                const SizedBox(width: 8),
                itemBuilder: (context, index) {
                  return ProductWidget(
                    productId: viewModel.dataProduct[index].id!,
                    title: viewModel.dataProduct[index].name ?? '',
                    discount: viewModel.dataProduct[index].discount?.percent ?? 0,
                    avatar: BaseApi.baseUrl_product + '${viewModel.dataProduct[index].img}',
                    price: ((viewModel.dataProduct.value[index].discount?.percent) ?? 0) !=0
                        ? viewModel.dataProduct.value[index].price! - (viewModel.dataProduct.value[index].price! * (viewModel.dataProduct.value[index].discount?.percent ?? 0)/100)
                        :  viewModel.dataProduct.value[index].price ?? 0,
                    quantity: viewModel.dataProduct[index].quantity!,
                  );
                },
              ),
            )
          );
  }

  @override
  ProductSellingModel createViewModel() => getIt<ProductSellingModel>();

}
