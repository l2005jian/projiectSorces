package com.sinaleju.lifecircle.app.model.json;

import java.util.LinkedList;
import java.util.List;

import org.json.JSONException;

import com.sinaleju.lifecircle.app.model.Model_TopicDetail;
import com.sinaleju.lifecircle.app.model.impl.MultiModelBase;
import com.sinaleju.lifecircle.app.model.impl.MultiModelsSet;

public class JSONParser_TopicDetailTop implements MultiJSONParserBase<MultiModelBase>{

	@Override
	public List<MultiModelBase> parseJSON(String json, MultiModelsSet set)
			throws JSONException {
		List<MultiModelBase> data = new LinkedList<MultiModelBase>();
		Model_TopicDetail m = new Model_TopicDetail();
		data.add(m);
		return data;
	}

}
