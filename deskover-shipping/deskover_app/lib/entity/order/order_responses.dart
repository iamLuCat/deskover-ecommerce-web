import 'package:json_annotation/json_annotation.dart';

part 'order_responses.g.dart';

@JsonSerializable()
class DataOrderReponse{

  DataOrderReponse();

  @JsonKey(name:'data')
  List<OrderReponses>? data;

  factory DataOrderReponse.fromJson(Map<String, dynamic> json) =>
      _$DataOrderReponseFromJson(json);
  Map<String, dynamic> toJson() => _$DataOrderReponseToJson(this);
}

@JsonSerializable()
class OrderReponses{
  @JsonKey(name: 'id')
  int? id;

  @JsonKey(name: 'orderCode')
  String? orderCode;

  @JsonKey(name: 'qrCode')
  String? qrCode;

  @JsonKey(name: 'fullName')
  String? fullName;

  @JsonKey(name: 'email')
  String? email;

  @JsonKey(name: 'address')
  String? address;

  @JsonKey(name: 'province')
  String? province;

  @JsonKey(name: 'district')
  String? district;

  @JsonKey(name: 'ward')
  String? ward;

  @JsonKey(name: 'tel')
  String? tel;

  @JsonKey(name: 'orderItem')
  List<OrderItem>? orderItem;

  @JsonKey(name: 'createdAt')
  String? createdAt;

  @JsonKey(name:'note')
  String? note;

  @JsonKey(name:'shipping_note')
  String? shipping_note;

  @JsonKey(name: 'code')
  String? code;

  @JsonKey(name: 'status')
  String? status;

  @JsonKey(name: 'totalPrice')
  String? totalPrice;

  @JsonKey(name: 'modifiedBy')
  String? modifiedBy;

  OrderReponses(
      {this.id,
        this.orderCode,
        this.fullName,
        this.email,
        this.address,
        this.province,
        this.district,
        this.ward,
        this.tel,
        this.orderItem,
        this.createdAt,
        this.code,
        this.status,
        this.totalPrice,
        this.modifiedBy});
  factory OrderReponses.fromJson(Map<String, dynamic> json) =>
      _$OrderReponsesFromJson(json);
  Map<String, dynamic> toJson() => _$OrderReponsesToJson(this);

}

@JsonSerializable()
class OrderItem{

  @JsonKey(name: 'name')
  String? name;

  @JsonKey(name: 'quantity')
  int? quantity;

  @JsonKey(name: 'img')
  String? img;

  @JsonKey(name: 'price')
  String? price;

  OrderItem({this.name, this.quantity, this.price,this.img});

  factory OrderItem.fromJson(Map<String, dynamic> json) =>
      _$OrderItemFromJson(json);
  Map<String, dynamic> toJson() => _$OrderItemToJson(this);


}