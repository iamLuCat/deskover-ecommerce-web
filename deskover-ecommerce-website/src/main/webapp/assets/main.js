$("#frmCreateOrder").submit(function () {
    var postData = $("#frmCreateOrder").serialize();
    var submitUrl = $("#frmCreateOrder").attr("action");
    $.ajax({
        type: "POST",
        url: submitUrl,
        data: postData,
        dataType: 'JSON',
        success: function (x) {
            if (x.code === '00') {
                if (window.vnpay) {
                    vnpay.open({width: 768, height: 600, url: x.data});
                } else {
                    location.href = x.data;
                }
                return false;
            } else {
                alert(x.Message);
            }
        }
    });
    return false;
});