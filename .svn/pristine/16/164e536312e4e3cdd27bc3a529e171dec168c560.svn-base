cordova.define("com.minxing.client.plugin.web.location.Plugin_intent", function(require, exports, module) { function Plugin_intent() {
}

Plugin_intent.prototype.intent = function (message, duration, position, successCallback, errorCallback) {
  cordova.exec(successCallback, errorCallback, "Plugin_intent", "intent", [message, duration, position]);
};

Plugin_intent.install = function () {
  if (!window.plugins) {
    window.plugins = {};
  }

   window.plugins.plugin_intent = new Plugin_intent();



  return window.plugins.plugin_intent;
};

cordova.addConstructor(Plugin_intent.install);
});



