//全局变量
index = 0;//当前最后一个的索引

function autoHeight() {
    var h = $(window).height() - 20;
    var h_old = 300;
    if (h > h_old) {
        $("#left").css('height', h);
    } else {
        return false;
    }
}

$(function () {
    autoHeight()
    $(window).resize(autoHeight);
});




isFirst = true ;

function getAllFileOnPath(path, obj_a,fileType,isAsync) {

    if(isFirst){
        $.ajax({
            type: 'POST',
            url: 'uri_phone_info',
            dataType: 'json',
            async: isAsync,
            success: function (data) {
                $('#span_phone_model').html(data.MODEL);
                $('#span_phone_sys').html(data.SDK);
            }
        });
        isFirst = false;
    }





    if(obj_a!=null){
//        console.log($(obj_a).parent().attr('id'));
        //    获取当前列div中的所有a标签
        var node_div_file_all_a =$(obj_a).parent().parent().find('a');
        var temp_str_file_name = path.substr(path.lastIndexOf('/')+1);
        $.each(node_div_file_all_a, function (index, content) {
            if($(content).html() == temp_str_file_name){
//                console.log(temp_str_file_name+"\t"+obj_a.parentElement);
                $(content.parentElement).css('background-color','#B2B2B2');
            }else {
                $(content.parentElement).css('background-color','#FFFFFF');
            }
        });
    }

    if(fileType != "5" ){
        layer.msg('暂不支持文件下载!');
        return ;
    }else{
        var layer_index = layer.load();
        $.ajax({
            type: 'POST',
            url: path,
            dataType: 'json',
            async: isAsync,
            success: function (data) {
                layer.close(layer_index);
                console.log(data);

                if(data.isDir=='1'){

                    $('#span_path').html(path);

                    if (index > 0) {

                        var temp_node_file_list_id = $(obj_a).parent().parent().attr('id');
                        if(temp_node_file_list_id != null){
                            var child_count = $('#div_left_bottom').children().length;
                            var now_temp_file_list_id_count = temp_node_file_list_id.substring(temp_node_file_list_id.length - 1);
                            var remove_start = parseInt(now_temp_file_list_id_count) + parseInt(1);
                            for (i = remove_start; i < child_count; i++) {
                                var obj = document.getElementById("div_id_" + i);
                                //                        console.log("被移除的id = " + $(obj).attr("id") + "\t temp string = " + ("div_id_" + i) + "\t   " + (obj == null ));
                                $(obj).remove();
                            }

                            index = remove_start;
                            //                console.log("index = " + index);
                            //                console.log("被点击列的id = " + temp_node_file_list_id);
                            //                console.log("移除的开始值 = " + remove_start);
                            //                console.log("多少列 = " + child_count);
                        }

                    }


                    var child_list = $('<div class="div_file_list"></div>');
                    child_list.attr('id', 'div_id_' + index);

                    index = index + 1;
                    console.log("index  ="+index);
                    if (data.list == null || data.list.length == 0) {
                        var item_img = $('<img >');
                        item_img.attr('src', "ico_null_dir.png");
                        child_list.append(item_img);
                        $('#div_left_bottom').append(child_list);
                        return;
                    } else {
                        $.each(data.list, function (index, content) {
                            var ico;
                            if (content.fileType == "5") {
                                ico = "ico_directory.png";
                            } else if (content.fileType == "0") {
                                ico = "ico_unknow.png";
                            } else if (content.fileType == "13") {
                                ico = "ico_sys_file.png";
                            } else if (content.fileType == "3") {
                                ico = "ico_photo.png";
                            }else if (content.fileType == "15") {
                                ico = "ico_pdf.png";
                            }else if (content.fileType == "1") {
                                ico = "ico_music.png";
                            }else if (content.fileType == "2") {
                                ico = "ico_movie.png";
                            }else if (content.fileType == "7") {
                                ico = "ico_rar.png";
                            }


                            var item_img = $('<img class="img_ico">');
                            item_img.attr('src', ico);
                            item_img.css({'margin': '5px 2px '});

                            var item_a = "<a href='javascript:void(0)' onclick='getAllFileOnPath(\"" + content.absPath + "\",this,"+content.fileType+",true)' style='text-decoration:none;'>" + content.name + "</a>";

                            //                      var item_a = $('<a>'+content.name+'</a>');
                            //                      item_a.attr('href','javascript:void(0)');
                            //                      $(item_a).bind('click',function(){
                            //                          getAllFileOnPath(content.absPath,item_a.this);
                            //                      });

                            var item = $('<div class="div_file_item"></div>');
                            if (content.fileType == "5") {
                                var img_directory = $('<img >');
                                img_directory.attr('src', "ico_more.png");
                                item.append(item_img, item_a, img_directory);
                            } else {
                                item.append(item_img, item_a);
                            }

                            child_list.append(item);
                        });
                        $('#div_left_bottom').append(child_list);
                    }}
            }
        });
    }

}


