"use strict";(self["webpackChunkelementui_jobhunt"]=self["webpackChunkelementui_jobhunt"]||[]).push([[745],{1745:function(t,e,a){a.r(e),a.d(e,{default:function(){return i}});var o=function(){var t=this,e=t._self._c;return e("div",[e("el-form",{attrs:{"label-position":"top","label-width":"80px",model:t.formData}},[e("el-form-item",{attrs:{label:"内容"}},[e("el-input",{attrs:{placeholder:"建议20个字以内"},model:{value:t.formData.content,callback:function(e){t.$set(t.formData,"content",e)},expression:"formData.content"}})],1),e("el-form-item",{attrs:{label:"文本颜色"}},[e("el-color-picker",{model:{value:t.formData.color,callback:function(e){t.$set(t.formData,"color",e)},expression:"formData.color"}})],1),e("el-form-item",{attrs:{size:"large"}},[e("el-button",{attrs:{type:"primary"},on:{click:t.onSubmit}},[t._v("提交修改")])],1)],1)],1)},r=[],l={data(){return{formData:{content:"",color:"#cf0bec"}}},methods:{onSubmit(){this.$request.put("/notice",this.formData).then((t=>{2e4===t.data.code?this.$message({type:"success",message:"修改成功!"}):this.$message({type:"error",message:"网络异常,修改未提交"})}))}},mounted(){this.$request.get("/notice").then((t=>{2e4===t.data.code&&null!=t.data.data&&(this.formData=t.data.data)}))}},n=l,s=a(1001),c=(0,s.Z)(n,o,r,!1,null,null,null),i=c.exports}}]);
//# sourceMappingURL=745.c29ef9cd.js.map