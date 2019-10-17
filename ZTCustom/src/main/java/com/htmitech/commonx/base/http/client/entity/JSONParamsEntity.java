/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.htmitech.commonx.base.http.client.entity;

import org.apache.http.entity.StringEntity;

import java.io.UnsupportedEncodingException;

/**
 * Author: wyouflf Date: 13-7-26 Time: 下午4:21
 */
public class JSONParamsEntity extends StringEntity implements Cloneable {
	public JSONParamsEntity(String json) throws UnsupportedEncodingException {
		this(json, null);
	}

	public JSONParamsEntity(String json, String charset)
			throws UnsupportedEncodingException {
		super(json, charset);
		setContentType("application/json");
	}
}