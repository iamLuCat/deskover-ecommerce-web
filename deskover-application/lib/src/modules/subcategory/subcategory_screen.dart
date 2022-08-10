import 'package:deskover_develop/src/config/base_api.dart';
import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/global_modules/widget/global_image.dart';
import 'package:deskover_develop/src/modules/product_widget/product_widget.dart';
import 'package:deskover_develop/src/modules/subcategory/subcategory_model.dart';
import 'package:deskover_develop/src/themes/dialogs/loading_dialog.dart';
import 'package:deskover_develop/src/themes/ui_colors.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:get/get_state_manager/src/rx_flutter/rx_obx_widget.dart';

class SubCategoryScreen extends StatefulWidget{
  final int categoryId;
  final String title;
  const SubCategoryScreen({Key? key,required this.categoryId, required this.title}) : super(key: key);
  @override
  State<StatefulWidget> createState()=> _SubCategoryScreen();

}
class _SubCategoryScreen extends ViewWidget<SubCategoryScreen, SubCategoryModel>{
  @override
  void initState() {
    super.initState();
      viewModel.categoryId.value = widget.categoryId;
      viewModel.loadByCategory();
      viewModel.loadSubCategory();
  }
  @override

  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: UIColors.white,
        title: Text(widget.title),
      ),
      body: SingleChildScrollView(
        child: Container(
          margin: const EdgeInsets.only(top: 6),
          child: Column(
            children: [
              Container(
                color: UIColors.white,
                child: Column(
                  children: [
                    Obx(
                          ()=> Visibility(
                        visible: viewModel.dataSubCategory.isNotEmpty,
                        child: SizedBox(
                          height: (viewModel.dataSubCategory.length) > 8
                              ? 230
                              : 230 / 2,
                          child: Center(
                            child: Obx(
                                  ()=> GridView.builder(
                                // primary: false,
                                shrinkWrap: true,
                                scrollDirection: Axis.horizontal,
                                // physics: const NeverScrollableScrollPhysics(),
                                padding: const EdgeInsets.all(0),
                                itemCount: viewModel.dataSubCategory.length,
                                gridDelegate: SliverGridDelegateWithFixedCrossAxisCount(
                                  crossAxisCount: (viewModel.dataSubCategory.length) > 8
                                      ? 2
                                      : 1,
                                  mainAxisExtent: 80,
                                  childAspectRatio: 2,
                                  mainAxisSpacing: 12,
                                  crossAxisSpacing: 8,
                                ),
                                itemBuilder: (context, i) {
                                  return TextButton(
                                    style: TextButton.styleFrom(
                                        padding: EdgeInsets.zero,
                                        tapTargetSize: MaterialTapTargetSize.shrinkWrap,
                                        minimumSize: Size.zero),
                                    onPressed: () async{
                                      viewModel.subcategoryId.value = viewModel.dataSubCategory[i].id!;
                                      viewModel.loadProductBySubId();
                                    }
                                   ,
                                    child: Column(
                                      crossAxisAlignment: CrossAxisAlignment.center,
                                      mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                                      children: [
                                        ClipRRect(
                                          borderRadius: BorderRadius.circular(999),
                                          child: Container(
                                            width: 60,
                                            height: 60,
                                            color: UIColors.black10,
                                            child:  GlobalImage(
                                              BaseApi.baseUrl+'/img/shop/categories/${viewModel.dataSubCategory[i].img}',
                                              fit: BoxFit.fill,
                                            ),
                                          ),
                                        ),
                                        SizedBox(
                                          height: 31,
                                          child: Padding(
                                            padding: const EdgeInsets.symmetric(horizontal: 3),
                                            child: Text(
                                              viewModel.dataSubCategory[i].name ?? '',
                                              textAlign: TextAlign.center,
                                              maxLines: 2,
                                              overflow: TextOverflow.ellipsis,
                                              style: const TextStyle(
                                                fontSize: 10,
                                                fontWeight: FontWeight.w400,
                                                color: UIColors.black,
                                              ),
                                            ),
                                          ),
                                        ),
                                      ],
                                    ),
                                  );
                                },
                              ),
                            ),

                          ),
                        ),
                        replacement: const Center(
                          child: LoadingDialog(
                            backgroundColor: Colors.transparent,
                            elevation: 0,
                            message: 'Đang tìm các danh mục...',
                          ),
                        ),
                      ),),

                  ],
                ),
              ),
              Container(
                color: UIColors.white,
                margin: const EdgeInsets.only(top: 5),
                padding: const EdgeInsets.only(top: 6),
                child: Obx(
                      ()=>    Visibility(
                        visible: viewModel.dataProductByCategory.isNotEmpty ,
                        child: Column(
                          children: [
                            Padding(
                              padding: const EdgeInsets.only(left: 16,right: 16),
                              child: Row(
                                children: [
                                  ElevatedButton(
                                      style: ElevatedButton.styleFrom(
                                        primary: viewModel.keySort.value == 'DESC' ? UIColors.brandA: UIColors.white,
                                        shape: RoundedRectangleBorder(
                                          side: const BorderSide(
                                              color: UIColors.brandA
                                          ),
                                          borderRadius: BorderRadius.circular(32.0),
                                        ),
                                      ),
                                      onPressed: (){
                                        viewModel.keySort.value = 'DESC';
                                        if(viewModel.subcategoryId.value <= 0){
                                          viewModel.loadByCategory();
                                        }else if(viewModel.subcategoryId.value > 0){
                                          viewModel.loadProductBySubId();
                                        }
                                      },
                                      child: Text(
                                          'Giảm dần',
                                        style: TextStyle(
                                            color: viewModel.keySort.value == 'DESC' ? UIColors.white : UIColors.brandA
                                        ),
                                      )
                                  ),
                                  const SizedBox(width: 10,),
                                  ElevatedButton(
                                      style: ElevatedButton.styleFrom(
                                        primary: viewModel.keySort.value == 'ASC' ? UIColors.brandA: UIColors.white,
                                        shape: RoundedRectangleBorder(
                                          side: const BorderSide(
                                            color: UIColors.brandA
                                          ),
                                          borderRadius: BorderRadius.circular(32.0),
                                        ),
                                      ),
                                      onPressed: (){
                                        viewModel.keySort.value = 'ASC';
                                        if(viewModel.subcategoryId.value <= 0){
                                          viewModel.loadByCategory();
                                        }else if(viewModel.subcategoryId.value > 0){
                                          viewModel.loadProductBySubId();
                                        }
                                      },
                                      child: Text(
                                          'Tăng dần',
                                        style: TextStyle(
                                          color: viewModel.keySort.value == 'ASC' ? UIColors.white : UIColors.brandA
                                        ),
                                      )
                                  ),


                                ],
                              ),
                            ),
                            const SizedBox(height: 6,),
                            GridView.builder(
                              padding: const EdgeInsets.symmetric(horizontal: 12),
                              shrinkWrap: true,
                              physics: const NeverScrollableScrollPhysics(),
                              gridDelegate:
                              const SliverGridDelegateWithFixedCrossAxisCount(
                                mainAxisSpacing: 6,
                                crossAxisSpacing: 2,
                                crossAxisCount: 2,
                                mainAxisExtent: 270,
                                // childAspectRatio: 0.4,
                              ),
                              itemCount: viewModel.dataProductByCategory.length,
                              itemBuilder: (context, index) {
                                return ProductWidget(
                                  productId:  viewModel.dataProductByCategory[index].id!,
                                  title: viewModel.dataProductByCategory[index].name ?? '',
                                  avatar: BaseApi.baseUrl+'/img/shop/products/${viewModel.dataProductByCategory[index].img}',
                                  price: viewModel.dataProductByCategory[index].price!,
                                  quantity: viewModel.dataProductByCategory[index].quantity!,
                                );
                              },
                            ),
                            SizedBox(
                              height: 40,
                              child: TextButton(
                                  style: TextButton.styleFrom(
                                    elevation: 0.0,
                                    padding: EdgeInsets.zero,
                                  ),
                                  onPressed:() async{
                                    if(viewModel.subcategoryId.value <= 0){
                                      viewModel.size+=8;
                                      viewModel.loadByCategory();
                                    }else if(viewModel.subcategoryId.value > 0){
                                      viewModel.size+=8;
                                      viewModel.loadProductBySubId();
                                    }
                                  },
                                  child: const Text(
                                    'Xem thêm >',
                                    style: TextStyle(
                                        color: UIColors.brandA
                                    ),
                                  )),
                            )
                          ],
                        ),
                        replacement: const Center(
                          child: LoadingDialog(
                            backgroundColor: Colors.transparent,
                            elevation: 0,
                            message: 'Danh mục trống...',
                          ),
                        ),
                      ),),
              ),

            ],
          ),
        ),
      ),
    );
  }

  @override
  SubCategoryModel createViewModel() => getIt<SubCategoryModel>();



}
