package sample.entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

public class ActorEntity {
	private int actorId;
	private String firstName;
	private String lastName;
	@JsonFormat(pattern="yyyy/MM/dd HH:mm:ss.SSS")
	private Date lastUpdate;
	public int getActorId() {
		return actorId;
	}
	public void setActorId(int actorId) {
		this.actorId = actorId;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastNmae) {
		this.lastName = lastNmae;
	}
	public Date getLastUpdate() {
		return lastUpdate;
	}
	public void setLastUpdate(Date lastUpdate) {
		this.lastUpdate = lastUpdate;
	}
}
