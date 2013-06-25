<%@ page pageEncoding="gbk" contentType="text/html;charset=gbk" import="java.io.*,java.util.*,java.net.URLEncoder"%>
<%!
/**
 * 本来用来做文件排序。
 */
class FileComparator implements Comparator<File>{
	String orderBy = "fileName";
	String direct = "asc";
	public FileComparator(String orderBy,String direct){
		this.orderBy = orderBy;
		this.direct = direct;
	}
	public FileComparator(String orderBy){
		this.orderBy = orderBy;
	}
	public FileComparator(){
	}
	public int compare(File f1, File f2){
		int r = compare2( f1,  f2);
		if("asc".equals(direct)){
			return r;
		}
		return -r;
	}
	public int compare2(File f1, File f2) {
		if("fileName".equals(orderBy))return f1.getName().compareTo(f2.getName());
		if("fileSize".equals(orderBy))return (int)(f1.length()-f2.length());
		if("lastModified".equals(orderBy))return (int)(f1.lastModified()-f2.lastModified());
		if("fileType".equals(orderBy)){
			String fix = "";
			if(f1.getName().lastIndexOf(".")>-1)
				fix = f1.getName().substring(f1.getName().lastIndexOf("."));
			String fix2 = "";
			if(f2.getName().lastIndexOf(".")>-1)
				fix2 = f2.getName().substring(f2.getName().lastIndexOf("."));
			return fix.compareTo(fix2);
		}
		return 0;
	}
}

//格式化文件大小的表示
public String formatFileSize(long size,long k, long m){
	if(size < k)return size + "B";
	if(size < m)return (size * 100 / 1024 /100.0) + "K";
	return (size * 100 / 1024 / 1024 /100.0) + "M";
}
public String formatFileSize(long size){
	return formatFileSize(size,1024,500*1024);
}
%>
<%
//获取根目录，获取文件夹和文件
//并进行排序
String path = request.getParameter("path");
String orderBy = request.getParameter("orderBy");
if(orderBy == null || "".equals(orderBy))orderBy = "fileName";
String direct = request.getParameter("direct");
if(direct == null || "".equals(direct))direct = "asc";

File currentFile = new File(path);
File[] dirs = currentFile.listFiles(new FileFilter(){
	public boolean accept(File f) {
		return f.isDirectory();
	}});
File[] files = currentFile.listFiles(new FileFilter(){
	public boolean accept(File f) {
		return f.isFile();
	}
});
//对文件进行排序
Arrays.sort(files, new FileComparator(orderBy,direct));
//对文件夹进行排序
if(!"fileSize".equals(orderBy))
	Arrays.sort(dirs, new FileComparator(orderBy,direct));

if("asc".equals(direct)){
	direct = "desc";
}else{
	direct = "asc";
}
String encoderPath = URLEncoder.encode(path);
request.setAttribute("encoderPath",encoderPath);
if(path.lastIndexOf('/') > -1){
	String parentPath = path.substring(0,path.lastIndexOf('/'));
	request.setAttribute("parentEncoderPath",URLEncoder.encode(parentPath));
}
request.setAttribute("direct",direct);

java.text.SimpleDateFormat dateFmt = new java.text.SimpleDateFormat("yy-MM-dd HH:mm:dd");
%>
<html>
<head>
<title> 文件列表 </title>
<meta http-equiv="pragma" content="no-cache"> 
<meta http-equiv="cache-control" content="no-cache,must-revalidate"> 
<meta http-equiv="expires" content="0"> 
</head>
<body>
<span style="white-space: nowrap">当前目录:<%=path %></span>
<table  border="1" width="100%">
	<tr bgcolor=#eeeeee>
		<th></th>
		<th nowrap="nowrap"><a href="ls.jsp?path=${encoderPath}&orderBy=fileName&direct=${direct }">文件名</a></th>
		<th nowrap="nowrap"><a href="ls.jsp?path=${encoderPath}&orderBy=fileSize&direct=${direct }">大小</a></th>
		<th nowrap="nowrap"><a href="ls.jsp?path=${encoderPath}&orderBy=fileType&direct=${direct }">类型</a></th>
		<th nowrap="nowrap"><a href="ls.jsp?path=${encoderPath}&orderBy=lastModified&direct=${direct }">修改时间</a></th>
		
	</tr>
	<tr>
	<td>0</td>
	<td>
    <a href="ls.jsp?path=${parentEncoderPath}">..</a>
    </td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
	<td>&nbsp;</td>
	</tr>
	<%
	int i = 0;
	for(; i < dirs.length; i++){
		File f = dirs[i];
		String fileName = f.getName();
		Date d = new Date(f.lastModified());
		String dateStr = dateFmt.format(d);
	%>
	<tr>
	<td><%=i+1 %></td>
	<td>
    <a href="ls.jsp?path=${encoderPath}/<%=URLEncoder.encode(fileName) %>"><%=fileName %></a>
    </td>
    <td>&nbsp;</td>
    <td>文件夹</td>
	<td nowrap><%=dateStr %></td>
	</tr>
	<%} %>
	
	<%
	int j = i;
	for(i = 0; i < files.length; i++){
		File f = files[i];
		String fileName = f.getName();
		Date d = new Date(f.lastModified());
		String dateStr = dateFmt.format(d);
		long fileLength = f.length();
		
		String sufix = "";
		int lastDotIndex = fileName.lastIndexOf(".");
		if(lastDotIndex > -1)
			sufix = fileName.substring(fileName.lastIndexOf(".")+1);
		
	%>
	<tr>
		<td><%=i+1+j %></td>
		<td>
	    	<%=fileName %>
	    </td>
	    <td nowrap><%=formatFileSize(fileLength) %></td>
	    <td><%=sufix %></td>
	    <td nowrap><%=dateStr %></td>
	</tr>
	<%} %>
</table> 
</body>
</html>

