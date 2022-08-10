// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'category_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

CategoryReponse _$CategoryReponseFromJson(Map<String, dynamic> json) =>
    CategoryReponse(
      id: json['id'] as int?,
      name: json['name'] as String?,
      img: json['img'] as String?,
      imgUrl: json['imgUrl'] as String?,
      description: json['description'] as String?,
      slug: json['slug'] as String?,
      modifiedAt: json['modifiedAt'] as String?,
      actived: json['actived'] as bool?,
      modifiedBy: json['modifiedBy'] as String?,
    );

Map<String, dynamic> _$CategoryReponseToJson(CategoryReponse instance) =>
    <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'img': instance.img,
      'imgUrl': instance.imgUrl,
      'description': instance.description,
      'slug': instance.slug,
      'modifiedAt': instance.modifiedAt,
      'actived': instance.actived,
      'modifiedBy': instance.modifiedBy,
    };
