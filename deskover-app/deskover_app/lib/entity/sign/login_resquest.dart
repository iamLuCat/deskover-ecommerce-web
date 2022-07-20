import 'package:json_annotation/json_annotation.dart';

part 'login_resquest.g.dart';

@JsonSerializable()
class UserLogin {
  String username;
  String password;

  UserLogin({
    required this.username,
    required this.password,
  });

  factory UserLogin.fromJson(Map<String, dynamic> json) =>
      _$UserLoginFromJson(json);
  Map<String, dynamic> toJson() => _$UserLoginToJson(this);
}