window.notify = function (message) {
    $.notify(message, {
        position: "right bottom",
        className: "success"
    });
}

ajax = function (data, success, url) {
    $.ajax({
        type: "POST",
        url,
        dataType: "json",
        data,
        success: function (response) {
            if (response["redirect"]) {
                location.href = response["redirect"]
                return ;
            }
            success(response)
        }
    });
}
