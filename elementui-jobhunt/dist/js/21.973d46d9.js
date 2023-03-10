"use strict";(self["webpackChunkelementui_jobhunt"]=self["webpackChunkelementui_jobhunt"]||[]).push([[21],{2021:function(t,e,s){s.r(e),s.d(e,{default:function(){return d}});var i=function(){var t=this,e=t._self._c;return e("div",{staticClass:"general"},[e("div",{staticClass:"first"},[e("div",{attrs:{id:"statistic"}},[e("div",{staticClass:"usertotal stabox"},[e("h3",[t._v("用户总数")]),e("span",{staticClass:"num"},[t._v(t._s(t.userCount))])]),e("div",{staticClass:"jobtotal stabox"},[e("h3",[t._v("帖子总数")]),e("span",{staticClass:"num"},[t._v(t._s(t.jobCount))])]),e("div",{staticClass:"alive stabox"},[e("h3",[t._v("活跃数")]),e("span",{staticClass:"num"},[t._v(t._s(t.aliveCount))])])]),e("div",{attrs:{id:"jobsort"}})]),t._m(0)])},a=[function(){var t=this,e=t._self._c;return e("div",{staticClass:"second"},[e("div",{attrs:{id:"usertrend"}}),e("div",{attrs:{id:"jobpost"}})])}],o={data(){return{jobCount:0,userCount:0,aliveCount:0,userTrendOption:{color:"#409eff",title:{text:"近七天注册用户数",left:"center"},xAxis:{type:"category",data:[]},yAxis:{type:"value"},series:[{data:[],type:"line",smooth:!0}]},jobSortOption:{title:{text:"文章类型占比",subtext:"用户已发布",left:"center"},tooltip:{trigger:"item"},legend:{orient:"vertical",left:"left"},series:[{name:"发布类型占比",type:"pie",radius:"50%",data:[{value:1048,name:"Search Engine"},{value:735,name:"Direct"},{value:580,name:"Email"},{value:484,name:"Union Ads"},{value:300,name:"Video Ads"}],emphasis:{itemStyle:{shadowBlur:10,shadowOffsetX:0,shadowColor:"rgba(0, 0, 0, 0.5)"}}}]},jobPostOption:{color:"#409eff",title:{text:"近七天帖子发布数",left:"center"},xAxis:{type:"category",data:[]},yAxis:{type:"value"},series:[{data:[],type:"bar"}]}}},mounted(){this.$request.get("/statistic/usertotal").then((t=>{this.userCount=t.data.data})),this.$request.get("/statistic/jobtotal").then((t=>{this.jobCount=t.data.data})),this.$request.get("/statistic/useralive").then((t=>{this.aliveCount=t.data.data})),this.$request.get("/statistic/usertrend").then((t=>{const e=t.data.data;e.forEach((t=>{this.userTrendOption.xAxis.data.push(Object.keys(t)[0].substring(5)),this.userTrendOption.series[0].data.push(t[Object.keys(t)])})),this.userTrendOption.xAxis.data.reverse(),this.userTrendOption.series[0].data.reverse();const s=document.getElementById("usertrend"),i=this.$echarts.init(s);i.setOption(this.userTrendOption)})),this.$request.get("/statistic/jobsort").then((t=>{const e=t.data.data;this.jobSortOption.series[0].data=e;const s=document.getElementById("jobsort"),i=this.$echarts.init(s);i.setOption(this.jobSortOption)})),this.$request.get("/statistic/jobpost").then((t=>{const e=t.data.data;e.forEach((t=>{this.jobPostOption.xAxis.data.push(Object.keys(t)[0].substring(5)),this.jobPostOption.series[0].data.push(t[Object.keys(t)])})),this.jobPostOption.xAxis.data.reverse(),this.jobPostOption.series[0].data.reverse();const s=document.getElementById("jobpost"),i=this.$echarts.init(s);i.setOption(this.jobPostOption)}))}},n=o,r=s(1001),u=(0,r.Z)(n,i,a,!1,null,null,null),d=u.exports}}]);
//# sourceMappingURL=21.973d46d9.js.map