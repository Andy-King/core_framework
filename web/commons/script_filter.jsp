<%@page import="java.net.URLDecoder,java.util.*"%>
<%
	
	List<String> keys = new ArrayList<String>();
	keys.add("<");
	keys.add(">");
	keys.add("select");
	keys.add("\"");
	keys.add("'");
	keys.add("delete");
	keys.add("from");
	keys.add("where");
	keys.add("script");
	keys.add("alert");
	keys.add("onclick");
	keys.add(" and ");
	keys.add(" or ");
	keys.add("%0d%0a");
	keys.add("%0a%20");
	keys.add("\n");
	String[] jsStr = new String[]{"javascript", "vbscript", "expression", "applet", "meta", "blink", "script", "embed", "object", "iframe", 
			"frame", "frameset", "ilayer", "layer", "bgsound", "base"};
	String[] jsStr1 = new String[]{"onabort", "onactivate", "onafterprint", "onafterupdate", "onbeforeactivate", "onbeforecopy", "onbeforecut", 
			"onbeforedeactivate", "onbeforeeditfocus", "onbeforepaste", "onbeforeprint", "onbeforeunload", "onbeforeupdate", "onblur", "onbounce",
			"oncellchange", "onchange", "onclick", "oncontextmenu", "oncontrolselect", "oncopy", "oncut", "ondataavailable", "ondatasetchanged", 
			"ondatasetcomplete", "ondblclick", "ondeactivate", "ondrag", "ondragend", "ondragenter", "ondragleave", "ondragover", "ondragstart", 
			"onmovestart", "onpaste", "onpropertychange", "onreadystatechange", "onreset", "onresize", "onresizeend", "onresizestart", "onrowenter",
			"onrowexit", "onrowsdelete", "onrowsinserted", "onscroll", "onselect", "onselectionchange", "onselectstart", "onstart", "onstop", "onsubmit", 
			"onunload","ondrop", "onerror", "onerrorupdate", "onfilterchange", "onfinish", "onfocus", "onfocusin", "onfocusout", "onhelp", "onkeydown",
			"onkeypress", "onkeyup", "onlayoutcomplete", "onload", "onlosecapture", "onmousedown", "onmouseenter", "onmouseleave", "onmousemove",
			"onmouseout", "onmouseover", "onmouseup", "onmousewheel", "onmove", "onmoveend","ondbclick"};
	keys.addAll(java.util.Arrays.asList(jsStr));
	keys.addAll(java.util.Arrays.asList(jsStr1));
	String method_str = request.getMethod();
	
	if (method_str.equals("POST") ) {
		Enumeration   enus=request.getParameterNames(); 
        while(enus.hasMoreElements()){ 
                String   paraName=(String)enus.nextElement(); 
                String paraNameValue = request.getParameter(paraName);
                for (String key : keys) {
        			if (paraNameValue.contains(key)) {
        				response.setStatus(500);
        				return;
        			}
        		}
        }
	} else {
		String jqString = request.getQueryString() == null ? "":request.getQueryString().replace("*","");
		String exString = request.getRequestURI() == null ? "":request.getRequestURI().replace("*","");
		String jqCodeStr = URLDecoder.decode(jqString).toLowerCase()+ URLDecoder.decode(exString).toLowerCase();
		jqString = jqString.toLowerCase() + exString.toLowerCase();
		for (String key : keys) {
			
			if (jqString.contains(key) || jqCodeStr.contains(key)) {
				response.setStatus(500);
				return;
			}
		}
	}

%>
