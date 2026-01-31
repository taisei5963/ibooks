var ibooks = ibooks || {};
if (typeof jQuery === 'undefined') {
    throw new Error('ibooks.js requires jQuery.');
}

ibooks.replaceNoImages = function(e) {
    var noImage = ibooks.getPath() + "../public/img/noImage.png";
    $(e).attr({
        src : noImage,
        alt : 'no image'
    });
};

ibooks.showLoading = function() {
    $('#loading .spinner-area').show();
    $('#loading .progress-area').hide();
    $('#loading').show();
};

ibooks.showProgress = function() {
    ibooks.updateProgress(1, 0);
    $('#loading .spinner-area').hide();
    $('#loading .progress-area').show();
    $('#loading').show();
};

ibooks.hideProgress = function() {
    $('#loading').hide();
};

ibooks.updateProgress = function(allcount, count) {
    var width = 0;
    if (allcount < 1) {
        width = 100;
    } else {
        width = count / allcount * 100;
    }
    $('#loading .progress-bar').css('width', width + '%');
};