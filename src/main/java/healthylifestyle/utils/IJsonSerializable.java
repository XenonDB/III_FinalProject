package healthylifestyle.utils;

import java.util.List;

public interface IJsonSerializable {

	public static String listToJson(List<? extends IJsonSerializable> l, IJsonUtilsWrapper wrapper) {
		Object[] l2 = l.stream().map(e -> e.getObjectForJsonSerialize()).toArray();
		return wrapper.objectToJson(l2).orElse("[]");
	}
	
	public static String listToJson(List<? extends IJsonSerializable> l) {
		return listToJson(l,IJsonUtilsWrapper.DEFAULT_WRAPPER);
	}
	
	default public String toJson(IJsonUtilsWrapper wrapper) {
		return wrapper.objectToJson(this.getObjectForJsonSerialize()).orElse("{}");
	}
	
	/**
	 * 這個介面存在的目的是讓某些不能簡單地直接將本身序列化為Json的物件設計的。因為該物件的設計可能不符合Java bean的規範。
	 * 因此這個函數會回傳一個「代理」來替這個物件做Json序列化
	 * 當然如果本身就滿足Java bean規範的話，也能直接回傳自己。
	 * (PS:應該早該這樣設計才對...)
	 * */
	public Object getObjectForJsonSerialize();
	
	default public String toJson() {
		return toJson(IJsonUtilsWrapper.DEFAULT_WRAPPER);
	}
	
}
