(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[1],{"O/iA":function(e,t,a){e.exports={"ant-select-auto-complete":"ant-select-auto-complete","ant-select-clear":"ant-select-clear"}},bsDN:function(e,t,a){e.exports={menu:"antd-pro-components-global-header-index-menu",right:"antd-pro-components-global-header-index-right",action:"antd-pro-components-global-header-index-action",search:"antd-pro-components-global-header-index-search",account:"antd-pro-components-global-header-index-account",avatar:"antd-pro-components-global-header-index-avatar",dark:"antd-pro-components-global-header-index-dark",name:"antd-pro-components-global-header-index-fileName"}},bx7e:function(e,t,a){"use strict";a.r(t);var n=a("0Owb"),r=a("oBTY"),o=a("k1fw"),c=(a("J+/v"),a("MoRW")),l=(a("+L6B"),a("2/Rp")),i=a("Hx5s"),u=a("q1tI"),s=a.n(u),m=a("55Ip"),p=a("9kvl"),d=a("oN5p"),h=a("eTk0"),f=(a("+BJd"),a("5Dmo"),a("3S7+")),g=a("Lyp1"),b=(a("T2oS"),a("W9HT")),y=(a("Telt"),a("Tckk")),v=(a("lUTK"),a("BvKs")),E=a("fWQN"),O=a("mtLc"),k=a("Nsem"),j=a("oZsa"),C=a("yKVA"),N=a("cJ7L"),x=a("eFNv"),S=a("aIfO"),w=a("uZXw"),R=a("bsDN"),T=a.n(R);function D(e){return function(){var t,a=Object(j["a"])(e);if(A()){var n=Object(j["a"])(this).constructor;t=Reflect.construct(a,arguments,n)}else t=a.apply(this,arguments);return Object(k["a"])(this,t)}}function A(){if("undefined"===typeof Reflect||!Reflect.construct)return!1;if(Reflect.construct.sham)return!1;if("function"===typeof Proxy)return!0;try{return Date.prototype.toString.call(Reflect.construct(Date,[],(function(){}))),!0}catch(e){return!1}}var L=function(e){Object(C["a"])(a,e);var t=D(a);function a(){var e;Object(E["a"])(this,a);for(var n=arguments.length,r=new Array(n),o=0;o<n;o++)r[o]=arguments[o];return e=t.call.apply(t,[this].concat(r)),e.onMenuClick=function(t){var a=t.key;if("logout"!==a)p["d"].push("/account/".concat(a));else{var n=e.props.dispatch;n&&n({type:"login/logout"})}},e}return Object(O["a"])(a,[{key:"render",value:function(){var e=this.props,t=e.currentUser,a=void 0===t?{avatar:"",name:""}:t,n=e.menu,r=s.a.createElement(v["a"],{className:T.a.menu,selectedKeys:[],onClick:this.onMenuClick},n&&s.a.createElement(v["a"].Item,{key:"center"},s.a.createElement(N["a"],null),"\u4e2a\u4eba\u4e2d\u5fc3"),n&&s.a.createElement(v["a"].Item,{key:"settings"},s.a.createElement(x["a"],null),"\u4e2a\u4eba\u8bbe\u7f6e"),n&&s.a.createElement(v["a"].Divider,null),s.a.createElement(v["a"].Item,{key:"logout"},s.a.createElement(S["a"],null),"\u9000\u51fa\u767b\u5f55"));return a&&a.name?s.a.createElement(w["a"],{overlay:r},s.a.createElement("span",{className:"".concat(T.a.action," ").concat(T.a.account)},s.a.createElement(y["a"],{size:"small",className:T.a.avatar,src:a.avatar,alt:"avatar"}),s.a.createElement("span",{className:T.a.name},a.name))):s.a.createElement("span",{className:"".concat(T.a.action," ").concat(T.a.account)},s.a.createElement(b["a"],{size:"small",style:{marginLeft:8,marginRight:8}}))}}]),a}(s.a.Component),I=Object(p["a"])((function(e){var t=e.user;return{currentUser:t.currentUser}}))(L),P=(a("cIOH"),a("O/iA"),a("OaEy"),a("Zm9Q")),V=a("TSYQ"),M=a.n(V),B=a("BGR+"),U=a("2fM7"),z=a("H84U"),H=a("6CfX");function J(){return J=Object.assign||function(e){for(var t=1;t<arguments.length;t++){var a=arguments[t];for(var n in a)Object.prototype.hasOwnProperty.call(a,n)&&(e[n]=a[n])}return e},J.apply(this,arguments)}function _(e){return _="function"===typeof Symbol&&"symbol"===typeof Symbol.iterator?function(e){return typeof e}:function(e){return e&&"function"===typeof Symbol&&e.constructor===Symbol&&e!==Symbol.prototype?"symbol":typeof e},_(e)}var K=U["a"].Option,Q=U["a"];function G(e){return e&&e.type&&(e.type.isSelectOption||e.type.isSelectOptGroup)}var W=function(e,t){var a,n=e.prefixCls,r=e.className,o=e.children,c=e.dataSource,l=Object(P["a"])(o),i=u["useRef"]();u["useImperativeHandle"](t,(function(){return i.current})),1===l.length&&u["isValidElement"](l[0])&&!G(l[0])&&(a=l[0]);var s,m=function(){return a};return s=l.length&&G(l[0])?o:c?c.map((function(e){if(u["isValidElement"](e))return e;switch(_(e)){case"string":return u["createElement"](K,{key:e,value:e},e);case"object":var t=e.value;return u["createElement"](K,{key:t,value:t},e.text);default:throw new Error("AutoComplete[dataSource] only supports type `string[] | Object[]`.")}})):[],u["useEffect"]((function(){Object(H["a"])(!("dataSource"in e),"AutoComplete","`dataSource` is deprecated, please use `options` instead."),Object(H["a"])(!a||!("size"in e),"AutoComplete","You need to control style self instead of setting `size` when using customize input.")}),[]),u["createElement"](z["a"],null,(function(t){var a=t.getPrefixCls,o=a("select",n);return u["createElement"](Q,J({ref:i},Object(B["a"])(e,["dataSource"]),{prefixCls:o,className:M()(r,"".concat(o,"-auto-complete")),mode:U["a"].SECRET_COMBOBOX_MODE_DO_NOT_USE,getInputElement:m}),s)}))},X=u["forwardRef"](W);X.Option=K;var Y=X,Z=(a("5NDa"),a("5rEg")),q=a("jrin"),F=a("tJVT"),$=a("PpiC"),ee=a("l+S1"),te=a("yUgw"),ae=a("j5Qg"),ne=a.n(ae),re=function(e){var t=e.className,a=e.defaultValue,n=e.onVisibleChange,r=e.placeholder,o=(e.open,e.defaultOpen),c=Object($["a"])(e,["className","defaultValue","onVisibleChange","placeholder","open","defaultOpen"]),l=Object(u["useRef"])(null),i=Object(te["a"])(a,{value:e.value,onChange:e.onChange}),m=Object(F["a"])(i,2),p=m[0],d=m[1],h=Object(te["a"])(o||!1,{value:e.open,onChange:n}),f=Object(F["a"])(h,2),g=f[0],b=f[1],y=M()(ne.a.input,Object(q["a"])({},ne.a.show,g));return s.a.createElement("div",{className:M()(t,ne.a.headerSearch),onClick:function(){b(!0),g&&l.current&&l.current.focus()},onTransitionEnd:function(e){var t=e.propertyName;"width"!==t||g||n&&n(g)}},s.a.createElement(ee["a"],{key:"Icon",style:{cursor:"pointer"}}),s.a.createElement(Y,{key:"AutoComplete",className:y,value:p,style:{height:28,marginTop:-6},options:c.options,onChange:d},s.a.createElement(Z["a"],{ref:l,defaultValue:a,"aria-label":r,placeholder:r,onKeyDown:function(e){"Enter"===e.key&&c.onSearch&&c.onSearch(p)},onBlur:function(){b(!1)}})))},oe=re,ce=a("trCS"),le=function(e){var t=e.theme,a=e.layout,n=T.a.right;return"dark"===t&&"topmenu"===a&&(n="".concat(T.a.right,"  ").concat(T.a.dark)),s.a.createElement("div",{className:n},s.a.createElement(oe,{className:"".concat(T.a.action," ").concat(T.a.search),placeholder:"\u7ad9\u5185\u641c\u7d22",defaultValue:"umi ui",options:[{label:s.a.createElement("a",{href:"https://umijs.org/zh/guide/umi-ui.html"},"umi ui"),value:"umi ui"},{label:s.a.createElement("a",{href:"next.ant.design"},"Ant Design"),value:"Ant Design"},{label:s.a.createElement("a",{href:"https://protable.ant.design/"},"Pro Table"),value:"Pro Table"},{label:s.a.createElement("a",{href:"https://prolayout.ant.design/"},"Pro Layout"),value:"Pro Layout"}]}),s.a.createElement(f["a"],{title:"\u4f7f\u7528\u6587\u6863"},s.a.createElement("a",{target:"_blank",href:"https://pro.ant.design/docs/getting-started",rel:"noopener noreferrer",className:T.a.action},s.a.createElement(g["a"],null))),s.a.createElement(I,null),!1,s.a.createElement(ce["a"],{className:T.a.action}))},ie=Object(p["a"])((function(e){var t=e.settings;return{theme:t.navTheme,layout:t.layout}}))(le),ue=a("c+yx"),se=a("mxmt"),me=a.n(se),pe=s.a.createElement(c["a"],{status:403,title:"403",subTitle:"Sorry, you are not authorized to access this page.",extra:s.a.createElement(l["a"],{type:"primary"},s.a.createElement(m["Link"],{to:"/login"},"Go Login"))}),de=function e(t){return t.map((function(t){var a=Object(o["a"])({},t,{children:t.children?e(t.children):[]});return h["a"].check(t.authority,a,null)}))},he=s.a.createElement(i["a"],{copyright:"2020 \u54c6\u5566A\u68a6\u6280\u672f\u51fa\u54c1",links:[{key:"Ant Design Pro",title:"Ant Design Pro",href:"https://pro.ant.design",blankTarget:!0},{key:"github",title:s.a.createElement(d["a"],null),href:"https://github.com/ant-design/ant-design-pro",blankTarget:!0},{key:"Ant Design",title:"Ant Design",href:"https://ant.design",blankTarget:!0}]}),fe=function(e){var t=e.dispatch,a=e.children,o=e.settings,c=e.location,l=void 0===c?{pathname:"/"}:c;Object(u["useEffect"])((function(){t&&t({type:"user/fetchCurrent"})}),[]);var d=function(e){t&&t({type:"global/changeLayoutCollapsed",payload:e})},f=Object(ue["a"])(e.route.routes,l.pathname||"/")||{authority:void 0},g=Object(p["g"])(),b=g.formatMessage;return s.a.createElement(i["d"],Object(n["a"])({logo:me.a,formatMessage:b,menuHeaderRender:function(e,t){return s.a.createElement(m["Link"],{to:"/"},e,t)},onCollapse:d,menuItemRender:function(e,t){return e.isUrl||e.children||!e.path?t:s.a.createElement(m["Link"],{to:e.path},t)},breadcrumbRender:function(){var e=arguments.length>0&&void 0!==arguments[0]?arguments[0]:[];return[{path:"/",breadcrumbName:b({id:"menu.home"})}].concat(Object(r["a"])(e))},itemRender:function(e,t,a,n){var r=0===a.indexOf(e);return r?s.a.createElement(m["Link"],{to:n.join("/")},e.breadcrumbName):s.a.createElement("span",null,e.breadcrumbName)},footerRender:function(){return he},menuDataRender:de,rightContentRender:function(){return s.a.createElement(ie,null)}},e,o),s.a.createElement(h["a"],{authority:f.authority,noMatch:pe},a))};t["default"]=Object(p["a"])((function(e){var t=e.global,a=e.settings;return{collapsed:t.collapsed,settings:a}}))(fe)},j5Qg:function(e,t,a){e.exports={headerSearch:"antd-pro-components-header-search-index-headerSearch",input:"antd-pro-components-header-search-index-input",show:"antd-pro-components-header-search-index-show"}}}]);