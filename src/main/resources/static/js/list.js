function deleteBook(id,name) {
    if (confirm("确定删除" + name + "?")) {
        $.post(
            "/library/book/delete",
            {"id": id},
            function (data) {
                data = $.parseJSON(data);
                console.log(data)
                if (data.msg === 1) {
                    console.log("delete success")
                    window.location.reload()
                }else {
                    console.log("delete fail")
                }
            }
        );
    }
}