// GENERATED CODE - DO NOT MODIFY BY HAND

part of 'product_response.dart';

// **************************************************************************
// JsonSerializableGenerator
// **************************************************************************

DataProductResponse _$DataProductResponseFromJson(Map<String, dynamic> json) =>
    DataProductResponse(
      content: (json['content'] as List<dynamic>?)
          ?.map((e) => Product.fromJson(e as Map<String, dynamic>))
          .toList(),
    );

Map<String, dynamic> _$DataProductResponseToJson(
        DataProductResponse instance) =>
    <String, dynamic>{
      'content': instance.content,
    };

Product _$ProductFromJson(Map<String, dynamic> json) => Product(
      id: json['id'] as int?,
      name: json['name'] as String?,
      slug: json['slug'] as String?,
      description: json['description'] as String?,
      price: (json['price'] as num?)?.toDouble(),
      img: json['img'] as String?,
      imgUrl: json['imgUrl'] as String?,
      actived: json['actived'] as bool?,
      modifiedAt: json['modifiedAt'] as String?,
      modifiedBy: json['modifiedBy'] as String?,
      spec: json['spec'] as String?,
      utility: json['utility'] as String?,
      design: json['design'] as String?,
      other: json['other'] as String?,
      video: json['video'] as String?,
      quantity: json['quantity'] as int?,
      discount: json['discount'] == null
          ? null
          : Discount.fromJson(json['discount'] as Map<String, dynamic>),
      subCategory: json['subCategory'] == null
          ? null
          : SubCategory.fromJson(json['subCategory'] as Map<String, dynamic>),
      brand: json['brand'] == null
          ? null
          : Brand.fromJson(json['brand'] as Map<String, dynamic>),
      flashSale: json['flashSale'] == null
          ? null
          : FlashSale.fromJson(json['flashSale'] as Map<String, dynamic>),
      ratings: (json['ratings'] as List<dynamic>?)
          ?.map((e) => Rating.fromJson(e as Map<String, dynamic>))
          .toList(),
      productThumbnails: (json['productThumbnails'] as List<dynamic>?)
          ?.map((e) => ProductThumbnails.fromJson(e as Map<String, dynamic>))
          .toList(),
      averageRating: json['averageRating'] as int?,
      totalRating: json['totalRating'] as int?,
      totalSold: json['totalSold'] as int?,
      rating5: json['rating5'] as int?,
      rating4: json['rating4'] as int?,
      rating3: json['rating3'] as int?,
      rating2: json['rating2'] as int?,
      rating1: json['rating1'] as int?,
    );

Map<String, dynamic> _$ProductToJson(Product instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'slug': instance.slug,
      'description': instance.description,
      'price': instance.price,
      'img': instance.img,
      'imgUrl': instance.imgUrl,
      'actived': instance.actived,
      'modifiedAt': instance.modifiedAt,
      'modifiedBy': instance.modifiedBy,
      'spec': instance.spec,
      'utility': instance.utility,
      'design': instance.design,
      'other': instance.other,
      'video': instance.video,
      'quantity': instance.quantity,
      'discount': instance.discount,
      'subCategory': instance.subCategory,
      'brand': instance.brand,
      'flashSale': instance.flashSale,
      'ratings': instance.ratings,
      'productThumbnails': instance.productThumbnails,
      'averageRating': instance.averageRating,
      'totalRating': instance.totalRating,
      'totalSold': instance.totalSold,
      'rating5': instance.rating5,
      'rating4': instance.rating4,
      'rating3': instance.rating3,
      'rating2': instance.rating2,
      'rating1': instance.rating1,
    };

Discount _$DiscountFromJson(Map<String, dynamic> json) => Discount(
      id: json['id'] as int?,
      name: json['name'] as String?,
      description: json['description'] as String?,
      percent: json['percent'] as int?,
      startDate: json['startDate'] as String?,
      endDate: json['endDate'] as String?,
      actived: json['actived'] as bool?,
      modifiedAt: json['modifiedAt'] as String?,
      modifiedBy: json['modifiedBy'] as String?,
    );

Map<String, dynamic> _$DiscountToJson(Discount instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'description': instance.description,
      'percent': instance.percent,
      'startDate': instance.startDate,
      'endDate': instance.endDate,
      'actived': instance.actived,
      'modifiedAt': instance.modifiedAt,
      'modifiedBy': instance.modifiedBy,
    };

