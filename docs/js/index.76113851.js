(function(n){function t(t){for(var a,r,l=t[0],s=t[1],c=t[2],p=0,f=[];p<l.length;p++)r=l[p],Object.prototype.hasOwnProperty.call(o,r)&&o[r]&&f.push(o[r][0]),o[r]=0;for(a in s)Object.prototype.hasOwnProperty.call(s,a)&&(n[a]=s[a]);u&&u(t);while(f.length)f.shift()();return i.push.apply(i,c||[]),e()}function e(){for(var n,t=0;t<i.length;t++){for(var e=i[t],a=!0,l=1;l<e.length;l++){var s=e[l];0!==o[s]&&(a=!1)}a&&(i.splice(t--,1),n=r(r.s=e[0]))}return n}var a={},o={index:0},i=[];function r(t){if(a[t])return a[t].exports;var e=a[t]={i:t,l:!1,exports:{}};return n[t].call(e.exports,e,e.exports,r),e.l=!0,e.exports}r.m=n,r.c=a,r.d=function(n,t,e){r.o(n,t)||Object.defineProperty(n,t,{enumerable:!0,get:e})},r.r=function(n){"undefined"!==typeof Symbol&&Symbol.toStringTag&&Object.defineProperty(n,Symbol.toStringTag,{value:"Module"}),Object.defineProperty(n,"__esModule",{value:!0})},r.t=function(n,t){if(1&t&&(n=r(n)),8&t)return n;if(4&t&&"object"===typeof n&&n&&n.__esModule)return n;var e=Object.create(null);if(r.r(e),Object.defineProperty(e,"default",{enumerable:!0,value:n}),2&t&&"string"!=typeof n)for(var a in n)r.d(e,a,function(t){return n[t]}.bind(null,a));return e},r.n=function(n){var t=n&&n.__esModule?function(){return n["default"]}:function(){return n};return r.d(t,"a",t),t},r.o=function(n,t){return Object.prototype.hasOwnProperty.call(n,t)},r.p="/frontend-gradle-plugin/";var l=window["webpackJsonp"]=window["webpackJsonp"]||[],s=l.push.bind(l);l.push=t,l=l.slice();for(var c=0;c<l.length;c++)t(l[c]);var u=s;i.push([0,"chunk-vendors"]),e()})({0:function(n,t,e){n.exports=e("ba59")},"141b":function(n,t,e){"use strict";var a=e("3eac"),o=e.n(a);o.a},"3eac":function(n,t,e){},"40de":function(n,t,e){n.exports=e.p+"img/siouan-icon.c3d44901.svg"},"8bfc":function(n,t,e){"use strict";var a=e("f981"),o=e.n(a);o.a},ba59:function(n,t,e){"use strict";e.r(t);e("e260"),e("e6cf"),e("cca6"),e("a79d");var a=e("2b0e"),o=e("8c4f"),i=e("a925"),r={lang:{english:"English",french:"Français"},maintenance:{inProgress:"Coming soon, thanks for your patience!"},menu:{configuration:"Configuration",home:"Home",installation:"Installation",tasks:"Tasks"},navigation:{nodejs:{label:"Node.js"},notFound:"The page you requested was not found.",npm:{label:"npm"},yarn1:{label:"Yarn",title:"Yarn 1.x"}},siouan:{organizationTooltip:"Siouan at GitHub.com",projectTooltip:"Frontend Gradle plugin at GitHub.com"}},l={lang:{english:"English",french:"Français"},maintenance:{inProgress:"Nous serons bientôt là, merci de votre patience !"},menu:{configuration:"Configuration",home:"Accueil",installation:"Installation",tasks:"Tâches"},navigation:{nodejs:{label:"Node.js"},notFound:"La page demandée n'existe pas.",npm:{label:"npm"},yarn1:{label:"Yarn",title:"Yarn 1.x"}},siouan:{organizationTooltip:"Siouan sur GitHub.com",projectTooltip:"Frontend Gradle plugin sur GitHub.com"}},s=function(){var n=this,t=n.$createElement,e=n._self._c||t;return e("div",{staticClass:"container-lg",attrs:{id:"app"}},[e("fgp-header",{staticClass:"mb-3",on:{"lang-change":function(t){return n.changeLang(t)}}}),e("router-view"),e("div",{staticClass:"mx-auto my-5 w-50 p-5 border text-center"},[n._v(n._s(n.$t("maintenance.inProgress")))])],1)},c=[],u=function(){var n=this,t=n.$createElement,a=n._self._c||t;return a("header",[a("nav",{staticClass:"navbar navbar-expand-sm navbar-light bg-light"},[a("a",{staticClass:"navbar-brand",attrs:{href:"https://github.com/siouan",title:n.$t("siouan.organizationTooltip")}},[a("img",{attrs:{alt:"Siouan logo",src:e("40de"),width:"32",height:"32"}})]),a("a",{staticClass:"text-dark",attrs:{href:"https://github.com/siouan/frontend-gradle-plugin",title:n.$t("siouan.projectTooltip")}},[a("i",{staticClass:"fab fa-github fa-2x"})]),a("router-link",{staticClass:"nav-link text-dark",attrs:{to:"/"}},[a("i",{staticClass:"fas fa-home fa-2x"})])],1)])},p=[],f=a["a"].component("fgp-header",{data:function(){return{menuVisible:!1}},methods:{hideMenu:function(){this.toggleMenuVisible(!1)},selectLang:function(n){this.$emit("lang-change",n)},toggleMenuVisible:function(){this.menuVisible=!this.menuVisible}}}),g=f,d=(e("8bfc"),e("2877")),h=Object(d["a"])(g,u,p,!1,null,"74215371",null),b=h.exports,m=a["a"].component("fgp-app",{components:{FgpHeader:b},methods:{changeLang:function(n){this.$i18n.locale=n}}}),v=m,x=(e("141b"),Object(d["a"])(v,s,c,!1,null,null,null)),y=x.exports,_=function(){var n=this,t=n.$createElement,e=n._self._c||t;return e("section")},j=[],w={},O=Object(d["a"])(w,_,j,!1,null,null,null),C=O.exports,k=function(){var n=this,t=n.$createElement,e=n._self._c||t;return e("section",{staticClass:"text-align-center"})},$=[],T=a["a"].component("fgp-home",{}),E=T,P=Object(d["a"])(E,k,$,!1,null,null,null),F=P.exports,S=function(){var n=this,t=n.$createElement,e=n._self._c||t;return e("section")},M=[],G={},H=Object(d["a"])(G,S,M,!1,null,null,null),L=H.exports,V=function(){var n=this,t=n.$createElement,e=n._self._c||t;return e("section")},Y=[],z={},N=Object(d["a"])(z,V,Y,!1,null,null,null),A=N.exports,I=function(){var n=this,t=n.$createElement,e=n._self._c||t;return e("div",{staticClass:"mx-auto my-5 w-50 p-5 border text-center bg-warning"},[n._v(n._s(n.$t("navigation.notFound")))])},J=[],q={},B=Object(d["a"])(q,I,J,!1,null,null,null),D=B.exports,K=[{path:"/",component:F},{path:"/configuration",component:C},{path:"/installation",component:L},{path:"/tasks",component:A},{path:"*",component:D}],Q=K;e("f9e3"),e("15f5"),e("7051");a["a"].config.productionTip=!0,a["a"].use(o["a"]),a["a"].use(i["a"]);var R=new o["a"]({mode:"history",routes:Q,base:"/frontend-gradle-plugin",linkExactActiveClass:"active"}),U=new i["a"]({fallbackLocale:!1,locale:"en",missing:function(n,t){return"??? ".concat(t," ???")},messages:{en:r,fr:l}});new a["a"]({router:R,i18n:U,render:function(n){return n(y)}}).$mount("#app")},f981:function(n,t,e){}});