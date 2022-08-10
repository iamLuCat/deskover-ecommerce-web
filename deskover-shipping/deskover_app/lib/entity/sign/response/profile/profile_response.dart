import 'package:json_annotation/json_annotation.dart';
part 'profile_response.g.dart';

@JsonSerializable()
class UserProfile {
  @JsonKey(name: 'id')
  int? id;

  @JsonKey(name: 'username')
  String? username;

  @JsonKey(name: 'fullname')
  String? fullname;

  @JsonKey(name: 'lastLogin')
  String? lastLogin;

  @JsonKey(name: 'modifiedAt')
  String? modifiedAt;

  @JsonKey(name: 'modifiedBy')
  String? modifiedBy;

  @JsonKey(name: 'actived')
  bool? actived;

  @JsonKey(name: 'avatar')
  String? avatar;

  @JsonKey(name: 'password')
  String? password;

  @JsonKey(name: 'authorities')
  List<Authorities>? authorities;

  UserProfile(
      {this.id,
        this.username,
        this.fullname,
        this.lastLogin,
        this.modifiedAt,
        this.modifiedBy,
        this.actived,
        this.avatar,
        this.password,
        this.authorities});
  factory UserProfile.fromJson(Map<String, dynamic> json) =>
      _$UserProfileFromJson(json);
  Map<String, dynamic> toJson() => _$UserProfileToJson(this);
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
  @JsonKey(name: 'description')
  String? description;
  @JsonKey(name: 'modifiedAt')
  String? modifiedAt;

  Role({this.id, this.name, this.description, this.modifiedAt});

  factory Role.fromJson(Map<String, dynamic> json) =>
      _$RoleFromJson(json);
  Map<String, dynamic> toJson() => _$RoleToJson(this);

}