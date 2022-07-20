import 'dart:async';
import 'dart:math';
import 'package:deskover_app/themes/ui_colors.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';

class SplashScreen extends StatefulWidget {
  const SplashScreen({Key? key, required this.action, required this.nextScreen}) : super(key: key);

  final Future<void> Function() action;
  final Widget nextScreen;

  @override
  State<SplashScreen> createState() => _SplashScreen();
}

class _SplashScreen extends State<SplashScreen> with TickerProviderStateMixin {

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
    Timer(
      const Duration(milliseconds: 3000),
          () => Get.offAll(() => widget.nextScreen),
    );
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
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Image.asset(
            "resources/icons/logo.png",
            height: 110,
            // color: Colors.blueAccent,
          ),
            // SvgPicture.asset("resources/images/img_loading.png", height: 79),
            Container(
              margin: const EdgeInsets.only(top: 20),
              child: AnimatedBuilder(
                animation: animationController,
                child: Image.asset(
                  "resources/images/img_loading.png",
                  height: 60,
                  color: Colors.blueAccent,
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

}
