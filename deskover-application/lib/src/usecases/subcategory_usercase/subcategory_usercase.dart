import 'package:deskover_develop/src/apis/product/response/product_response.dart';
import 'package:deskover_develop/src/apis/subcategory/subcategory_datasource.dart';
import 'package:injectable/injectable.dart';

@LazySingleton()
class SubCateUserCase{
  final SubCategoryDataSource _subCategoryDataSource;

  @factoryMethod
  SubCateUserCase(this._subCategoryDataSource);

  Future<List<SubCategory>?> doGetSubByCategoryId(int categoryId)
  => _subCategoryDataSource.doGetSubByCategoryId(categoryId);

}