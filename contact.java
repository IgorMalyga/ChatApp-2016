

import java.io.Serializable;

public class contact implements Serializable{
  private String name;
  private String ip;
  public contact(String name, String ip){
	  this.ip=ip;
	  this.name=name;
  }
public String getName() {
	return name;
}
public String getIp(){
      return ip.toString();
}
public void setName(String name) {
	this.name = name;
}
}
