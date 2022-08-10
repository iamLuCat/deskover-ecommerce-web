import 'dart:io';

import 'package:deskover_develop/src/apis/cart/response/cart_response.dart';
import 'package:deskover_develop/src/apis/message_response.dart';
import 'package:deskover_develop/src/apis/user/response/user_response.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:dio/dio.dart';
import 'package:injectable/injectable.dart';
import 'package:retrofit/http.dart';
part 'login_api.g.dart';

@RestApi(baseUrl: BaseApi.baseUrl)
@LazySingleton()
abstract class LoginAPI {
  @factoryMethod
  factory LoginAPI(Dio dio) = _LoginAPI;

  @POST('/v1/api/customer/auth/login')
  Future<MessageResponse> doLogin(@Body() Map<String, dynamic> map);

  @GET('/v1/api/customer/auth/get-principal')
  Future<User> doGetProfiled();

  @POST('/v1/api/customer/upload-file')
  Future<void> doPostUploadFile(
      @Part(
    name: 'file',
  )
          File file);

  @PUT('/v1/api/customer/user')
  Future<MessageResponse> doPutUpdate(@Body() User user);
}
