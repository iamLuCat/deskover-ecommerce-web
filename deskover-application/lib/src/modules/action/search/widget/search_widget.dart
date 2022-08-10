import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/action/search/search_model.dart';
import 'package:deskover_develop/src/modules/action/search/widget/search_result.dart';
import 'package:deskover_develop/src/themes/space_values.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';

class SearchWidget extends StatefulWidget {
  const SearchWidget({Key? key}) : super(key: key);

  @override
  State<SearchWidget> createState() => _SearchWidgetState();
}

class _SearchWidgetState extends ViewWidget<SearchWidget, SearchModel> {
  @override
  Widget build(BuildContext context) {
    return SafeArea(
      child: SingleChildScrollView(
          child: Padding(
            padding: const EdgeInsets.all(SpaceValues.space16),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
//                 Obx(
//                       () => Wrap(
//                     spacing: SpaceValues.space8,
//                     children: [
//                       for (int i = 0; i < viewModel.listCaterory.length; i++)
//                         if (viewModel.listCateroryFilter[i])
//                           Stack(
//                               clipBehavior: Clip.none, alignment: Alignment.bottomLeft, children: [
//                             Padding(
//                               padding: const EdgeInsets.only(
//                                   right: 12,
//                                   bottom: 12
//                               ),
//                               child: Chip(
//                                 label: Text(
//                                     viewModel.listCaterory[i].name.toString(),
//                                     style: TextStyle(color: UIColors.white,fontSize:12,
//                                     )),
//
//                                 backgroundColor: UIColors.title70,
//                               ),
//                             ),
//                             Positioned(
//                               top: 0,
//                               right: SpaceValues.space4,
//                               child: InkWell(
//                                 onTap: () =>
//                                 {viewModel.listCateroryFilter[i] = false},
//                                 child: Container(
//                                   decoration: BoxDecoration(
//                                       color: UIColors.white,
//                                       shape: BoxShape.circle),
//                                   child: SvgPicture.asset(
//                                     IconAssets.navigationClose,
//                                     height: 18,
//                                   ),
//                                 ),
//                               ),
//                             ),
//                           ]),
// /////////////////////////////////////
//                       for (int i = 0; i < viewModel.listWareHouse.length; i++)
//                         if (viewModel.listWareHouseFilter[i])
//                           Stack(
//                               clipBehavior: Clip.none, alignment: Alignment.bottomLeft, children: [
//                             Padding(
//                               padding: const EdgeInsets.only(
//                                   right: 12
//                               ),
//                               child: Chip(
//                                 label: Text(
//                                     viewModel.listWareHouse[i].name.toString(),
//                                     style: TextStyle(color: UIColors.white,fontSize:12,
//                                     )),
//
//                                 backgroundColor: UIColors.title70,
//                               ),
//                             ),
//                             Positioned(
//                               top: 0,
//                               right: 0,
//                               child: InkWell(
//                                 onTap: () =>
//                                 {viewModel.listWareHouseFilter[i] = false},
//                                 child: Container(
//                                   decoration: BoxDecoration(
//                                       color: UIColors.white,
//                                       shape: BoxShape.circle),
//                                   child: SvgPicture.asset(
//                                     IconAssets.navigationClose,
//                                     height: 18,
//                                   ),
//                                 ),
//                               ),
//                             ),
//                           ]),
//                     ],
//                   ),
//                 ),
                const SizedBox(height: SpaceValues.space16), const Text(
                  "Tìm kiếm phổ biến",
                  style: TextStyle(fontWeight: FontWeight.bold),
                ),
                const SizedBox(height: SpaceValues.space8),
                // Obx(
                //       () => Wrap(
                //     spacing: SpaceValues.space8,
                //     children: [
                //       for (int i = 0; i < viewModel.listKeyWord.length; i++)
                //         Padding(
                //           padding: const EdgeInsets.fromLTRB(0, 0, 0, SpaceValues.space8),
                //           child: ChoiceChip(
                //             label: Text(viewModel.listKeyWord[i].keyword.toString()),
                //             selected: false,
                //             selectedColor: UIColors.title70,
                //             labelStyle: TextStyle(color: UIColors.title50),
                //             onSelected: (value) {
                //               viewModel.search(name: viewModel.listKeyWord[i].keyword);
                //             },
                //           ),
                //         ),
                //     ],
                //   ),
                // ),
                const SizedBox(height: SpaceValues.space12),
                ListTile(
                  contentPadding: EdgeInsets.zero,
                  title: const Text(
                    "Danh mục đề xuất",
                    style: TextStyle(fontWeight: FontWeight.w700),
                  ),
                  tileColor: Colors.transparent,
                  trailing: const Text(
                    "Xem tất cả >",
                    style: TextStyle( fontSize: 12, fontWeight: FontWeight.w400, color: UIColors.brandA),
                  ),
                  onTap: () {
                    // Get.to(const ForgetPassWordScreen());
                  },
                ),
                Obx(
                      () => Wrap(
                    spacing: SpaceValues.space8,
                    children: [
                      for (int i = 0; i < viewModel.listCaterory.length; i++)
                        Padding(
                          padding: const EdgeInsets.fromLTRB(0, 0, 0, SpaceValues.space8),
                          child: ChoiceChip(
                            label: Text(viewModel.listCaterory[i].name.toString()),
                            selected: false,
                            selectedColor: UIColors.title70,
                            labelStyle: TextStyle(color: UIColors.title50),
                            onSelected: (value) {
                              viewModel.getProductSearch(viewModel.listCaterory[i].name.toString());
                            },
                          ),
                        ),
                    ],
                  ),
                ),
              ],
            ),
          )),
    );
  }

  @override
  SearchModel createViewModel() => getIt<SearchModel>();
}

class Search extends StatefulWidget {
  @override
  State<Search> createState() => _SearchState();
}

class _SearchState extends ViewWidget<Search, SearchModel> {
  @protected
  @mustCallSuper
  void dispose() {
    viewModel.index = 0.obs;
    super.dispose();
  }
  // SearchResultWidget(), SearchErrorWidget()
  var tabs = [SearchWidget(),SearchResultWidget() ];

  @override
  Widget build(BuildContext context) {
    return SingleChildScrollView(
      child: SafeArea(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            const Padding(
              padding: EdgeInsets.only(top: SpaceValues.space16, left: SpaceValues.space16),
              child: Text(
                "Nhập từ khóa",
                style: TextStyle(fontWeight: FontWeight.bold),
              ),
            ),
            const SizedBox(height: SpaceValues.space8),
            Padding(
              padding: const EdgeInsets.symmetric(horizontal: SpaceValues.space16),
              child: TextField(
                decoration: const InputDecoration(
                  border: OutlineInputBorder(),
                  hintText: 'Nhập tên sản phẩm hoặc thương hiệu',
                ),
                onSubmitted: (value) {
                  viewModel.getProductSearch(value);
                },
              ),
            ),
            Obx(() => tabs[viewModel.index.value])
          ],
        ),
      ),
    );
  }

  @override
  SearchModel createViewModel() => getIt<SearchModel>();
}
