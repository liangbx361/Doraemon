(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([[2],{"0jlH":function(t,e,n){"use strict";n.r(e);var r=n("fWQN"),c=n("mtLc"),a=n("Nsem"),o=n("oZsa"),i=n("yKVA"),s=n("q1tI"),u=n.n(s),l=n("Hx5s"),f=n("Ty5D"),p=n("9kvl"),d=n("s4NR");function y(t){return function(){var e,n=Object(o["a"])(t);if(h()){var r=Object(o["a"])(this).constructor;e=Reflect.construct(n,arguments,r)}else e=n.apply(this,arguments);return Object(a["a"])(this,e)}}function h(){if("undefined"===typeof Reflect||!Reflect.construct)return!1;if(Reflect.construct.sham)return!1;if("function"===typeof Proxy)return!0;try{return Date.prototype.toString.call(Reflect.construct(Date,[],(function(){}))),!0}catch(t){return!1}}var v=function(t){Object(i["a"])(n,t);var e=y(n);function n(){var t;Object(r["a"])(this,n);for(var c=arguments.length,a=new Array(c),o=0;o<c;o++)a[o]=arguments[o];return t=e.call.apply(e,[this].concat(a)),t.state={isReady:!1},t}return Object(c["a"])(n,[{key:"componentDidMount",value:function(){this.setState({isReady:!0});var t=this.props.dispatch;t&&t({type:"user/queryCurrentUser"})}},{key:"render",value:function(){var t=this.state.isReady,e=this.props,n=e.children,r=e.loading,c=e.currentUser,a=c&&c.id,o=Object(d["stringify"])({redirect:window.location.href});return!a&&r||!t?u.a.createElement(l["c"],null):a||"/login"===window.location.pathname?n:u.a.createElement(f["Redirect"],{to:"/login?".concat(o)})}}]),n}(u.a.Component);e["default"]=Object(p["a"])((function(t){var e=t.user,n=t.loading;return{currentUser:e.currentUser,loading:n.models.user}}))(v)}}]);