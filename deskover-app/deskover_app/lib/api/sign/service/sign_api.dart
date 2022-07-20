import 'package:deskover_app/config/base_api.dart';
import 'package:deskover_app/entity/sign/response/sign_response.dart';
import 'package:dio/dio.dart';
import 'package:injectable/injectable.dart';
import 'package:retrofit/http.dart';
part 'sign_api.g.dart';

@RestApi(baseUrl: BaseApi.baseUrl)
@LazySingleton()
abstract class SigninAPI{
  @factoryMethod
  factory SigninAPI(Dio dio) = _SigninAPI;

  @POST('/v1/api/admin/auth/login')
  Future<SigninResponses> signin(@Body() Map<String, dynamic> map);

  @GET('/v1/api/admin/auth/get-principal')
  Future<SigninResponses> getProfile();

  @PUT('/v1/api/admin/administrator/password')
  Future<dynamic> changePassword(@Body() dynamic body);
}