function pull() {
    var layer_index = layer.load();
    $.ajax({
        type: 'POST',
        url: "myfinal_pull_text_function_w2a",
        dataType: 'json',
        async: true,
        success: function (data) {
            $.ajax({
                type: 'POST',
                url: "myfinal_pull_text_function_w2a_get",
                dataType: 'text',
                timeout: 10000,
                async: true,
                success: function (data) {
                    layer.close(layer_index);
                    $('#pull_push_textarea').val(data);
                }
            });
        }
    });
}

function push() {
    var layer_index = layer.load();
    var value = $('#pull_push_textarea').val();
    $.ajax({
        type: 'POST',
        url: "myfinal_push_text_function_w2a",
        dataType: 'text',
        async: true,
        data: {'push_text': value},
        success: function (data) {
            layer.close(layer_index);
            layer.msg(data);
        }
    });
}

function btn_create_dir(){

    var path = $("#span_path",window.parent.document).html() ;
    temp_path_find_a = path.substr(1);

    var node_a ;

    if(temp_path_find_a.length>0){
        var paths = path.split("/");
        console.log(temp_path_find_a);

        var node_all_a = $(('#div_id_'+(paths.length - 2)),window.parent.document).find('a');
        console.log(('#div_id_'+(paths.length - 2))+"\t 节点个数:"+node_all_a.length);

        console.log("被点击的item文字:" +paths[paths.length-1]);

        $.each(node_all_a, function (index, content) {

            if($(content).html() == paths[paths.length-1]){
                node_a = $(content);
                console.log($(content).html() +"\t"+ paths[paths.length-1]);
                return ;
            }
        });
    }
    console.log(node_a);

    layer.open({
        type: 2,
        title: '创建文件夹',
        shadeClose: true,
        shade: 0.8,
        area: ['400px', '250px'],
        content: 'input_text.html',
        end: function () {
            if(node_a!=null){
                getAllFileOnPath(path,node_a,5,true);
            }
        }
    });
}

function btn_del_dir(){
    var path = $("#span_path",window.parent.document).html() ;
    if(path == '/'){
        layer.msg('根目录无法删除!');
        return ;
    }

    temp_path_find_a = path.substr(1);
    console.log("路径分割的数组:"+temp_path_find_a);


    var node_a ;

    if(temp_path_find_a.length>0){
        var paths = path.split("/");
        console.log(temp_path_find_a);

        var node_all_a = $(('#div_id_'+(paths.length - 3)),window.parent.document).find('a');
        console.log(('#div_id_'+(paths.length - 3))+"\t 节点个数:"+node_all_a.length);

        console.log("被点击的item文字:" +paths[paths.length-2]);

        $.each(node_all_a, function (index, content) {

            if($(content).html() == paths[paths.length-2]){
                node_a = $(content);
                console.log($(content).html() +"\t"+ paths[paths.length-2]);
                return ;
            }
        });
    }

    layer.confirm('确定要删除\n'+path+"\n目录吗?<br> 警告子目录及文件都将被删除!!", {
        btn: ['确定','取消'] //按钮
    }, function(){
        var layer_index = layer.load();
        $.ajax({
            type: 'POST',
            url: "uri_del_dir",
            dataType: 'text',
            async: true,
            data: {'path': path},
            success: function (data) {
                path_click = path.substr(0,path.lastIndexOf('/')) ;
                console.log(node_a );
                getAllFileOnPath(path_click,node_a,5,true);
                layer.close(layer_index);
                layer.msg(data);
            }
        });
    });
}

function btn_this_dir(){
    var layer_index = layer.load();
    $('#div_left_bottom').empty();
    index = 0 ;
    $.ajax({
        type: 'POST',
        url: "uri_get_project_catch_dir",
        dataType: 'text',
        async: true,
        success: function (data) {
            layer.close(layer_index);
            console.log(data);
            if(data!=null){
                var paths = data.split("/");
                paths[0] ='/';

                var path="";
                for(var i=0;i<paths.length;i++){
                    if(i<=1){
                        path += paths[i];
                    }else {
                        path+='/'+paths[i]
                    }

                    console.log(path);
                    var node_a ;
                    if(i>0){
                        var node_all_a = $('#div_id_'+(i-1)).find('a');
                        console.log(('#div_id_'+(i-1))+"\t 节点个数:"+node_all_a.length);
                        console.log("被点击的item文字:" +paths[i]);
                        $.each(node_all_a, function (index, content) {
                            if($(content).html() == paths[i]){
                                node_a = $(content);
                                console.log($(content).html() +"\t"+ paths[i]);
                                return ;
                            }
                        });
                    }
                    console.log('--------------------------');

                    getAllFileOnPath(path,node_a,5,false);
                }
            }
        }
    });
}

