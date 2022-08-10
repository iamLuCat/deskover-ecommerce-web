import 'package:deskover_develop/src/apis/category/category_datasource.dart';
import 'package:deskover_develop/src/apis/category/response/category_response.dart';
import 'package:injectable/injectable.dart';

@LazySingleton()
class CategoryUserCase{
  final CategoryDataSource _categoryDataSource;

  @factoryMethod
  CategoryUserCase(this._categoryDataSource);

  Future<List<CategoryReponse>?> doGetAll()
  => _categoryDataSource.goGetAll();


}