import 'package:deskover_app/constants/icon_assets.dart';
import 'package:deskover_app/themes/space_values.dart';
import 'package:deskover_app/themes/ui_colors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';


class AppUtils {
  Future<void> showPopup({String title = '', String subtitle = '', bool isSuccess = true}) async {
    await Get.dialog(AlertDialog(
      titlePadding: EdgeInsets.zero,
      contentPadding: const EdgeInsets.symmetric(horizontal: SpaceValues.space16, vertical: 0),
      shape: const RoundedRectangleBorder(
          borderRadius: BorderRadius.all(Radius.circular(5))),
      title: Row(
        children: [
          const Expanded(child: SizedBox.shrink()),
          TextButton(
            onPressed: Get.back,
            child: SvgPicture.asset(IconAssets.navigationClose,color: UIColors.black),
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
              child: SvgPicture.asset(IconAssets.actionCheckCircle, color: const Color(0xFF6CB33F), width: 45),
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
            child: SvgPicture.asset(IconAssets.navigationClose,color: UIColors.black),
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
              style: ElevatedButton.styleFrom(
                primary: Colors.blueAccent
              ),
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

}