package dto;

import java.sql.Timestamp;

public class GearTable {
	private int id;
	private String type = null;
	private String color = null;
	private String makers = null;
	private String image = null;
	private Timestamp date_create = null;
	private Timestamp date_update = null;

	public GearTable(int id,String type,String color,String makers,String image,Timestamp date_create,Timestamp date_update) {
		this.id = id;
		this.type = type;
		this.color = color;
		this.makers = makers;
		this.image = image;
		this.date_create = date_create;
		this.date_update = date_update;
	}

	public int getId() {
		return id;
	}
	public String getType() {
		return type;
	}
	public String getColor() {
		return color;
	}
	public String getMakers() {
		return makers;
	}
	public String getImage() {
		return image;
	}
	public Timestamp getDate_create() {
		return date_create;
	}
	public Timestamp getDate_update() {
		return date_update;
	}

	public void setId(int id) {
		this.id = id;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setColor(String color) {
		this.color = color;
	}
	public void setMakers(String makers) {
		this.makers = makers;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public void setDate_create(Timestamp date_create) {
		this.date_create = date_create;
	}
	public void setDate_update(Timestamp date_update) {
		this.date_update = date_update;
	}


}
