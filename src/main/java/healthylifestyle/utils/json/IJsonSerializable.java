package healthylifestyle.utils.json;

import java.io.InputStream;
import java.io.Reader;
import java.io.Serializable;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;

/**
 * 這個介面存在的目的是讓某些不能簡單地直接將本身序列化為Json的物件設計的。因為該物件的設計可能不符合Java bean的規範。<br>
 * 因此這個函數會回傳一個「代理」來替這個物件做Json序列化<br>
 * 當然如果本身就滿足Java bean規範的話，也能直接回傳自己。<br>
 * (PS:應該早該這樣設計才對...)<br>
 * T表示代理物件的型態
 * */
public interface IJsonSerializable<T extends Serializable> {

	public static String listToJson(List<? extends IJsonSerializable<?>> l, IJsonUtilsWrapper<?> wrapper){
		Object[] l2 = l.stream().map(e -> e.getObjectForJsonSerialize()).toArray();
		return wrapper.objectToJson(l2).orElse("[]");
	}
	
	public static String listToJson(List<? extends IJsonSerializable<?>> l){
		return listToJson(l,IJsonUtilsWrapper.DEFAULT_WRAPPER);
	}
	
	@JsonIgnore
	public Object getObjectForJsonSerialize();
	
	@JsonIgnore
	public Class<T> getProxyClass();
	
	/**
	 * 回傳一個建構好的代理物件來建構自己
	 * 需要注意的是，就算代理物件的型態和自身完全相同，代理物件和自身仍然算是不同的物件
	 * 因此需要一個一個從代理物件上取值出來存入自身中。
	 * */
	public void constructWithProxy(T proxy);
	
	
	default public String toJson(IJsonUtilsWrapper<?> wrapper) {
		return wrapper.objectToJson(this.getObjectForJsonSerialize()).orElse("{}");
	}
	
	default public String toJson() throws JsonProcessingException {
		return toJson(IJsonUtilsWrapper.DEFAULT_WRAPPER);
	}
	
	default public void constructWithJson(IJsonUtilsWrapper<?> wrapper, String json) {
		this.constructWithProxy(wrapper.<T>JsonToObject(json, getProxyClass()).orElse(null));
	}
	
	default public void constructWithJson(String json){
		this.constructWithJson(IJsonUtilsWrapper.DEFAULT_WRAPPER, json);
	}
	
	default public void constructWithJson(IJsonUtilsWrapper<?> wrapper, InputStream jsoninput) {
		this.constructWithProxy(wrapper.<T>JsonToObject(jsoninput, getProxyClass()).orElse(null));
	}
	
	default public void constructWithJson(InputStream jsoninput) {
		this.constructWithJson(IJsonUtilsWrapper.DEFAULT_WRAPPER, jsoninput);
	}
	
	default public void constructWithJson(IJsonUtilsWrapper<?> wrapper, Reader jsoninput) {
		this.constructWithProxy(wrapper.<T>JsonToObject(jsoninput, getProxyClass()).orElse(null));
	}
	
	default public void constructWithJson(Reader jsoninput) {
		this.constructWithJson(IJsonUtilsWrapper.DEFAULT_WRAPPER, jsoninput);
	}
}
