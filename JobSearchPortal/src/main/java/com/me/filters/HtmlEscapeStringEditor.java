package com.me.filters;

import java.beans.PropertyEditorSupport;

import org.springframework.web.util.HtmlUtils;

public class HtmlEscapeStringEditor extends PropertyEditorSupport {
	@Override
	public void setAsText(String text) throws IllegalArgumentException {
		String x = "";
		if (text != null)
			x = HtmlUtils.htmlEscape(text.trim());

		setValue(x);
	}

	@Override
	public String getAsText() {
		String x = (String) getValue();
		if (x == null)
			x = "";
		return x;
	}

}
