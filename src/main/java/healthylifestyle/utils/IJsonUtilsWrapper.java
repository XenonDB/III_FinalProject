package healthylifestyle.utils;

import java.util.Optional;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public interface IJsonUtilsWrapper {

	public static final IJsonUtilsWrapper DEFAULT_WRAPPER = new IJsonUtilsWrapper() {
		
		ObjectMapper ow = new ObjectMapper();
		
		@Override
		public Optional<String> objectToJson(Object o){
			try {
				return Optional.ofNullable(ow.writeValueAsString(o));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return Optional.empty();
			}
		}
		
		@Override
		public <T> Optional<T> JsonToObject(String json, Class<T> objCls) {
			try {
				return Optional.ofNullable(ow.readValue(json, objCls));
			} catch (JsonProcessingException e) {
				e.printStackTrace();
				return Optional.empty();
			}
		}
	};
	
	
	
	public Optional<String> objectToJson(Object o);
	
	public <T> Optional<T> JsonToObject(String json, Class<T> objCls);
	
}
