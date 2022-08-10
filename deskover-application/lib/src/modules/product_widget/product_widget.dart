import 'dart:math';

import 'package:deskover_develop/src/config/injection_config.dart';
import 'package:deskover_develop/src/modules/cart/creat_cart.dart';
import 'package:deskover_develop/src/modules/global_modules/widget/global_image.dart';
import 'package:deskover_develop/src/modules/product_widget/product_detail.dart';
import 'package:deskover_develop/src/modules/product_widget/product_model.dart';
import 'package:deskover_develop/src/utils/widgets/view_widget.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:flutter_svg/flutter_svg.dart';
import 'package:get/get.dart';
import 'package:get/get_core/src/get_main.dart';
import 'package:intl/intl.dart';

import '../../themes/space_values.dart';
import '../../themes/ui_colors.dart';


class ProductWidget extends StatefulWidget {

  const ProductWidget({
    Key? key,
    required this.productId,
    this.title = '',
    this.priceOrigin = 0,
    this.price = 0,
    this.avatar = '',
    this.promotion = '',
    this.isCanBuy = true,
    this.flashSale =true,
    this.qrCode = '',
    this.width = 180,
    this.height = 270,
    this.quantity=0, this.discount = 0,
  }) : super(key: key);

  final int productId;
  final String avatar;
  final String title;
  final String promotion;
  final double priceOrigin;
  final double price;
  final int discount;
  final int quantity;
  final bool isCanBuy;
  final bool flashSale;
  final double width;
  final double height;
  final String qrCode;

  @override
  State<ProductWidget> createState() => _ProductWidgetState();
}

class _ProductWidgetState extends ViewWidget<ProductWidget,ProductCartModel>{

  String? _herotag;
  String get herotag {
    _herotag ??= '${widget.productId.toString()}${Random().nextDouble()}';
    return _herotag!;
  }
  final formatCurrency = NumberFormat.currency(locale:"vi_VN", symbol: "đ");

