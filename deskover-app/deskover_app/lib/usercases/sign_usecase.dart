import 'package:deskover_app/api/sign/sign_datasource.dart';
import 'package:deskover_app/entity/sign/response/sign_response.dart';
import 'package:injectable/injectable.dart';

@LazySingleton()
class SigninUsecase {
  final SignDatasource _signDatasource;
  @factoryMethod
  SigninUsecase(this._signDatasource);

  Future<SigninResponses> signin(String username, String password)
  => _signDatasource.signup(username,password);

  Future<dynamic> changePassword(dynamic body)
  => _signDatasource.changePassword(body);
}
