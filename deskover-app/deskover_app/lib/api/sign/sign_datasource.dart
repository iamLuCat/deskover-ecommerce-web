

import 'package:deskover_app/api/sign/service/sign_api.dart';
import 'package:deskover_app/entity/sign/response/sign_response.dart';
import 'package:injectable/injectable.dart';

abstract class SignDatasource{
    Future<SigninResponses> signup(String username, String phone);
    Future<dynamic> changePassword(dynamic body);
}

@LazySingleton(as: SignDatasource)
class SignDatasourceImpl extends SignDatasource{

  final SigninAPI _signinAPI;
  SignDatasourceImpl(this._signinAPI);

  @override
  Future<SigninResponses> signup(String username, String password)
    => _signinAPI.signin({
        "username": username,
        "password": password,
    });

  @override
  Future<dynamic> changePassword(body)
  => _signinAPI.changePassword(body);

    

}