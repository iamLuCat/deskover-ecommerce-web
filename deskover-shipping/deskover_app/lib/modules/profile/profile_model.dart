import 'package:deskover_app/utils/widgets/view_model.dart';
import 'package:get/get.dart';
import 'package:injectable/injectable.dart';
import 'package:shared_preferences/shared_preferences.dart';

@injectable
class ProfileModel extends ViewModel{
   final SharedPreferences sharedPreferences;
   RxString username = ''.obs;
   RxString avatar = ''.obs;

  @factoryMethod
  ProfileModel(this.sharedPreferences);

   @override
   void initState()   {
     super.initState();
     load();
   }
   Future<void> load()async {
     username.value = sharedPreferences.getString('username') ?? '';
     avatar.value = sharedPreferences.getString('avatar') ?? '';
   }



}