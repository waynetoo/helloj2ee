<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" import="java.io.*"%>
<%
String rootPath = request.getParameter("rootPath");
//如果没有传入rootPath的参数，那么取项目根目录
if(rootPath == null || "".equals(rootPath))rootPath = application.getRealPath("/");
File rootFile = new File(rootPath);
rootFile = rootFile.getAbsoluteFile();
String rootPathName = rootFile.getName();
String rootFilePath = rootFile.getPath().replace('\\','/');
if(rootFilePath.endsWith("/"))rootFilePath=rootFilePath.substring(0,rootFilePath.length()-1);
%>
<html>
<head>
<title> 文件夹导航 </title>
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache,must-revalidate"> 
<meta http-equiv="expires" content="0"> 
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath() %>/ext/resources/css/ext-all.css" />
  <link rel="stylesheet" type="text/css" href="tree.css" />
  <script src="<%=request.getContextPath() %>/ext/adapter/ext/ext-base.js"></script>
  <script src="<%=request.getContextPath() %>/ext/ext-all-debug.js"></script>
</head>
<body>

<div id="treeDiv" class="tree"></div>
<script>
<!--
var rootPath = "<%=rootFilePath%>";
var rootPathName = "<%=rootPathName%>";
var root = null;

Ext.treeLoading = function () {
   Ext.BLANK_IMAGE_URL = '<%=request.getContextPath() %>/ext/resources/images/default/s.gif';
   Tree = Ext.tree;
   tree = new Tree.TreePanel( {
      el : 'treeDiv', 
      useArrows : false, 
      autoHeight : true, 
      autoScroll : false, 
      animate : true, 
      enableDD : false, 
      containerScroll : false, 
      loader : new Ext.tree.TreeLoader(), 
      rootVisible : true, 
      border : false 
     }
   );
   root = new Tree.AsyncTreeNode( {
      text : '文件目录树', 
      draggable : false, 
      id : 'treeRoot', 
      iconCls : 'company', 
      fileName : "" 
    }
   );
   tree.setRootNode(root);
   //在载入子节点前，指定生成子结点内容的路径
   tree.on('beforeload', function(node) {
      var path = rootPath + "/" + getParentPath(node) + "/" + node.attributes.fileName; 
      path = path.replace("//","/");
      this.loader.dataUrl = "json_dir.jsp?path=" + path+"&reqId="+(new Date()).getTime(); 
     }
   );
   //当点击右键时
   tree.on('contextmenu', contextmenu, tree);
   function contextmenu(node, e) {
       treeMenu = new Ext.menu.Menu( {
           id : 'treeMenu',
           items : [new Ext.menu.Item( {
                   text : '刷新',
                   iconCls : 'refreshNode',
                   handler : refreshNode,
                   treeNode:node
               }),
               new Ext.menu.Item( {
                   text : '新窗口中打开',
                   iconCls : 'newWindow',
                   handler : openLsInNewWindow,
                   treeNode:node
               })
               ]
       });
       var coords = e.getXY();
       treeMenu.showAt(coords);
   }
   function refreshNode(item){
  	 item.treeNode.reload();
   }
   function openLsInNewWindow(item){
	   var path = rootPath + "/" + getParentPath(item.treeNode) + "/" + item.treeNode.attributes.fileName; 
	   path = path.replace("//","/");
		window.open("ls.jsp?path="+path);
   }
   //双击树时
   tree.on('dblclick', function(node) {}
   );
   //单击时，延迟200毫秒执行
   tree.on('click', 
		   function(node) {
		   	var path = rootPath + "/" + getParentPath(node) + "/" + node.attributes.fileName; 
		   	path = path.replace("//","/");
		   	parent.frames["frame_content"].location=("ls.jsp?path="+path);
		   }, 
		   this, 
		   {delay : 200}
   );
   //显示树
   tree.render();
   //展开根节点
   root.expand();
}

//当页面载入后
Ext.onReady(Ext.treeLoading); 



//获取上级路径
function getParentPath(node) {
	function recuise(node){
	   if(node.parentNode != null )return recuise(node.parentNode) + "/" + node.parentNode.attributes.fileName;
	   return "";
	}
	var rtn = recuise(node);
	if(rtn.charAt(0)=="/")rtn = rtn.substring(1);
	if(rtn.charAt(0)=="/")rtn = rtn.substring(1);
	if(rtn.charAt(rtn.length-1)=="/")rtn = rtn.substring(0,rtn.length-1);
	return rtn;
}

//获取所有的已载入的节点。
function getAllNodes(root) {
   var allNodes = [];
   function iterator(node) {
      allNodes.push(node);
      node.eachChild(function(child) {
         iterator(child); }
      );
      }
   iterator(root);
   return allNodes;
}
//-->
</script>
</body>
</html>
