// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'order7ago.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

DataTotalPrice7Ago _$DataTotalPrice7AgoFromJson(Map<String, dynamic> json) =>
    DataTotalPrice7Ago()
      ..data = (json['data'] as List<dynamic>?)
          ?.map((e) => TotalPrice7DaysAgo.fromJson(e as Map<String, dynamic>))
          .toList();

Map<String, dynamic> _$DataTotalPrice7AgoToJson(DataTotalPrice7Ago instance) =>
    <String, dynamic>{
      'data': instance.data,
    };

TotalPrice7DaysAgo _$TotalPrice7DaysAgoFromJson(Map<String, dynamic> json) =>
    TotalPrice7DaysAgo(
      date: json['date'] as String?,
      totalPrice: (json['totalPrice'] as num?)?.toDouble(),
      priceFormat: json['priceFormat'] as String?,
    );

Map<String, dynamic> _$TotalPrice7DaysAgoToJson(TotalPrice7DaysAgo instance) =>
    <String, dynamic>{
      'date': instance.date,
      'totalPrice': instance.totalPrice,
      'priceFormat': instance.priceFormat,
    };
