import 'package:deskover_develop/src/apis/notify/service/notify_api.dart';
import 'package:injectable/injectable.dart';

abstract class NotifyDatasoure{
  Future<void> changeNotify(int notifyId);

}
@LazySingleton(as: NotifyDatasoure)
class NotifyDatasoureImpl extends NotifyDatasoure{
  final NotifyApi _notifyApi;

  NotifyDatasoureImpl(this._notifyApi);

  @override
  Future<void> changeNotify(int notifyId)
  => _notifyApi.changeNotify(notifyId);

}