<%@ page contentType="text/html;charset=utf-8" pageEncoding="utf-8" import="java.io.*"%>[<%
	String path = request.getParameter("path");
	File dir = new File(path);
	File[] subDirs = dir.listFiles();
	if(subDirs != null && subDirs.length > 0){
		int j = 0;
		  for(int i = 0; i < subDirs.length; i++){
			  File subDir = subDirs[i];
			  if(!subDir.isDirectory())continue;
if(j != 0){%>,<%}
			  j++;
%>
{
    text: '<%=subDir.getName() %>', 
    leaf: false,
    fileName: '<%=subDir.getName() %>'
}
<%
		  }
	}
%>
]