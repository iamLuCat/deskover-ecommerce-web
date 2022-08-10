// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'user_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

User _$UserFromJson(Map<String, dynamic> json) => User(
      id: json['id'] as int?,
      username: json['username'] as String?,
      fullname: json['fullname'] as String?,
      avatar: json['avatar'] as String?,
      modifiedAt: json['modifiedAt'] as String?,
      lastLogin: json['lastLogin'] as String?,
      actived: json['actived'] as bool?,
      verify: json['verify'] as bool?,
      modifiedBy: json['modifiedBy'] as String?,
      email: json['email'] as String?,
      phone: json['phone'] as String?,
    );

Map<String, dynamic> _$UserToJson(User instance) => <String, dynamic>{
      'id': instance.id,
      'username': instance.username,
      'fullname': instance.fullname,
      'avatar': instance.avatar,
      'email': instance.email,
      'phone': instance.phone,
      'modifiedAt': instance.modifiedAt,
      'lastLogin': instance.lastLogin,
      'actived': instance.actived,
      'verify': instance.verify,
      'modifiedBy': instance.modifiedBy,
    };
