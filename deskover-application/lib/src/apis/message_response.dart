import 'package:json_annotation/json_annotation.dart';
part 'message_response.g.dart';
@JsonSerializable()
class MessageResponse{
    @JsonKey(name: 'message')
    String? message;

    MessageResponse({
     this.message
   });
    factory MessageResponse.fromJson(Map<String, dynamic> json) => _$MessageResponseFromJson(json);
    Map<String, dynamic> toJson() => _$MessageResponseToJson(this);
}