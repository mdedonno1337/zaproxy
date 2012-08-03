/*
 * Zed Attack Proxy (ZAP) and its related class files.
 * 
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package org.zaproxy.zap.extension.httpsessions;

import java.util.HashMap;
import java.util.Map;

import org.parosproxy.paros.network.HtmlParameter;

/**
 * The Class HttpSession defines the data that is stored regarding an existing HTTP session on a
 * particular site.
 */
public class HttpSession {

	/** The name. */
	private String name;

	/** Whether it is active. */
	private boolean active;

	/** The session tokens' values for this session. */
	private HashMap<String, String> tokenValues;

	/**
	 * Instantiates a new http session.
	 * 
	 * @param name the name
	 */
	public HttpSession(String name) {
		super();
		this.name = name;
		this.active = false;
		this.tokenValues = new HashMap<String, String>(1);
	}

	/**
	 * Gets the name.
	 * 
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * Sets the name.
	 * 
	 * @param name the new name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Checks if it is active.
	 * 
	 * @return true, if is active
	 */
	public boolean isActive() {
		return active;
	}

	/**
	 * Sets whether it is active.
	 * 
	 * @param active the new active
	 */
	public void setActive(boolean active) {
		this.active = active;
	}

	/**
	 * Sets a particular value for a session token. If the value is null, that token is deleted from
	 * the session.
	 * 
	 * @param tokenName the token name
	 * @param value the new value of the token, or null, if the token has to be deleted
	 */
	public void setTokenValue(String tokenName, String value) {
		if (value == null)
			tokenValues.remove(tokenName);
		else
			tokenValues.put(tokenName, value);
	}

	/**
	 * Checks if a particular cookie has the same value as one of the token values in the HTTP
	 * session.
	 * 
	 * @param cookie the cookie
	 * @return true, if true
	 */
	public boolean matchesCookie(HtmlParameter cookie) {
		String tokenValue = tokenValues.get(cookie.getName().toLowerCase());
		if (tokenValue != null && tokenValue.equals(cookie.getValue()))
			return true;
		return false;
	}

	@Override
	public String toString() {
		return "HttpSession [name=" + name + ", active=" + active + ", tokenValues=" + getTokenValuesString() + "]";
	}

	/**
	 * Gets the token values string representation.
	 * 
	 * @return the token values string
	 */
	public String getTokenValuesString() {
		if (tokenValues.isEmpty())
			return "";
		StringBuilder buf = new StringBuilder();

		for (Map.Entry<String, String> entry : tokenValues.entrySet())
			buf.append(entry.getKey() + "=" + entry.getValue() + ";");
		buf.deleteCharAt(buf.length() - 1);

		return buf.toString();

	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		HttpSession other = (HttpSession) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}

}