FlashSale _$FlashSaleFromJson(Map<String, dynamic> json) => FlashSale(
      id: json['id'] as int?,
      name: json['name'] as String?,
      startDate: json['startDate'] as String?,
      startDateFormat: json['startDateFormat'] as String?,
      endDate: json['endDate'] as String?,
      actived: json['actived'] as bool?,
      modifiedBy: json['modifiedBy'] as String?,
      endDateFormat: json['endDateFormat'] as String?,
    )
      ..description = json['description'] as String?
      ..percent = json['percent'] as int?
      ..modifiedAt = json['modifiedAt'] as String?;

Map<String, dynamic> _$FlashSaleToJson(FlashSale instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'description': instance.description,
      'percent': instance.percent,
      'startDate': instance.startDate,
      'startDateFormat': instance.startDateFormat,
      'endDate': instance.endDate,
      'endDateFormat': instance.endDateFormat,
      'actived': instance.actived,
      'modifiedAt': instance.modifiedAt,
      'modifiedBy': instance.modifiedBy,
    };

SubCategory _$SubCategoryFromJson(Map<String, dynamic> json) => SubCategory(
      id: json['id'] as int?,
      category: json['category'] == null
          ? null
          : CategoryReponse.fromJson(json['category'] as Map<String, dynamic>),
      img: json['img'] as String?,
      imgUrl: json['imgUrl'] as String?,
      name: json['name'] as String?,
      description: json['description'] as String?,
      slug: json['slug'] as String?,
      modifiedAt: json['modifiedAt'] as String?,
      actived: json['actived'] as bool?,
      modifiedBy: json['modifiedBy'] as String?,
    );

Map<String, dynamic> _$SubCategoryToJson(SubCategory instance) =>
    <String, dynamic>{
      'id': instance.id,
      'category': instance.category,
      'img': instance.img,
      'imgUrl': instance.imgUrl,
      'name': instance.name,
      'description': instance.description,
      'slug': instance.slug,
      'modifiedAt': instance.modifiedAt,
      'actived': instance.actived,
      'modifiedBy': instance.modifiedBy,
    };

Brand _$BrandFromJson(Map<String, dynamic> json) => Brand(
      id: json['id'] as int?,
      name: json['name'] as String?,
      description: json['description'] as String?,
      slug: json['slug'] as String?,
      actived: json['actived'] as bool?,
      modifiedAt: json['modifiedAt'] as String?,
      modifiedBy: json['modifiedBy'] as String?,
    );

Map<String, dynamic> _$BrandToJson(Brand instance) => <String, dynamic>{
      'id': instance.id,
      'name': instance.name,
      'description': instance.description,
      'slug': instance.slug,
      'actived': instance.actived,
      'modifiedAt': instance.modifiedAt,
      'modifiedBy': instance.modifiedBy,
    };

ProductThumbnails _$ProductThumbnailsFromJson(Map<String, dynamic> json) =>
    ProductThumbnails(
      id: json['id'] as int?,
      thumbnail: json['thumbnail'] as String?,
      thumbnailUrl: json['thumbnailUrl'] as String?,
      modifiedAt: json['modifiedAt'] as String?,
      modifiedBy: json['modifiedBy'] as String?,
    );

Map<String, dynamic> _$ProductThumbnailsToJson(ProductThumbnails instance) =>
    <String, dynamic>{
      'id': instance.id,
      'thumbnail': instance.thumbnail,
      'thumbnailUrl': instance.thumbnailUrl,
      'modifiedAt': instance.modifiedAt,
      'modifiedBy': instance.modifiedBy,
    };

Rating _$RatingFromJson(Map<String, dynamic> json) => Rating(
      id: json['id'] as int?,
      fullname: json['fullname'] as String?,
      email: json['email'] as String?,
      point: json['point'] as int?,
      content: json['content'] as String?,
      actived: json['actived'] as bool?,
      modifiedAt: json['modifiedAt'] as String?,
    );

Map<String, dynamic> _$RatingToJson(Rating instance) => <String, dynamic>{
      'id': instance.id,
      'fullname': instance.fullname,
      'email': instance.email,
      'point': instance.point,
      'content': instance.content,
      'actived': instance.actived,
      'modifiedAt': instance.modifiedAt,
    };
