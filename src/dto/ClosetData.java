package dto;

public class ClosetData {
	String key = null;
	int count = -1;

	public ClosetData(String key, int count){
		this.key = key;
		this.count = count;
	}

	public String getKey() {
		return key;
	}

	public int getCount() {
		return count;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setCount(int count) {
		this.count = count;
	}
}
