
/*!
	VNPAY PaymentJS 1.0.0
	https://pay.vnpay.vn
*/
(function() {
    "use strict";
    var method = {}, $overlay, $modal, $content, $close, $iframe;
    var cfg = {
        width: 768,
        height: 490,
        url: '#'
    };
    var vnpay = {};
    vnpay.open = function(settings) {
        cfg = $.extend({}, cfg, settings);
         window.top.location.href = cfg.url;
         return;
        //Check browser version
        //var ua = this.browser();
       
        //if (ua !== null && ua.mobile) {
        //    window.top.location.href = cfg.url;
        //    return;
        //} else {
        //    if (ua !== null && ua.name === 'safari') {
        //        window.top.location.href = cfg.url;
        //        return;
        //    } else {
        //        ////check IE 7,8
        //        if (ua !== null && ua.name === 'msie') {
        //            window.top.location.href = cfg.url;
        //            return;

        //        } else {
        //            ////check chrome
        //            if (ua !== null && ua.name === 'chrome') {
        //                window.top.location.href = cfg.url;
        //                return;

        //            }
        //        }
        //    }

        //    $iframe = $('<iframe id="vnpay_frame"/>')
        //        .attr('src', cfg.url);
        //    //set style for iframe
        //    $iframe.css("border", "none");
        //    $iframe.css({
        //        // width: cfg.width - 14 || 'auto',
        //        height: cfg.height - 14 || 'auto'
        //    });
        //    $content.empty().append($iframe);
        //    $modal.css({
        //        width: cfg.width || 'auto',
        //        height: cfg.height || 'auto'
        //    });
        //    $content.css({
        //        width: cfg.width - 14 || 'auto',
        //        height: cfg.height - 14 || 'auto'
        //    });
        //    method.center();
        //    $(window).bind('resize.modal', method.center);
        //    $modal.show();
        //    $overlay.show();
        //    //add event for Listener
        //    var evt = window.addEventListener ? "addEventListener" : "attachEvent";
        //    var evtEnter = window[evt];
        //    var messageEvent = evt === "attachEvent" ? "onmessage" : "message";
        //    // Listen to message from child window
        //    evtEnter(messageEvent,
        //        function(e) {
        //            //call resize frame
        //            var newWidth = e.data.width;
        //            var newHeight = e.data.height;
        //            if (newWidth === 'undefined') {
        //                newWidth = 768;
        //            }
        //            if (newHeight === 'undefined') {
        //                newHeight = 600;
        //            }

        //            if (newHeight > 490) {
        //                cfg.height = newHeight;

        //            } else {
        //                cfg.height = 490;
        //            }
        //            if (newWidth > 768) {
        //                cfg.width = newWidth;
        //            } else {
        //                cfg.width = 768;
        //            }
        //            method.resize();
        //        },
        //        false);

        //}
    }
    //Resize
    method.resize = function() {
        $iframe.css({
            width: cfg.width - 14 || 'auto',
            height: cfg.height - 14 || 'auto'
        });
        $modal.css({
            width: cfg.width || 'auto',
            height: cfg.height || 'auto'
        });
        $content.css({
            width: cfg.width - 14 || 'auto',
            height: cfg.height - 14 || 'auto'
        });
    }
    // Close the modal
    method.close = function() {
        $modal.hide();
        $overlay.hide();
        $content.empty();
        $(window).unbind('resize.modal');
    };
    // Center the modal in the viewport
    method.center = function() {
        var top, left;
        top = Math.max($(window).height() - $modal.outerHeight(), 0) / 2;
        left = Math.max($(window).width() - $modal.outerWidth(), 0) / 2;
        if ($modal.width() < 768) {
            left = Math.max($(window).width() - 768, 0) / 2;
        }
        $modal.css({
            top: top + $(window).scrollTop(),
            left: left + $(window).scrollLeft()
        });
    };
    vnpay.browser = function(ua) {
        // If an UA is not provided, default to the current browser UA.
        if (ua === undefined) {
            ua = window.navigator.userAgent;
        }
        ua = ua.toLowerCase();
        var match = /(edge)\/([\w.]+)/.exec(ua) ||
            /(opr)[\/]([\w.]+)/.exec(ua) ||
            /(chrome)[ \/]([\w.]+)/.exec(ua) ||
            /(iemobile)[\/]([\w.]+)/.exec(ua) ||
            /(version)(applewebkit)[ \/]([\w.]+).*(safari)[ \/]([\w.]+)/.exec(ua) ||
            /(webkit)[ \/]([\w.]+).*(version)[ \/]([\w.]+).*(safari)[ \/]([\w.]+)/.exec(ua) ||
            /(webkit)[ \/]([\w.]+)/.exec(ua) ||
            /(opera)(?:.*version|)[ \/]([\w.]+)/.exec(ua) ||
            /(msie) ([\w.]+)/.exec(ua) ||
            ua.indexOf("trident") >= 0 && /(rv)(?::| )([\w.]+)/.exec(ua) ||
            ua.indexOf("compatible") < 0 && /(mozilla)(?:.*? rv:([\w.]+)|)/.exec(ua) ||
            [];
        //  debugger;
        var platform_match = /(ipad)/.exec(ua) ||
            /(ipod)/.exec(ua) ||
            /(windows phone)/.exec(ua) ||
            /(iphone)/.exec(ua) ||
            /(kindle)/.exec(ua) ||
            /(silk)/.exec(ua) ||
            /(android)/.exec(ua) ||
            /(win)/.exec(ua) ||
            /(mac)/.exec(ua) ||
            /(linux)/.exec(ua) ||
            /(cros)/.exec(ua) ||
            /(playbook)/.exec(ua) ||
            /(bb)/.exec(ua) ||
            /(blackberry)/.exec(ua) ||
            [];

        var browser = {},
            matched = {
                browser: match[5] || match[3] || match[1] || "",
                version: match[2] || match[4] || "0",
                versionNumber: match[4] || match[2] || "0",
                platform: platform_match[0] || ""
            };
       
        if (matched.browser) {
            browser[matched.browser] = true;
            browser.version = matched.version;
            browser.versionNumber = parseInt(matched.versionNumber, 10);
        }
        if (matched.platform) {
            browser[matched.platform] = true;
        }
        // These are all considered mobile platforms, meaning they run a mobile browser
        if (browser.android ||
            browser.bb ||
            browser.blackberry ||
            browser.ipad ||
            browser.iphone ||
            browser.ipod ||
            browser.kindle ||
            browser.playbook ||
            browser.silk ||
            browser["windows phone"]) {
            browser.mobile = true;
        }

        // These are all considered desktop platforms, meaning they run a desktop browser
        if (browser.cros || browser.mac || browser.linux || browser.win) {
            browser.desktop = true;
        }
        // Chrome, Opera 15+ and Safari are webkit based browsers
        if (browser.chrome || browser.opr || browser.safari) {
            browser.webkit = true;
        }
        // IE11 has a new token so we will assign it msie to avoid breaking changes
        if (browser.rv || browser.iemobile) {
            var ie = "msie";
            matched.browser = ie;
            browser[ie] = true;
        }
        // Edge is officially known as Microsoft Edge, so rewrite the key to match
        if (browser.edge) {
            delete browser.edge;
            var msedge = "msedge";
            matched.browser = msedge;
            browser[msedge] = true;
        }
        // Blackberry browsers are marked as Safari on BlackBerry
        if (browser.safari && browser.blackberry) {
            var blackberry = "blackberry";
            matched.browser = blackberry;
            browser[blackberry] = true;
        }
        // Playbook browsers are marked as Safari on Playbook
        if (browser.safari && browser.playbook) {
            var playbook = "playbook";
            matched.browser = playbook;
            browser[playbook] = true;
        }
        // BB10 is a newer OS version of BlackBerry
        if (browser.bb) {
            var bb = "blackberry";
            matched.browser = bb;
            browser[bb] = true;
        }
        // Opera 15+ are identified as opr
        if (browser.opr) {
            var opera = "opera";
            matched.browser = opera;
            browser[opera] = true;
        }
        // Stock Android browsers are marked as Safari on Android.
        if (browser.safari && browser.android) {
            var android = "android";
            matched.browser = android;
            browser[android] = true;
        }

        // Kindle browsers are marked as Safari on Kindle
        if (browser.safari && browser.kindle) {
            var kindle = "kindle";
            matched.browser = kindle;
            browser[kindle] = true;
        }

        // Kindle Silk browsers are marked as Safari on Kindle
        if (browser.safari && browser.silk) {
            var silk = "silk";
            matched.browser = silk;
            browser[silk] = true;
        }

        // Assign the name and platform variable
        browser.name = matched.browser;
        browser.platform = matched.platform;
        return browser;

    };

    // Generate the HTML and add it to the document
    $overlay = $('<div id="vnpay_overlay" class="vnpay_overlay"></div>');
    $modal = $('<div id="vnpay_modal" class="vnpay_modal"></div>');
    $content = $('<div id="vnpay_content" class="vnpay_content"></div>');
    $close = $(' <a id="vnpay_close" href="#" class="vnpay_close">close</a> ');
    $modal.hide();
    $overlay.hide();
    $modal.append($content, $close);
    $(document).ready(function() {
        $('body').append($overlay, $modal);
    });
    $close.click(function(e) {
        e.preventDefault();
        method.close();
    });

    if (!window.vnpay) {
        window.vnpay = vnpay;
    }
})();