  @override
  Widget build(BuildContext context) {
    return SizedBox(
      width: widget.width,
      height: MediaQuery.of(context).size.height*0.3,
      child: InkWell(
        onTap: () {
          if(widget.flashSale == true){
            Get.back();
            Get.to(ProductDetail(
              idProduct: widget.productId,
              heroTag: herotag,
            )
            );
          }
          if(widget.flashSale == false){
            null;
          }
          ;
        },
        child: Card(
          elevation: 0.0,
          semanticContainer: true,
          clipBehavior: Clip.antiAliasWithSaveLayer,
          child: Padding(
            padding:  const EdgeInsets.all(SpaceValues.space8),
            child: Column(
              crossAxisAlignment: CrossAxisAlignment.start,
              children: <Widget>[
                Expanded(
                  child: Stack(
                    children: [
                      Positioned.fill(
                        child: Hero(
                          tag: herotag,
                          child: GlobalImage(
                            widget.avatar,
                            width: 270,
                            height: 270,
                            fit: BoxFit.cover,
                          ),
                          // GlobalImage(
                          //   widget.avatar,
                          //   width: 270,
                          //   height: 270,
                          // ),
                        ),
                      ),
                      Align(
                        alignment: Alignment.bottomLeft,
                        child: Visibility(
                          visible: ('' != widget.promotion) && '0%' != widget.promotion.replaceAll(' ', ''),
                          child: Card(
                            elevation: 0,
                            shape: OutlineInputBorder(
                              borderRadius: BorderRadius.circular(4),
                              borderSide: BorderSide.none,
                            ),
                            color: UIColors.brandA,
                            child: Padding(
                              padding: const EdgeInsets.all(4.0),
                              child: Text(
                                widget.promotion,
                                style: const TextStyle(
                                  color: UIColors.white,
                                  fontSize: 10,
                                ),
                              ),
                            ),
                          ),
                        ),
                      ),
                      Padding(
                        padding: const EdgeInsets.all(4.0),
                        child: Align(
                          alignment: Alignment.bottomLeft,
                          child: SvgPicture.asset(
                            widget.qrCode
                          )
                        ),
                      ),
                    ],
                  ),
                ),
                const SizedBox(height: SpaceValues.space8),
                Text(
                  widget.title,
                  maxLines: 2,
                  overflow: TextOverflow.ellipsis,
                  style: const TextStyle(fontSize: 12, color: UIColors.title, fontWeight: FontWeight.w700),
                ),
                const SizedBox(height: SpaceValues.space4),
                Visibility(
                  visible: widget.discount > 0,
                  child: Text(
                    formatCurrency.format(widget.priceOrigin),
                    maxLines: 1,
                    overflow: TextOverflow.ellipsis,
                    style: const TextStyle(fontSize: 12, color: UIColors.title, decoration: TextDecoration.lineThrough),
                  ),
                  replacement: const SizedBox.shrink(),
                ),
                const SizedBox(height: SpaceValues.space4),
                RichText(
                  text: TextSpan(
                    text: 'Số lượng còn  ',
                    style:
                    const TextStyle(fontSize: 10, color: UIColors.title70),
                    children: <TextSpan>[
                      TextSpan(
                          text: widget.quantity.toString(),
                          style: const TextStyle(
                              fontWeight: FontWeight.w700,
                              fontSize: 12,
                              color: UIColors.title)),
                    ],
                  ),
                ),
                const SizedBox(height: SpaceValues.space4),
                RichText(
                  text: TextSpan(
                    text: 'Giá từ:  ',
                    style:
                    const TextStyle(fontSize: 10, color: UIColors.title70),
                    children: <TextSpan>[
                      TextSpan(
                          text: formatCurrency.format(widget.price.toDouble()),
                          style: const TextStyle(
                              fontWeight: FontWeight.w700,
                              fontSize: 12,
                              color: UIColors.title)),
                    ],
                  ),
                ),
                const SizedBox(
                  height: 10,
                ),
                SizedBox(
                  height: 26,
                  child: Visibility(
                    visible: widget.isCanBuy,
                    child: Row(
                      // mainAxisAlignment: MainAxisAlignment.center,
                      children: [
                        Expanded(
                          child: ElevatedButton(
                            style: ElevatedButton.styleFrom(
                              textStyle: const TextStyle(
                                fontSize: 12,
                              ),
                              tapTargetSize: MaterialTapTargetSize.shrinkWrap,
                              padding: EdgeInsets.zero,
                            ),
                            onPressed: () async
                            => await viewModel.btnAddToCart(widget.productId).then((value) async{
                                Get.to(()=> CreateChangePointCart());
                            })
                            ,
                            child: const Text(
                              'Mua ngay',
                              style: TextStyle(
                                  fontSize: 10, fontWeight: FontWeight.w400),
                            ),
                          ),
                        ),
                        const SizedBox(
                          width: SpaceValues.space8,
                        ),
                        InkWell(
                          onTap: ()
                          async => await viewModel.btnAddToCart( widget.productId).then((value) async{

                          }),
                          child: Container(
                            height: 30,
                            alignment: Alignment.center,
                            width: 48,
                            decoration: BoxDecoration(
                                borderRadius:
                                const BorderRadius.all(Radius.circular(8)),
                                border: Border.all(
                                  color: Colors.blue, //                   <--- border color
                                  width: 1.0,
                                )),
                            child: Icon(
                              Icons.add_shopping_cart,
                              color: Colors.blue,
                              size: 14,
                            ),
                          ),
                        ),
                      ],
                    ),
                    replacement: Visibility(
                      visible: widget.flashSale,
                      child: Row(
                        children: [
                          Expanded(
                            child: ElevatedButton(
                              style: ElevatedButton.styleFrom(
                                textStyle: const TextStyle(
                                  fontSize: 12,
                                ),
                                tapTargetSize: MaterialTapTargetSize.shrinkWrap,
                                padding: EdgeInsets.zero,
                              ),
                              onPressed: null,
                              child: const Text(
                                'Hết hàng',
                                style: TextStyle(
                                    fontSize: 10, fontWeight: FontWeight.w400),
                              ),
                            ),
                          ),
                        ],
                      ),
                      replacement: Row(
                        children: [
                          Expanded(
                            child: ElevatedButton(
                              style: ElevatedButton.styleFrom(
                                textStyle: const TextStyle(
                                  fontSize: 12,
                                ),
                                tapTargetSize: MaterialTapTargetSize.shrinkWrap,
                                padding: EdgeInsets.zero,
                              ),
                              onPressed: null,
                              child: const Text(
                                'Deal sắp diễn ra',
                                style: TextStyle(
                                    fontSize: 10, fontWeight: FontWeight.w400),
                              ),
                            ),
                          ),
                        ],
                      ),
                    ),
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );
  }

  @override
  ProductCartModel createViewModel() => getIt<ProductCartModel>();

}

