import 'package:deskover_app/utils/widgets/view_model.dart';
import 'package:get/get_rx/src/rx_types/rx_types.dart';
import 'package:injectable/injectable.dart';

@lazySingleton
class HomePageModel extends ViewModel{
    RxInt index = 0.obs;
}