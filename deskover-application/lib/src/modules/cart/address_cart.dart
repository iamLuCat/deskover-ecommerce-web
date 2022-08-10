import 'package:deskover_develop/src/config/assets/icon_assets.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/address/add_addrees/add_address.dart';
import 'package:deskover_develop/src/modules/cart/cart_model.dart';
import 'package:deskover_develop/src/themes/dialogs/loading_dialog.dart';
import 'package:deskover_develop/src/themes/space_values.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/svg.dart';
import 'package:get/get.dart';

class AddressCart extends StatefulWidget{
  const AddressCart({Key? key}) : super(key: key);
  @override
  State<AddressCart> createState() => _AddressCart();
}

class _AddressCart extends ViewWidget<AddressCart,CartModel> {
  @override
  Widget build(BuildContext context) {
    viewModel.loadAddress();
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Colors.white,
        iconTheme: const IconThemeData(
          color: Colors.black,
        ),
        title: const Text("Sổ địa chỉ"),
      ),
      body: RefreshIndicator(
        onRefresh: ()=> Future.wait([viewModel.loadAddress()]),
        child: Column(
          children: [
            Obx(() {
              return Visibility(
                visible: viewModel.dataAddress.value != null,
                child: Expanded(
                  child: ListView(
                    children: [
                      Visibility(
                        visible: viewModel.dataAddress.value.length > 0,
                        child: ListView.builder(
                          shrinkWrap: true,
                          physics: const NeverScrollableScrollPhysics(),
                          itemCount: viewModel.dataAddress.value.length,
                          itemBuilder: (subcontext, index) {
                            return Container(
                              margin: const EdgeInsets.only(top: SpaceValues.space8),
                              padding: const EdgeInsets.fromLTRB(16,0,16,12),
                              decoration: BoxDecoration(
                                borderRadius: BorderRadius.circular(5),
                                color: Colors.white,
                              ),
                              child: Column(
                                children: [
                                  Row(
                                    mainAxisAlignment: MainAxisAlignment.start,
                                    children: [
                                      RichText(
                                        text: TextSpan(
                                            text: viewModel.dataAddress[index].fullname ?? '...',
                                            style: const TextStyle(
                                                fontSize: 12.0,
                                                color: UIColors.brandA,
                                                fontWeight: FontWeight.w600),
                                            children: [
                                              TextSpan(
                                                text: (viewModel.dataAddress[index].actived ?? false) == true ? '  (Mặc định)' : '',
                                                style: const TextStyle(
                                                  fontSize: 10.0,
                                                  color: UIColors.brandA,
                                                ),
                                              ),
                                            ]),
                                      ),
                                      SizedBox(width: 8,),
                                      SvgPicture.asset(viewModel.dataAddress[index].choose == true ? IconAssets.actionCheckCircle : ''),
                                      Expanded(child: SizedBox()),
                                      PopupMenuButton<int>(
                                        icon: SvgPicture.asset(IconAssets.list1,),
                                        onSelected: (val) {
                                          switch(val) {
                                            case 0: // edit
                                              Get.to(()=>NotAddressPage(defaultAddress: viewModel.dataAddress[index],));
                                              break;
                                            case 1:
                                              viewModel.btnChooseAddress(viewModel.dataAddress[index].id ?? 0);
                                              Get.back();
                                              break;

                                          }
                                        },
                                        itemBuilder: (BuildContext subcontext) {
                                          return [
                                            const PopupMenuItem(
                                              child: Text('Chỉnh sửa'),
                                              value: 0,
                                            ),
                                           const PopupMenuItem(
                                              child: Text('Chọn'),
                                              value: 1,
                                            )

                                          ];
                                        },
                                      )
                                    ],
                                  ),
                                  Row(
                                    children: [
                                      SvgPicture.asset(
                                        IconAssets.phone1,
                                      ),
                                      const SizedBox(
                                        width: 20,
                                      ),
                                      Text(
                                        viewModel.dataAddress[index].tel ?? '...',
                                        style: const TextStyle(fontSize: 12),
                                      ),
                                    ],
                                  ),
                                  const SizedBox(height: SpaceValues.space12,),
                                  Row(
                                    children: [
                                      SvgPicture.asset(
                                        IconAssets.address1,
                                      ),
                                      const SizedBox(
                                        width: 20,
                                      ),
                                      SizedBox(
                                        width: MediaQuery.of(context).size.width * 0.70,
                                        child: Text(
                                          viewModel.dataAddress[index].address ?? '...',
                                          maxLines: 2,
                                          style: const TextStyle(fontSize: 12),
                                        ),
                                      ),
                                    ],
                                  ),
                                ],
                              ),
                            );
                          },
                        ),
                        replacement: const Padding(
                          padding: EdgeInsets.symmetric(horizontal: SpaceValues.space16),
                          child: Text(
                            'Không có địa chỉ nào!',
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
                replacement: const Expanded(
                  child: LoadingDialog(
                    elevation: 0,
                    backgroundColor: Colors.transparent,
                    message: 'Đang tìm địa chỉ...',
                  ),
                ),
              );
            }),
            Container(
              padding: const EdgeInsets.all(SpaceValues.space16),
              child: FractionallySizedBox(
                widthFactor: 1,
                child: ElevatedButton(
                  onPressed: (){
                    Get.to(()=>NotAddressPage());
                  },
                  child: const Text('Thêm địa chỉ'),
                ),
              ),
            ),
          ],
        ),
      ),
    );
  }

  @override
  CartModel  createViewModel() => getIt<CartModel>();
}

