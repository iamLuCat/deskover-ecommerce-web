import 'package:flutter/material.dart';

class Util {
  Util._();

  static void hideKeyboard(BuildContext context) {
    FocusScope.of(context).requestFocus(new FocusNode());
  }

  static String htlmWrapper(String body) {
    return '<!DOCTYPE html><html><head><meta name="viewport" content="width=device-width, initial-scale=1.0"></head><body style=\'"margin: 0; padding: 0;\'>$body</body></html>';
  }

}
