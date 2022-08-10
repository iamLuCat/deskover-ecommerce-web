import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:webview_flutter_plus/webview_flutter_plus.dart';

import '../../../themes/dialogs/loading_dialog.dart';
import '../../../themes/ui_colors.dart';

typedef ConfirmCallback = void Function(String value);

class CaptchaUtils {
  CaptchaUtils();
  Future<void> show({required ConfirmCallback confirmCallback, bool isV3 = false}) async {
    if (isV3) {
      await Get.dialog(
        Center(
          child: SizedBox(
            height: 512,
            child: Stack(
              children: [
                Positioned.fill(
                  child: CaptchaV3Widget(
                    onSubmit: (value) {
                      confirmCallback(value);
                      Get.back();
                    },
                  ),
                ),
                const Align(
                  child: LoadingDialog(
                    elevation: 0,
                    backgroundColor: Colors.transparent,
                  ),
                  alignment: Alignment.center,
                )
              ],
            ),
          ),
        ),
        useSafeArea: true,
      );
      return;
    }
    await Get.dialog(
      Center(
        child: SizedBox(
          height: 512,
          child: CaptchaWidget(
            onSubmit: (value) {
              confirmCallback(value);
              Get.back();
            },
          ),
        ),
      ),
      useSafeArea: true,
    );
  }
}

class CaptchaButton extends StatelessWidget {
  final ConfirmCallback confirmCallback;
  const CaptchaButton({Key? key, required this.confirmCallback}) : super(key: key);
  @override
  Widget build(BuildContext context) {
    return ElevatedButton(
      onPressed: () {
        CaptchaUtils().show(confirmCallback: confirmCallback);
      },
      child: const Text('Mở Captcha'),
    );
  }
}

class CaptchaWidget extends StatelessWidget {
  const CaptchaWidget({Key? key, required this.onSubmit}) : super(key: key);
  final ConfirmCallback onSubmit;

  @override
  Widget build(BuildContext context) {
    return WebViewPlus(
      javascriptMode: JavascriptMode.unrestricted,
      backgroundColor: UIColors.backgroundLight,
      initialUrl: 'resources/captcha.html',
      javascriptChannels: {
        JavascriptChannel(
          name: 'ReceiveToken',
          onMessageReceived: (mess) {
            onSubmit(mess.message);
          },
        ),
      },
    ).marginZero.paddingZero;
  }

}

class CaptchaV3Widget extends StatelessWidget {
  const CaptchaV3Widget({Key? key, required this.onSubmit}) : super(key: key);
  final ConfirmCallback onSubmit;

  @override
  Widget build(BuildContext context) {
    return WebViewPlus(
      javascriptMode: JavascriptMode.unrestricted,
      backgroundColor: UIColors.backgroundLight,
      initialUrl: 'resources/captchav3.html',
      javascriptChannels: {
        JavascriptChannel(
          name: 'ReceiveToken',
          onMessageReceived: (mess) {
            onSubmit(mess.message);
          },
        ),
      },
    ).marginZero.paddingZero;
  }

}