(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[14],{wdlV:function(t,e,n){"use strict";n.r(e);var c=n("fWQN"),a=n("mtLc"),r=n("Nsem"),u=n("oZsa"),o=n("yKVA"),i=n("9kvl"),s=n("q1tI"),f=n.n(s),l=n("If6m"),d=n("yPet"),p=n("67Hk");function h(t){return function(){var e,n=Object(u["a"])(t);if(m()){var c=Object(u["a"])(this).constructor;e=Reflect.construct(n,arguments,c)}else e=n.apply(this,arguments);return Object(r["a"])(this,e)}}function m(){if("undefined"===typeof Reflect||!Reflect.construct)return!1;if(Reflect.construct.sham)return!1;if("function"===typeof Proxy)return!0;try{return Date.prototype.toString.call(Reflect.construct(Date,[],(function(){}))),!0}catch(t){return!1}}var v=function(t){Object(o["a"])(n,t);var e=h(n);function n(t){var a;return Object(c["a"])(this,n),a=e.call(this,t),a.query=function(t){Object(p["m"])(t).then((function(t){a.setState({game:t})}))},a.create=function(t,e){Object(p["c"])(t).then((function(t){e(t)}))},a.update=function(t,e,n){Object(p["s"])(t,e.name,e.code).then((function(t){n(t)}))},a.state={game:null},a}return Object(a["a"])(n,[{key:"render",value:function(){var t=this.state.game,e=[{type:l["a"].TEXT,name:"name",value:null===t||void 0===t?void 0:t.name,label:"\u6e38\u620f\u540d\u79f0",rules:[{required:!0}]},{type:l["a"].TEXT,name:"code",value:null===t||void 0===t?void 0:t.code,label:"\u6e38\u620f\u7f16\u7801",rules:[{required:!0}]}];return f.a.createElement(d["a"],{id:this.props.match.params.id,query:this.query,create:this.create,update:this.update,dataSource:e})}}]),n}(s["Component"]);e["default"]=Object(i["a"])((function(t){var e=t.pack;return{pack:e}}))(v)}}]);