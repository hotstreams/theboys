function C(){}function P(n,t){for(const e in t)n[e]=t[e];return n}function B(n){return n()}function Q(){return Object.create(null)}function I(n){n.forEach(B)}function R(n){return typeof n=="function"}function V(n,t){return n!=n?t==t:n!==t||n&&typeof n=="object"||typeof n=="function"}let h;function X(n,t){return n===t?!0:(h||(h=document.createElement("a")),h.href=t,n===h.href)}function Y(n){return Object.keys(n).length===0}function M(n,...t){if(n==null){for(const i of t)i(void 0);return C}const e=n.subscribe(...t);return e.unsubscribe?()=>e.unsubscribe():e}function Z(n,t,e){n.$$.on_destroy.push(M(t,e))}function $(n,t,e,i){if(n){const r=N(n,t,e,i);return n[0](r)}}function N(n,t,e,i){return n[1]&&i?P(e.ctx.slice(),n[1](i(t))):e.ctx}function nn(n,t,e,i){if(n[2]&&i){const r=n[2](i(e));if(t.dirty===void 0)return r;if(typeof r=="object"){const o=[],c=Math.max(t.dirty.length,r.length);for(let u=0;u<c;u+=1)o[u]=t.dirty[u]|r[u];return o}return t.dirty|r}return t.dirty}function tn(n,t,e,i,r,o){if(r){const c=N(t,e,i,o);n.p(c,r)}}function en(n){if(n.ctx.length>32){const t=[],e=n.ctx.length/32;for(let i=0;i<e;i++)t[i]=-1;return t}return-1}let p=!1;function rn(){p=!0}function cn(){p=!1}function O(n,t,e,i){for(;n<t;){const r=n+(t-n>>1);e(r)<=i?n=r+1:t=r}return n}function T(n){if(n.hydrate_init)return;n.hydrate_init=!0;let t=n.childNodes;if(n.nodeName==="HEAD"){const l=[];for(let s=0;s<t.length;s++){const a=t[s];a.claim_order!==void 0&&l.push(a)}t=l}const e=new Int32Array(t.length+1),i=new Int32Array(t.length);e[0]=-1;let r=0;for(let l=0;l<t.length;l++){const s=t[l].claim_order,a=(r>0&&t[e[r]].claim_order<=s?r+1:O(1,r,q=>t[e[q]].claim_order,s))-1;i[l]=e[a]+1;const E=a+1;e[E]=l,r=Math.max(E,r)}const o=[],c=[];let u=t.length-1;for(let l=e[r]+1;l!=0;l=i[l-1]){for(o.push(t[l-1]);u>=l;u--)c.push(t[u]);u--}for(;u>=0;u--)c.push(t[u]);o.reverse(),c.sort((l,s)=>l.claim_order-s.claim_order);for(let l=0,s=0;l<c.length;l++){for(;s<o.length&&c[l].claim_order>=o[s].claim_order;)s++;const a=s<o.length?o[s]:null;n.insertBefore(c[l],a)}}function D(n,t){if(p){for(T(n),(n.actual_end_child===void 0||n.actual_end_child!==null&&n.actual_end_child.parentNode!==n)&&(n.actual_end_child=n.firstChild);n.actual_end_child!==null&&n.actual_end_child.claim_order===void 0;)n.actual_end_child=n.actual_end_child.nextSibling;t!==n.actual_end_child?(t.claim_order!==void 0||t.parentNode!==n)&&n.insertBefore(t,n.actual_end_child):n.actual_end_child=t.nextSibling}else(t.parentNode!==n||t.nextSibling!==null)&&n.appendChild(t)}function ln(n,t,e){p&&!e?D(n,t):(t.parentNode!==n||t.nextSibling!=e)&&n.insertBefore(t,e||null)}function un(n){n.parentNode&&n.parentNode.removeChild(n)}function on(n,t){for(let e=0;e<n.length;e+=1)n[e]&&n[e].d(t)}function H(n){return document.createElement(n)}function L(n){return document.createElementNS("http://www.w3.org/2000/svg",n)}function v(n){return document.createTextNode(n)}function sn(){return v(" ")}function an(){return v("")}function fn(n,t,e,i){return n.addEventListener(t,e,i),()=>n.removeEventListener(t,e,i)}function _n(n,t,e){e==null?n.removeAttribute(t):n.getAttribute(t)!==e&&n.setAttribute(t,e)}function dn(n){return n.dataset.svelteH}function hn(n){return n===""?null:+n}function mn(n){return Array.from(n.childNodes)}function z(n){n.claim_info===void 0&&(n.claim_info={last_index:0,total_claimed:0})}function A(n,t,e,i,r=!1){z(n);const o=(()=>{for(let c=n.claim_info.last_index;c<n.length;c++){const u=n[c];if(t(u)){const l=e(u);return l===void 0?n.splice(c,1):n[c]=l,r||(n.claim_info.last_index=c),u}}for(let c=n.claim_info.last_index-1;c>=0;c--){const u=n[c];if(t(u)){const l=e(u);return l===void 0?n.splice(c,1):n[c]=l,r?l===void 0&&n.claim_info.last_index--:n.claim_info.last_index=c,u}}return i()})();return o.claim_order=n.claim_info.total_claimed,n.claim_info.total_claimed+=1,o}function j(n,t,e,i){return A(n,r=>r.nodeName===t,r=>{const o=[];for(let c=0;c<r.attributes.length;c++){const u=r.attributes[c];e[u.name]||o.push(u.name)}o.forEach(c=>r.removeAttribute(c))},()=>i(t))}function pn(n,t,e){return j(n,t,e,H)}function bn(n,t,e){return j(n,t,e,L)}function F(n,t){return A(n,e=>e.nodeType===3,e=>{const i=""+t;if(e.data.startsWith(i)){if(e.data.length!==i.length)return e.splitText(i.length)}else e.data=i},()=>v(t),!0)}function yn(n){return F(n," ")}function gn(n,t){t=""+t,n.data!==t&&(n.data=t)}function xn(n,t){n.value=t??""}function vn(n,t,e,i){e==null?n.style.removeProperty(t):n.style.setProperty(t,e,i?"important":"")}function wn(n,t,e){for(let i=0;i<n.options.length;i+=1){const r=n.options[i];if(r.__value===t){r.selected=!0;return}}(!e||t!==void 0)&&(n.selectedIndex=-1)}function En(n){const t=n.querySelector(":checked");return t&&t.__value}function U(n,t,{bubbles:e=!1,cancelable:i=!1}={}){return new CustomEvent(n,{detail:t,bubbles:e,cancelable:i})}function kn(n,t){return new n(t)}let m;function b(n){m=n}function w(){if(!m)throw new Error("Function called outside component initialization");return m}function Nn(n){w().$$.on_mount.push(n)}function An(n){w().$$.after_update.push(n)}function jn(){const n=w();return(t,e,{cancelable:i=!1}={})=>{const r=n.$$.callbacks[t];if(r){const o=U(t,e,{cancelable:i});return r.slice().forEach(c=>{c.call(n,o)}),!o.defaultPrevented}return!0}}const d=[],k=[];let _=[];const g=[],S=Promise.resolve();let x=!1;function W(){x||(x=!0,S.then(J))}function Sn(){return W(),S}function G(n){_.push(n)}function qn(n){g.push(n)}const y=new Set;let f=0;function J(){if(f!==0)return;const n=m;do{try{for(;f<d.length;){const t=d[f];f++,b(t),K(t.$$)}}catch(t){throw d.length=0,f=0,t}for(b(null),d.length=0,f=0;k.length;)k.pop()();for(let t=0;t<_.length;t+=1){const e=_[t];y.has(e)||(y.add(e),e())}_.length=0}while(d.length);for(;g.length;)g.pop()();x=!1,y.clear(),b(n)}function K(n){if(n.fragment!==null){n.update(),I(n.before_update);const t=n.dirty;n.dirty=[-1],n.fragment&&n.fragment.p(n.ctx,t),n.after_update.forEach(G)}}function Cn(n){const t=[],e=[];_.forEach(i=>n.indexOf(i)===-1?t.push(i):e.push(i)),e.forEach(i=>i()),_=t}export{En as A,xn as B,G as C,wn as D,I as E,X as F,Z as G,jn as H,hn as I,k as J,qn as K,An as L,kn as M,Sn as N,Q as O,J as P,R as Q,Y as R,Cn as S,m as T,b as U,B as V,d as W,W as X,rn as Y,cn as Z,nn as a,sn as b,$ as c,un as d,an as e,H as f,en as g,yn as h,ln as i,pn as j,mn as k,dn as l,_n as m,vn as n,Nn as o,D as p,fn as q,C as r,V as s,v as t,tn as u,F as v,gn as w,on as x,L as y,bn as z};
