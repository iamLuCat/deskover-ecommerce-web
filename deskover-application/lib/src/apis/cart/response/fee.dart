class FeeGHTK {
  Fee? fee;

  FeeGHTK({this.fee});

  FeeGHTK.fromJson(Map<String, dynamic> json) {
    fee = json['fee'] != null ? new Fee.fromJson(json['fee']) : null;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    if (this.fee != null) {
      data['fee'] = this.fee!.toJson();
    }
    return data;
  }
}

class Fee {
  int? fee;
  bool? delivery;

  Fee({this.fee, this.delivery});

  Fee.fromJson(Map<String, dynamic> json) {
    fee = json['fee'];
    delivery = json['delivery'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = new Map<String, dynamic>();
    data['fee'] = this.fee;
    data['delivery'] = this.delivery;
    return data;
  }
}