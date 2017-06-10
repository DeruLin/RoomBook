/**
 * Created by cblin on 2017/6/8.
 */

function msg(msg) {
    layer.msg(msg,{
        time: 1000
    });
}

function openLoading() {
    layer.load(2);
}

function closeLoading() {
    layer.closeAll('loading');
}
