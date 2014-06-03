var $badgrNotification = (function() {
	
	var base = AJS.contextPath() + "/rest/badgr/1.0/notifications",
        container = AJS.$("<div class='aui-message success shadowed closeable'></div>");
	
	var render = function(notification) {
		container.html("<p class='title'>"+ notification.message +"</p><span class='aui-icon aui-icon-small aui-iconfont-approve' style='color:#14892c;'></span><span class='aui-icon icon-close' role='button' tabindex='0' onclick=\"$badgrNotification.close("+ notification.id +")\"></span>");
		AJS.$("body #header").prepend(container)
	};
	
	return {
		init: function() {
			AJS.$.getJSON(base)
				.done(function(data) {
					if (data != null) render(data);
				});
		},
		
		close: function(notificationId) {
			AJS.$.ajax({
				url: base + "/" + notificationId,
				dataType: 'json',
				type: 'DELETE'
			}).done(function () {
				container.animate({
					opacity: 0.1,
					height: 'toggle'
				}, 500, function() {
					container.html(null);
				});				
			});
		}
	}
	
})();

AJS.toInit(function () {
	$badgrNotification.init();
});