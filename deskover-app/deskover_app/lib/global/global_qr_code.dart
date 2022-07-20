import 'package:deskover_app/constants/icon_assets.dart';
import 'package:deskover_app/themes/ui_colors.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import 'package:mobile_scanner/mobile_scanner.dart';

import '../themes/space_values.dart';

class GlobalQRScannerScreen extends StatelessWidget {

  final MobileScannerController controller = MobileScannerController();
  final void Function(Barcode barcode, MobileScannerArguments? args) onDetect;
  bool onDetectCall = false;

  RxBool lightPug = false.obs;

  GlobalQRScannerScreen({Key? key, required this.onDetect}) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
        appBar: AppBar(
          title: const Text('Quét mã QR'),
          backgroundColor: UIColors.white,
          actions: [
            IconButton(
              onPressed: () {
                controller.switchCamera();
              },
              icon: Icon(Icons.camera_alt_rounded)
              // SvgPicture.asset(
              //   IconAssets.imageSwitchCamera,
              //   color: UIColors.title70,
              // ),
            ),
            Visibility(
              visible: controller.hasTorch,
              child: IconButton(
                onPressed: controller.hasTorch ? () {
                  controller.toggleTorch();
                  lightPug.value = controller.torchEnabled ?? false;
                } : null,
                icon: Obx(() => lightPug.value ? Icon(Icons.camera_alt_rounded) : Icon(Icons.camera)),
              ),
            ),
          ],
        ),
        body: Stack(
          children: [
            MobileScanner(
              controller: controller,
              fit: BoxFit.contain,
              allowDuplicates: false,
              onDetect: (barcode, args) {
                if (!onDetectCall) {
                  onDetectCall = true;
                  onDetect(barcode, args);
                }
              },
            ),
            Row(
              children: [
                Container(
                  width: SpaceValues.space32,
                  color: UIColors.black70,
                ),
                Expanded(
                  child: Column(
                    children: [
                      Expanded(
                        child: Container(color: UIColors.black70,),
                      ),
                      const AspectRatio(
                        aspectRatio: 1,
                        child: SizedBox.shrink(),
                      ),
                      Expanded(
                        child: Container(color: UIColors.black70,),
                      ),
                    ],
                  ),
                ),
                Container(
                  width: SpaceValues.space32,
                  color: UIColors.black70,
                ),
              ],
            ),
          ],
        )
    );
  }

}