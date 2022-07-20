import 'package:json_annotation/json_annotation.dart';

part 'sign_response.g.dart';

@JsonSerializable()
class SigninResponses {

  SigninResponses();

  @JsonKey(name: 'token')
  String? token;

  @JsonKey(name: 'fullname')
  String? fullname;

  @JsonKey(name: 'avatar')
  String? avatar;

  @JsonKey(name: 'authorities')
  List<Authorities>? authorities;

  factory SigninResponses.fromJson(Map<String, dynamic> json) =>
      _$SigninResponsesFromJson(json);
  Map<String, dynamic> toJson() => _$SigninResponsesToJson(this);
}

@JsonSerializable()
class Authorities {
  @JsonKey(name: 'id')
  int? id;
  @JsonKey(name: 'role')
  Role? role;

  Authorities({this.id, this.role});

  factory Authorities.fromJson(Map<String, dynamic> json) =>
      _$AuthoritiesFromJson(json);
  Map<String, dynamic> toJson() => _$AuthoritiesToJson(this);
}

@JsonSerializable()
class Role {
  @JsonKey(name: 'id')
  int? id;

  @JsonKey(name: 'name')
  String? name;

  @JsonKey(name: 'roleId')
  String? roleId;

  @JsonKey(name: 'modifiedAt')
  String? modifiedAt;

  Role({this.id, this.name, this.modifiedAt,this.roleId});

  factory Role.fromJson(Map<String, dynamic> json) =>
      _$RoleFromJson(json);
  Map<String, dynamic> toJson() => _$RoleToJson(this);
  }