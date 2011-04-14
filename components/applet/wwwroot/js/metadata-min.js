/*
 * Metadata - Quietly extract JSON metadata contained within DOM elements
 *
 * @namespace YAHOO.jQuery
 * @module metadata
 *
 * Copyright (c) 2006 John Resig, Yehuda Katz, Jorn Zaefferer, Paul McLanahan
 *
 * Ported to YUI from jQuery by tszming (tszming@gmail.com)
 *
 * Dual licensed under the MIT and GPL licenses:
 *   http://www.opensource.org/licenses/mit-license.php
 *   http://www.gnu.org/licenses/gpl.html
 *
 */
YAHOO.namespace("plugin");YAHOO.plugin.Metadata={cache:{},defaults:{type:"class",name:"metadata",cre:/({.*})/,single:"metadata"},setType:function(b,a){this.defaults.type=b;this.defaults.name=a},get:function(elem,opts){var settings=this.defaults;opts=opts||{};if("undefined"!==typeof opts.type){settings.type=opts.type}if("undefined"!==typeof opts.name){settings.name=opts.name}if(!settings.single.length){settings.single="metadata"}var elemId=YAHOO.util.Dom.generateId(elem);if("undefined"===typeof this.cache[elemId]){this.cache[elemId]={}}var data=this.cache[elemId][settings.single];if(data){return data}data="{}";if(settings.type=="class"){var m=settings.cre.exec(elem.className);if(m){data=m[1]}}else{if(settings.type=="elem"){if(!elem.getElementsByTagName){return}var e=elem.getElementsByTagName(settings.name);if(e.length){data=YAHOO.lang.trim(e[0].innerHTML)}}else{if(elem.getAttribute!=undefined){var attr=elem.getAttribute(settings.name);if(attr){data=attr}}}}if(data.indexOf("{")<0){data="{"+data+"}"}data=eval("("+data+")");this.cache[elemId][settings.single]=data;return data}};YAHOO.register("YAHOO.plugin.Metadata",YAHOO.plugin.Metadata,{version:"2.0",build:"2"});