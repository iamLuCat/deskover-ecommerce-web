import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/main_page.dart';
import 'package:deskover_develop/src/modules/signin_signup/app/signin/app/signin_screen.dart';
import 'package:deskover_develop/src/modules/splashsreen/splashsreen.dart';
import 'package:deskover_develop/src/themes/themes.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get_navigation/src/root/get_material_app.dart';
import 'package:injectable/injectable.dart';
import 'package:shared_preferences/shared_preferences.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  await configureDependencies();
  SystemChrome.setPreferredOrientations([DeviceOrientation.portraitUp]).then((_) {
    runApp(
      getIt<MyApp>(),
    );
  });

}

@Injectable()
class MyApp extends StatelessWidget {
  final SharedPreferences _sharedPreferences;
  const MyApp(this._sharedPreferences);

  @override
  Widget build(BuildContext context) {
    return GetMaterialApp(
      useInheritedMediaQuery: true,
      // locale: DevicePreview.locale(context),
      // builder: DevicePreview.appBuilder,
      debugShowCheckedModeBanner: false,
      title: 'Deskover',
      theme: Themes.mainTheme,
      home: SplashScreen(
          nextScreen:
              (_sharedPreferences.getString('uToken')?.isNotEmpty ?? false)
                  ? const MainPage()
                  : const SigninPage()),
    );
  }
}
