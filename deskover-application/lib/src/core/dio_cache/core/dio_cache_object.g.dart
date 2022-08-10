// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'dio_cache_object.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

DioCacheObject _$DioCacheObjectFromJson(Map<String, dynamic> json) =>
    DioCacheObject(
      json['key'] as String,
      json['content'] as String,
      subKey: json['subKey'] as String? ?? "",
      statusCode: json['statusCode'] as int? ?? 200,
      headers:
          (json['headers'] as List<dynamic>?)?.map((e) => e as int).toList(),
    )
      ..maxAgeDate = json['max_age_date'] as int?
      ..maxExpirationDate = json['max_expiration_date'] as int?;

Map<String, dynamic> _$DioCacheObjectToJson(DioCacheObject instance) =>
    <String, dynamic>{
      'key': instance.key,
      'subKey': instance.subKey,
      'max_age_date': instance.maxAgeDate,
      'max_expiration_date': instance.maxExpirationDate,
      'content': instance.content,
      'statusCode': instance.statusCode,
      'headers': instance.headers,
    };
