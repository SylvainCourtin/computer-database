package com.excilys.computerdatabase.tags;

import java.io.IOException;
import java.time.LocalDate;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.SimpleTagSupport;

import com.excilys.computerdatabase.utils.DateUtil;

public class TagDate extends SimpleTagSupport {
	
	private String sDate="";
	private LocalDate localDate=null;
	
	public void setLocalDate(LocalDate localDate) {
		this.localDate = localDate;
	}
	
	public void setsDate(String sDate) {
		this.sDate = sDate;
	}
	
	public void doTag() {
		PageContext pageContext = (PageContext) getJspContext();
		JspWriter out = pageContext.getOut();
		
		StringBuffer stringBuffer = new StringBuffer();
		
		if(localDate!= null)
		{
			stringBuffer.append(DateUtil.formatDateToString(localDate));
		}
		else if(sDate != null && !sDate.equals(""))
		{
			stringBuffer.append(DateUtil.stringToDateInv(sDate).toString());
		}
		try {
			out.println(stringBuffer.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
