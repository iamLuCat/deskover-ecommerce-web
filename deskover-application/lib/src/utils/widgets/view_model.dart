import 'dart:async';
import 'dart:convert';
import 'package:deskover_develop/src/themes/dialogs/loading_dialog.dart';
import 'package:deskover_develop/src/utils/AppUtils.dart';
import 'package:dio/dio.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_connect/http/src/status/http_status.dart';

class ViewModel {
  initState() {}

  dispose() {}

  @protected
  Future<T?> loading<T>(
      Future<T> Function() future, {
        Widget? dialog,
        bool showErrorDialog = true,
        dynamic Function()? onSuccess,
        dynamic Function(dynamic error)? onFailure,
        bool? showLoading,
        bool reCatchString = false,
      }) async {
    bool showDialog = false;
    T? result;
    try {
      WidgetsBinding.instance?.addPostFrameCallback((timeStamp) {
        showDialog = true;
        Get.dialog(
          dialog ?? const LoadingDialog(),
          barrierDismissible: false,
        );
      });
      result = await future();
    } on DioError catch (error) {
      if (error.response?.statusCode == HttpStatus.unauthorized) {
        rethrow;
      }
      Map<String, dynamic> errorMap = {
        'status': error.response?.statusCode,
        'statusMessage': error.response?.statusMessage,
        'message': jsonDecode(error.response.toString())?['message'],
        'onemessage': jsonDecode(error.response.toString())?['message'],
        'validMessage': error.response?.data?['errors'].toString(),
      };

      if (showErrorDialog) {

        String messageDialog = errorMap['message'] == 'Full authentication is required to access this resource' ? errorMap['message'] = 'Vui lòng đăng nhập để xử dụng tính năng' : errorMap['message']  ??
            errorMap['validMessage'] ??
            errorMap['statusMessage'] ??
            'Không thể xác định (${errorMap['status']})';
        WidgetsBinding.instance?.addPostFrameCallback((timeStamp) {
          if ((Get.isDialogOpen ?? false) && showDialog) {
            showDialog = false;
            Get.back();
          }
          showAlertDialog(messageDialog);
        });
      }
      rethrow;
    } on String catch (value) {
      WidgetsBinding.instance?.addPostFrameCallback((timeStamp) {
        if ((Get.isDialogOpen ?? false) && showDialog) {
          showDialog = false;
          Get.back();
        }
        showAlertDialog(value);
      });
      if (reCatchString) {
        rethrow;
      }
    } finally {
      if ((Get.isDialogOpen ?? false) && showDialog) {
        showDialog = false;
        Get.back();
      }
    }
    return result;
  }

  Future<void> showAlertDialog(String messageDialog) {
    return AppUtils().showPopup(
      title: 'Thông báo',
      subtitle: messageDialog,
      isSuccess: false,
    );
  }
}
