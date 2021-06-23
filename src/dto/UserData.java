package dto;

public class UserData {
	private String id = null;
	private String pass = null;
	private String name = null;

	public UserData(String id, String pass, String name){
		this.id = id;
		this.pass = pass;
		this.name = name;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return this.id;
	}

	public String getPass() {
		return this.pass;
	}

	public String getName() {
		return this.name;
	}
}


