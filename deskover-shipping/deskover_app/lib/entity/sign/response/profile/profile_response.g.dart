// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'profile_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

UserProfile _$UserProfileFromJson(Map<String, dynamic> json) => UserProfile(
      id: json['id'] as int?,
      username: json['username'] as String?,
      fullname: json['fullname'] as String?,
      lastLogin: json['lastLogin'] as String?,
      modifiedAt: json['modifiedAt'] as String?,
      modifiedBy: json['modifiedBy'] as String?,
      actived: json['actived'] as bool?,
      avatar: json['avatar'] as String?,
      password: json['password'] as String?,
      authorities: (json['authorities'] as List<dynamic>?)
          ?.map((e) => Authorities.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$UserProfileToJson(UserProfile instance) =>
    <String, dynamic>{
      'id': instance.id,
      'username': instance.username,
      'fullname': instance.fullname,
      'lastLogin': instance.lastLogin,
      'modifiedAt': instance.modifiedAt,
      'modifiedBy': instance.modifiedBy,
      'actived': instance.actived,
      'avatar': instance.avatar,
      'password': instance.password,
      'authorities': instance.authorities,
    };

Authorities _$AuthoritiesFromJson(Map<String, dynamic> json) => Authorities(
      id: json['id'] as int?,
      role: json['role'] == null
          ? null
          : Role.fromJson(json['role'] as Map<String, dynamic>),
    );

Map<String, dynamic> _$AuthoritiesToJson(Authorities instance) =>
    <String, dynamic>{
      'id': instance.id,
      'role': instance.role,
    };

Role _$RoleFromJson(Map<String, dynamic> json) => Role(
      id: json['id'] as int?,
      name: json['name'] as String?,
      description: json['description'] as String?,
      modifiedAt: json['modifiedAt'] as String?,
    );

Map<String, dynamic> _$RoleToJson(Role instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'description': instance.description,
      'modifiedAt': instance.modifiedAt,
    };
