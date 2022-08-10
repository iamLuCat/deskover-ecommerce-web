import 'package:badges/badges.dart';
import 'package:deskover_develop/src/config/assets/icon_assets.dart';
import 'package:deskover_develop/src/config/assets/image_asset.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/action/search/search_model.dart';
import 'package:deskover_develop/src/modules/action/search/widget/search_widget.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';


class SearchPage extends StatefulWidget {
  const SearchPage({Key? key}) : super(key: key);

  @override
  State<SearchPage> createState() => _SearchPageState();
}

class _SearchPageState extends ViewWidget<SearchPage, SearchModel> {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Tìm kiếm sản phẩm",
            style: TextStyle(fontWeight: FontWeight.w700, fontSize: 14)),
        backgroundColor: UIColors.white,
        actions: [
          // IconButton(
          //   onPressed: () {
          //     // Get.to(FilterPage(viewModel: viewModel,codeCategorychose: List.from(viewModel.listCateroryFilter),codeWereHouseChose: List.from(viewModel.listWareHouseFilter),));
          //   },
          //   icon: SvgPicture.asset(IconAssets.actionFilterAlt),
          // ),
          // Padding(
          //   padding: const EdgeInsets.fromLTRB(0, 8, 8, 8.0),
          //   child: StreamBuilder(
          //     initialData: getIt<NumCartDetail>().number,
          //     stream: getIt<NumCartDetail>().getStreamController.stream,
          //     builder: (context, snapshot) {
          //       return Badge(
          //         showBadge: (snapshot.data ?? 0) != 0,
          //         shape: BadgeShape.circle,
          //         position: BadgePosition.topEnd(top: 0, end: 3),
          //         badgeContent: Text(
          //           '${snapshot.data ?? ''}',
          //           style: const TextStyle(
          //             color: Colors.white,
          //             fontWeight: FontWeight.w700,
          //             fontSize: 10,
          //           ),
          //         ),
          //         badgeColor: UIColors.brandA,
          //         animationType: BadgeAnimationType.fade,
          //         child: IconButton(
          //           onPressed: () {
          //             // Get.to(const CardManagerScreen());
          //           },
          //           icon: SvgPicture.asset(SvgImageAssets.imgcard),
          //         ),
          //       );
          //     },
          //   ),
          // ),
        ],
      ),
      backgroundColor: Colors.white,
      body: Search(),
    );
  }

  @override
  SearchModel createViewModel() => getIt<SearchModel>();
}
