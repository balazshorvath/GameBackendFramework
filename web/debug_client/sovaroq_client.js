$(function() {
	Application.init();
});

var Application = {
	init: function(){
		DebugPort.start(this.promptForServer());
	},
	promptForServer: function(){
		var url = null;
		while((url = prompt("Sovaroq server")) == null);
		return url;
	}
};

var DebugPort = {
	url: null,
	interval: null,
	start: function(url){
		// Delete if stuff remained from a previous port.
		var $container = $("#container");
		if($container != null){
			$container.remove();
		}
		this.clearInfo();
		this.clearError();
		
		this.url = url;
		
		// Construct visuals
		$container = $(document.createElement("div")).attr("id", "container");
		var $event = $(document.createElement("div")).attr("id", "event");
		
		var $message = $(document.createElement("div")).attr("id", "message");
		var $messageInfo = $(document.createElement("div")).attr("id", "info");
		var $messageError = $(document.createElement("div")).attr("id", "error");
		
		$message.append($messageInfo, $messageError);
		$container.append($event, $message);
		$("body").append($container);
		
		// Start
		this.showInfo("Querying server: " + this.url);
		this.startInterval();
	},
	stop: function(){
		this.stopInterval();
	},
	startInterval(){
		this.interval = setInterval(this.queryMessages, 1000);
	},
	stopInterval(){
		if(this.interval != null){
			this.stopInterval(this.interval);
			this.interval = null;
		}
	},
	queryMessages(){
		var self = this;
		$.get(this.url, function(data, status){
			if(status == "success"){
				$.each(data, function(index, value){
					// TODO: so far, OK, but later a better fomat might be needed
					$("div#event").append("<p>" + JSON.stringify(value) + "</p>");
				});
			}else{
				self.showError("Server is unreachable! Status: " + status);
				self.stop();
			}
		}, "json");
	},
	showInfo: function(message){
		$("div#info").append("<p>" + message + "</p>");
	},
	showError: function(message){
		$("div#error").append("<p>" + message + "</p>");
	},
	clearInfo: function(){
		$("div#error").html("<p></p>");
	},
	clearError: function(){
		$("div#error").html("<p></p>");
	}
}
