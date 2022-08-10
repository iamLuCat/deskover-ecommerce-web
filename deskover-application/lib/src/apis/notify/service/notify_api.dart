import 'package:deskover_develop/src/apis/notify/notify_response.dart';
import 'package:deskover_develop/src/config/base_api.dart';
import 'package:dio/dio.dart';
import 'package:injectable/injectable.dart';
import 'package:retrofit/http.dart';
part 'notify_api.g.dart';

@RestApi(baseUrl: BaseApi.baseUrl)
@LazySingleton()
abstract class NotifyApi {
  @factoryMethod
  factory NotifyApi(Dio dio) = _NotifyApi;

  @GET('/v1/api/customer/notify')
  Future<List<NotifyResponse>?> doGetNotifyByUser();

  @GET('/v1/api/customer/notify/ordercode')
  Future<List<NotifyResponse>?> doGetNotifyByUserAndOrderCode(@Query('orderCode') String? orderCode);

  @PUT('/v1/api/customer/notify/{notifyId}')
  Future<void> changeNotify(@Path('notifyId') int? notifyId);


}
