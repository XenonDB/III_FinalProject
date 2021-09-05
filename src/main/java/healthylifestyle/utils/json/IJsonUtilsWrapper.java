package healthylifestyle.utils.json;

import java.io.InputStream;
import java.io.Reader;
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
				throw new JsonSerializeException(e);
			}
		}
		
		@Override
		public <T> Optional<T> JsonToObject(String json, Class<T> objCls) {
			try {
				return Optional.ofNullable(ow.readValue(json, objCls));
			} catch (JsonProcessingException e) {
				throw new JsonDeserializeException(e);
			}
		}

		@Override
		public <T> Optional<T> JsonToObject(InputStream jsoninput, Class<T> objCls) {
			try {
				return Optional.ofNullable(ow.readValue(jsoninput, objCls));
			} catch (Exception e) {
				throw new JsonDeserializeException(e);
			}
		}

		@Override
		public <T> Optional<T> JsonToObject(Reader jsoninput, Class<T> objCls) {
			try {
				return Optional.ofNullable(ow.readValue(jsoninput, objCls));
			} catch (Exception e) {
				throw new JsonDeserializeException(e);
			}
		}
	};
	
	public Optional<String> objectToJson(Object o);
	
	public <T> Optional<T> JsonToObject(String json, Class<T> objCls);
	
	public <T> Optional<T> JsonToObject(InputStream jsoninput, Class<T> objCls);
	
	public <T> Optional<T> JsonToObject(Reader jsoninput, Class<T> objCls);
	
}
