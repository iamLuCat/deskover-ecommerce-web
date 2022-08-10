import 'package:deskover_develop/src/apis/notify/notify_response.dart';
import 'package:deskover_develop/src/apis/notify/service/notify_api.dart';
import 'package:deskover_develop/src/utils/widgets/view_model.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';

import 'datasource/notify_datasource.dart';

@injectable
class NotifyModel extends ViewModel{
  final NotifyApi _notifyApi;
  final NotifyDatasoure _notifyDatasoure;

  RxList<NotifyResponse> listNotify = RxList.empty();

  NotifyModel(this._notifyApi, this._notifyDatasoure);

  @override
  void initState() {
    super.initState();
    loadNotify();
  }

  Future<void> loadNotify() async{
    _notifyApi.doGetNotifyByUser().then((value) {
            listNotify.value = value ?? [];
    });
  }
  void changeNotify(int notifyId) async{
      loading(() async{
         await  _notifyDatasoure.changeNotify(notifyId).then((value) async{
           loadNotify();
        });
      });
  }

}