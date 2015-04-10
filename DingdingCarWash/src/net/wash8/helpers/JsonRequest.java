package net.wash8.helpers;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonObjectRequest;
import org.json.JSONObject;

public class JsonRequest extends JsonObjectRequest{
	
	//private String authorization;

	public JsonRequest(int method, String url, JSONObject jsonRequest,
			Listener<JSONObject> listener, ErrorListener errorListener) {
		super(method, url, jsonRequest, listener, errorListener);
	}
	
	

	@Override
	public byte[] getBody() {
		// TODO Auto-generated method stub
		return super.getBody();
	}



	@Override
	protected VolleyError parseNetworkError(VolleyError volleyError) {
		// TODO Auto-generated method stub
		return super.parseNetworkError(volleyError);
	}



	@Override
	protected Response<JSONObject> parseNetworkResponse(NetworkResponse response) {
		
		if(response.data == null || response.data.length == 0){
			return Response.success(new JSONObject(), HttpHeaderParser.parseCacheHeaders(response));
		}
		else if("null".equals(new String(response.data))){
			return Response.success(new JSONObject(), HttpHeaderParser.parseCacheHeaders(response));
		}
		return super.parseNetworkResponse(response);
	}



/*	@Override
	public Map<String, String> getHeaders() throws AuthFailureError {
		Map<String, String> headers = new HashMap<String, String>();  
//		headers.put("Charsert", "UTF-8");  
//		headers.put("Accept", "text/plain");
//		headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");  
//		headers.put("Accept-Encoding", "gzip,deflate");  
		if(authorization != null)
			headers.put("Authorization", authorization);
		return headers;
	} */
}