function btn_to_file_dir(flag){
    var parameter ;
    if(flag == "wx"){
        parameter = "wx";
    }else if(flag == "qq"){
        parameter = "qq";
    }else {
        layer.msg('输入的路径错误!');
        return ;
    }

    var layer_index = layer.load();
    $('#div_left_bottom').empty();


    $.ajax({
        type: 'POST',
        url: 'uri_get_file_catch_dir',
        dataType: 'json',
        async: true,
        data:{'file_type': parameter},
        success: function (data) {
            index = 0 ;
            layer.close(layer_index);
            if(data.flag=="0"){
                var str ;
                if(parameter == "wx"){
                    str ="没有定位到微信的储存路径";
                }else {
                    str ="没有定位到qq的储存路径";
                }
                layer.msg(str);
                getAllFileOnPath('/',null,5,true);
                return ;
            }else if(data.flag=="1"){


                console.log(data);
                if(data!=null){
                    var paths = data.path.split("/");
                    paths[0] ='/';

                    var path="";
                    for(var i=0;i<paths.length;i++){
                        if(i<=1){
                            path += paths[i];
                        }else {
                            path+='/'+paths[i]
                        }

                        console.log(path);
                        var node_a ;
                        if(i>0){

                            var node_all_a = $('#div_id_'+(i-1)).find('a');
                            console.log(('#div_id_'+(i-1))+"\t 节点个数:"+node_all_a.length);
                            console.log("被点击的item文字:" +paths[i]);
                            $.each(node_all_a, function (index, content) {
                                if($(content).html() == paths[i]){
                                    node_a = $(content);
                                    console.log($(content).html() +"\t"+ paths[i]);
                                    return ;
                                }
                            });
                        }
                        console.log('--------------------------');

                        getAllFileOnPath(path,node_a,5,false);
                    }
                }

            }}
    });
}


// 文件上传
jQuery(function() {
    var $ = jQuery,
        $list = $('#thelist'),
        $btn = $('#ctlBtn'),
        state = 'pending',
        uploader;

    uploader = WebUploader.create({

        // 不压缩image
        resize: false,

        // swf文件路径
        swf: 'webuploader/0.1.5/Uploader.swf',

        // 文件接收服务端。
        server: 'uri_upload_file',

        // 选择文件的按钮。可选。
        // 内部根据当前运行是创建，可能是input元素，也可能是flash.
        pick: '#picker'
    });

    // 当有文件添加进来的时候
    uploader.on( 'fileQueued', function( file ) {
        $list.append( '<div id="' + file.id + '" class="item">' +
            '<span class="info">' + file.name + '</span>' +
            '<span class="state">等待上传...</span>' +
            '</div>' );
    });

    // 文件上传过程中创建进度条实时显示。
    uploader.on( 'uploadProgress', function( file, percentage ) {
        var $li = $( '#'+file.id ),
            $percent = $li.find('.progress .progress-bar');

        // 避免重复创建
        if ( !$percent.length ) {
            $percent = $('<div class="progress progress-striped active">' +
                '<div class="progress-bar" role="progressbar" style="width: 0%;">' +
                '</div>' +
                '</div>').appendTo( $li ).find('.progress-bar');

        }
        var temp = percentage * 100 +"";
        if(temp.indexOf('.')){
            if(temp.length>5){
                temp = temp.substr(0,5);
            }
        }
        $li.find('span.info').text(file.name+"------>"+(temp+ '%')+"------>");
        $li.find('span.state').text('上传中');

        <!--$percent.css( 'width', percentage * 100 + '%' );-->
        <!--$percent.html(percentage * 100 + '%' );-->
        console.log(percentage * 100);
    });

    uploader.on( 'uploadSuccess', function( file ) {
        $( '#'+file.id ).find('span.state').text('已上传');
    });

    uploader.on( 'uploadError', function( file ) {
        $( '#'+file.id ).find('span.state').text('上传出错');
    });

    uploader.on( 'uploadComplete', function( file ) {
        $( '#'+file.id ).find('.progress').fadeOut();
    });

    uploader.on( 'all', function( type ) {
        if ( type === 'startUpload' ) {
            state = 'uploading';
        } else if ( type === 'stopUpload' ) {
            state = 'paused';
        } else if ( type === 'uploadFinished' ) {
            state = 'done';
            $btn.attr("disabled", false);
        }

        if ( state === 'uploading' ) {
            $btn.text('开始上传');
            $btn.attr("disabled", true);
        } else {
            $btn.text('开始上传');
        }
    });

    $btn.on( 'click', function() {
        if ( state === 'uploading' ) {
            uploader.stop();
        } else {
            uploader.upload();
        }
    });
});
