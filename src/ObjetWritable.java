	import java.io.DataOutput;
	import java.io.DataInput;
	import java.io.IOException;

import org.apache.hadoop.io.Writable;
	public class ObjetWritable implements Writable 
	{
	   private int age;
	   private int arron;
	   public ObjetWritable(){}
	  public ObjetWritable(int age,int arron){
	  	this.setAge(age);
	  	this.setArron(arron);
	  }
	 @Override
	public void write(DataOutput sortie) throws IOException {
	sortie.writeInt(arron);
	sortie.writeInt(age);
	}
	 @Override
	public void readFields(DataInput entree) throws IOException {
	this.arron=entree.readInt();
	this.age=entree.readInt();
	}
	public String toString() {
	return "age : "+getAge()+" arron : "+getArron();
	}
	public int getArron() {
		return arron;
	}
	public void setArron(int arron) {
		this.arron = arron;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	}

