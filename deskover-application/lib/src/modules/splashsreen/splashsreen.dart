
import 'dart:math';

import 'package:deskover_develop/src/config/assets/image_asset.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/splashsreen/splashsreen_model.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({Key? key, required this.nextScreen}) : super(key: key);

  final Widget nextScreen;

  @override
  State<SplashScreen> createState() => _SplashScreen();
}

class _SplashScreen extends ViewWidget<SplashScreen,SplashsreenModel> with TickerProviderStateMixin {

  late final AnimationController animationController = AnimationController(
    vsync: this,
    duration: const Duration(milliseconds: 700),
  )..repeat();

  bool loading = true;

  loadingScreen() async {
    if (!loading) {
      return;
    }
    loading = false;
    await viewModel.loadModel();
    Get.offAll(() => widget.nextScreen);
  }

  @override
  void initState() {
    super.initState();
    loadingScreen();
  }

  @override
  void dispose() {
    animationController.stop();
    animationController.dispose();
    super.dispose();
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: UIColors.white,
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Image.asset( ImageAssets.imgLogobanner, height: 150),
            Container(
              margin: const EdgeInsets.only(top: 40),
              child: AnimatedBuilder(
                animation: animationController,
                child: Image.asset(
                  ImageAssets.icLoading,
                  height: 77,
                ),
                builder: (context, child) {
                  return Transform.rotate(
                    angle: animationController.value * 2 * pi,
                    child: child,
                  );
                },
              ),
            )
          ],
        ),
      ),
    );
  }

  @override
  SplashsreenModel createViewModel() => getIt<SplashsreenModel>();
}
