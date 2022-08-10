import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';

import '../config/assets/icon_assets.dart';
import '../themes/space_values.dart';
import '../themes/ui_colors.dart';

class AppUtils {
  Future<void> showPopup({String title = '', String subtitle = '', bool isSuccess = true, List<Widget>? action, EdgeInsets? insetPadding}) async {
    await Get.dialog(AlertDialog(
      insetPadding: insetPadding ?? const EdgeInsets.symmetric(horizontal: 40.0, vertical: 24.0),
      titlePadding: EdgeInsets.zero,
      contentPadding: const EdgeInsets.symmetric(horizontal: SpaceValues.space16, vertical: 0),
      title: Row(
        children: [
          const Expanded(child: SizedBox.shrink()),
          TextButton(
            onPressed: Get.back,
            child: SvgPicture.asset(IconAssets.navigationClose),
          ),
        ],
      ),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Transform.translate(
            offset: const Offset(0, -16),
            child: Visibility(
              visible: isSuccess,
              child: SvgPicture.asset(IconAssets.actionCheckCircle, color: UIColors.brandA, width: 45),
              replacement: SvgPicture.asset(IconAssets.navigationCancel, color: const Color(0xFFFA4D4E), width: 45),
            ),
          ),
          Text(
            title,
            style: const TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.w700,
            ),
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: SpaceValues.space8,),
          Visibility(
            visible: subtitle.isNotEmpty,
            child: ConstrainedBox(
              constraints: BoxConstraints(maxHeight: MediaQuery.of(Get.context!).size.height * .5),
              child: SingleChildScrollView(
                child: Text(
                  subtitle,
                  style: const TextStyle(
                    fontSize: 12,
                  ),
                  textAlign: TextAlign.center,
                ),
              ),
            ),
          ),
          Visibility(
            visible: (action ?? []).isNotEmpty,
            child: Padding(
              padding: const EdgeInsets.only(top: 12),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.center,
                children: action ?? [const SizedBox.shrink()],
              ),
            ),
          ),
          const SizedBox(
            height: SpaceValues.space24,
          ),
        ],
      ),
    ));
  }

  Future<void> showPopupSuccessWarranty({String title = '', String subtitle = '', bool isSuccess = true}) async {
    if (!isSuccess) {
      return await showPopup(title: title, subtitle: subtitle, isSuccess: isSuccess);
    }
    await Get.dialog(AlertDialog(
      titlePadding: EdgeInsets.zero,
      contentPadding: const EdgeInsets.symmetric(horizontal: SpaceValues.space16, vertical: 0),
      title: Row(
        children: [
          const Expanded(child: SizedBox.shrink()),
          TextButton(
            onPressed: Get.back,
            child: SvgPicture.asset(IconAssets.navigationClose),
          ),
        ],
      ),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        children: [
          Transform.translate(
            offset: const Offset(0, -16),
            child: Visibility(
              visible: isSuccess,
              child: SvgPicture.asset(IconAssets.actionCheckCircle, color: UIColors.brandA, width: 45),
              replacement: SvgPicture.asset(IconAssets.navigationCancel, color: const Color(0xFFFA4D4E), width: 45),
            ),
          ),
          Text(
            title,
            style: const TextStyle(
              fontSize: 16,
              fontWeight: FontWeight.w700,
            ),
            textAlign: TextAlign.center,
          ),
          const SizedBox(height: SpaceValues.space8,),
          Visibility(
            visible: subtitle.isNotEmpty,
            child: ConstrainedBox(
              constraints: BoxConstraints(maxHeight: MediaQuery.of(Get.context!).size.height * .5),
              child: SingleChildScrollView(
                child: Text(
                  subtitle,
                  style: const TextStyle(
                    fontSize: 12,
                  ),
                  textAlign: TextAlign.center,
                ),
              ),
            ),
          ),
          const SizedBox(
            height: SpaceValues.space12,
          ),
          SizedBox(
            width: MediaQuery.of(Get.context!).size.width * .5,
            child: ElevatedButton(
              onPressed: Get.back,
              child: const Text('Xác nhận'),
            ),
          ),
          const SizedBox(
            height: SpaceValues.space12,
          ),
        ],
      ),
    ));
  }

  Future<void> showPopupConfirm({String title = 'Bạn chắc chắn xoá giỏ quà?', String subtitle = 'Thông tin của giỏ quà sẽ mất đi', bool isSuccess = true, List<Widget>? action, EdgeInsets? insetPadding}) async {
    await Get.dialog(AlertDialog(
      insetPadding: insetPadding ?? const EdgeInsets.symmetric(horizontal: 40.0, vertical: 24.0),
      titlePadding: EdgeInsets.zero,
      contentPadding: const EdgeInsets.symmetric(horizontal: 0, vertical: 0),
      content: Column(
        mainAxisSize: MainAxisSize.min,
        crossAxisAlignment: CrossAxisAlignment.start,
        children: [
          SizedBox(height: 20,),
          Padding(
            padding: const EdgeInsets.symmetric(horizontal: 16),
            child: Row(
              children: [
                Container(
                  width: 50,
                  height: 50,
                  decoration: BoxDecoration(
                      color: UIColors.red,
                      borderRadius: BorderRadius.circular(999)
                  ),
                  child: Center(child: SvgPicture.asset('resources/icons/change_point/DeleteOutlined.svg',height: 20,color: UIColors.white,)),

                ),
                SizedBox(width: 16,),
                Expanded(
                  child: Column(
                    crossAxisAlignment: CrossAxisAlignment.start,
                    children: [
                      Text(
                        title,
                        style: TextStyle(
                          fontSize: 14,
                          fontWeight: FontWeight.w700,
                        ),
                        textAlign: TextAlign.center,

                      ),
                      SizedBox(height: SpaceValues.space8,),
                      Visibility(
                        visible: subtitle.isNotEmpty,
                        child: ConstrainedBox(
                          constraints: BoxConstraints(maxHeight: MediaQuery.of(Get.context!).size.height * .5),
                          child: SingleChildScrollView(
                            child: Text(
                              subtitle,
                              style: const TextStyle(
                                fontSize: 12,
                              ),
                              textAlign: TextAlign.center,
                            ),
                          ),
                        ),
                      ),
                    ],
                  ),
                ),
                SizedBox(width: 40,)
              ],
            ),
          ),
          const SizedBox(height: SpaceValues.space8,),
          Divider(),
          Visibility(
            visible: (action ?? []).isNotEmpty,
            child: Padding(
              padding: const EdgeInsets.only(top: 12),
              child: Row(
                mainAxisAlignment: MainAxisAlignment.end,
                children: action ?? [const SizedBox.shrink()],
              ),
            ),
          ),
          const SizedBox(
            height: SpaceValues.space24,
          ),
        ],
      ),
    ));
  }

}