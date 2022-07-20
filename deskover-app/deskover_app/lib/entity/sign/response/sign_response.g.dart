// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'sign_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

SigninResponses _$SigninResponsesFromJson(Map<String, dynamic> json) =>
    SigninResponses()
      ..token = json['token'] as String?
      ..fullname = json['fullname'] as String?
      ..avatar = json['avatar'] as String?
      ..authorities = (json['authorities'] as List<dynamic>?)
          ?.map((e) => Authorities.fromJson(e as Map<String, dynamic>))
          .toList();

Map<String, dynamic> _$SigninResponsesToJson(SigninResponses instance) =>
    <String, dynamic>{
      'token': instance.token,
      'fullname': instance.fullname,
      'avatar': instance.avatar,
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
      modifiedAt: json['modifiedAt'] as String?,
      roleId: json['roleId'] as String?,
    );

Map<String, dynamic> _$RoleToJson(Role instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'roleId': instance.roleId,
      'modifiedAt': instance.modifiedAt,
    };
