(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[12],{s55s:function(e,t,a){"use strict";a.r(t);var n=a("oBTY"),r=a("fWQN"),c=a("mtLc"),o=a("Nsem"),s=a("oZsa"),l=a("yKVA"),u=a("9kvl"),i=a("q1tI"),p=a.n(i),d=a("If6m"),h=a("yPet"),f=a("67Hk"),y=a("v/WP");function g(e){return function(){var t,a=Object(s["a"])(e);if(m()){var n=Object(s["a"])(this).constructor;t=Reflect.construct(a,arguments,n)}else t=a.apply(this,arguments);return Object(o["a"])(this,t)}}function m(){if("undefined"===typeof Reflect||!Reflect.construct)return!1;if(Reflect.construct.sham)return!1;if("function"===typeof Proxy)return!0;try{return Date.prototype.toString.call(Reflect.construct(Date,[],(function(){}))),!0}catch(e){return!1}}var v=function(e){Object(l["a"])(a,e);var t=g(a);function a(e){var c;return Object(r["a"])(this,a),c=t.call(this,e),c.query=function(e){var t=c.props.match.params.gameId;Object(f["k"])(t,e).then((function(e){var t=[];e.pluginIds.map((function(e){return Number(e)})).forEach((function(e){t.push(e)})),c.setState({channel:e,targetKeys:t})}))},c.create=function(e,t){var a=c.props.match.params.gameId,n=[];c.state.targetKeys.forEach((function(e){c.props.pack.plugins.forEach((function(t){e==t.id.toString()&&n.push(t.id)}))}));var r={gameId:a,name:e.name,code:e.code,packageName:e.packageName,type:e.type,pluginIds:n};Object(f["b"])(a,r).then((function(e){t(e)}))},c.update=function(e,t,a){var n=c.props.match.params.gameId;Object(f["r"])(n,e,t).then((function(e){a(e)}))},c.handleChange=function(e,t,a){c.setState({targetKeys:e}),console.log("targetKeys: ",e),console.log("direction: ",t),console.log("moveKeys: ",a)},c.handleSelectChange=function(e,t){c.setState({selectedKeys:[].concat(Object(n["a"])(e),Object(n["a"])(t))}),console.log("sourceSelectedKeys: ",e),console.log("targetSelectedKeys: ",t)},c.handleScroll=function(e,t){console.log("direction:",e),console.log("target:",t.target)},c.state={channel:null,targetKeys:[],selectedKeys:[]},c}return Object(c["a"])(a,[{key:"componentDidMount",value:function(){var e=this.props.dispatch;e({type:"pack/queryPlugins"})}},{key:"render",value:function(){var e,t=this.state.channel,a=this.props.pack.plugins;e=null==a?[]:a.map((function(e){return{key:e.id,name:e.name}}));var n=[{type:d["a"].TEXT,name:"name",value:null===t||void 0===t?void 0:t.name,label:"\u540d\u79f0",rules:[{required:!0}]},{type:d["a"].SELECT,name:"code",value:null===t||void 0===t?void 0:t.code,label:"\u6e20\u9053",rules:[{required:!0}],select:{options:y["a"]}},{type:d["a"].TEXT,name:"packageName",value:null===t||void 0===t?void 0:t.packageName,label:"\u5305\u540d",rules:[{required:!0}]},{type:d["a"].TEXT,name:"type",value:null===t||void 0===t?void 0:t.type,label:"\u7c7b\u578b",rules:[{required:!0}]},{type:d["a"].TRANSFER,name:"pluginIds",label:"\u63d2\u4ef6\u96c6\u6210",value:this.state.targetKeys,rules:[{required:!0}],transfer:{props:{dataSource:e,titles:["\u672a\u9009","\u5df2\u9009"],render:function(e){return e.name},targetKeys:this.state.targetKeys,selectedKeys:this.state.selectedKeys,onChange:this.handleChange,onScroll:this.handleScroll,onSelectChange:this.handleSelectChange,oneWay:!0}}}];return p.a.createElement(h["a"],{id:this.props.match.params.channelId,query:this.query,create:this.create,update:this.update,dataSource:n})}}]),a}(i["Component"]);t["default"]=Object(u["a"])((function(e){var t=e.pack;return{pack:t}}))(v)}}]);