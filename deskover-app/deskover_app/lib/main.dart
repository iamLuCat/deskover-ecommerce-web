import 'dart:async';
import 'dart:io';
import 'package:deskover_app/config/injection_config.dart';
import 'package:deskover_app/modules/main_page/home_page.dart';
import 'package:deskover_app/modules/splashcreen/splashscreen.dart';
import 'package:deskover_app/themes/themes.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';
import 'package:get/get_navigation/get_navigation.dart';
import 'modules/receive_orders/find_order.dart';
import 'modules/sign/login_screen.dart';

StreamSubscription? iosSubscription;

class MyHttpOverrides extends HttpOverrides{
  @override
  HttpClient createHttpClient(SecurityContext? context){
    return super.createHttpClient(context)
      ..badCertificateCallback = (X509Certificate cert, String host, int port)=> true;
  }
}
void main() async {

  HttpOverrides.global = MyHttpOverrides();
  WidgetsFlutterBinding.ensureInitialized();// fix
  await configureDependencies();
  runApp( const MyApp(),
  );
}

class MyApp extends StatelessWidget {
  const MyApp({Key? key}) : super(key: key);

  Future<void> loading() async {
    await Future.delayed(const Duration(seconds: 3));
  }

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      useInheritedMediaQuery: true,
      // locale: DevicePreview.locale(context), // Add the locale here
      // builder: DevicePreview.appBuilder, // Add the builder here
      debugShowCheckedModeBanner: false,
      title: 'Deskover-depp',
      theme: Themes.mainTheme,
      home: SplashScreen(action: loading, nextScreen: const LoginScreen()),
    );
  }
}




