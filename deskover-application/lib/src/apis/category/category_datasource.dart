import 'package:deskover_develop/src/apis/category/response/category_response.dart';
import 'package:deskover_develop/src/apis/category/service/category_api.dart';
import 'package:injectable/injectable.dart';

abstract class CategoryDataSource{
  Future<List<CategoryReponse>?> goGetAll();
}
@LazySingleton(as: CategoryDataSource)
class CategoryDataSourceImpl extends CategoryDataSource{

  final CategoryAPI _categoryAPI;

  CategoryDataSourceImpl(this._categoryAPI);

  @override
  Future<List<CategoryReponse>?> goGetAll()
  => _categoryAPI.goGetAll();

}