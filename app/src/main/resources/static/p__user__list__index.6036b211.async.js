(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[21],{TGXo:function(e,t,n){"use strict";n.r(t);n("+L6B");var a=n("2/Rp"),r=(n("P2fV"),n("NJEC")),o=(n("+BJd"),n("mr32")),c=n("q1tI"),l=n.n(c),u=n("Hx5s"),i=n("Qiat"),d=n("xvlK"),s=n("9kvl"),f=n("BqDR");function m(e,t){return Object(f["f"])(e,t).then((function(e){return{data:e.content,success:!0,total:e.totalElements}}),(function(e){return{data:[],success:!1,total:0}}))}var p=function(e){var t=Object(c["useRef"])(),n=[{title:"ID",dataIndex:"id"},{title:"\u540d\u79f0",dataIndex:"name"},{title:"\u89d2\u8272",dataIndex:"roles",render:function(e,t){return l.a.createElement(l.a.Fragment,null,t.roles.map((function(e){return l.a.createElement(o["a"],{color:"volcano"},e)})))}},{title:"\u64cd\u4f5c",dataIndex:"option",valueType:"option",render:function(e,n){return l.a.createElement(l.a.Fragment,null,l.a.createElement("a",{onClick:function(){s["d"].push("/user/".concat(n.id))}},"\u4fee\u6539"),l.a.createElement(r["a"],{title:"\u786e\u8ba4\u8981\u5220\u9664\u5417\uff1f",onConfirm:function(){Object(f["b"])(n.id).finally((function(){var e;null===(e=t.current)||void 0===e||e.reload()}))}},l.a.createElement("a",{href:""},"\u5220\u9664")))}}];return l.a.createElement(u["b"],null,l.a.createElement(i["a"],{headerTitle:"",search:!1,actionRef:t,rowKey:"key",columns:n,onChange:function(e,t,n){var a=n;console.log(a)},toolBarRender:function(e,t){t.selectedRows;return[l.a.createElement(a["a"],{type:"primary",onClick:function(){return s["d"].push("/user/create")}},l.a.createElement(d["a"],null)," \u65b0\u5efa")]},request:function(e){return m(e.current-1,null===e||void 0===e?void 0:e.pageSize)}}))};t["default"]=Object(s["a"])((function(e){var t=e.user;return{user:t}}))(p)}}]);