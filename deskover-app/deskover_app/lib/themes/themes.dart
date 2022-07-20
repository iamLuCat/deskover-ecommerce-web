import 'package:deskover_app/themes/ui_colors.dart';
import 'package:flutter/material.dart';

class Themes {
  static ThemeData mainTheme = ThemeData(

    fontFamily: 'OpenSans',
    primaryColor: UIColors.brandA,
    backgroundColor: UIColors.white,
    scaffoldBackgroundColor: UIColors.backgroundLight,
    dialogBackgroundColor: UIColors.brandA,
    disabledColor: UIColors.title10,
    dividerColor: UIColors.divider5,
    tabBarTheme: const TabBarTheme(
      labelColor: UIColors.title70,
      unselectedLabelColor: UIColors.black40,
    ),
    appBarTheme: const AppBarTheme(
      elevation: 0,
      backgroundColor: UIColors.backgroundLight,
      titleTextStyle: TextStyle(fontWeight: FontWeight.w700, color: UIColors.title),
      centerTitle: true,
      iconTheme: IconThemeData(
        color: UIColors.black40,
      ),
      actionsIconTheme: IconThemeData(
        color: UIColors.brandB,
      ),
    ),
    buttonTheme: const ButtonThemeData(
      buttonColor: UIColors.brandA,
      disabledColor: UIColors.ink30,
    ),
    elevatedButtonTheme: ElevatedButtonThemeData(
      style: ElevatedButton.styleFrom(
        padding: const EdgeInsets.symmetric(vertical: 8, horizontal: 12),
        primary: UIColors.brandA,
        textStyle: const TextStyle(fontWeight: FontWeight.w700),
      ),
    ),
    outlinedButtonTheme: OutlinedButtonThemeData(
      style: OutlinedButton.styleFrom(
        primary: UIColors.navSelected,
      ),
    ),
    textButtonTheme: TextButtonThemeData(
      style: TextButton.styleFrom(
        primary: UIColors.navSelected,
      ),
    ),
    iconTheme: const IconThemeData(
      color: UIColors.brandA,
    ),
    dialogTheme: const DialogTheme(
      backgroundColor: UIColors.white,
    ),
    cardTheme: CardTheme(
      color: UIColors.white,
      shape: OutlineInputBorder(
        borderRadius: BorderRadius.circular(8),
        borderSide: const BorderSide(color: UIColors.border10),
      ),
      elevation: 1,
    ),
    textTheme: const TextTheme(
      headline1: TextStyle(fontSize: 18, fontWeight: FontWeight.w700),
      headline2: TextStyle(fontSize: 16, fontWeight: FontWeight.w400),
      headline3: TextStyle(fontSize: 18, fontWeight: FontWeight.w700),
      headline4: TextStyle(fontSize: 32, fontWeight: FontWeight.w700),
      headline5: TextStyle(fontSize: 14, fontWeight: FontWeight.w700),
      headline6: TextStyle(
          fontSize: 14, fontWeight: FontWeight.w700, color: UIColors.title),
      bodyText1: TextStyle(fontSize: 14),
      bodyText2: TextStyle(fontSize: 14),
      subtitle1: TextStyle(fontSize: 14),
      subtitle2: TextStyle(fontSize: 14),
      overline: TextStyle(fontSize: 14),
      caption: TextStyle(fontSize: 14),
      button: TextStyle(fontSize: 14),
    ),
    dividerTheme: const DividerThemeData(
      color: UIColors.divider5,
    ),
    colorScheme: const ColorScheme.light(
      background: UIColors.backgroundLight,
    ),
    floatingActionButtonTheme: const FloatingActionButtonThemeData(
      backgroundColor: UIColors.brandA,
    ),
    bottomAppBarTheme: const BottomAppBarTheme(
      color: UIColors.brandA,
    ),
    listTileTheme: const ListTileThemeData(
      textColor: UIColors.title,
      tileColor: UIColors.title70,
      iconColor: UIColors.black40,
    ),
    inputDecorationTheme: InputDecorationTheme(
      iconColor: UIColors.black40,
      fillColor: UIColors.white,
      filled: true,
      prefixIconColor: UIColors.black40,
      suffixIconColor: UIColors.black40,
      contentPadding: const EdgeInsets.all(12),
      isDense: true,
      errorStyle: const TextStyle(
        color: UIColors.error80,
        height: 1,
      ),
      hintStyle: const TextStyle(
        color: UIColors.title50,
      ),
      labelStyle: const TextStyle(
        color: UIColors.black70,
      ),
      border: OutlineInputBorder(
        borderSide: const BorderSide(color: UIColors.title50, width: 1),
        borderRadius: BorderRadius.circular(8),
      ),
      enabledBorder: OutlineInputBorder(
        borderSide: const BorderSide(color: UIColors.title50, width: 1),
        borderRadius: BorderRadius.circular(8),
      ),
      disabledBorder: OutlineInputBorder(
        borderSide: const BorderSide(color: UIColors.title30, width: 1),
        borderRadius: BorderRadius.circular(8),
      ),
      errorBorder: OutlineInputBorder(
        borderSide: const BorderSide(color: UIColors.error80, width: 1),
        borderRadius: BorderRadius.circular(8),
      ),
      focusedBorder: OutlineInputBorder(
        borderSide:
            const BorderSide(color: UIColors.focusedInputBorder, width: 1),
        borderRadius: BorderRadius.circular(8),
      ),
    ),
  );
}
