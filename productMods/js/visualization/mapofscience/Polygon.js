var INFO_WINDOW = createInfoWindow("", 300);

/* $This file is distributed under the terms of the license in /doc/license.txt$ */
var Polygon = Class.extend({
	init : function(options) {
		this.options = $.extend({}, this.options, options);
		if (options.polygon) {
			this.polygon = options.polygon;
		} else {
			this.polygon = createGoogleCirclePolygon(options);
		}
		this.hide();
		this.registerEvents();
	},
	options : {
		map : null,
		icon : null,
		position : null,
		content : null
	},
	addToMap : function() {
		this.polygon.setMap(this.options.map);
		this.registerEvents();
	},
	removeFromMap : function() {
		this.unregisterEvents();
		this.polygon.setMap(null);
	},
	show : function() {
		this.polygon.setMap(this.options.map);
	},
	hide : function() {
		this.polygon.setMap(null);
	},
	setIcon : function(icon) {
	},
	setZIndex: function(zIndex){
		this.polygon.zIndex = zIndex;
	},
	setTitle : function(title) {
		this.polygon.title = title;
	},
	setOptions: function(options) {
		this.polygon.setOptions(options);
	},
	registerEvent : function(handler) {
		var me = this;
		if (me.handlers == null) {
			me.handlers = new Array();
		}
		me.handlers.push(handler);
	},
	unregisterEvent : function(handler) {
		if (this.handlers[handler]) {
			removeListener(handler);
			delete(this.handlers[handler]);
		}
	},
	registerEvents : function() {
	},
	unregisterEvents : function() {
		$.each(this.handlers, function(){
			removeListener(this);
		});
		this.handlers = null;
	}
});