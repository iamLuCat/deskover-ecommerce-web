class NotifyResponse {
  int? id;
  String? title;
  String? orderCode;
  bool? isWatched;
  String? createdAt;

  NotifyResponse(
      {this.id, this.title, this.orderCode, this.isWatched, this.createdAt});

  NotifyResponse.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    title = json['title'];
    orderCode = json['orderCode'];
    isWatched = json['isWatched'];
    createdAt = json['createdAt'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['id'] = this.id;
    data['title'] = this.title;
    data['orderCode'] = this.orderCode;
    data['isWatched'] = this.isWatched;
    data['createdAt'] = this.createdAt;
    return data;
  }
}