AJS.toInit(function () {
    AJS.$(".badgr-dial").knob()
    
    AJS.$('.clickable-badge').on('click', function(e) {
        console.log(e);
        var code = AJS.$(e.currentTarget).data('key-id');
        window.location = AJS.contextPath() + "/plugins/servlet/achievements/badges/" + code;
    });
});