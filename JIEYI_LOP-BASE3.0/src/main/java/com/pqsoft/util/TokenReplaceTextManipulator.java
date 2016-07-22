package com.pqsoft.util;

import java.util.Collections;
import java.util.Map;
@SuppressWarnings("unchecked")
public class TokenReplaceTextManipulator
{
	private String startToken = "{";
	private String endToken = "}";
	private String nullValue = "";
	/**
	 * @see net.mlw.vlh.adapter.util.TextManipulator#manipulate(java.lang.String, java.lang.Object)
	 */
	public StringBuffer manipulate(StringBuffer text, Map model)
	{
		if (model == null)
		{
			model = Collections.EMPTY_MAP;
		}

		//Replace any [key] with the value in the whereClause Map.
		for (int i = 0, end = 0, start; ((start = text.toString().indexOf(startToken, end)) >= 0); i++)
		{
			end = text.toString().indexOf(endToken, start);
			String key = text.substring(start + 1, end);

			Object modelValue = model.get(key);
			text.replace(start, end + 1, (modelValue == null) ? nullValue : modelValue.toString());
			end -= (key.length() + 2);
		}

		return text;
	}

	/**
	 * @param endToken The endToken to set.
	 */
	public void setEndToken(String endToken)
	{
		this.endToken = endToken;
	}

	/**
	 * @param startToken The startToken to set.
	 */
	public void setStartToken(String startToken)
	{
		this.startToken = startToken;
	}
	
	/**
	 * @param nullValue The nullValue to set.
	 */
	public void setNullValue(String nullValue)
	{
		this.nullValue = nullValue;
	}
}

