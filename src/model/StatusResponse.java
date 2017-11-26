package model;

public class StatusResponse {
	private boolean done;
	private String messaggio;
	
	public StatusResponse(boolean done, String messaggio) {
		this.done = done;
		this.messaggio = messaggio;
	}
	public boolean isDone() {
		return done;
	}
	public void setDone(boolean done) {
		this.done = done;
	}
	public String getMessaggio() {
		return messaggio;
	}
	public void setMessaggio(String messaggio) {
		this.messaggio = messaggio;
	}
	
}
