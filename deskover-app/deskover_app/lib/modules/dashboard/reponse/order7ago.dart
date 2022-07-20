import 'package:json_annotation/json_annotation.dart';
part 'order7ago.g.dart';

@JsonSerializable()
class DataTotalPrice7Ago{
  DataTotalPrice7Ago();

  @JsonKey(name: 'data')
  List<TotalPrice7DaysAgo>? data;

  factory DataTotalPrice7Ago.fromJson(Map<String, dynamic> json) =>
      _$DataTotalPrice7AgoFromJson(json);
  Map<String, dynamic> toJson() => _$DataTotalPrice7AgoToJson(this);

}
@JsonSerializable()
class TotalPrice7DaysAgo {

  @JsonKey(name: 'date')
  String? date;

  @JsonKey(name: 'totalPrice')
  double? totalPrice;

  @JsonKey(name: 'priceFormat')
  String? priceFormat;

  TotalPrice7DaysAgo({this.date,this.totalPrice,this.priceFormat});

  factory TotalPrice7DaysAgo.fromJson(Map<String, dynamic> json) =>
      _$TotalPrice7DaysAgoFromJson(json);
  Map<String, dynamic> toJson() => _$TotalPrice7DaysAgoToJson(this);

}